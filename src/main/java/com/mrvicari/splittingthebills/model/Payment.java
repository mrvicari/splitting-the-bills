package com.mrvicari.splittingthebills.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Payment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    private Integer id;
    private String name;
    private Double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant payer;

    @ManyToMany
    private List<Tenant> tenants = new ArrayList<>();

    public Payment()
    {
    }

    public Payment(Integer id, String name, Double amount, PaymentType paymentType)
    {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.paymentType = paymentType;
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
