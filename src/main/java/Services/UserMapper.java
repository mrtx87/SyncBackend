package Services;

public class UserMapper {
	public static User reduce(User source) {
		User target = new User();
		target.setAdmin(source.getAdmin());
		target.setMute(source.getMute());
		target.setUserId(source.getUserId());
		target.setUserName(source.getUserName());
		return target;
	}
	
}
