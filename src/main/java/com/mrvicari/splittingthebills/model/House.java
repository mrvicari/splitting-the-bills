package com.mrvicari.splittingthebills.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for the representation of a House
 */
@Entity
@Table(name = "house")
public class House
{
    /**
     * Unique identifier of the House
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "house_id")
    private Integer id;

    /**
     * User-given name of the House
     */
    private String name;

    /**
     * User-given keyphrase of the House
     */
    private String keyphrase;

    /**
     * Concatenation of name and keyphrase the uniquely identifies a House
     */
    @Column(unique = true)
    private String nameKeyphrase;

    /**
     * Tenants who belong to the House
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "house", cascade = CascadeType.ALL)
    private List<Tenant> tenants = new ArrayList<>();

    /**
     * Bills that belong to the House
     */
    @OneToMany
    private List<Bill> bills = new ArrayList<>();

    /**
     * Payments that belong to the House
     */
    @OneToMany(fetch = FetchType.EAGER)
    private List<Payment> payments = new ArrayList<>();

    /**
     * Messages that belong to the House
     */
    @OneToMany
    private List<Message> messages = new ArrayList<>();

    /**
     * No-arg constructor used by Hibernate to construct a House
     */
    public House()
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

    public String getKeyphrase()
    {
        return keyphrase;
    }

    public void setKeyphrase(String keyphrase)
    {
        this.keyphrase = keyphrase;
    }

    public String getNameKeyphrase()
    {
        return nameKeyphrase;
    }

    public void setNameKeyphrase(String nameKeyphrase)
    {
        this.nameKeyphrase = nameKeyphrase;
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
