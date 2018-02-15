package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.Payment;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.PaymentRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService
{
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TenantRepository tenantRepository;

    public void createPayment(Payment payment, String email)
    {
        Tenant tenant = tenantRepository.findByEmail(email);
        payment.setPayer(tenant);
        paymentRepository.save(payment);

        int numOfTenants = payment.getTenants().size() + 1;
        double billAmount = payment.getAmount();

        tenant.setBalance(tenant.getBalance() + billAmount - billAmount/numOfTenants);

        for (Tenant tPayment : payment.getTenants())
        {
            Tenant t = tenantRepository.findOne(tPayment.getId());

            t.setBalance(t.getBalance() - billAmount/numOfTenants);

            tenantRepository.save(t);
        }
    }

    public List<Payment> getAllPayments()
    {
        return paymentRepository.findAll();
    }
}
