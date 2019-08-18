package messages;

import java.util.HashMap;

import Services.HtmlParameterInterpolator;

public class HealthPage {
	
	private int numberOfPrivateRooms = -1;
	private int numberOfPublicRooms = -1;
	private int numberOfUsers = -1;
	private long numberOfChatMessages = -1;
	
	@Override
	public String toString() {
		HashMap<String,String> parameters = new HashMap<String, String>();
		parameters.put("numberOfPrivateRooms", ""+numberOfPrivateRooms);
		parameters.put("numberOfPublicRooms", ""+numberOfPublicRooms);
		parameters.put("numberOfUsers", ""+numberOfUsers);
		parameters.put("numberOfChatMessages", ""+numberOfChatMessages);

		return HtmlParameterInterpolator.interpolate(HTML_STUB, parameters);
	}
	
	
	private static String HTML_STUB =			
			"<!DOCTYPE html>\n" + 
			"<html>\n" + 
			"  <style>\n" + 
			"    body {\n" + 
			"      background-color: #0d0d11\n" + 
			"      color: white;\n" + 
			"      font-family: Monospace;\n" + 
			"    }\n" + 
			"\n" + 
			"    table,\n" + 
			"    th,\n" + 
			"    td {\n" + 
			"      border: 1px solid white;\n" + 
			"      border-collapse: collapse;\n" + 
			"    }\n" + 
			"\n" + 
			"    table {\n" + 
			"      width: 40%;\n" + 
			"    }\n" + 
			"\n" + 
			"  </style>\n" + 
			"\n" + 
			"  <body>\n" + 
			"\n" + 
			"    <h2>Health Page</h2>\n" + 
			"    <p>Health Page Information</p>\n" + 
			"\n" + 
			"    <table>\n" + 
			"      <tr>\n" + 
			"        <td>Anzahl User</td>\n" + 
			"        <td>${numberOfUsers}</td>\n" + 
			"      </tr>\n" + 
			"      <tr>\n" + 
			"        <td>Anzahl Private Rooms</td>\n" + 
			"        <td>${numberOfPrivateRooms}</td>\n" + 
			"      </tr>\n" + 
			"      <tr>\n" + 
			"        <td>Anzahl Public Rooms</td>\n" + 
			"        <td>${numberOfPublicRooms}</td>\n" + 
			"      </tr>\n" + 
			"      <tr>\n" + 
			"        <td>Anzahl der Chatnachrichten</td>\n" + 
			"        <td>${numberOfChatMessages}</td>\n" + 
			"      </tr>\n" + 
			"    </table>\n" + 
			"\n" + 
			"  </body>\n" + 
			"\n" + 
			"</html>\n";
	
	
	// Streamed links, playlists

	public int getNumberOfPrivateRooms() {
		return numberOfPrivateRooms;
	}

	public void setNumberOfPrivateRooms(int numberOfPrivateRooms) {
		this.numberOfPrivateRooms = numberOfPrivateRooms;
	}

	public int getNumberOfPublicRooms() {
		return numberOfPublicRooms;
	}

	public void setNumberOfPublicRooms(int numberOfPublicRooms) {
		this.numberOfPublicRooms = numberOfPublicRooms;
	}

	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public long getNumberOfChatMessages() {
		return numberOfChatMessages;
	}

	public void setNumberOfChatMessages(long numberOfChatMessages) {
		this.numberOfChatMessages = numberOfChatMessages;
	}
	
	
	
}
