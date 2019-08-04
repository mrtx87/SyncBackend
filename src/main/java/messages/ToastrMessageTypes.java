package messages;

public class ToastrMessageTypes {
	public static String CREATE_ROOM = "create-room";
	public static String JOIN_ROOM = "join-room";
	public static String DISCONNECT = "disconnect-client";
	public static String ASSIGNED_AS_ADMIN = "assigned-as-admin";
	public static String TO_PUBLIC_ROOM = "to-public-room";
	public static String TO_PRIVATE_ROOM = "to-private-room";
	public static String KICKED_USER = "kicked-user";
	public static String UPDATE_KICK_CLIENT = "update-kick-client";
	public static String REFRESH_ROOM_ID = "refresh-raumid";
	public static String ADDED_VIDEO_TO_PLAYLIST = "added-video-to-playlist";
	public static String UPDATE_TITLE_AND_DESCRIPTION = "update-title-and-description";
	public static String REMOVE_VIDEO_PLAYLIST = "remove-video-playlist";
	public static String IMPORTED_PLAYLIST = "imported-playlist";
	public static String INTEGRATED_PLAYLIST = "integrated-playlist";
	public static String CHANGED_PLAYBACK_RATE = "change-playback-rate";
	public static String MUTE_USER = "mute-user";
	public static String CHANGED_USER_NAME = "changed-user-name";
	public static String PARDONED_KICKED_USER = "pardon-kicked-user";
	public static String ONLY_LOGGING = "only-logging"; 
	
	
	public static ToastrMessageTypesObject cloneInstance() {
		return new ToastrMessageTypesObject(
			CREATE_ROOM,
			JOIN_ROOM ,
			DISCONNECT,
			ASSIGNED_AS_ADMIN ,
			TO_PUBLIC_ROOM ,
			TO_PRIVATE_ROOM ,
			KICKED_USER ,
			UPDATE_KICK_CLIENT ,
			REFRESH_ROOM_ID ,
			ADDED_VIDEO_TO_PLAYLIST ,
			UPDATE_TITLE_AND_DESCRIPTION ,
			REMOVE_VIDEO_PLAYLIST ,
			IMPORTED_PLAYLIST ,
			INTEGRATED_PLAYLIST ,
			CHANGED_PLAYBACK_RATE,
			MUTE_USER ,
			CHANGED_USER_NAME ,
			PARDONED_KICKED_USER,
			ONLY_LOGGING
			);
	}
	
}
