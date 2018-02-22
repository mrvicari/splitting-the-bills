package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.Message;
import com.mrvicari.splittingthebills.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController
{
    @Autowired
    private MessageService messageService;

    @PostMapping("/message")
    public void createMessage(@RequestBody Message message)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        messageService.createMessage(message, email);
    }
}