package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.Bill;
import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.stereotype.Service;

/**
 * Service class containing business logic related to Houses
 */
@Service
public class HouseService
{
    /**
     * Repository for database interaction regarding Houses
     */
    private HouseRepository houseRepository;

    /**
     * Repository for database interaction regarding Tenants
     */
    private TenantRepository tenantRepository;

    /**
     * Constructor to inject repository dependencies
     * @param houseRepository repository for database interaction regarding Houses
     * @param tenantRepository repository for database interaction regarding Tenants
     */
    public HouseService(HouseRepository houseRepository, TenantRepository tenantRepository)
    {
        this.houseRepository = houseRepository;
        this.tenantRepository = tenantRepository;
    }

    /**
     * Retrieve the House of the Tenant sending the request
     * @param email email address of the Tenant sending the request
     * @return House object found
     */
    public House getCurrentTenantHouse(String email)
    {
        Tenant tenant = tenantRepository.findByEmail(email);

        return houseRepository.findHouseByTenantsContains(tenant);
    }

    /**
     * Save House object and assign requesting Tenant to it
     * @param house House object passed in through HTTP request
     * @param email email address of the Tenant sending the request
     */
    public void createHouse(House house, String email)
    {
        Tenant tenant = tenantRepository.findByEmail(email);

        house.getTenants().add(tenant);
        houseRepository.save(house);

        House h = houseRepository.findOne(house.getId());
        tenant.setHouse(h);
        tenantRepository.save(tenant);
    }

    /**
     * Edit a House's fields based on incoming data
     * @param email email address of the Tenant sending the request
     * @param editedHouse House with updated values
     */
    public void editHouse (String email, House editedHouse)
    {
        House house = tenantRepository.findByEmail(email).getHouse();

        house.setName(editedHouse.getName());
        house.setKeyphrase(editedHouse.getKeyphrase());
        house.setNameKeyphrase(editedHouse.getNameKeyphrase());

        houseRepository.save(house);
    }

    /**
     * Add a Tenant to a House's list of Tenants
     * @param houseNameKeyphrase name and keyphrase House identifier
     * @param email email address of the Tenant sending the request
     * @throws Exception house with name and keyphrase combination not found
     */
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

    /**
     * Remove a Tenant from a House's list of Tenants
     * @param email email address of the Tenant sending the request
     * @throws Exception Tenant is not allowed to leave the house
     */
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
