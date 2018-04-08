package com.mrvicari.splittingthebills.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * Entity class for the representation of a Tenant
 */
@Entity
@Table(name = "tenant")
@Data public class Tenant
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
        // Hibernate only requires no-arg constructor
    }
}
