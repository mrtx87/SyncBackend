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
	public List<Video> getRaumPlaylist(@PathVariable("raumId") Long raumId) {
		ArrayList<Video> playlist = syncService.getCopyOfRaumPlaylist(raumId);
		if(playlist != null) {			
			return playlist;
		}
		return new ArrayList<Video>();
	}
	
	@PostMapping("/room/{raumId}/playlist")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Message> importPlaylist(@PathVariable("raumId") Long raumId, @RequestBody ImportedPlaylist importedPlaylist) {
		if(syncService.importPlaylist(raumId, importedPlaylist)) {		
			return ResponseEntity.ok(new Message());
		}
		return ResponseEntity.badRequest().build();
	}

	
	/*
	 * @TODO Filterung für kicked users mit ralf
	 */
	@GetMapping("/publicrooms")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<RaumDTO>> getPublicRooms() {
		ArrayList<RaumDTO> publicrooms = syncService.getPublicRooms();
		if(publicrooms != null) {			
			return ResponseEntity.ok(publicrooms);
		}
		return ResponseEntity.badRequest().body(new ArrayList<RaumDTO>());
	}
	
	/*
	 * @TODO Filterung für kicked users mit ralf
	 */
	@GetMapping("/room/{raumId}/playlist")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<ArrayList<User>> getCurrentUsers(@PathVariable("raumId") Long raumId) {
		ArrayList<User> publicrooms = (ArrayList<User>) syncService.getCurrentUsersInRaum(raumId);
		if(publicrooms != null) {			
			return ResponseEntity.ok(publicrooms);
		}
		return ResponseEntity.badRequest().body(new ArrayList<User>());
	}
	
}
