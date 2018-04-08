package com.mrvicari.splittingthebills.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entity class for the representation of a Payment
 */
@Entity
@Table(name = "payment")
@Data public class Payment
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
}
