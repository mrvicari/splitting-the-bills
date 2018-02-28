package com.mrvicari.splittingthebills.repository;

import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends CrudRepository<House, Integer>
{
    House findHouseByNameKeyphrase(String nameKeyphrase);

    House findHouseByTenantsContains(Tenant tenant);
}
