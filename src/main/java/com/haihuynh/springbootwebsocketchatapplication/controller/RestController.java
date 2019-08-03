package com.haihuynh.springbootwebsocketchatapplication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import Apis.SupportedApi;
import Services.ImportedPlaylist;
import Services.RaumDTO;
import Services.SyncService;
import Services.User;
import messages.Message;
import messages.Video;


@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	private SyncService syncService;
	
	@GetMapping("/room/{raumId}/playlist")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Video> getRaumPlaylist(@PathVariable("raumId") String raumId) {
		ArrayList<Video> playlist = syncService.getCopyOfRaumPlaylist(raumId);
		if(playlist != null) {			
			return playlist;
		}
		return new ArrayList<Video>();
	}
	
	
	@PostMapping("/room/{raumId}/userId/{userId}/playlist")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Message> importPlaylist(@PathVariable("raumId") String raumId, @PathVariable("userId") String userId, @RequestBody ImportedPlaylist importedPlaylist) {
		if(syncService.importPlaylist(raumId, importedPlaylist, userId)) {		
			return ResponseEntity.ok(new Message());
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/room/{raumId}/userId/{userId}/history")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<Video>> getHistory(@PathVariable("raumId") String raumId, @PathVariable("userId") String userId){
		ArrayList<Video> history = syncService.getHistory(raumId, userId);
		if(history != null) {
			return ResponseEntity.ok(history);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/publicrooms/userId/{userId}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<RaumDTO>> getPublicRooms(@PathVariable("userId") String userId) {
		ArrayList<RaumDTO> publicrooms = syncService.getPublicRooms(userId);
		if(publicrooms != null) {			
			return ResponseEntity.ok(publicrooms);
		}
		return ResponseEntity.badRequest().body(new ArrayList<RaumDTO>());
	}
	
	@GetMapping("/room/{raumId}/users")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<ArrayList<User>> getCurrentUsers(@PathVariable("raumId") String raumId) {
		ArrayList<User> publicrooms = (ArrayList<User>) syncService.getCurrentUsersInRaum(raumId);
		if(publicrooms != null) {			
			return ResponseEntity.ok(publicrooms);
		}
		return ResponseEntity.badRequest().body(new ArrayList<User>());
	}
	
	@GetMapping("/supported-apis")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<SupportedApi>> getSupportedApis() {
		ArrayList<SupportedApi> supportedApis = syncService.getSupportedApis();
		if(supportedApis != null) {			
			return ResponseEntity.ok(supportedApis);
		}
		return ResponseEntity.badRequest().body(new ArrayList<SupportedApi>());
	}
	
	
	
}
