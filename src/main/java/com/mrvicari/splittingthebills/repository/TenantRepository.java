package com.mrvicari.splittingthebills.repository;

import com.mrvicari.splittingthebills.model.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for database interaction regarding Tenants
 */
@Repository
public interface TenantRepository extends CrudRepository<Tenant, Integer>
{
    /**
     * Retrieve a Tenant given an email address
     * @param email email address to search
     * @return Tenant object found
     */
    Tenant findByEmail(String email);
}
