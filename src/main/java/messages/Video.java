package messages;

public class Video {
	
	int playlistNr;

	String videoId;
	Float timestamp;
	String title;
	String description;
	
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

	public Video(String videoId, Float timestamp, String title) {
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
	public Float getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Float timestamp) {
		this.timestamp = timestamp;
	}
	
	public Video clone() {
		return new Video(this.videoId, this.timestamp, this.title);
	}
	
	public int getPlaylistNr() {
		return playlistNr;
	}

	public void setPlaylistNr(int playlistNr) {
		this.playlistNr = playlistNr;
	}

}
