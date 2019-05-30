package messages;

public class Video {
	
	String videoId;
	Long timestamp;
	
	public Video(String videoId, Long timestamp) {
		this.videoId = videoId;
		this.timestamp = timestamp;
	}
	
	public Video() {}
	
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	

}
