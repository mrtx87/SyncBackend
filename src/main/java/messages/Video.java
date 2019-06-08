package messages;

public class Video {
	
	String videoId;
	Long timestamp;
	String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Video(String videoId, Long timestamp, String title) {
		this.videoId = videoId;
		this.timestamp = timestamp;
		this.title = title;
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
	
	public Video clone() {
		return new Video(this.videoId, this.timestamp, this.title);
	}
	

}
