package com.iqmsoft.jms.service;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iqmsoft.jms.dto.MessageDTO;

@Service
public class MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private ProducerTemplate template;
    


    public void send(final MessageDTO messageDto) {
        LOGGER.debug("Send message {}", messageDto);
        template.sendBody("activemq:sample.queue", messageDto);
    }
    
}
