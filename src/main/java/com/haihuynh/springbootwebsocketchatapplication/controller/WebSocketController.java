package com.haihuynh.springbootwebsocketchatapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import Services.SyncService;
import messages.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messageService;
    private final SyncService syncService;

    @Autowired
    public WebSocketController(final SimpMessagingTemplate template, final SyncService syncService) {
        this.messageService = template;
        this.syncService = syncService;
    }

    @MessageMapping("/send/message")
    public void onReceiveMessage(@Nullable final String message) {
    	
    	
        this.messageService.convertAndSend("/chat",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ": " + message);
    }
    
    @MessageMapping("/send/create-room")
    public void onCreateRoom(@Nullable final Message message) {
    	syncService.createRoom(message);
    }
    
    /*
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }*/
}
