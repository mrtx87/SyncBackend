package Services;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import messages.Message;

@Service
public class SyncService {
	
	HashMap<Long, Raum> rooms = new HashMap<>();
	Long roomIdCounter = new Long(1000);
	
	public HashMap<Long, Raum> getRooms() {
		return rooms;
	}

	public void setRooms(HashMap<Long, Raum> rooms) {
		this.rooms = rooms;
	}
	
	public Long generateNewRaumId() {
		roomIdCounter +=1;
		return roomIdCounter;
	}
	
	public Raum createRaum(Message message) {
		try {
			Long userID = message.getUserId();
			Raum raum = new Raum();
			raum.setRaumId(generateNewRaumId());
			raum.addUser(message.getUserId());
			rooms.put(roomIdCounter, raum);
			return raum;
		}catch(Exception e) {
			return null;
		}
		
	}


}
