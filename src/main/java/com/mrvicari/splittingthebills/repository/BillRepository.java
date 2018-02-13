package com.mrvicari.splittingthebills.repository;

import com.mrvicari.splittingthebills.model.Bill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends CrudRepository<Bill, Integer>
{
}
