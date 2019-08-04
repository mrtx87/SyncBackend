package messages;

public class MessageTypesObject {
	public String CREATE_ROOM = "create-room";
	public String JOIN_ROOM = "join-room";
	public String UPDATE_CLIENT = "update-client";
	public String INSERT_NEW_VIDEO = "insert-new-video";
	public String CHAT_MESSAGE = "chat-message";
	public String SEEK_TO_TIMESTAMP = "seekto-timestamp";
	public String TOGGLE_PLAY =  "toggle-play";
	public String DISCONNECT = "disconnect-client";
	public String ASSIGNED_AS_ADMIN = "assigned-as-admin";
	public String TO_PUBLIC_ROOM = "to-public-room";
	public String TO_PRIVATE_ROOM = "to-private-room";
	public String SWITCH_VIDEO = "switch-video";
	public String UPDATE_KICKED_USERS = "update-kicked-users";
	public String KICKED_USER = "kicked-user";
	public String UPDATE_PLAYLIST ="update-playlist";
	public String REQUEST_SYNC_TIMESTAMP ="request-sync-timestamp";
	public String REMOVE_VIDEO_PLAYLIST = "remove-video-playlist";
	public String UPDATE_KICK_CLIENT = "update-kick-client";
	public String REFRESH_ROOM_ID = "refresh-raumid";
	public String REFRESH_USER_AND_LIST = "refresh-user-and-list";
	public String REFRESH_USERLIST = "refresh-userlist";
	public String ADDED_VIDEO_TO_PLAYLIST = "added-video-to-playlist";
	public String UPDATE_TITLE_AND_DESCRIPTION = "update-title-and-description";
	public String IMPORTED_PLAYLIST = "imported-playlist";
	public String CHANGED_PLAYBACK_RATE = "change-playback-rate";
	public String MUTE_USER = "mute-user";
	public String PARDONED_KICKED_USER = "pardon-kicked-user";
	public String TOGGLE_PLAYLIST_RUNNING_ORDER = "toggle-playlist-running-order";
	public String TOGGLE_PLAYLIST_LOOP = "toggle-playlist-loop";
	
	public MessageTypesObject(String cREATE_ROOM, String jOIN_ROOM, String uPDATE_CLIENT, String iNSERT_NEW_VIDEO, String cHAT_MESSAGE,
			String sEEK_TO_TIMESTAMP, String tOGGLE_PLAY, String dISCONNECT, String aSSIGNED_AS_ADMIN,
			String tO_PUBLIC_ROOM, String tO_PRIVATE_ROOM, String sWITCH_VIDEO, String uPDATE_KICKED_USERS,
			String kICKED_USER, String uPDATE_PLAYLIST, String rEQUEST_SYNC_TIMESTAMP, String rEMOVE_VIDEO_PLAYLIST,
			String uPDATE_KICK_CLIENT, String rEFRESH_ROOM_ID, String rEFRESH_USER_AND_LIST, String rEFRESH_USERLIST,
			String aDDED_VIDEO_TO_PLAYLIST, String uPDATE_TITLE_AND_DESCRIPTION, String iMPORTED_PLAYLIST,
			String cHANGED_PLAYBACK_RATE, String mUTE_USER, String pARDONED_KICKED_USER,
			String tOGGLE_PLAYLIST_RUNNING_ORDER, String tOGGLE_PLAYLIST_LOOP) {
		super();
		CREATE_ROOM = cREATE_ROOM;
		JOIN_ROOM = jOIN_ROOM;
		UPDATE_CLIENT = uPDATE_CLIENT;
		INSERT_NEW_VIDEO = iNSERT_NEW_VIDEO;
		CHAT_MESSAGE = cHAT_MESSAGE;
		SEEK_TO_TIMESTAMP = sEEK_TO_TIMESTAMP;
		TOGGLE_PLAY = tOGGLE_PLAY;
		DISCONNECT = dISCONNECT;
		ASSIGNED_AS_ADMIN = aSSIGNED_AS_ADMIN;
		TO_PUBLIC_ROOM = tO_PUBLIC_ROOM;
		TO_PRIVATE_ROOM = tO_PRIVATE_ROOM;
		SWITCH_VIDEO = sWITCH_VIDEO;
		UPDATE_KICKED_USERS = uPDATE_KICKED_USERS;
		KICKED_USER = kICKED_USER;
		UPDATE_PLAYLIST = uPDATE_PLAYLIST;
		REQUEST_SYNC_TIMESTAMP = rEQUEST_SYNC_TIMESTAMP;
		REMOVE_VIDEO_PLAYLIST = rEMOVE_VIDEO_PLAYLIST;
		UPDATE_KICK_CLIENT = uPDATE_KICK_CLIENT;
		REFRESH_ROOM_ID = rEFRESH_ROOM_ID;
		REFRESH_USER_AND_LIST = rEFRESH_USER_AND_LIST;
		REFRESH_USERLIST = rEFRESH_USERLIST;
		ADDED_VIDEO_TO_PLAYLIST = aDDED_VIDEO_TO_PLAYLIST;
		UPDATE_TITLE_AND_DESCRIPTION = uPDATE_TITLE_AND_DESCRIPTION;
		IMPORTED_PLAYLIST = iMPORTED_PLAYLIST;
		CHANGED_PLAYBACK_RATE = cHANGED_PLAYBACK_RATE;
		MUTE_USER = mUTE_USER;
		PARDONED_KICKED_USER = pARDONED_KICKED_USER;
		TOGGLE_PLAYLIST_RUNNING_ORDER = tOGGLE_PLAYLIST_RUNNING_ORDER;
		TOGGLE_PLAYLIST_LOOP = tOGGLE_PLAYLIST_LOOP;
	}
	
	public MessageTypesObject() {
		// TODO Auto-generated constructor stub
	}
	
	
}
