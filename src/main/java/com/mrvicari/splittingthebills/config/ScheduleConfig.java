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

    @Scheduled(fixedRate = 86400000)
    public void updateBills()
    {
        for (Bill bill : billRepository.findAll())
        {
            int monthsOffset;

            switch (bill.getPeriod())
            {
                case MONTHLY:
                    monthsOffset = 1;
                    break;
                case QUARTERLY:
                    monthsOffset = 3;
                    break;
                case BIANNUALLY:
                    monthsOffset = 6;
                    break;
                case ANNUALLY:
                    monthsOffset = 12;
                    break;
                default:
                    monthsOffset = 9999;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(bill.getDate());
            calendar.add(Calendar.MONTH, monthsOffset);
            Date datePlusPeriod = calendar.getTime();

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
                billRepository.save(bill);
            }
        }
    }
}
