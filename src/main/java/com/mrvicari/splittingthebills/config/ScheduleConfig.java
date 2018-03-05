package com.mrvicari.splittingthebills.config;

import com.mrvicari.splittingthebills.model.Bill;
import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.BillRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.Date;

@Configuration
@EnableScheduling
public class ScheduleConfig
{
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private TenantRepository tenantRepository;

//    @Scheduled(fixedRate = 86400000)
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
            }
        }
    }
}
