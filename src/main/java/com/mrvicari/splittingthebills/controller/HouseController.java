package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.service.HouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(value = "House", description = "Operations about houses", tags = { "House" })
public class HouseController
{
    @Autowired
    private HouseService houseService;

    @GetMapping("/house")
    @ApiOperation(value = "Get house")
    public House getCurrentTenantHouse()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return houseService.getCurrentTenantHouse(email);
    }

    @PostMapping("/house")
    @ApiOperation(value = "Create a house")
    public void createHouse(@RequestBody House house)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        houseService.createHouse(house, email);
    }

    @PutMapping("/house")
    @ApiOperation(value = "Edit house")
    public void editHouse(@RequestBody House house)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        houseService.editHouse(email, house);
    }

    @PutMapping("/house/{houseNameKeyphrase}/join")
    @ApiOperation(value = "Join a house")
    public void joinHouse(@PathVariable String houseNameKeyphrase) throws Exception
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        houseService.joinHouse(houseNameKeyphrase, email);
    }

    @PutMapping("/house/leave")
    @ApiOperation(value = "Leave a house")
    public void leaveHouse() throws Exception
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        houseService.leaveHouse(email);
    }
}
