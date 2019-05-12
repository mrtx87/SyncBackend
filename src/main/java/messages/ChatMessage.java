package messages;

public class ChatMessage {

	Long userId;
	Long raumId;
	String userName;
	String timestamp;
	String messageText;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getRaumId() {
		return raumId;
	}
	public void setRaumId(Long raumId) {
		this.raumId = raumId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id: " + userId + " | name:" + userName + " | time:" + timestamp +
				"\ntext: " + messageText;
	}
	
	
}
