package Services;

import java.util.ArrayList;
import java.util.HashMap;

import messages.Message;

public class RaumDTO {

	long raumId;
	ArrayList<Long> userIds;
	String videoLink;
	ArrayList<Message> chatMessages;
	HashMap<Long, Long> timeStamps;
	
	public ArrayList<Message> getChatMessages() {
		return chatMessages;
	}
	public void setChatMessages(ArrayList<Message> chatMessages) {
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
	public ArrayList<Long> getUserIds() {
		return userIds;
	}
	public void setUserIds(ArrayList<Long> userIds) {
		this.userIds = userIds;
	}
	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	
	public void addUser(Long id) {
		userIds.add(id);
	}
	
    public void remove(Long id) {
    	userIds.remove(id);
    }
    
    public void addChatMessage(Message message) {
    	chatMessages.add(message);
    }
    @Override
    public String toString() {
    	
    	String result = "";
    	result += "roomid[" + raumId + "] -> userIds[" + getUserIds().toString() + "]"; 
    	
    	return result;
    }
    
}
