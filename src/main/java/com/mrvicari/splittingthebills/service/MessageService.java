package com.mrvicari.splittingthebills.service;

import com.mrvicari.splittingthebills.model.House;
import com.mrvicari.splittingthebills.model.Message;
import com.mrvicari.splittingthebills.model.Tenant;
import com.mrvicari.splittingthebills.repository.HouseRepository;
import com.mrvicari.splittingthebills.repository.MessageRepository;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageService
{
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private HouseRepository houseRepository;

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
