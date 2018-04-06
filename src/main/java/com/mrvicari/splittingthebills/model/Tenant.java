package com.mrvicari.splittingthebills.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Entity class for the representation of a Tenant
 */
@Entity
@Table(name = "tenant")
public class Tenant
{
    /**
     * Unique identifier of the Tenant
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tenant_id")
    private Integer id;

    /**
     * Display name chosen by the Tenant
     */
    private String name;

    /**
     * Email address of the Tenant
     */
    @Column(unique = true)
    private String email;

    /**
     * Password of the Tenant's account
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * Balance of the Tenant with respect to the other members of the House
     */
    private Double balance;

    /**
     * House to which the Tenant belongs
     */
    @ManyToOne
    @JoinColumn(name = "house_id")
    @JsonBackReference
    private House house;

    /**
     * No-arg constructor used by Hibernate to construct a Tenant
     */
    public Tenant()
    {
    }

    public Tenant(String name, String email, String password, Double balance)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Double getBalance()
    {
        return balance;
    }

    public void setBalance(Double balance)
    {
        this.balance = balance;
    }

    public House getHouse()
    {
        return house;
    }

    public void setHouse(House house)
    {
        this.house = house;
    }
}
