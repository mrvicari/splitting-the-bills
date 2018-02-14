package com.mrvicari.splittingthebills.repository;

import com.mrvicari.splittingthebills.model.Bill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends CrudRepository<Bill, Integer>
{
    @Override
    List<Bill> findAll();
}
