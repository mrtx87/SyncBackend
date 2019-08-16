package messages;

public class HealthPage {

	private int numberOfPrivateRooms;
	private int numberOfPublicRooms;
	private int numberOfUsers;
	private long numberOfChatMessages;
	
	// Streamed links, playlists
	
	public HealthPage() {}
	
	public HealthPage(int numberOfPrivateRooms, int numberOfPublicRooms, int numberOfUsers, long numberOfChatMessages) {
		this.numberOfPrivateRooms = numberOfPrivateRooms;
		this.numberOfPublicRooms = numberOfPublicRooms;
		this.numberOfUsers = numberOfUsers;
		this.numberOfChatMessages = numberOfChatMessages;
	}
	
	

	public int getNumberOfPrivateRooms() {
		return numberOfPrivateRooms;
	}

	public void setNumberOfPrivateRooms(int numberOfPrivateRooms) {
		this.numberOfPrivateRooms = numberOfPrivateRooms;
	}

	public int getNumberOfPublicRooms() {
		return numberOfPublicRooms;
	}

	public void setNumberOfPublicRooms(int numberOfPublicRooms) {
		this.numberOfPublicRooms = numberOfPublicRooms;
	}

	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public long getNumberOfChatMessages() {
		return numberOfChatMessages;
	}

	public void setNumberOfChatMessages(long numberOfChatMessages) {
		this.numberOfChatMessages = numberOfChatMessages;
	}
	
	
}
