package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.Payment;
import com.mrvicari.splittingthebills.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@Api(value = "Payment", description = "Operations about payments", tags = { "Payment" })
public class PaymentController
{
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService)
    {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    @ApiOperation(value = "Create a payment")
    public void createPayment(@RequestBody Payment payment)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        paymentService.createPayment(payment, email);
    }
}
