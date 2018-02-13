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

    public void createTenant(Tenant tenant)
    {
        tenant.setEmail(tenant.getEmail().toLowerCase());
        tenant.setPassword(getPasswordEncoder().encode(tenant.getPassword()));
        tenant.setBalance(0.0);

        tenantRepository.save(tenant);
    }
}
