package Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	public Message joinRaum(Message message) {
		
		if(rooms.containsKey(message.getRaumId())) {
			Long userID = message.getUserId();
			Raum raum = rooms.get(message.getRaumId());
			raum.addUser(message.getUserId());


			WebSocketConfiguration
			.registryInstance
			.enableSimpleBroker("/"+userID);
			
			Message responseMessage = new Message();
			responseMessage.setType("join-room");
			responseMessage.setContent(raum);
			responseMessage.setRaumId(raum.raumId);
			responseMessage.setUserId(userID);
			
			return responseMessage;
		
		}
		
		return null;		
	}

	public List<Message> addAndShareNewVideo(Message message) {
		Raum raum = rooms.get(message.getRaumId());
		Long userID = message.getUserId();
		String videoLink = message.getVideoLink();
		raum.setVideoLink(videoLink);
		List<Message> list = new ArrayList<>();
		
		for (Long id : raum.getUserIds()) {
			
			Message responseMessage = new Message();
			responseMessage.setType("insert-new-video");
			responseMessage.setRaumId(raum.raumId);
			responseMessage.setUserId(id);
			responseMessage.setVideoLink(videoLink);
			list.add(responseMessage);
		}
		
	
		return 	list;	
	
	}
	
	

}
