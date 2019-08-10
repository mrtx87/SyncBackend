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
 *    - switch für privaten modus ob alle gleichberechtig oder nicht   
 *    - öffentliche raum kicken und auf blocklist setzen und cookie setzen bei usern mit userid 
 *    - copy link to clipboard
 *    - controllen für video
 *    - chat schroll down
 *    - playlist fertig bauen (refesh konzept?)
 *    - playlist funktionen implementieren
 *    - löscehn fr jeden playlisteintrag
 *    - playlist funkionalität im backend
 *    - histoey
 *    - make over styling etc
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

	
	@MessageMapping("/send/update-title-and-description")
	public void onReceiveUpdateTitleAndDescription(@Nullable final Message message) {
		List<Message> responseMessages = syncService.generateUpdateTitleAndDescriptionMessages(message);
		sendResponseMessages(responseMessages, message, "[user:" + message.getUserId() + " sharing title and description: " + message.getRaumTitle() + " - " + message.getRaumDescription() + "]");
	}

	
	/**
	 * TODO UMBAU WIE ANDERE FUNKTIONEN
	 * @param message
	 */
	
	@MessageMapping("/send/chat-message")
	public void onReceiveMessage(@Nullable final Message message) {
			message.setType("chat-message");
			message.getChatMessage().setTimestamp(this.syncService.getCurrentTime());
			List<Message> responseMessages = syncService.generateSaveChatMessageResponse(message);

			sendResponseMessages(responseMessages, message, "[user:" + message.getUserId() + " sends chat-message: " + message.getChatMessage() + "]");	
	}

	@MessageMapping("/send/create-room")
	public void onCreateRaum(@Nullable final Message message) {
		System.err.println("Trying to Create Room by UseId: " + message.getUserId());
		Message responseMessage = syncService.createRaum(message);
		sendResponseMessage(responseMessage, message, "userId: " + responseMessage.getUserId() + " -> [created room - " + responseMessage.getRaumId() + " |  status: " + syncService.getRaum(responseMessage.getRaumId()).getRaumStatus() + "]");
	}

	@MessageMapping("/send/change-user-name")
	public void onReceiveChangeUserName(@Nullable final Message message) {
		List<Message> responseMessages = syncService.generateChangeUserNameMessages(message);
		sendResponseMessages(responseMessages, message, "user: " + message.getUserId() + " -> [changed name to - " + message.getUserName() + "]");
	}

	@MessageMapping("/send/join-room")
	public void onJoinRoom(@Nullable final Message message) {
		List<Message> responseMessages = syncService.joinRaum(message);
		sendResponseMessages(responseMessages, message, "user: " + message.getUserId() + " -> [joined room - " + message.getRaumId() + "]");
	}

	@MessageMapping("/send/disconnect-client")
	public void onDisconnectClient(@Nullable final Message message) {
		List<Message> responseMessages = syncService.disconnectClient(message);
		sendResponseMessages(responseMessages, message, "user: " + message.getUserId() + " -> [left room - " + message.getRaumId() + "]");
	}

	@MessageMapping("/send/sync-timestamp")
	public void onReceiveSyncTimeStamp(@Nullable final Message message) {
		Message responseMessage = syncService.addUserTimeStamp(message);
		sendResponseMessage(responseMessage, message, "[receive sync timestamp from User " + responseMessage.getUser() + "]");
	}
	
	
	@MessageMapping("/send/seekto-timestamp")
	public void onReceiveSeekToTimeStamp(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateSyncSeekToMessages(message);
		sendResponseMessages(responseMessages, message, "user: " + message.getUserId() + "-> [jumps in room - " + message.getRaumId() + "]");
	}
	
	
	@MessageMapping("/send/toggle-play")
	public void onReceiveTogglePlay(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateSyncPlayToggleMessages(message);
		if (responseMessages != null) {
			System.out.println("user: " + message.getUserId() + " -> [toggle play in room - " + message.getRaumId() + "]");
			List<String> userIds = this.syncService.getRaum(message.getRaumId()).getUserIds();
			for (int i = 0; i < responseMessages.size(); i++) {
				this.messageService.convertAndSend("/chat/" + userIds.get(i), responseMessages.get(i));
			}						
		} else {
			this.messageService.convertAndSend("/chat" + message.getUserId(), new Message("error"));
		}

	}
	
	@MessageMapping("/send/assign-admin")
	public void onReceiveAssignAdmin(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateAssignedAdminMessages(message);
		sendResponseMessages(responseMessages, message, "[user: " + message.getAssignedUser().getUserId() + " has been assigned admin by " + message.getUserId() + "]");
	}
	
	@MessageMapping("/send/to-public-room")
	public void onReceiveToPublicRoomRequest(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateToPublicRoomMessages(message);
		sendResponseMessages(responseMessages, message, "[user: " + message.getUserId() + " has set room " + message.getRaumId() + " to public]");
	}
	
	
	@MessageMapping("/send/to-private-room")
	public void onReceiveToPrivateRoomRequest(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateToPrivateRoomMessages(message);
		sendResponseMessages(responseMessages, message, "[user: " + message.getUserId() + " has set room " + message.getRaumId() + " to public]");
	} 
	
	@MessageMapping("/send/kick-user")
	public void onReceiveKickUser(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateKickMessages(message);
		sendResponseMessages(responseMessages, message, "[user: " + message.getUserId() + " has kicked User: "+ message.getAssignedUser().getUserId() + "from room:" + message.getRaumId() + "]");
	}
	
	@MessageMapping("/send/pardon-kicked-user")
	public void onReceivePardonKickedUser(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generatePardonKickedUserMessages(message);
		sendResponseMessages(responseMessages, message, "[user: " + message.getUserId() + " has pardonded User: "+ message.getAssignedUser().getUserId() + "from room:" + message.getRaumId() + "]");
	}
	
	
	@MessageMapping("/send/refresh-raumid")
	public void onReceiveRefreshRaumId(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.generateRefreshRaumIdMessages(message);
		sendResponseMessages(responseMessages, message, "[user: " + message.getUserId() + " has wants to resfresh Raumid: " + message.getRaumId() + "]");
	}
	
	@MessageMapping("/send/add-video-to-playlist")
	public void onReceiveAddVideoToPlaylist(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.addVideoToPlaylistMessages(message, SyncService.AddVideoMode.LAST);
		sendResponseMessages(responseMessages, message, "[user: " + message.getUserId() + " added video " + message.getPlaylistVideo().getVideoId() + "to playlist as Last]");
	}
	
	@MessageMapping("/send/add-video-to-playlist-asnext")
	public void onReceiveAddVideoToPlaylistAsNext(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.addVideoToPlaylistMessages(message, SyncService.AddVideoMode.NEXT);
		sendResponseMessages(responseMessages, message, "[user: " + message.getUserId() + " added video " + message.getPlaylistVideo().getVideoId() + "to playlist as Next]");
	}
	
	@MessageMapping("/send/add-video-to-playlist-ascurrent")
	public void onReceiveAddVideoToPlaylistAsCurrent(@Nullable final Message message) {
		List<Message> responseMessages = this.syncService.addVideoToPlaylistMessages(message, SyncService.AddVideoMode.CURRENT);
		sendResponseMessages(responseMessages, message, "[user: " + message.getUserId() + " added video " + message.getPlaylistVideo().getVideoId() + "to playlist as current]");
	}
	
	@MessageMapping("/send/remove-video-from-playlist")
	public void onReceiveRemoveVideoFromPlaylist(@Nullable final Message message) {
		
		List<Message> responseMessages = this.syncService.generateRemoveVideoFromPlaylistMessages(message);
		sendResponseMessages(responseMessages, message,"[video removed from playlist by user " + message.getUser() + "]");
	}
	
	
	@MessageMapping("/send/switch-playlist-video")
	public void onReceiveSwitchPlaylistVideo(@Nullable final Message message) {
		
		List<Message> responseMessages = this.syncService.generateSwitchPlaylistVideoMessages(message);
		sendResponseMessages(responseMessages, message, "[switched playlist video by user " + message.getUser() + " to " + message.getPlaylistVideo().getTitle() + "]");
	}
	
	@MessageMapping("/send/auto-next-playlist-video")
	public void onReceiveAutoNextPlaylistVideo(@Nullable final Message message) {
		
		List<Message> responseMessages = this.syncService.processAutoNextPlaylistVideo(message);
		sendResponseMessages(responseMessages, message,"[auto-next-playlist-video send to clients]");
	}
	
	
	@MessageMapping("/send/toggle-playlist-loop")
	public void onReceiveTogglePlaylistLoop(@Nullable final Message message) {
		
		List<Message> responseMessages = this.syncService.generateTogglePlaylistLoopMessages(message);
		sendResponseMessages(responseMessages, message,"[playlist-loop toggled by User " + message.getUser() + "]");
	}
	
	@MessageMapping("/send/toggle-playlist-running-order")
	public void onReceiveTogglePlaylistRunningOrder(@Nullable final Message message) {
		
		List<Message> responseMessages = this.syncService.generateTogglePlaylistRunningOrderMessages(message);
		sendResponseMessages(responseMessages, message,"[playlist-running-order toggled by user " + message.getUser() + "]");
	}
	
	@MessageMapping("/send/change-playback-rate")
	public void onReceiveChangePlaybackRate(@Nullable final Message message) {
		
		List<Message> responseMessages = this.syncService.generateChangePlaybackRateMessages(message);
		sendResponseMessages(responseMessages, message,"[change-playback-rate by User " + message.getUser() + "]");
	}
	
	@MessageMapping("/send/toggle-mute-user")
	public void onReceiveToggleMuteUser(@Nullable final Message message) {
		
		List<Message> responseMessages = this.syncService.generateToggleMuteUserMessages(message);
		sendResponseMessages(responseMessages, message,"[toggle-mute-user " + message.getAssignedUser()  + " by User" + message.getUser() + "]");
	}
	
	
	@MessageMapping("/send/current-timestamp")
	public void onReceiveCurrentTimestamp(@Nullable final Message message) {
		
		List<Message> responseMessages = this.syncService.processJoinTimeStamp(message);
		System.out.println("[" + responseMessages.size() + " Users getting synced]");
		sendResponseMessages(responseMessages, message, "[have send sync timestamps to users]");
		if (responseMessages != null) {
			this.syncService.clearJoiningUsers(message.getRaumId());
		} 
	}
	
	@MessageMapping("/send/request-sync-timestamp")
	public void onReceiveRequestSyncTimestamp(@Nullable final Message message) {
		
		Message responseMessage = this.syncService.generateRequestSyncTimestampMessages(message);
		sendResponseMessage(responseMessage, message, "[requested sync timestamp from User " + responseMessage.getUser() + "]");
	}
	

	private void sendResponseMessages(List<Message> responseMessages, Message requestMessage, String log) {
		if (responseMessages != null) {
			for (Message responseMessage : responseMessages) {
				this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
			}			
			System.out.println(log);
		} else {
			this.messageService.convertAndSend("/chat/" + requestMessage.getUser(), new Message("error"));
		}
	}
	
	private void sendResponseMessage(Message responseMessage, Message requestMessage, String log) {
		if (responseMessage != null) {
			this.messageService.convertAndSend("/chat/" + responseMessage.getUserId(), responseMessage);
			System.out.println(log);
		} else {
			this.messageService.convertAndSend("/chat/" + requestMessage.getUser(), new Message("error"));
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
