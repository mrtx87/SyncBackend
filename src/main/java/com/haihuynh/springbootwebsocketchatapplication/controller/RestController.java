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
import messages.Message;
import messages.Video;


@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	private SyncService syncService;
	
	@GetMapping("/room/{raumId}/playlist")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Video> getRaumPlaylist(@PathVariable("raumId") Long raumId) {
		ArrayList<Video> playlist = syncService.getRaumPlaylist(raumId);
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
	
	
	@GetMapping("/publicrooms")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<RaumDTO> getPublicRooms() {
		ArrayList<RaumDTO> publicrooms = syncService.getPublicRooms();
		if(publicrooms != null) {			
			return publicrooms;
		}
		return new ArrayList<RaumDTO>();
	}
}
