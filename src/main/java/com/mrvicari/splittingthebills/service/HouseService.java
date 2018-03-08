package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService
{
    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private TenantRepository tenantRepository;

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

        if (tenant.getBalance() >= -0.1 && tenant.getBalance() <= 0.1)
        {
            house.getTenants().remove(tenant);
            houseRepository.save(house);

            tenant.setHouse(null);
            tenantRepository.save(tenant);
        }
        else
        {
            throw new Exception("Balance must be 0 before leaving a house");
        }
    }
}
