package com.mrvicari.splittingthebills.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entity class for the representation of a Payment
 */
@Entity
public class Payment
{
    /**
     * Unique identifier of the Payment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    private Integer id;

    /**
     * User-given name of the Payment
     */
    private String name;

    /**
     * Total cost of the Payment
     */
    private Double amount;

    /**
     * Date when the Payment was made
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    /**
     * Way in which the payment will be divided among involved tenants
     */
    private PaymentType paymentType;

    /**
     * Tenant who initiates the Pyament
     */
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant payer;

    /**
     * Tenant(s) who are being paid for/receiving the Payment
     */
    @ManyToMany
    private List<Tenant> tenants = new ArrayList<>();

    /**
     * No-arg constructor used by Hibernate to construct a Payment
     */
    public Payment()
    {
        // Hibernate only requires no-arg constructor
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public PaymentType getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType)
    {
        this.paymentType = paymentType;
    }

    public Tenant getPayer()
    {
        return payer;
    }

    public void setPayer(Tenant payer)
    {
        this.payer = payer;
    }

    public List<Tenant> getTenants()
    {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants)
    {
        this.tenants = tenants;
    }
}
