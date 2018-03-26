package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.service.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(value = "Tenant", description = "Operations about tenants", tags = { "Tenant" })
public class TenantController
{
    private TenantService tenantService;

    public TenantController(TenantService tenantService)
    {
        this.tenantService = tenantService;
    }

    @PostMapping("/tenant/register")
    @ApiOperation(value = "Register a tenant")
    public void createTenant(@RequestBody Tenant tenant)
    {
        tenantService.createTenant(tenant);
    }

    @GetMapping("/tenant")
    @ApiOperation(value = "Get tenant")
    public Tenant getTenant()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return tenantService.getTenant(email);
    }

    @PutMapping("/tenant")
    @ApiOperation(value = "Edit tenant")
    public void editTenant(@RequestBody Tenant tenant)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        tenantService.editTenant(email, tenant);
    }
}
