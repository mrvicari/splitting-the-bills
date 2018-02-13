package com.mrvicari.splittingthebills.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bill")
public class Bill
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bill_id")
    private Integer id;
    private String name;
    private Double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @Enumerated(EnumType.STRING)
    private Period period;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    public Bill()
    {
    }

    public Bill(String name, Double amount, Date date, Period period)
    {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.period = period;
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

    public Period getPeriod()
    {
        return period;
    }

    public void setPeriod(Period period)
    {
        this.period = period;
    }

    public Tenant getTenant()
    {
        return tenant;
    }

    public void setTenant(Tenant tenant)
    {
        this.tenant = tenant;
    }
}
