package Services;

import java.util.ArrayList;

public class Raum {

	long raumId;
	ArrayList<Long> userIDs = new ArrayList<>();
	String videoLink;
	public long getRaumId() {
		return raumId;
	}
	public void setRaumId(long raumId) {
		this.raumId = raumId;
	}
	public ArrayList<Long> getUserIDs() {
		return userIDs;
	}
	public void setUserIDs(ArrayList<Long> userIDs) {
		this.userIDs = userIDs;
	}
	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	
	public void addUser(Long id) {
		userIDs.add(id);
	}
	
    public void remove(Long id) {
    	userIDs.remove(id);
    }
    
    @Override
    public String toString() {
    	
    	String result = "";
    	result += "roomid[" + raumId + "] -> userIds[" + getUserIDs().toString() + "]"; 
    	
    	return result;
    }
    
}
