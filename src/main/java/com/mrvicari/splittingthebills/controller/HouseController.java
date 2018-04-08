package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.service.HouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for the management of endpoints related to Houses
 */
@RestController
@CrossOrigin
@Api(value = "House", description = "Operations about houses", tags = { "House" })
public class HouseController
{
    /**
     * Service for business logic regarding Houses
     */
    private HouseService houseService;

    /**
     * Constructor to inject service dependencies
     * @param houseService service for business logic regarding Houses
     */
    public HouseController(HouseService houseService)
    {
        this.houseService = houseService;
    }

    /**
     * Process request for getting a House
     * @return House object found
     */
    @GetMapping("/house")
    @ApiOperation(value = "Get house")
    public House getCurrentTenantHouse()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return houseService.getCurrentTenantHouse(email);
    }

    /**
     * Process request for creating a House
     * @param house House object passed in HTTP request body
     * @throws Exception house with name and keyphrase combination
     */
    @PostMapping("/house")
    @ApiOperation(value = "Create a house")
    public void createHouse(@RequestBody House house) throws Exception
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        houseService.createHouse(house, email);
    }

    /**
     * Process request for editing a House
     * @param house House with updated values
     */
    @PutMapping("/house")
    @ApiOperation(value = "Edit house")
    public void editHouse(@RequestBody House house)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        houseService.editHouse(email, house);
    }

    /**
     * Process request for joining a House
     * @param houseNameKeyphrase name and keyphrase House identifier
     * @throws Exception house with name and keyphrase combination not found
     */
    @PutMapping("/house/{houseNameKeyphrase}/join")
    @ApiOperation(value = "Join a house")
    public void joinHouse(@PathVariable String houseNameKeyphrase) throws Exception
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        houseService.joinHouse(houseNameKeyphrase, email);
    }

    /**
     * Process request for leaving a House
     * @throws Exception Tenant is not allowed to leace the House
     */
    @PutMapping("/house/leave")
    @ApiOperation(value = "Leave a house")
    public void leaveHouse() throws Exception
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        houseService.leaveHouse(email);
    }
}
