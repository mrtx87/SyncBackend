package Services;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.haihuynh.springbootwebsocketchatapplication.configuration.WebSocketConfiguration;

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
	
	public Message createRaum(Message message) {
		try {
			Long userID = message.getUserId();
			Raum raum = new Raum();
			raum.setRaumId(generateNewRaumId());
			raum.addUser(message.getUserId());
			rooms.put(roomIdCounter, raum);
			
			WebSocketConfiguration
			.registryInstance
			.enableSimpleBroker("/"+userID);
			
			Message responseMessage = new Message();
			responseMessage.setType("create-room");
			responseMessage.setContent(raum);
			responseMessage.setRaumId(raum.raumId);
			responseMessage.setUserId(userID);
			
			return responseMessage;
		}catch(Exception e) {
			return null;
		}
		
	}
	
	public Raum joinRaum(Message message) {
		
		if(rooms.containsKey(message.getRaumId())) {
			
			Raum raum = rooms.get(message.getRaumId());
			raum.addUser(message.getUserId());
			return raum;
		
		}
		
		return null;		
	}


}
