package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.service.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for the management of endpoints related to Tenants
 */
@RestController
@CrossOrigin
@Api(value = "Tenant", description = "Operations about tenants", tags = { "Tenant" })
public class TenantController
{
    /**
     * Service for business logic regarding Tenants
     */
    private TenantService tenantService;

    /**
     * Constructor to inject service dependencies
     * @param tenantService service for business logic regarding Tenants
     */
    public TenantController(TenantService tenantService)
    {
        this.tenantService = tenantService;
    }

    /**
     * Process request for creating a Tenant
     * @param tenant Tenant object passed in HTTP request body
     */
    @PostMapping("/tenant/register")
    @ApiOperation(value = "Register a tenant")
    public void createTenant(@RequestBody Tenant tenant) throws Exception
    {
        tenantService.createTenant(tenant);
    }

    /**
     * Process request for getting a Tenant
     * @return Tenant object found
     */
    @GetMapping("/tenant")
    @ApiOperation(value = "Get tenant")
    public Tenant getTenant()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return tenantService.getTenant(email);
    }

    /**
     * Process request for editing a Tenant
     * @param tenant Tenant with updated values
     */
    @PutMapping("/tenant")
    @ApiOperation(value = "Edit tenant")
    public void editTenant(@RequestBody Tenant tenant) throws Exception
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        tenantService.editTenant(email, tenant);
    }
}
