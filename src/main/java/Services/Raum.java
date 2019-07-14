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
	Video currentVideo;
	int currentVideoIndex;
	Boolean raumStatus;
	int playerState;
	
	boolean randomOrder = false;
	int loop = 0;
	
	float currentPlaybackRate = 1;

	ArrayList<ChatMessage> chatMessages = new ArrayList<>();
	HashMap<Long, User> users = new HashMap<>();
	ArrayList<Video> playlist = new ArrayList<>();
	HashMap<Long, Float> timeStamps = new HashMap<>();
	HashMap<Long, User> joiningUsers = new HashMap<>();
	
	int countingNextVidRequests = 0;

	public int getCountingNextVidRequests() {
		return countingNextVidRequests;
	}

	public void setCountingNextVidRequests(int countingNextVidRequests) {
		this.countingNextVidRequests = countingNextVidRequests;
	}
	
	public int countNextVidRequest() {
		countingNextVidRequests += 1;
		return countingNextVidRequests;
	}
	
	public void resetCountingNextVidRequests() {
		countingNextVidRequests = 0;
	}

	public float getCurrentPlaybackRate() {
		return currentPlaybackRate;
	}

	public void setCurrentPlaybackRate(float currentPlaybackRate) {
		this.currentPlaybackRate = currentPlaybackRate;
	}

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
	
	
	
	public boolean isRandomOrder() {
		return randomOrder;
	}

	public void setRandomOrder(boolean randomOrder) {
		this.randomOrder = randomOrder;
	}
	
	public boolean toggleRandomOrder() {
		randomOrder = !randomOrder;
		return randomOrder;
	}

	public int getLoop() {
		return loop;
	}

	public void setLoop(int loop) {
		this.loop = loop;
	}

	public int toggleLoop() {
		loop =+1;
		if(loop > 2) {
			loop = 0;
		}
		return loop;
	}
	
	public int getCurrentVideoIndex() {
		return currentVideoIndex;
	}

	public void setCurrentVideoIndex(int currentVideoIndex) {
		this.currentVideoIndex = currentVideoIndex;
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
		return this.playlist;
	}

	public boolean isVideo(Video _video) {
		return currentVideo.getVideoId().equals(_video.getVideoId());
	}

	public boolean isVideo(String videoId) {
		return currentVideo.getVideoId().equals(videoId);
	}

	public Video updateTimestamp(Video _video) {
		if (isVideo(_video)) {
			currentVideo.setTimestamp(_video.getTimestamp());
			return currentVideo;
		}
		return null;
	}

	public Video updateTimestamp(String videoId, Float timestamp) {
		if (isVideo(videoId)) {
			currentVideo.setTimestamp(timestamp);
			return currentVideo;
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

	public Video getPlaylistVideo(int index) {
		if(playlist.size() > index) {
			return playlist.get(index);
		}
		return null;
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

	public Video getCurrentVideo() {
		return currentVideo;
	}

	public void setCurrentVideo(Video video) {
		this.currentVideo = video;
		this.currentVideoIndex = this.getIndexOfVideo(video);
	}

	public void addUser(User user) {
		users.put(user.getUserId(), user);
	}

	public User remove(Long id) {
		return users.remove(id);
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

	public Video addVideoToPlaylist(Video video) {
		if(hasCurrentVideo()) {
			playlist.add(playlist.size(), video);
			return null;
		}else {
			playlist.add(playlist.size(), video);
			setCurrentVideo(video);
			return video;
		}
	}
	
	public Video addVideoToPlaylistAsNext(Video video) {
		if(hasCurrentVideo()) {
			int index = getIndexOfVideo(getCurrentVideo())+1;
			playlist.add(index,video);
			return null;
		}else {
			playlist.add(playlist.size(), video);
			setCurrentVideo(video);
			return video;
		}
	}

	
	public Video addVideoToPlaylistAsCurrent(Video video) {
		if(hasCurrentVideo()) {
			int index = getIndexOfVideo(getCurrentVideo())+1;
			playlist.add(index, video);
			setCurrentVideo(video);
			return video;
		}else {
			playlist.add(video);
			setCurrentVideo(video);
			return video;
		}
	}
	
	private Video removeVideoFromPlaylistById(String Id) {
		for(int i = 0; i < playlist.size(); i++) {
			if(Id.equals(playlist.get(i).getId())) {
				return playlist.remove(i);
			}
		}
		return null;
	}
	
	public Video removeVideoFromPlaylist(Video video) {
		return removeVideoFromPlaylistById(video.getId());
	}
		
	
	public Video switchCurrentPlaylistVideo(Video video) {
		int index = playlistContains(video);
		if(index > -1) {
			setCurrentVideoIndex(index);
			setCurrentVideo(video);
			return video;
		}
		return null;
	}

	public void setPlaylist(ArrayList<Video> playlist) {
		this.playlist = playlist;
	}

	public void addTimeStamp(Long userId, Float timeStamp) {
		timeStamps.put(userId, timeStamp);
	}
	
	public int getIndexOfVideo(Video video) {
		if(video != null) {
			return playlistContains(video);
		}
		return -1;
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
	
	public String generateVideoObjectId() {
		return SyncService.generateVideoObjectId();
	}

	public boolean importPlaylist(ImportedPlaylist importedPlaylist) {
		if(importedPlaylist.getMode() == 0)  {
			importedPlaylist.items.stream().forEach(v -> v.setId(generateVideoObjectId()));
			ArrayList<Video> processedPlaylist =  importedPlaylist.items;
			this.setPlaylist(processedPlaylist);
			return true;
		}else if(importedPlaylist.getMode() == 1) {
			//TODO
			return false;
		}
		System.out.println("[WRONG MODE - PLAYLIST NOT IMPORTED]");
		return false;
		
	}
	
	public boolean hasCurrentVideo() {
		return getCurrentVideo() != null;
	}
	
	public int playlistContains(Video video) {
		for(int i = 0; i < playlist.size(); i++) {
			if(playlist.get(i).equalsTo(video)) {
				return i;
			}
		}
		return -1;
	}
	
}
