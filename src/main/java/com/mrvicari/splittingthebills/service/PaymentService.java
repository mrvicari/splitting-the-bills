package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Payment;
import com.mrvicari.splittingthebills.model.PaymentType;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.PaymentRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentService
{
    private PaymentRepository paymentRepository;
    private TenantRepository tenantRepository;
    private HouseRepository houseRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          TenantRepository tenantRepository,
                          HouseRepository houseRepository)
    {
        this.paymentRepository = paymentRepository;
        this.tenantRepository = tenantRepository;
        this.houseRepository = houseRepository;
    }

    public void createPayment(Payment payment, String email)
    {
        Tenant payer = tenantRepository.findByEmail(email);
        payment.setPayer(payer);

        if (payment.getPaymentType().equals(PaymentType.SPLIT))
        {
            int numOfTenants = payment.getTenants().size() + 1;
            double billAmount = payment.getAmount();

            payer.setBalance(payer.getBalance() + billAmount - billAmount/numOfTenants);
            tenantRepository.save(payer);

            for (Tenant tPayment : payment.getTenants())
            {
                Tenant t = tenantRepository.findOne(tPayment.getId());

                t.setBalance(t.getBalance() - billAmount/numOfTenants);

                tenantRepository.save(t);
            }
        }
        else if (payment.getPaymentType().equals(PaymentType.DIRECT))
        {
            Tenant payee = tenantRepository.findByEmail(payment.getTenants().get(0).getEmail());

            payer.setBalance(payer.getBalance() + payment.getAmount());
            payee.setBalance(payee.getBalance() - payment.getAmount());

            tenantRepository.save(payer);
            tenantRepository.save(payee);
        }

        payment.setDate(new Date());
        paymentRepository.save(payment);
        House house = payer.getHouse();
        house.getPayments().add(payment);
        houseRepository.save(house);
    }

    public void deletePayment(String email, Integer paymentId)
    {
        Payment payment = paymentRepository.findOne(paymentId);
        House house = houseRepository.findHouseByTenantsContains(tenantRepository.findByEmail(email));

        if (payment.getPaymentType().equals(PaymentType.SPLIT))
        {
            Tenant payer = payment.getPayer();

            int numOfTenants = payment.getTenants().size() + 1;
            double billAmount = payment.getAmount();

            payer.setBalance(payer.getBalance() - billAmount + billAmount/numOfTenants);
            tenantRepository.save(payer);

            for (Tenant t : payment.getTenants())
            {
                t.setBalance(t.getBalance() + billAmount/numOfTenants);
                tenantRepository.save(t);
            }
        }
        else if (payment.getPaymentType().equals(PaymentType.DIRECT))
        {
            Tenant payer = payment.getPayer();
            Tenant payee = payment.getTenants().get(0);

            payer.setBalance(payer.getBalance() - payment.getAmount());
            payee.setBalance(payee.getBalance() + payment.getAmount());

            tenantRepository.save(payer);
            tenantRepository.save(payee);
        }

        house.getPayments().remove(payment);
        paymentRepository.delete(payment);
    }
}
