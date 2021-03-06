package Services;

public class User {
	
	String userKey;
	String userId;
	String userName;
	Boolean admin;
	Boolean mute;


	public Boolean getMute() {
		return mute;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public Boolean isMute() {
		return mute;
	}
	public void setMute(Boolean mute) {
		this.mute = mute;
	}
	public Boolean getAdmin() {
		return admin;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean isAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getUserId() + "(" + getUserName() +")";
	}
	
}
