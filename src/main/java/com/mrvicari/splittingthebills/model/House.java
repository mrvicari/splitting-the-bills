package com.mrvicari.splittingthebills.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for the representation of a House
 */
@Entity
@Table(name = "house")
@Data public class House
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
     * Six digit uniquely identifying code
     */
    @Column(unique = true)
    private String code;

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
}
