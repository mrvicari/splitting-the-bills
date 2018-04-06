package com.mrvicari.splittingthebills.repository;

import com.mrvicari.splittingthebills.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for database interaction regarding Payments
 */
@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer>
{
}
