package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class HouseController
{
    @Autowired
    private HouseService houseService;

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

    @PutMapping("/house")
    public void editHouse(@RequestBody House house)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        houseService.editHouse(email, house);
    }

    @PutMapping("/house/{houseNameKeyphrase}/join")
    public void joinHouse(@PathVariable String houseNameKeyphrase) throws Exception
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        houseService.joinHouse(houseNameKeyphrase, email);
    }

    @PutMapping("/house/leave")
    public void leaveHouse() throws Exception
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        houseService.leaveHouse(email);
    }
}
