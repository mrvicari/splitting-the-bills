package com.mrvicari.splittingthebills.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "house")
public class House
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "house_id")
    private Integer id;
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "house", cascade = CascadeType.ALL)
    private List<Tenant> tenants = new ArrayList<>();

    @OneToMany
    private List<Bill> bills = new ArrayList<>();

    @OneToMany
    private List<Payment> payments = new ArrayList<>();

    @OneToMany
    private List<Message> messages = new ArrayList<>();

    public House()
    {
    }

    public House(String name)
    {
        this.name = name;
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

    public List<Tenant> getTenants()
    {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants)
    {
        this.tenants = tenants;
    }

    public List<Bill> getBills()
    {
        return bills;
    }

    public void setBills(List<Bill> bills)
    {
        this.bills = bills;
    }

    public List<Payment> getPayments()
    {
        return payments;
    }

    public void setPayments(List<Payment> payments)
    {
        this.payments = payments;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
    }
}
