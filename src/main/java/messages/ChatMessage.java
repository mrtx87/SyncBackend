package messages;

import Services.User;

public class ChatMessage {

	boolean isPrivate;

	User user;
	Long raumId;

	String timestamp;
	String messageText;

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		return "id: " + user.getUserId() + " | name:" + user.getUserName() + " | time:" + timestamp + "\ntext: "
				+ messageText;
	}

}
