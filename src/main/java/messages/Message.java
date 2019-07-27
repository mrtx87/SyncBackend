package messages;

import java.util.ArrayList;

import Services.ImportedPlaylist;
import Services.RaumDTO;
import Services.User;

public class Message {

	String type;
	User user;
	long raumId;

	Video video;
	int playerState;
	ChatMessage chatMessage;
	ArrayList<ChatMessage> chatMessages;
	ArrayList<User> users;
	ArrayList<User> kickedUsers;
	
	Boolean raumStatus;
	ArrayList<RaumDTO> publicRaeume;
	User assignedUser;
	String raumDescription;
	String raumTitle;
	
	Video playlistVideo;
	int loop; //0 noloop, 1 loop all, 2 loop single video
	boolean randomOrder; //false sequentiell, true random
	
	float currentPlaybackRate;
	
	
	public ArrayList<User> getKickedUsers() {
		return kickedUsers;
	}
	public void setKickedUsers(ArrayList<User> kickedUsers) {
		this.kickedUsers = kickedUsers;
	}
	
	public float getCurrentPlaybackRate() {
		return currentPlaybackRate;
	}

	public void setCurrentPlaybackRate(float currentPlaybackRate) {
		this.currentPlaybackRate = currentPlaybackRate;
	}

	public String getRaumDescription() {
		return raumDescription;
	}

	public void setRaumDescription(String raumDescription) {
		this.raumDescription = raumDescription;
	}

	public String getRaumTitle() {
		return raumTitle;
	}

	public void setRaumTitle(String raumTitle) {
		this.raumTitle = raumTitle;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Video getPlaylistVideo() {
		return playlistVideo;
	}

	public void setPlaylistVideo(Video playlistVideo) {
		this.playlistVideo = playlistVideo;
	}

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

	public int getLoop() {
		return loop;
	}

	public void setLoop(int loop) {
		this.loop = loop;
	}

	public boolean isRandomOrder() {
		return randomOrder;
	}

	public void setRandomOrder(boolean randomOrder) {
		this.randomOrder = randomOrder;
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
