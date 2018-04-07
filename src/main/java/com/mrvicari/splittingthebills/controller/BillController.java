package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.Bill;
import com.mrvicari.splittingthebills.service.BillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for the management of endpoints related to Bills
 */
@RestController
@CrossOrigin
@Api(value = "Bill", description = "Operations about bills", tags = { "Bill" })
public class BillController
{
    /**
     * Service for business logic regarding Bills
     */
    private BillService billService;

    /**
     * Constructor to inject service dependencies
     * @param billService service for business logic regarding Bills
     */
    public BillController(BillService billService)
    {
        this.billService = billService;
    }

    /**
     * Process request for creating a Bill
     * @param bill Bill object passed in HTTP request body
     */
    @PostMapping("/bill")
    @ApiOperation(value = "Create a bill")
    public void createBill(@RequestBody Bill bill)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        billService.createBill(bill, email);
    }

    /**
     * Process request for editing a Bill
     * @param bill Bill with updated values
     * @param billId identifier of the Bill to be edited
     */
    @PutMapping("/bill/{billId}")
    @ApiOperation(value = "Edit a bill")
    public void editBill(@RequestBody Bill bill, @PathVariable Integer billId)
    {
        billService.editBill(bill, billId);
    }

    /**
     * Process request for deleting a Bill
     * @param billId identifier of the Bill to be deleted
     */
    @DeleteMapping("/bill/{billId}")
    @ApiOperation(value = "Delete a bill")
    public void deleteBill(@PathVariable Integer billId)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        billService.deleteBill(email, billId);
    }
}
