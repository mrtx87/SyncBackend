package Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import messages.ChatMessage;
import messages.Message;

public class Raum {

	long raumId;
	String videoLink;
	Boolean raumStatus;
	int playerState;
	
	ArrayList<ChatMessage> chatMessages = new ArrayList<>();
	HashMap<Long, Long> timeStamps = new HashMap<>();
	HashMap<Long, User> users= new HashMap<>();
	
	public Raum() {

		setRaumId(SyncService.generateRaumId());
}
	
	public int getPlayerState() {
		return playerState;
	}

	public void setPlayerState(int playerState) {
		this.playerState = playerState;
	}

	public int getSize() {
		return users.size();
	}
	
	public Boolean getRaumStatus() {
		return raumStatus;
	}
	public void setRaumStatus(Boolean publicRoom) {
		this.raumStatus = publicRoom;
	}
	public void setUsers(HashMap<Long, User> users) {
		this.users = users;
	}
	
	public ArrayList<ChatMessage> getChatMessages() {
		return chatMessages;
	}
	
	public ArrayList<ChatMessage> getPublicChatMessages() {
		return (ArrayList<ChatMessage>) chatMessages.stream().filter(cm -> !cm.isPrivate()).collect(Collectors.toList());
	}
	
	public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
		this.chatMessages = chatMessages;
	}
	public void addTimeStamp(Long userId,Long time) {
		timeStamps.put(userId, time);
	}
	public HashMap<Long, Long> getTimeStamps() {
		return timeStamps;
	}
	public void setTimeStamps(HashMap<Long, Long> timeStamps) {
		this.timeStamps = timeStamps;
	}
	public long getRaumId() {
		return raumId;
	}
	public void setRaumId(long raumId) {
		this.raumId = raumId;
	}
	public HashMap<Long, User> getUsers() {
		return users;
	}

	public ArrayList<User> getUserList() {

		return (ArrayList<User>) this.users.values().stream().collect(Collectors.toList());
		
	}
	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	
	public void addUser(Long id, User user) {
		users.put(id, user);
	}
	
    public void remove(Long id) {
    	users.remove(id);
    }
    
    public void addChatMessage(ChatMessage message) {
    	chatMessages.add(message);
    }
    
    public ArrayList<Long> getUserIds() {
    	return (ArrayList<Long>) this.users.keySet().stream().collect(Collectors.toList());
    }
    
    public ArrayList<String> getUserNames() {
    	return  (ArrayList<String>) this.users.values().stream().map(u -> u.getUserName()).collect(Collectors.toList());
    }
    
    @Override
    public String toString() {
    	
    	String result = "";
    	result += "roomid[" + raumId + "] -> userIds[" + getUserIds().toString() + "]"; 
    	
    	return result;
    }
    
    public User getUser(Long userId) {
    	return users.get(userId);
    }
    
    public boolean exists(Long userId) {
    	return users.containsKey(userId);
    }
    
    public void assignUserAsAdmin(User user) {
    	User newAdmin = users.get(user.userId);
    	newAdmin.setAdmin(true);
    }
    
    public void setAllUsersToAdmins() {
    	users.values().stream().forEach(u -> u.setAdmin(true));	
    }
    
    public User deleteUser(Long userId) {
    	return users.remove(userId);
    }
    
}
