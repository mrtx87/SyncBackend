package Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import messages.ChatMessage;
import messages.Video;

public class Raum {

	long raumId;
	String title;
	String description;
	String createdAt;
	Video video;
	Boolean raumStatus;
	int playerState;
	ArrayList<ChatMessage> chatMessages = new ArrayList<>();
	HashMap<Long, User> users = new HashMap<>();
	HashMap<String, Video> playlist = new HashMap<>();
	HashMap<Long, Float> timeStamps = new HashMap<>();
	HashMap<Long, User> joiningUsers = new HashMap<>();

	public HashMap<Long, User> getJoiningUsers() {
		return joiningUsers;
	}
	
	public void setJoiningUsers(HashMap<Long, User> joiningUsers) {
		this.joiningUsers = joiningUsers;
	}

	public HashMap<Long, Float> getTimeStamps() {
		return timeStamps;
	}

	public void setTimeStamps(HashMap<Long, Float> timeStamps) {
		this.timeStamps = timeStamps;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public ArrayList<Video> getPlaylist() {
		return (ArrayList<Video>) playlist.values().stream().collect(Collectors.toList());
	}

	public boolean isVideo(Video _video) {
		return video.getVideoId().equals(_video.getVideoId());
	}

	public boolean isVideo(String videoId) {
		return video.getVideoId().equals(videoId);
	}

	public Video updateTimestamp(Video _video) {
		if (isVideo(_video)) {
			video.setTimestamp(_video.getTimestamp());
			return video;
		}
		return null;
	}

	public Video updateTimestamp(String videoId, Float timestamp) {
		if (isVideo(videoId)) {
			video.setTimestamp(timestamp);
			return video;
		}
		return null;
	}

	public Raum() {

		setRaumId(SyncService.generateRaumId());
	}

	public int getPlayerState() {
		return playerState;
	}

	public void setPlayerState(int playerState) {
		this.playerState = playerState;
	}

	public int getSize() {
		return users.size();
	}

	public Boolean getRaumStatus() {
		return raumStatus;
	}

	public void setRaumStatus(Boolean publicRoom) {
		this.raumStatus = publicRoom;
	}

	public void setUsers(HashMap<Long, User> users) {
		this.users = users;
	}

	public ArrayList<ChatMessage> getChatMessages() {
		return chatMessages;
	}

	public ArrayList<ChatMessage> getPublicChatMessages() {
		return (ArrayList<ChatMessage>) chatMessages.stream().filter(cm -> !cm.isPrivate())
				.collect(Collectors.toList());
	}

	public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
		this.chatMessages = chatMessages;
	}

	public long getRaumId() {
		return raumId;
	}

	public void setRaumId(long raumId) {
		this.raumId = raumId;
	}

	public HashMap<Long, User> getUsers() {
		return users;
	}

	public ArrayList<User> getUserList() {

		return (ArrayList<User>) this.users.values().stream().collect(Collectors.toList());

	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public void addUser(User user) {
		users.put(user.getUserId(), user);
	}

	public void remove(Long id) {
		users.remove(id);
	}

	public void addChatMessage(ChatMessage message) {
		chatMessages.add(message);
	}

	public ArrayList<Long> getUserIds() {
		return (ArrayList<Long>) this.users.keySet().stream().collect(Collectors.toList());
	}

	public ArrayList<String> getUserNames() {
		return (ArrayList<String>) this.users.values().stream().map(u -> u.getUserName()).collect(Collectors.toList());
	}

	@Override
	public String toString() {

		String result = "";
		result += "roomid[" + raumId + "] -> userIds[" + getUserIds().toString() + "]";

		return result;
	}

	public User getUser(Long userId) {
		return users.get(userId);
	}

	public boolean exists(Long userId) {
		return users.containsKey(userId);
	}

	public void assignUserAsAdmin(User user) {
		User newAdmin = users.get(user.userId);
		newAdmin.setAdmin(true);
	}

	public void setAllUsersToAdmins() {
		users.values().stream().forEach(u -> u.setAdmin(true));
	}

	public User deleteUser(Long userId) {
		return users.remove(userId);
	}

	public void addVideoToPlaylist(Video video) {
		playlist.put(video.getVideoId(), video);
	}

	public void removeVideoFromPlaylist(Video video) {
		playlist.remove(video.getVideoId());
	}

	public void setPlaylist(HashMap<String, Video> playlist) {
		this.playlist = playlist;
	}

	public void addTimeStamp(Long userId, Float timeStamp) {
		timeStamps.put(userId, timeStamp);
	}

	public ArrayList<Float> getTimeStampList() {

		return (ArrayList<Float>) timeStamps.values().stream().collect(Collectors.toList());
	}

	public int getTimeStampsCount() {

		return timeStamps.size();

	}

	public int getUsersInRoomCount() {
		return users.size();
	}

	public ArrayList<User> getJoiningUserList() {

		return (ArrayList<User>) joiningUsers.values().stream().collect(Collectors.toList());

	}

	public void addJoiningUser(User user) {

		joiningUsers.put(user.getUserId(), user);

	}

	public void clearJoiningUsers() {

		this.joiningUsers.clear();

	}

	public void cleartimestamps() {
		this.timeStamps.clear();
	}
	
	public boolean isJoiningUser(User user) {
		
		return joiningUsers.containsKey(user.getUserId());
		
	}

}
