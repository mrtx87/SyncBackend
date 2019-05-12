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
	ArrayList<ChatMessage> chatMessages = new ArrayList<>();
	HashMap<Long, Long> timeStamps = new HashMap<>();
	HashMap<Long, String> users= new HashMap<>();


	
	public ArrayList<ChatMessage> getChatMessages() {
		return chatMessages;
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
	public HashMap<Long, String> getUsers() {
		return users;
	}

	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	
	public void addUser(Long id, String name) {
		users.put(id, name);
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
    	return  (ArrayList<String>) this.users.values().stream().collect(Collectors.toList());
    }
    
    @Override
    public String toString() {
    	
    	String result = "";
    	result += "roomid[" + raumId + "] -> userIds[" + getUserIds().toString() + "]"; 
    	
    	return result;
    }
    
}
