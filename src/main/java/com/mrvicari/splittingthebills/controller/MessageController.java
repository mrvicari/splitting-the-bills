package com.mrvicari.splittingthebills.controller;

import com.mrvicari.splittingthebills.model.Message;
import com.mrvicari.splittingthebills.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for the management of endpoints related to Messages
 */
@RestController
@CrossOrigin
@Api(value = "Message", description = "Operations about messages", tags = { "Message" })
public class MessageController
{
    /**
     * Service for business logic regarding Messages
     */
    private MessageService messageService;

    /**
     * Constructor to inject service dependencies
     * @param messageService service for business logic regarding Messages
     */
    public MessageController(MessageService messageService)
    {
        this.messageService = messageService;
    }

    /**
     * Process request for creating a Message
     * @param message Message object passed in HTTP request body
     */
    @PostMapping("/message")
    @ApiOperation(value = "Create a message")
    public void createMessage(@RequestBody Message message)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        messageService.createMessage(message, email);
    }
}
