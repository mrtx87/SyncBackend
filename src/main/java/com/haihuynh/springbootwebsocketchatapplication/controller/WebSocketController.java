package com.haihuynh.springbootwebsocketchatapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import Services.Raum;
import Services.SyncService;
import messages.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    	Message responseMessage = syncService.createRaum(message);
    	if(responseMessage != null) {
    		this.messageService.convertAndSend("/chat/"+message.getUserId(),
    				responseMessage);
    	}else {
    		this.messageService.convertAndSend("/chat"+message.getUserId(),
    				new Message("error"));
    	}
    	
    }
    
    //onReceiveNewVideo
    @MessageMapping("/send/receive-new-video")
    public void onReceiveNewVideo(@Nullable final Message message) {
    	List<Message> responseMessages = syncService.addAndShareNewVideo(message);
    	if(responseMessages.size() > 0) {
	    	for (Message responseMessage : responseMessages) {
	    		if(responseMessage != null) {
	        		this.messageService.convertAndSend("/chat/"+ responseMessage.getUserId(),
	        				responseMessage);
	        	}
			}
    	}else {
    		this.messageService.convertAndSend("/chat/"+ message.getUserId(),
    				new Message("error"));
    		
    	}
    	
				  	
    }
    
    @MessageMapping("/send/join-room")
    public void onJoinRoom(@Nullable final Message message) {
    	Message responseMessage = syncService.joinRaum(message);
    	if(responseMessage != null) {
    		this.messageService.convertAndSend("/chat/"+message.getUserId(),
    				responseMessage);
    	}else {
    		this.messageService.convertAndSend("/chat"+message.getRaumId(),
    				new Message("error"));
    	}
    	
    }
    
    @MessageMapping("/send/video-timestamp")
    public void onReceiveTimeStamp(@Nullable final Message message) {
    	Message responseMessage = syncService.videoTimeStamp(message);
    	if(responseMessage != null) {
    		this.messageService.convertAndSend("/chat/"+message.getTimeStamp(),
        			responseMessage);
    	}else {
    		this.messageService.convertAndSend("/chat"+message.getTimeStamp(),
    				new Message("error"));
    	}
    	
    }
    
    /*
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }*/
}
