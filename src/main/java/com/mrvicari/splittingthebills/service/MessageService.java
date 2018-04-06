package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Message;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.MessageRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service class containing business logic related to Messages
 */
@Service
public class MessageService
{
    /**
     * Repository for database interaction regarding Messages
     */
    private MessageRepository messageRepository;

    /**
     * Repository for database interaction regarding Tenants
     */
    private TenantRepository tenantRepository;

    /**
     * Repository for database interaction regarding Houses
     */
    private HouseRepository houseRepository;

    /**
     * Constructor to inject repository dependencies
     * @param messageRepository repository for database interaction regarding Messages
     * @param tenantRepository repository for database interaction regarding Tenants
     * @param houseRepository repository for database interaction regarding Houses
     */
    public MessageService(MessageRepository messageRepository,
                          TenantRepository tenantRepository,
                          HouseRepository houseRepository)
    {
        this.messageRepository = messageRepository;
        this.tenantRepository = tenantRepository;
        this.houseRepository = houseRepository;
    }

    /**
     * Save a Message object and assign it to the corresponding House
     * @param message Message object passed in through HTTP request
     * @param email email address of the Tenant sending the request
     */
    public void createMessage(Message message, String email)
    {
        Tenant tenant = tenantRepository.findByEmail(email);

        message.setTenant(tenant);
        message.setDate(new Date());
        messageRepository.save(message);

        House house = tenant.getHouse();
        house.getMessages().add(message);
        houseRepository.save(house);
    }
}
