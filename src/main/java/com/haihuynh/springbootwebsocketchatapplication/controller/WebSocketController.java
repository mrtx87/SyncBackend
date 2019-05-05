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
import messages.ChatMessageStub;
import messages.Message;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*
 *   @TODO
 *   - chatfunktion im backend: implementieren von onReceiveMessage(@Nullable final String message)
 *    + an alle user senden die im raum sind. message beinhaltet roomId userId und den String der Nachricht
 *    + Alle chat nachrichten im Raum speichern mit ArrayList eintrag pro zeile
 *    
 *   - über synchronisierung gedanken machen.
 *    
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
		if("chat-message".equals(message.getType())) {
			Message shareMessage = new Message();
			shareMessage.setType(message.getType());
			shareMessage.setRaumId(message.getRaumId());
			shareMessage.setUserId(message.getUserId());
			
			ChatMessageStub messageStub = new ChatMessageStub();
			messageStub.setChatMessage((String)message.getContent());
			messageStub.setTimestamp(Instant.now().toString());
			shareMessage.setContent(messageStub);
			List<Long> userIds = syncService.saveMessage(shareMessage);
			if(userIds != null) {
				
				for (Long userId : userIds) {
					this.messageService.convertAndSend("/chat/"+userId, shareMessage);
				}				
			}
		}
		
	}

	@MessageMapping("/send/create-room")
	public void onCreateRoom(@Nullable final Message message) {
		Message responseMessage = syncService.createRaum(message);
		if (responseMessage != null) {
			this.messageService.convertAndSend("/chat/" + message.getUserId(), responseMessage);
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}

	}

	// onReceiveNewVideo
	@MessageMapping("/send/receive-new-video")
	public void onReceiveNewVideo(@Nullable final Message message) {
		List<Message> responseMessages = syncService.addAndShareNewVideo(message);
		if (responseMessages.size() > 0) {
			for (Message responseMessage : responseMessages) {
				if (responseMessage != null) {
					this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
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
			}
		}else {
			this.messageService.convertAndSend("/chat/" + message.getUserId(), new Message("error"));
		}

	}

	@MessageMapping("/send/disconnect-client")
	public void onDisconnectClient(@Nullable final Message message) {
		List<Message> responseMessages = syncService.disconnectClient(message);
		for (Message responseMessage : responseMessages) {
			this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
		}

	}

	@MessageMapping("/send/sync-timestamp")
	public void onReceiveSyncTimeStamp(@Nullable final Message message) {
		Message responseMessage = syncService.addUserTimeStamp(message);
		if (responseMessage != null) {
			this.messageService.convertAndSend("/chat/" + message.getTimeStamp(), responseMessage);
		} else {
			this.messageService.convertAndSend("/chat" + message.getTimeStamp(), new Message("error"));
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
