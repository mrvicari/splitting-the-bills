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
        paymentRepository.save(payment);

        Tenant tenant = tenantRepository.findByEmail(email);

        int numOfTenants = payment.getTenants().size();
        double billAmount = payment.getAmount();

        for (Tenant t : payment.getTenants())
        {
            if (t.getId().equals(tenant.getId()))
            {
                t.setBalance(t.getBalance() + billAmount - billAmount/numOfTenants);
            }
            else
            {
                t.setBalance(t.getBalance() - billAmount/numOfTenants);
            }
            tenantRepository.save(t);
        }
    }

    public List<Payment> getAllPayments()
    {
        return paymentRepository.findAll();
    }
}
