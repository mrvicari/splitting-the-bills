package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.Bill;
import com.mrvicari.splittingthebills.service.BillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Api(value = "Bill", description = "Operations about bills", tags = { "Bill" })
public class BillController
{
    @Autowired
    private BillService billService;

    @PostMapping("/bill")
    @ApiOperation(value = "Create a bill")
    public void createBill(@RequestBody Bill bill)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        billService.createBill(bill, email);
    }
}
