package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.Bill;
import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.BillRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService
{
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private TenantRepository tenantRepository;

    public void createBill(Bill bill, String email)
    {
        Tenant tenant = tenantRepository.findByEmail(email);

        bill.setTenant(tenant);
        billRepository.save(bill);

        House house = tenant.getHouse();
        house.getBills().add(bill);

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
    }
}
