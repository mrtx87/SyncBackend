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
	
	public Long generateNewRoomId() {
		roomIdCounter +=1;
		return roomIdCounter;
	}
	
	public Raum createRoom(Message message) {
		try {
			Long userID = message.getUserId();
			Raum room = new Raum();
			room.setRaumId(generateNewRoomId());
			room.addUser(message.getUserId());
			rooms.put(roomIdCounter, room);
			return room;
		}catch(Exception e) {
			return null;
		}
		
	}


}
