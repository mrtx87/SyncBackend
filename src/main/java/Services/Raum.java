package Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import messages.ChatMessage;
import messages.ToastrMessage;
import messages.Video;

public class Raum {

	String raumId;
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
	HashMap<String, User> users = new HashMap<>();
	HashMap<String, User> joiningUsers = new HashMap<>();
	HashMap<String, User> allTimeUsers = new HashMap<>();
	HashMap<String, User> kickedUsers = new HashMap<>();
	

	ArrayList<Video> playlist = new ArrayList<>();
	ArrayList<Video> history = new ArrayList<>();
	ArrayList<ToastrMessage> toastrMessages = new ArrayList<>();

	HashMap<String, Float> timeStamps = new HashMap<>();
	
	int countingNextVidRequests = 0;
	
	public void addToastrMessage(ToastrMessage toastrMessage) {
		toastrMessages.add(toastrMessage);
	}
	
	public int getSizeOfTaostrMessages() {
		return toastrMessages.size();
	}
	
	public ArrayList<ToastrMessage> getToastrMessages() {
		return toastrMessages;
	}

	public void setToastrMessages(ArrayList<ToastrMessage> toastrMessages) {
		this.toastrMessages = toastrMessages;
	}

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
	
	public boolean hasBeenToRaum(User user) {
		User realUser  = allTimeUsers.get(user.getUserId());
		if(realUser != null) {
			if(realUser.getUserKey().equals(user.getUserKey())) {
				return true;
			}
		}
		return false;
	}
	
	public HashMap<String, User> getAllTimeUsers() {
		return allTimeUsers;
	}
	
	public void addToAllTimeUsers(User user) {
		allTimeUsers.put(user.getUserId(), user);
	}

	public void setAllTimeUsers(HashMap<String, User> allTimeUsers) {
		this.allTimeUsers = allTimeUsers;
	}
	
	public User getUserFromAllTimeUsers(User user) {
		return allTimeUsers.get(user.getUserId());
	}

	public HashMap<String, User> getJoiningUsers() {
		return joiningUsers;
	}
	
	public void setJoiningUsers(HashMap<String, User> joiningUsers) {
		this.joiningUsers = joiningUsers;
	}

	public HashMap<String, Float> getTimeStamps() {
		return timeStamps;
	}

	public void setTimeStamps(HashMap<String, Float> timeStamps) {
		this.timeStamps = timeStamps;
	}
	
	public Video getRandomPlaylistVideo() {
		return playlist.get((int) (playlist.size() * Math.random()));
	}
	
	public User addKickedUser(User user) {
		return kickedUsers.put(user.getUserId(), user);
	}
	
	public boolean existsOnKickedUsersList(String userId) {
		return kickedUsers.containsKey(userId);
	}
	
	public User removeFromKickedUserList(String userId) {
		return kickedUsers.remove(userId);
	}
	
	public ArrayList<User> getKickedUsersList(){
		return (ArrayList<User>) getKickedUsers()
				.values()
				.stream()
				.collect(Collectors.toList());
	}
	
	public HashMap<String, User> getKickedUsers() {
		return kickedUsers;
	}
	public void setKickedUsers(HashMap<String, User> kickedUsers) {
		this.kickedUsers = kickedUsers;
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
		loop +=1;
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

	public ArrayList<Video> getHistory() {
		return history;
	}

	public void setHistory(ArrayList<Video> history) {
		this.history = history;
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

	public void setUsers(HashMap<String, User> users) {
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
	
	
	public String getRaumId() {
		return raumId;
	}

	public void setRaumId(String raumId) {
		this.raumId = raumId;
	}

	public HashMap<String, User> getUsers() {
		return users;
	}

	public ArrayList<User> getUserList() {

		return (ArrayList<User>) this.users.values().stream().map(UserMapper::reduce).collect(Collectors.toList());

	}
	
	public ArrayList<User> getAdminList() {

		return (ArrayList<User>) this.users.values().stream().filter(u -> u.admin).collect(Collectors.toList());

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

	public User remove(String id) {
		return users.remove(id);
	}

	public void addChatMessage(ChatMessage message) {
		chatMessages.add(message);
	}

	public ArrayList<String> getUserIds() {
		return (ArrayList<String>) this.users.keySet().stream().collect(Collectors.toList());
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

	public User getUser(String userId) {
		return users.get(userId);
	}

	public boolean userExists(User user) {
		User realUser = users.get(user.getUserId());
		if(realUser != null) {
			if(realUser.getUserKey().equals(user.getUserKey())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean userIsAdmin(User user) {
		User realUser = users.get(user.getUserId());
		if(realUser != null) {
			if(realUser.getUserKey().equals(user.getUserKey()) && realUser.isAdmin()) {
				return true;
			}
		}
		return false;
	}

	public void assignUserAsAdmin(User user) {
		User newAdmin = users.get(user.userId);
		newAdmin.setAdmin(true);
	}

	public void setAllUsersToAdmins() {
		users.values().stream().forEach(u -> u.setAdmin(true));
	}

	public User deleteUser(String userId) {
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
		if(Id != null) {
			for(int i = 0; i < playlist.size(); i++) {
				if(Id.equals(playlist.get(i).getId())) {
					return playlist.remove(i);
				}
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

	public void addTimeStamp(String userId, Float timeStamp) {
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

		return (ArrayList<User>) joiningUsers.values().stream().map(UserMapper::reduce).collect(Collectors.toList());

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
			importedPlaylist.items.stream().forEach(v -> v.setId(generateVideoObjectId()));
			importedPlaylist.items.stream().forEach(v -> this.playlist.add(v));
			return true;
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
	
	public User toggleMuteUser(User user) {
		if(userExists(user)) {
			user.setMute(!user.isMute());
			return user;
		}
		return null;
	}
	
	public User changeUserName(User user) {
		if(userExists(user)){
			User changeNameUser = getUser(user.getUserId());
			changeNameUser.setUserName(user.getUserName());
			allTimeUsers.put(changeNameUser.getUserId(), changeNameUser);
			users.put(changeNameUser.getUserId(), changeNameUser);
			return changeNameUser;
		}
		return null;
	}

	public void addToHistory(Video video) {
		history.add(video);
	}
	
}
