package com.mrvicari.splittingthebills.repository;

import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for database interaction regarding Houses
 */
@Repository
public interface HouseRepository extends CrudRepository<House, Integer>
{
    /**
     * Retrieve a House given a name and keyphrase combination
     * @param nameKeyphrase name and keyphrase combination to search
     * @return House object found
     */
    House findHouseByNameKeyphrase(String nameKeyphrase);

    /**
     * Retrieve a House given a Tenant that belongs to it
     * @param tenant Tenant to search
     * @return House object found
     */
    House findHouseByTenantsContains(Tenant tenant);

    /**
     * Retrieve a house give a unique code
     * @param code code to search
     * @return House object found
     */
    House findHouseByCode(String code);
}
