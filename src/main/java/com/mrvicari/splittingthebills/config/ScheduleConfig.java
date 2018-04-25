package com.mrvicari.splittingthebills.config;

import com.mrvicari.splittingthebills.model.*;
import com.mrvicari.splittingthebills.repository.BillRepository;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.PaymentRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Schedule class for performing tasks given a certain schedule
 */
@Configuration
@EnableScheduling
public class ScheduleConfig
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
     * Repository for database interaction regarding Payments
     */
    private PaymentRepository paymentRepository;

    /**
     * Repository for database interaction regarding Houses
     */
    private HouseRepository houseRepository;

    private JavaMailSender javaMailSender;

    /**
     * Constructor to inject repository dependencies
     * @param billRepository repository for database interaction regarding Bills
     * @param tenantRepository repository for database interaction regarding Tenants
     * @param paymentRepository repository for database interaction regarding Payments
     * @param houseRepository repository for database interaction regarding Houses
     */
    public ScheduleConfig(BillRepository billRepository,
                          TenantRepository tenantRepository,
                          PaymentRepository paymentRepository,
                          HouseRepository houseRepository,
                          JavaMailSender javaMailSender)
    {
        this.billRepository = billRepository;
        this.tenantRepository = tenantRepository;
        this.paymentRepository = paymentRepository;
        this.houseRepository = houseRepository;
        this.javaMailSender = javaMailSender;
    }

    /**
     * Notify (email) tenants with their and their house mates' balances
     */
//    @Scheduled(fixedRate = 30000)
    @Scheduled(cron = "0 0 12 * * SUN")
    public void balanceSummary()
    {
        for (House house: houseRepository.findAll())
        {
            for (Tenant tenant : house.getTenants())
            {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date today = new Date();

                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(tenant.getEmail());
                mail.setFrom("splittingthebills@gmail.com");
                mail.setSubject("Balance summary " + sdf.format(today));

                StringBuilder mailText = new StringBuilder();
                mailText.append("Hi ");
                mailText.append(tenant.getName());
                mailText.append(",\n\nYour current balance is £");
                mailText.append(tenant.getBalance());
                mailText.append(".\n\nConsider settling up with your house mates:");
                for (Tenant otherTenant : house.getTenants())
                {
                    if (!tenant.getId().equals(otherTenant.getId()))
                    {
                        mailText.append("\n");
                        mailText.append(otherTenant.getName());
                        mailText.append(": £");
                        mailText.append(otherTenant.getBalance());
                    }
                }

                mail.setText(mailText.toString());

                javaMailSender.send(mail);
            }
        }
    }

    /**
     * Check for Bills due and update balances where necessary (daily basis)
     */
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

    /**
     * Create a Payment entry when a Bill is paid
     * @param billName name of the bill
     * @param today today's date
     * @param billAmount amount of the bill
     * @param payer tenant who paid the bill
     * @param house house to which the bill belongs to
     */
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
