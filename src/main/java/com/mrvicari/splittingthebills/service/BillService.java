package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.Bill;
import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.BillRepository;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.stereotype.Service;

@Service
public class BillService
{
    private BillRepository billRepository;
    private TenantRepository tenantRepository;
    private HouseRepository houseRepository;

    public BillService(BillRepository billRepository,
                       TenantRepository tenantRepository,
                       HouseRepository houseRepository)
    {
        this.billRepository = billRepository;
        this.tenantRepository = tenantRepository;
        this.houseRepository = houseRepository;
    }

    public void createBill(Bill bill, String email)
    {
        Tenant tenant = tenantRepository.findByEmail(email);

        bill.setTenant(tenant);

        billRepository.save(bill);

        House house = tenant.getHouse();
        house.getBills().add(bill);
        houseRepository.save(house);
    }

    public void editBill(Bill editedBill, Integer billId)
    {
        Bill bill = billRepository.findOne(billId);

        bill.setName(editedBill.getName());
        bill.setAmount(editedBill.getAmount());
        bill.setNextDate(editedBill.getNextDate());
        bill.setPeriod(editedBill.getPeriod());
        bill.setTenant(editedBill.getTenant());

        billRepository.save(bill);
    }

    public void deleteBill(String email, Integer billId)
    {
        Bill bill = billRepository.findOne(billId);
        House house = houseRepository.findHouseByTenantsContains(tenantRepository.findByEmail(email));

        house.getBills().remove(bill);
        billRepository.delete(bill);
    }
}
