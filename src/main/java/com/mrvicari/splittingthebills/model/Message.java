package com.mrvicari.splittingthebills.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity class for the representation of a Message
 */
@Entity
@Table(name = "message")
@Data public class Message
{
    /**
     * Unique identifier of the Message
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    private Integer id;

    /**
     * Date when the Message was sent
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    /**
     * Content of the Message
     */
    private String message;

    /**
     * Tenant who sent the Message
     */
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    /**
     * No-arg constructor used by Hibernate to construct a Message
     */
    public Message()
    {
        // Hibernate only requires no-arg constructor
    }
}
