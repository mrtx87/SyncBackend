package messages;

public class Message {
	
	String type;
	
	long userId;
	long roomId;
	
	Object content;
	
	public Message() {

	}
	
	public Message(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRaumId(long roomId) {
		this.roomId = roomId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	

}
	

