package messages;

public class MessageTypes {
	public static String CREATE_ROOM = "create-room";
	public static String JOIN_ROOM = "join-room";
	public static String UPDATE_CLIENT = "update-client";
	public static String INSERT_NEW_VIDEO = "insert-new-video";
	public static String CHAT_MESSAGE = "chat-message";
	public static String SEEK_TO_TIMESTAMP = "seekto-timestamp";
	public static String TOGGLE_PLAY =  "toggle-play";
	public static String DISCONNECT = "disconnect-client";
	public static String ASSIGNED_AS_ADMIN = "assigned-as-admin";
	public static String TO_PUBLIC_ROOM = "to-public-room";
	public static String TO_PRIVATE_ROOM = "to-private-room";
	public static String SWITCH_VIDEO = "switch-video";
	public static String UPDATE_KICKED_USERS = "update-kicked-users";
	public static String KICKED_USER = "kicked-user";
	public static String UPDATE_PLAYLIST ="update-playlist";
	public static String REQUEST_SYNC_TIMESTAMP ="request-sync-timestamp";
	public static String REMOVE_VIDEO_PLAYLIST = "remove-video-playlist";
	public static String UPDATE_KICK_CLIENT = "update-kick-client";
	public static String REFRESH_ROOM_ID = "refresh-raumid";
	public static String REFRESH_USER_AND_LIST = "refresh-user-and-list";
	public static String REFRESH_USERLIST = "refresh-userlist";
	public static String ADDED_VIDEO_TO_PLAYLIST = "added-video-to-playlist";
	public static String UPDATE_TITLE_AND_DESCRIPTION = "update-title-and-description";
	public static String IMPORTED_PLAYLIST = "imported-playlist";
	public static String CHANGED_PLAYBACK_RATE = "change-playback-rate";
	public static String MUTE_USER = "mute-user";
	public static String PARDONED_KICKED_USER = "pardon-kicked-user";
	public static String TOGGLE_PLAYLIST_RUNNING_ORDER = "toggle-playlist-running-order";
	public static String TOGGLE_PLAYLIST_LOOP = "toggle-playlist-loop";
	
	public static MessageTypesObject cloneInstance() {
		return new MessageTypesObject(
				JOIN_ROOM,
				UPDATE_CLIENT,
				INSERT_NEW_VIDEO,
				CHAT_MESSAGE,
				SEEK_TO_TIMESTAMP,
				TOGGLE_PLAY,
				DISCONNECT ,
				ASSIGNED_AS_ADMIN ,
				TO_PUBLIC_ROOM ,
				TO_PRIVATE_ROOM ,
				SWITCH_VIDEO ,
				UPDATE_KICKED_USERS ,
				KICKED_USER ,
				UPDATE_PLAYLIST ,
				REQUEST_SYNC_TIMESTAMP ,
				REMOVE_VIDEO_PLAYLIST ,
				UPDATE_KICK_CLIENT ,
				REFRESH_ROOM_ID ,
				REFRESH_USER_AND_LIST ,
				REFRESH_USERLIST ,
				ADDED_VIDEO_TO_PLAYLIST ,
				UPDATE_TITLE_AND_DESCRIPTION,
				IMPORTED_PLAYLIST ,
				CHANGED_PLAYBACK_RATE ,
				MUTE_USER ,
				PARDONED_KICKED_USER ,
				TOGGLE_PLAYLIST_RUNNING_ORDER ,
				TOGGLE_PLAYLIST_LOOP 
				);
	
	}
}
