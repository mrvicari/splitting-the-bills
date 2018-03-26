package com.mrvicari.splittingthebills.config;

import com.mrvicari.splittingthebills.model.*;
import com.mrvicari.splittingthebills.repository.BillRepository;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.PaymentRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.Date;

@Configuration
@EnableScheduling
public class ScheduleConfig
{
    private BillRepository billRepository;
    private TenantRepository tenantRepository;
    private PaymentRepository paymentRepository;
    private HouseRepository houseRepository;

    public ScheduleConfig(BillRepository billRepository,
                          TenantRepository tenantRepository,
                          PaymentRepository paymentRepository,
                          HouseRepository houseRepository)
    {
        this.billRepository = billRepository;
        this.tenantRepository = tenantRepository;
        this.paymentRepository = paymentRepository;
        this.houseRepository = houseRepository;
    }

//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedRate = 10000)
    public void updateBills()
    {
        for (Bill bill : billRepository.findAll())
        {
            Date datePlusPeriod = bill.getNextDate();
            Date today = new Date();

            if (datePlusPeriod.before(today) || datePlusPeriod.equals(today))
            {
                Tenant tenant = bill.getTenant();
                House house = tenant.getHouse();

                int numOfTenants = house.getTenants().size();
                double billAmount = bill.getAmount();

                for (Tenant t : house.getTenants())
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

                bill.setDate(today);

                Calendar cal = Calendar.getInstance();
                cal.setTime(today);
                cal.add(Calendar.MONTH, bill.getPeriod());
                Date nextDate = cal.getTime();

                bill.setNextDate(nextDate);

                billRepository.save(bill);

                createPayment(bill.getName(), today, billAmount, tenant, house);
            }
        }
    }

    private void createPayment(String billName, Date today, Double billAmount, Tenant payer, House house)
    {
        Payment payment = new Payment();
        payment.setName(billName);
        payment.setDate(today);
        payment.setAmount(billAmount);
        payment.setPaymentType(PaymentType.BILL);
        payment.setPayer(payer);

        for (Tenant t : house.getTenants())
        {
            if (!t.getId().equals(payer.getId()))
            {
                payment.getTenants().add(t);
            }
        }

        paymentRepository.save(payment);

        house.getPayments().add(payment);
        houseRepository.save(house);
    }
}
