package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class HouseController
{
    @Autowired
    private HouseService houseService;

    @CrossOrigin(origins = "http://splitting-the-bills.miguelrv.c9users.io:8081")
    @GetMapping("/house")
    public House getCurrentTenantHouse()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return houseService.getCurrentTenantHouse(email);
    }

    @PostMapping("/house")
    public void createHouse(@RequestBody House house)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        houseService.createHouse(house, email);
    }

    @PutMapping("/house/{houseName}/join")
    public void joinHouse(@PathVariable String houseName)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        houseService.joinHouse(houseName, email);
    }
}
