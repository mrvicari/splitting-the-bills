package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TenantService
{
    @Autowired
    private TenantRepository tenantRepository;

    @Bean
    private PasswordEncoder getPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    public Tenant getTenant(String email)
    {
        return tenantRepository.findByEmail(email);
    }

    public void createTenant(Tenant tenant)
    {
        tenant.setEmail(tenant.getEmail().toLowerCase());
        tenant.setPassword(getPasswordEncoder().encode(tenant.getPassword()));
        tenant.setBalance(0.0);

        tenantRepository.save(tenant);
    }

    public void editTenant(String email, Tenant editedTenant)
    {
        Tenant tenant = tenantRepository.findByEmail(email);

        tenant.setName(editedTenant.getName());
        tenant.setEmail(editedTenant.getEmail());
        if (editedTenant.getPassword() != null && !editedTenant.getPassword().isEmpty())
        {
            tenant.setPassword(getPasswordEncoder().encode(editedTenant.getPassword()));
        }

        tenantRepository.save(tenant);
    }
}
