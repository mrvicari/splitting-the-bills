package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class TenantController
{
    @Autowired
    private TenantService tenantService;

    @PostMapping("/register")
    public void createTenant(@RequestBody Tenant tenant)
    {
        tenantService.createTenant(tenant);
    }

    @GetMapping("/tenant")
    public Tenant getTenant()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return tenantService.getTenant(email);
    }

    @PutMapping("/tenant")
    public void editTenant(@RequestBody Tenant tenant)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        tenantService.editTenant(email, tenant);
    }
}
