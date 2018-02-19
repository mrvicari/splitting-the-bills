package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.Payment;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController
{
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment")
    public void createPayment(@RequestBody Payment payment)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        paymentService.createPayment(payment, email);
    }

    @PostMapping("/payment/settle")
    public void settlePayment(@RequestBody Tenant tenant)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        paymentService.settlePayment(tenant, email);
    }
}
