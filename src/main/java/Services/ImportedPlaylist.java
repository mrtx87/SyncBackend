package Services;

import java.util.ArrayList;

import messages.Video;

public class ImportedPlaylist {
	
	int mode;
	String title;
	ArrayList<Video> items;
	User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<Video> getItems() {
		return items;
	}
	public void setItems(ArrayList<Video> items) {
		this.items = items;
	}
	
	

}
