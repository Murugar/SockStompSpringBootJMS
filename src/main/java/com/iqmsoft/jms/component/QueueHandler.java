package com.iqmsoft.jms.component;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import com.iqmsoft.jms.dto.MessageDTO;


@Component(value = "queueHandler")
public class QueueHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueHandler.class);


    @Autowired
    private SimpMessageSendingOperations template;

    private static Map<String, Object> headers;

    static {
        headers = new HashMap<String, Object>();
        // add the Content-Type: application/json header by default
        headers.put(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
    }

    public void handle(Exchange exchange) {
        LOGGER.debug("handle exchange");
        Message camelMessage = exchange.getIn();
        MessageDTO message = camelMessage.getBody(MessageDTO.class);
        // send the message specifically to the destination user by using STOMP's user-directed messaging
        template.convertAndSendToUser(message.to, "/topic/messages", message, headers);
    }
}
