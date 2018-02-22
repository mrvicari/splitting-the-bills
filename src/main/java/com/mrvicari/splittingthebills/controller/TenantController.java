package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TenantController
{
    @Autowired
    private TenantService tenantService;

    @CrossOrigin(origins = "http://splitting-the-bills.miguelrv.c9users.io:8081")
    @PostMapping("/register")
    public void createTenant(@RequestBody Tenant tenant)
    {
        tenantService.createTenant(tenant);
    }
}
