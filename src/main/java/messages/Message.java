package messages;

import java.util.ArrayList;
import Services.RaumDTO;
import Services.User;

public class Message {

	String type;
	User user;
	long raumId;

	String videoLink;
	long timeStamp;
	int playerState;
	ChatMessage chatMessage;
	ArrayList<ChatMessage> chatMessages;
	ArrayList<User> users;
	Boolean raumStatus;
	ArrayList<RaumDTO> publicRaeume;
	User assignedUser;

   
	public User getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}

	public ArrayList<RaumDTO> getPublicRaeume() {
		return publicRaeume;
	}

	public void setPublicRaeume(ArrayList<RaumDTO> publicRaeume) {
		this.publicRaeume = publicRaeume;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getRaumStatus() {
		return raumStatus;
	}

	public void setRaumStatus(Boolean raumStatus) {
		this.raumStatus = raumStatus;
	}

	public ArrayList<ChatMessage> getChatMessages() {
		return chatMessages;
	}

	public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
		this.chatMessages = chatMessages;
	}

	public int getPlayerState() {
		return playerState;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
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
		if (user != null) {
			return user.getUserName();
		}
		return null;
	}

	public void setUserName(String userName) {
		if (user != null) {
			this.user.setUserName(userName);
		}
		user = new User();
		user.setUserName(userName);
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

	public Long getUserId() {
		if (user != null)
			return user.getUserId();
		return null;
	}

	public void setUserId(long userId) {
		if (user != null) {
			this.user.setUserId(userId);
		}
		user = new User();
		user.setUserId(userId);
	}

	public Boolean isAdmin() {
		if (user != null)
			return user.isAdmin();
		return null;
	}

	public void setAdmin(Boolean admin) {
		if (user != null) {
			this.user.setAdmin(admin);
		}
		user = new User();
		user.setAdmin(admin);
	}

}
