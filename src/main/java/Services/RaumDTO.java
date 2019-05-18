package Services;

import java.util.ArrayList;
import java.util.HashMap;

import messages.Message;

public class RaumDTO {

	long raumId;
	int size; 
	String videoLink;
	
	
	public long getRaumId() {
		return raumId;
	}
	public void setRaumId(long raumId) {
		this.raumId = raumId;
	}
	
	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

    
}
