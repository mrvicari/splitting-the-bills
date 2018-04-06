package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.Bill;
import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.BillRepository;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.stereotype.Service;

/**
 * Service class containing business logic related to Bills
 */
@Service
public class BillService
{
    /**
     * Repository for database interaction regarding Bills
     */
    private BillRepository billRepository;

    /**
     * Repository for database interaction regarding Tenants
     */
    private TenantRepository tenantRepository;

    /**
     * Repository for database interaction regarding Houses
     */
    private HouseRepository houseRepository;

    /**
     * Constructor to inject repository dependencies
     * @param billRepository repository for database interaction regarding bills
     * @param tenantRepository repository for database interaction regarding tenants
     * @param houseRepository repository for database interaction regarding houses
     */
    public BillService(BillRepository billRepository,
                       TenantRepository tenantRepository,
                       HouseRepository houseRepository)
    {
        this.billRepository = billRepository;
        this.tenantRepository = tenantRepository;
        this.houseRepository = houseRepository;
    }

    /**
     * Save Bill object and assign it to corresponding House
     * @param bill Bill object passed in through HTTP request
     * @param email email address of the Tenant sending the request
     */
    public void createBill(Bill bill, String email)
    {
        Tenant tenant = tenantRepository.findByEmail(email);

        bill.setTenant(tenant);

        billRepository.save(bill);

        House house = tenant.getHouse();
        house.getBills().add(bill);
        houseRepository.save(house);
    }

    /**
     * Edit a Bill's fields based on incoming data
     * @param editedBill Bill with updated values
     * @param billId identifier of the Bill to be edited
     */
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

    /**
     * Remove a Bill from the database permanently
     * @param email email address of the tenant sending the request
     * @param billId identifier of the Bill to be deleted
     */
    public void deleteBill(String email, Integer billId)
    {
        Bill bill = billRepository.findOne(billId);
        House house = houseRepository.findHouseByTenantsContains(tenantRepository.findByEmail(email));

        house.getBills().remove(bill);
        billRepository.delete(bill);
    }
}
