package messages;

public class Message {
	
	String type;
	
	long userId;
	long raumId;
	
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

	public long getRaumId() {
		return raumId;
	}

	public void setRaumId(long raumId) {
		this.raumId = raumId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	

}
	

