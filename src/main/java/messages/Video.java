package messages;

import java.util.Date;

public class Video {
	
	String id;

	String videoId;
	Float timestamp;
	String title;
	String description;
    Date publishedAt;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
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

	public Video(String id, String videoId, Float timestamp, String title, String description, Date publishedAt) {
		this.id = id;
		this.videoId = videoId;
		this.timestamp = timestamp;
		this.title = title;
		this.description = description;
	    this.publishedAt = publishedAt;
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
		return new Video(this.id,this.videoId, this.timestamp, this.title, this.description, this.publishedAt);
	}

	public boolean equalsTo(Video video) {
		if(video != null)
			return video.getId().equals(getId());
		return false;
	}
	
	public boolean equalsTo(String id) {
		if(id != null)
			return id.equals(getId());
		return false;
	}

}
