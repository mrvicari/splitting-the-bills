package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Payment;
import com.mrvicari.splittingthebills.model.PaymentType;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.PaymentRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PaymentService
{
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private HouseRepository houseRepository;

    public void createPayment(Payment payment, String email)
    {
        Tenant payer = tenantRepository.findByEmail(email);
        payment.setPayer(payer);

        if (payment.getPaymentType().equals(PaymentType.SPLIT))
        {
            int numOfTenants = payment.getTenants().size() + 1;
            double billAmount = payment.getAmount();

            payer.setBalance(payer.getBalance() + billAmount - billAmount/numOfTenants);

            for (Tenant tPayment : payment.getTenants())
            {
                Tenant t = tenantRepository.findOne(tPayment.getId());

                t.setBalance(t.getBalance() - billAmount/numOfTenants);

                tenantRepository.save(t);
            }
        }
        else if (payment.getPaymentType().equals(PaymentType.SETTLE))
        {
            Tenant payee = tenantRepository.findByEmail(payment.getTenants().get(0).getEmail());

            double payerBalance = payer.getBalance();
            double payeeBalance = payee.getBalance();

            double paymentAmount = Math.min(Math.abs(payerBalance), Math.abs(payeeBalance));

            payer.setBalance(payerBalance + paymentAmount);
            payee.setBalance(payeeBalance - paymentAmount);

            tenantRepository.save(payer);
            tenantRepository.save(payee);

            payment.setAmount(paymentAmount);
        }

        payment.setDate(new Date());
        paymentRepository.save(payment);
        House house = payer.getHouse();
        house.getPayments().add(payment);
        houseRepository.save(house);
    }

    public void settlePayment(Tenant tenant, String email)
    {
        Tenant payer = tenantRepository.findByEmail(email);
        Tenant payee = tenantRepository.findByEmail(tenant.getEmail());

        double payerBalance = payer.getBalance();
        double payeeBalance = payee.getBalance();

        double paymentAmount = Math.min(Math.abs(payerBalance), Math.abs(payeeBalance));

        payer.setBalance(payerBalance + paymentAmount);
        payee.setBalance(payeeBalance - paymentAmount);

        tenantRepository.save(payer);
        tenantRepository.save(payee);
    }
}
