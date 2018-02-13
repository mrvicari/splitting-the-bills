package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TenantController
{
    @Autowired
    private TenantService tenantService;

    @PostMapping("/register")
    public void createTenant(@RequestBody Tenant tenant)
    {
        tenantService.createTenant(tenant);
    }
}
