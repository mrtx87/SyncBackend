package Services;

import java.util.ArrayList;
import java.util.HashMap;

import messages.Message;
import messages.Video;

public class RaumDTO {

	long raumId;
	int size; 
	Video video;
	
	
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
