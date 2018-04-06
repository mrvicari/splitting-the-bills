package com.mrvicari.splittingthebills.repository;

import com.mrvicari.splittingthebills.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for database interaction regarding Messages
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Integer>
{
}
