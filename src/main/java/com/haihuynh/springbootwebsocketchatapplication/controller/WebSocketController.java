package com.haihuynh.springbootwebsocketchatapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.standard.InstantFormatter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import Services.Raum;
import Services.SyncService;
import messages.ChatMessage;
import messages.Message;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*
 *   @TODO
 *    FEAT:
 *    - über synchronisierung gedanken machen.
 *    - youtube dui abbilden mit unsereem overlay (pause, volume, cc, geschwindigkeit, vollbild, kinomodus)
 *    . video historie in chatverlauf anzeigen und click und startbar machen
 *    BUGS:
 *    - namen werden nicht richtig übertagen 
 *    - joinmessage kommt nicht immer an
 * 
 */

@Controller
public class WebSocketController {

	private final SimpMessagingTemplate messageService;
	private final SyncService syncService;

	@Autowired
	public WebSocketController(final SimpMessagingTemplate template, final SyncService syncService) {
		this.messageService = template;
		this.syncService = syncService;
	}

	@MessageMapping("/send/chat-message")
	public void onReceiveMessage(@Nullable final Message message) {
			message.setType("chat-message");
			message.getChatMessage().setTimestamp(this.syncService.getTimeStamp());
			List<Long> userIds = syncService.saveChatMessage(message);
			if(userIds != null) {
				for (Long userId : userIds) {
					this.messageService.convertAndSend("/chat/"+userId, message);
				}
				System.out.println("[broadcast message: " + message.getChatMessage().toString() + "]");
			}
		
		
	}

	@MessageMapping("/send/create-room")
	public void onCreateRoom(@Nullable final Message message) {
		Message responseMessage = syncService.createRaum(message);
		if (responseMessage != null) {
			this.messageService.convertAndSend("/chat/" + message.getUserId(), responseMessage);
			System.err.println("user: " + responseMessage.getUserId() + " -> [created room - " + responseMessage.getRaumId() + "]");
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}

	}

	// onReceiveNewVideo
	@MessageMapping("/send/receive-new-video")
	public void onReceiveNewVideo(@Nullable final Message message) {
		List<Message> responseMessages = syncService.addAndShareNewVideo(message);
		if (responseMessages.size() > 0) {
			System.out.println("[user:" + message.getUserId() + " sharing video: " + message.getVideoLink() + "]");
			for (Message responseMessage : responseMessages) {
				if (responseMessage != null) {
					this.messageService.convertAndSend("/chat/" + responseMessage.getUserId() , responseMessage);
				}
			}
		} else {
			this.messageService.convertAndSend("/chat/" + message.getUserId(), new Message("error"));

		}

	}

	@MessageMapping("/send/join-room")
	public void onJoinRoom(@Nullable final Message message) {

		List<Message> responseMessages = syncService.joinRaum(message);
		if (responseMessages != null) {
			for (Message responseMessage : responseMessages) {
				this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
				System.err.println("user: " + responseMessage.getUserId() + " -> [joined room - " + responseMessage.getRaumId() + "]");
			}
		}else {
			this.messageService.convertAndSend("/chat/" + message.getUserId(), new Message("error"));
		}

	}

	@MessageMapping("/send/disconnect-client")
	public void onDisconnectClient(@Nullable final Message message) {
		List<Message> responseMessages = syncService.disconnectClient(message);
		System.out.println("user: " + message.getUserId() + " -> [left room - " + message.getRaumId() + "]");
		for (Message responseMessage : responseMessages) {
			this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
		}

	}

	@MessageMapping("/send/sync-timestamp")
	public void onReceiveSyncTimeStamp(@Nullable final Message message) {
		Message responseMessage = syncService.addUserTimeStamp(message);
		if (responseMessage != null) {
			this.messageService.convertAndSend("/chat/" + message.getUserId(), responseMessage);
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}

	}
	
	
	@MessageMapping("/send/seekto-timestamp")
	public void onReceiveSeekToTimeStamp(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateSyncSeekToMessages(message);
		if (responseMessages != null) {
			System.out.println("user: " + message.getUserId() + "-> [jumps in room - " + message.getRaumId() + "]");
			for (Message responseMessage : responseMessages) {
				this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
			}						
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}

	}
	
	
	@MessageMapping("/send/toggle-play")
	public void onReceiveTogglePlay(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateSyncPlayToggleMessages(message);
		if (responseMessages != null) {
			System.out.println("user: " + message.getUserId() + " -> [toggle play in room - " + message.getRaumId() + "]");
			List<Long> userIds = this.syncService.getRaum(message.getRaumId()).getUserIds();
			for (int i = 0; i < responseMessages.size(); i++) {
				
				this.messageService.convertAndSend("/chat/" + userIds.get(i), responseMessages.get(i));
				
			}						
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}

	}
	
	
	/*
	 * @MessageMapping("/chat")
	 * 
	 * @SendTo("/topic/messages") public OutputMessage send(Message message) throws
	 * Exception { String time = new SimpleDateFormat("HH:mm").format(new Date());
	 * return new OutputMessage(message.getFrom(), message.getText(), time); }
	 */
}
