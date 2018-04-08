package com.mrvicari.splittingthebills.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity class for the representation of a Bill
 */
@Entity
@Table(name = "bill")
@Data public class Bill
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
        // Hibernate only requires no-arg constructor
    }
}
