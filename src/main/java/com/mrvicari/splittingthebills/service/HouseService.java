package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.Bill;
import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.stereotype.Service;

@Service
public class HouseService
{
    private HouseRepository houseRepository;
    private TenantRepository tenantRepository;

    public HouseService(HouseRepository houseRepository, TenantRepository tenantRepository)
    {
        this.houseRepository = houseRepository;
        this.tenantRepository = tenantRepository;
    }

    public House getCurrentTenantHouse(String email)
    {
        Tenant tenant = tenantRepository.findByEmail(email);

        return houseRepository.findHouseByTenantsContains(tenant);
    }

    public void createHouse(House house, String email)
    {
        Tenant tenant = tenantRepository.findByEmail(email);

        house.getTenants().add(tenant);
        houseRepository.save(house);

        House h = houseRepository.findOne(house.getId());
        tenant.setHouse(h);
        tenantRepository.save(tenant);
    }

    public void editHouse (String email, House editedHouse)
    {
        House house = tenantRepository.findByEmail(email).getHouse();

        house.setName(editedHouse.getName());
        house.setKeyphrase(editedHouse.getKeyphrase());
        house.setNameKeyphrase(editedHouse.getNameKeyphrase());

        houseRepository.save(house);
    }

    public void joinHouse(String houseNameKeyphrase, String email) throws Exception
    {
        try
        {
            House house = houseRepository.findHouseByNameKeyphrase(houseNameKeyphrase);
            Tenant tenant = tenantRepository.findByEmail(email);

            house.getTenants().add(tenant);
            houseRepository.save(house);

            tenant.setHouse(house);
            tenantRepository.save(tenant);
        }
        catch (Exception e)
        {
            throw new Exception("House name and keyphrase combination not found");
        }
    }

    public void leaveHouse(String email) throws Exception
    {
        Tenant tenant = tenantRepository.findByEmail(email);
        House house = houseRepository.findHouseByTenantsContains(tenant);

        if (tenant.getBalance() >= 0.1 || tenant.getBalance() <= -0.1)
        {
            throw new Exception("Balance must be 0 before leaving a house");
        }

        for (Bill bill : house.getBills())
        {
            if (bill.getTenant().equals(tenant))
            {
                throw  new Exception("Must not be payer of any bills before leaving house");
            }
        }

        house.getTenants().remove(tenant);
        houseRepository.save(house);

        tenant.setHouse(null);
        tenantRepository.save(tenant);
    }
}
