package com.mrvicari.splittingthebills.repository;

import com.mrvicari.splittingthebills.model.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends CrudRepository<Tenant, Integer>
{
    Tenant findByEmail(String email);
}
