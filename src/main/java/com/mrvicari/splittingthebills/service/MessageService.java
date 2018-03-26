package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Message;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.MessageRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageService
{
    private MessageRepository messageRepository;
    private TenantRepository tenantRepository;
    private HouseRepository houseRepository;

    public MessageService(MessageRepository messageRepository,
                          TenantRepository tenantRepository,
                          HouseRepository houseRepository)
    {
        this.messageRepository = messageRepository;
        this.tenantRepository = tenantRepository;
        this.houseRepository = houseRepository;
    }

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
