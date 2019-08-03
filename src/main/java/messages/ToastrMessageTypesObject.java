package messages;

public class ToastrMessageTypesObject {
	public String CREATE_ROOM = "create-room";
	public String JOIN_ROOM = "join-room";
	public String DISCONNECT = "disconnect-client";
	public String ASSIGNED_AS_ADMIN = "assigned-as-admin";
	public String TO_PUBLIC_ROOM = "to-public-room";
	public String TO_PRIVATE_ROOM = "to-private-room";
	public String KICKED_USER = "kicked-user";
	public String UPDATE_KICK_CLIENT = "update-kick-client";
	public String REFRESH_ROOM_ID = "refresh-raumid";
	public String ADDED_VIDEO_TO_PLAYLIST = "added-video-to-playlist";
	public String UPDATE_TITLE_AND_DESCRIPTION = "update-title-and-description";
	public String REMOVE_VIDEO_PLAYLIST = "remove-video-playlist";
	public String IMPORTED_PLAYLIST = "imported-playlist";
	public String INTEGRATED_PLAYLIST = "integrated-playlist";
	public String CHANGED_PLAYBACK_RATE = "change-playback-rate";
	public String MUTE_USER = "mute-user";
	public String CHANGED_USER_NAME = "changed-user-name";
	public String PARDONED_KICKED_USER = "pardon-kicked-user";
	public ToastrMessageTypesObject(String cREATE_ROOM, String jOIN_ROOM, String dISCONNECT, String aSSIGNED_AS_ADMIN,
			String tO_PUBLIC_ROOM, String tO_PRIVATE_ROOM, String kICKED_USER, String uPDATE_KICK_CLIENT,
			String rEFRESH_ROOM_ID, String aDDED_VIDEO_TO_PLAYLIST, String uPDATE_TITLE_AND_DESCRIPTION,
			String rEMOVE_VIDEO_PLAYLIST, String iMPORTED_PLAYLIST, String iNTEGRATED_PLAYLIST,
			String cHANGED_PLAYBACK_RATE, String mUTE_USER, String cHANGED_USER_NAME, String pARDONED_KICKED_USER) {
		super();
		CREATE_ROOM = cREATE_ROOM;
		JOIN_ROOM = jOIN_ROOM;
		DISCONNECT = dISCONNECT;
		ASSIGNED_AS_ADMIN = aSSIGNED_AS_ADMIN;
		TO_PUBLIC_ROOM = tO_PUBLIC_ROOM;
		TO_PRIVATE_ROOM = tO_PRIVATE_ROOM;
		KICKED_USER = kICKED_USER;
		UPDATE_KICK_CLIENT = uPDATE_KICK_CLIENT;
		REFRESH_ROOM_ID = rEFRESH_ROOM_ID;
		ADDED_VIDEO_TO_PLAYLIST = aDDED_VIDEO_TO_PLAYLIST;
		UPDATE_TITLE_AND_DESCRIPTION = uPDATE_TITLE_AND_DESCRIPTION;
		REMOVE_VIDEO_PLAYLIST = rEMOVE_VIDEO_PLAYLIST;
		IMPORTED_PLAYLIST = iMPORTED_PLAYLIST;
		INTEGRATED_PLAYLIST = iNTEGRATED_PLAYLIST;
		CHANGED_PLAYBACK_RATE = cHANGED_PLAYBACK_RATE;
		MUTE_USER = mUTE_USER;
		CHANGED_USER_NAME = cHANGED_USER_NAME;
		PARDONED_KICKED_USER = pARDONED_KICKED_USER;
	}
	
	public ToastrMessageTypesObject() {
		
	}
	
	
	
	
}
