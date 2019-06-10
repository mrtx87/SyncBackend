package com.haihuynh.springbootwebsocketchatapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import Services.SyncService;
import messages.Message;
import java.util.List;

/*
 *   @TODO
 *    FEAT: *    
 *    - resetRaumId für admins nur im privatenmodus
 *    - switch für privaten modus ob alle gleichberechtig oder nicht
 *    
 *    - öffentliche raum kicken und auf blocklist setzen und cookie setzen bei usern mit userid 
 *    
 *    
 *    - copy link to clipboard
 *    - get playlist from api and video title(s)
 *    -controllen für video
 *    BUGS:
 * 	  -
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
	
	
	@MessageMapping("/send/request-public-raeume")
	public void onRequestPublicRaeume(@Nullable final Message message) {
			Message responseMessage = syncService.generatePublicRaeumeMessage(message);
			this.messageService.convertAndSend("/chat/"+ responseMessage.getUserId(), responseMessage);
			System.out.println("[broadcast public Räume: " + message.getUserId() + "]");		
	}


	@MessageMapping("/send/chat-message")
	public void onReceiveMessage(@Nullable final Message message) {
			message.setType("chat-message");
			message.getChatMessage().setTimestamp(this.syncService.getCurrenTime());
			List<Long> userIds = syncService.saveChatMessage(message);
			if(userIds != null) {
				for (Long userId : userIds) {
					this.messageService.convertAndSend("/chat/"+userId, message);
				}
				System.out.println("[broadcast message: " + message.getChatMessage().toString() + "]");
			}	
	}

	@MessageMapping("/send/create-room")
	public void onCreateRaum(@Nullable final Message message) {
		System.err.println("Trying to Create Room by UseId: " + message.getUserId());
		Message responseMessage = syncService.createRaum(message);
		if (responseMessage != null) {
			this.messageService.convertAndSend("/chat/" + message.getUserId(), responseMessage);
			System.err.println("userId: " + responseMessage.getUserId() + " -> [created room - " + responseMessage.getRaumId() + " |  status: " + syncService.getRaum(responseMessage.getRaumId()).getRaumStatus() + "]");
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}

	}

	// onReceiveNewVideo
	@MessageMapping("/send/receive-new-video")
	public void onReceiveNewVideo(@Nullable final Message message) {
		List<Message> responseMessages = syncService.addAndShareNewVideo(message);
		if (responseMessages.size() > 0) {
			System.out.println("[user:" + message.getUserId() + " sharing video: " + message.getVideo().getVideoId() + "]");
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
			}
			System.err.println("user: " + message.getUserId() + " -> [joined room - " + message.getRaumId() + "]");
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
	
	@MessageMapping("/send/sync-roomstate")
	public void onReceiveRoomState(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateSyncRoomStateMessages(message);
		if (responseMessages != null) {
			System.out.println("user: " + message.getUserId() + " -> [switch Roomstate - " + message.getRaumStatus() + "]");
			for (Message responseMessage : responseMessages) {
				this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
			}						
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}

	}
	
	
	@MessageMapping("/send/assign-admin")
	public void onReceiveAssignAdmin(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateAssignedAdminMessages(message);
		if (responseMessages != null) {
			System.out.println("[user: " + message.getAssignedUser().getUserId() + " has been assigned admin by " + message.getUserId() + "]");
			for (Message responseMessage : responseMessages) {
				this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
			}						
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}

	}
	
	
	
	@MessageMapping("/send/to-public-room")
	public void onReceiveToPublicRoomRequest(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateToPublicRoomMessages(message);
		if (responseMessages != null) {
			System.out.println("[user: " + message.getUserId() + " has set room " + message.getRaumId() + " to public]");
			for (Message responseMessage : responseMessages) {
				this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
			}						
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}

	}
	
	
	@MessageMapping("/send/to-private-room")
	public void onReceiveToPrivateRoomRequest(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateToPrivateRoomMessages(message);
		if (responseMessages != null) {
			System.out.println("[user: " + message.getUserId() + " has set room " + message.getRaumId() + " to public]");
			for (Message responseMessage : responseMessages) {
				this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
			}						
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}

	} 
	
	@MessageMapping("/send/kick-user")
	public void onReceiveKickUser(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateKickMessages(message);
		if (responseMessages != null) {
			System.out.println("[user: " + message.getUserId() + " has kicked User: "+ message.getAssignedUser().getUserId() + "from room:" + message.getRaumId() + "]");
			for (Message responseMessage : responseMessages) {
				this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
			}						
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}

	}
	
	@MessageMapping("/send/refresh-raumid")
	public void onReceiveRefreshRaumId(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateRefreshRaumIdMessages(message);
		if (responseMessages != null) {
			System.out.println("[user: " + message.getUserId() + " has wants to resfresh Raumid: " + message.getRaumId() + "]");
			for (Message responseMessage : responseMessages) {
				this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
			}						
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}
	}
	
	@MessageMapping("/send/add-video-to-playlist")
	public void onReceiveAddVideoToPlaylist(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.addVideoToPlaylistMessages(message);
		if (responseMessages != null) {
			System.out.println("[user: " + message.getUserId() + " added video " + message.getVideo().getVideoId() + "to playlist]");
			for (Message responseMessage : responseMessages) {
				this.messageService.convertAndSend("/chat/" + responseMessage.getUser(), responseMessage);
			}						
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}
	}
	
	@MessageMapping("/send/current-timestamp")
	public void onReceiveCurrentTimestamp(@Nullable final Message message) {
		
		List<Message> responseMessages = this.syncService.processJoinTimeStamp(message);
		System.out.println("[" + responseMessages.size() + " Users getting synced]");
		if (responseMessages != null) {
			for (Message responseMessage : responseMessages) {
				System.out.println("[joining user " + responseMessage.getUser() + " synced ]");

				this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
			}
			this.syncService.clearJoiningUsers(message.getRaumId());
			this.syncService.clearTimestamps(message.getRaumId());
			
		} 
	}
	
	@MessageMapping("/send/request-sync-timestamp")
	public void onReceiveRequestSyncTimestamp(@Nullable final Message message) {
		
		Message responseMessage = this.syncService.generateRequestSyncTimestampMessages(message);
		if (responseMessage != null) {
			System.out.println("[requested sync timestamp from User " + responseMessage.getUser() + "]");
			this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
		} else {
			this.messageService.convertAndSend("/chat/" + message.getUser(), new Message("error"));
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
