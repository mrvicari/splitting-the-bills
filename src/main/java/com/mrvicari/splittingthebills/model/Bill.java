package com.mrvicari.splittingthebills.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity class for the representation of a Bill
 * @author Miguel Restrepo Vicari
 */
@Entity
@Table(name = "bill")
public class Bill
{
    /**
     * Unique identifier of the Bill
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bill_id")
    private Integer id;

    /**
     * User-given name of the Bill
     */
    private String name;

    /**
     * Total cost of the Bill
     */
    private Double amount;

    /**
     * Date when the Bill was last paid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    /**
     * Date when the Bill must be paid next
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date nextDate;

    /**
     * Length of time (months) between payments of the Bill
     */
    private Integer period;

    /**
     * Tenant who pays for the Bill
     */
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    /**
     * No-arg constructor used by Hibernate to construct a Bill
     */
    public Bill()
    {
    }

    /**
     * Contructs a new Bill with field values specified by the arguments of the same name
     * @param name the name of the Bill
     * @param amount the total cost of the Bill
     * @param date the date when the Bill was last paid
     * @param period Date when the Bill must be paid next
     */
    public Bill(String name, Double amount, Date date, Integer period)
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

    public Date getNextDate()
    {
        return nextDate;
    }

    public void setNextDate(Date nextDate)
    {
        this.nextDate = nextDate;
    }

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod(Integer period)
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
