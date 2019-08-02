package Services;

public class User {
	
	String userId;
	String userName;
	Boolean admin;
	Boolean isMute;

	public Boolean getIsMute() {
		return isMute;
	}
	public void setIsMute(Boolean isMute) {
		this.isMute = isMute;
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
