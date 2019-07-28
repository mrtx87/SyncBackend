package messages;

import java.util.Date;

public class Video {
	
	String id;
	int api;
	

	String videoId;
	Float timestamp;
	String title;
	String description;
    Date publishedAt;
    String thumbnail;
    
	public Video(String id, String videoId, Float timestamp, String title, String description, Date publishedAt, String thumbnail, int api) {
		this.id = id;
		this.videoId = videoId;
		this.timestamp = timestamp;
		this.title = title;
		this.description = description;
	    this.publishedAt = publishedAt;
	    this.thumbnail = thumbnail;
	    this.api = api;
	}
    
	
	
	public int getApi() {
		return api;
	}


	public void setApi(int api) {
		this.api = api;
	}

	public Video clone() {
		return new Video(this.id,this.videoId, this.timestamp, this.title, this.description, this.publishedAt, this.thumbnail, this.api);

	}
    
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

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
