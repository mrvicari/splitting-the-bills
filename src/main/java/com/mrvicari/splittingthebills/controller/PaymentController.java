package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.Payment;
import com.mrvicari.splittingthebills.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for the management of endpoints related to Payments
 */
@RestController
@CrossOrigin
@Api(value = "Payment", description = "Operations about payments", tags = { "Payment" })
public class PaymentController
{
    /**
     * Service for business logic regarding Payments
     */
    private PaymentService paymentService;

    /**
     * Constructor to inject service dependencies
     * @param paymentService service for business logic regarding Payments
     */
    public PaymentController(PaymentService paymentService)
    {
        this.paymentService = paymentService;
    }

    /**
     * Process request for creating a Payment
     * @param payment Payment object passed in HTTP request body
     */
    @PostMapping("/payment")
    @ApiOperation(value = "Create a payment")
    public void createPayment(@RequestBody Payment payment)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        paymentService.createPayment(payment, email);
    }

    /**
     * Process request for deleting a Payment
     * @param paymentId identifier of the Payment to be deleted
     */
    @DeleteMapping("/payment/{paymentId}")
    @ApiOperation(value = "Delete a payment")
    public void deletePayment(@PathVariable Integer paymentId)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        paymentService.deletePayment(email, paymentId);
    }
}
