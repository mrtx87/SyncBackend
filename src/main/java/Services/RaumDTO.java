package Services;

import java.util.ArrayList;
import java.util.HashMap;

import messages.Message;
import messages.Video;

public class RaumDTO {

	long raumId;
	String createdAt;
	String description;
	String title;
	int size; 
	Video video;
	Boolean raumStatus;
	
	
	public Boolean getRaumStatus() {
		return raumStatus;
	}
	public void setRaumStatus(Boolean raumStatus) {
		this.raumStatus = raumStatus;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public long getRaumId() {
		return raumId;
	}
	public void setRaumId(long raumId) {
		this.raumId = raumId;
	}
	
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

    
}
