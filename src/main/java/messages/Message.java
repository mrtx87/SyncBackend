package messages;

import java.util.ArrayList;
import java.util.HashMap;

import Services.Raum;

public class Message {
	
	String type;
	long userId;
	String userName;
	long raumId;
	String videoLink;
	long timeStamp;
	int playerState;
	ChatMessage chatMessage;
	ArrayList<ChatMessage> chatMessages;
	HashMap<Long, String> users;
	
	public ArrayList<ChatMessage> getChatMessages() {
		return chatMessages;
	}


	public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
		this.chatMessages = chatMessages;
	}
	
	public int getPlayerState() {
		return playerState;
	}
	

	public HashMap<Long, String> getUsers() {
		return users;
	}


	public void setUsers(HashMap<Long, String> users) {
		this.users = users;
	}


	public void setPlayerState(int playerState) {
		this.playerState = playerState;
	}
	
	public ChatMessage getChatMessage() {
		return chatMessage;
	}

	public void setChatMessage(ChatMessage chatMessage) {
		this.chatMessage = chatMessage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	public Message() {

	}
	
	public Message(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getRaumId() {
		return raumId;
	}

	public void setRaumId(long raumId) {
		this.raumId = raumId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	

}
	

