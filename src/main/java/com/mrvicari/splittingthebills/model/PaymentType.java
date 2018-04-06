package com.mrvicari.splittingthebills.model;

/**
 * Different types a Payment can be
 */
public enum PaymentType
{
    /**
     * Payment is split evenly amongst all tenants involved
     */
    SPLIT,

    /**
     * Payment is a Bill
     */
    BILL,

    /**
     * Payment is a direct transfer from one Tenant to another
     */
    DIRECT
}
