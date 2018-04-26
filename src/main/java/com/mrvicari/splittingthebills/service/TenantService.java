package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class containing business logic related to Tenants
 */
@Service
public class TenantService
{
    /**
     * Repository for database interaction regarding Tenants
     */
    private TenantRepository tenantRepository;

    /**
     * Constructor to inject repository dependencies
     * @param tenantRepository repository for database interaction regarding Tenants
     */
    public TenantService(TenantRepository tenantRepository)
    {
        this.tenantRepository = tenantRepository;
    }

    /**
     * Create a password encoder for encryption
     * @return new BCryptPasswordEncoder
     */
    @Bean
    private PasswordEncoder getPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * Retrieve a single Tenant's details
     * @param email email of the Tenant sending the request
     * @return Tenant object found
     */
    public Tenant getTenant(String email)
    {
        return tenantRepository.findByEmail(email);
    }

    /**
     * Save Tenant object upon user registration
     * @param tenant Tenant object passed in through HTTP request
     */
    public void createTenant(Tenant tenant) throws Exception
    {
        try
        {
            tenant.setEmail(tenant.getEmail().toLowerCase());
            tenant.setPassword(getPasswordEncoder().encode(tenant.getPassword()));
            tenant.setBalance(0.0);

            tenantRepository.save(tenant);
        }
        catch (Exception e)
        {
            throw new Exception("Email already in use");
        }
    }

    /**
     * Edit a Tenant's fields based on incoming data
     * @param email email of the Tenant sending the request
     * @param editedTenant Tenant with updated values
     */
    public void editTenant(String email, Tenant editedTenant) throws Exception
    {
        Tenant tenant = tenantRepository.findByEmail(email);

        tenant.setName(editedTenant.getName());
        tenant.setEmail(editedTenant.getEmail());
        if (editedTenant.getPassword() != null && !editedTenant.getPassword().isEmpty())
        {
            tenant.setPassword(getPasswordEncoder().encode(editedTenant.getPassword()));
        }

        try
        {
            tenantRepository.save(tenant);
        }
        catch (Exception e)
        {
            throw new Exception("Email already in use");
        }
    }
}
