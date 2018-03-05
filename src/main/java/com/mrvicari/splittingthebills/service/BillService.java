package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.Bill;
import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.BillRepository;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BillService
{
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private HouseRepository houseRepository;

    public void createBill(Bill bill, String email)
    {
        Tenant tenant = tenantRepository.findByEmail(email);

        bill.setTenant(tenant);

        Calendar cal = Calendar.getInstance();
        cal.setTime(bill.getDate());
        cal.add(Calendar.MONTH, bill.getPeriod());
        Date nextDate = cal.getTime();

        bill.setNextDate(nextDate);

        billRepository.save(bill);

        House house = tenant.getHouse();
        house.getBills().add(bill);
        houseRepository.save(house);
    }
}
