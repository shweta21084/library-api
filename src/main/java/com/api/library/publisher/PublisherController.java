package com.api.library.publisher;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.library.exception.LibraryResourceAlreadyExistsException;
import com.api.library.exception.LibraryResourceNotFoundException;
import com.api.library.util.LibraryAPIUtils;

@RestController
@RequestMapping(path = "/v1/publishers")
public class PublisherController {
	
	PublisherService publisherService;
	
	public PublisherController(PublisherService publisherService) {
		this.publisherService = publisherService;
	}

	@GetMapping(path = "/{publisherId}")
	public ResponseEntity<?> getPublisher(@PathVariable Integer publisherId) {
		Publisher publisher = null;
		try {
			publisher = publisherService.getPublisher(publisherId);
		} catch (LibraryResourceNotFoundException le) {
			return new ResponseEntity<>(le.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(publisher, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> addPublisher(@RequestBody Publisher publisher) {
		
		try {
			publisherService.addPublisher(publisher);
		} catch (LibraryResourceAlreadyExistsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(publisher, HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/{publisherId}")
	public ResponseEntity<?> updatePublisher(@PathVariable Integer publisherId, @RequestBody Publisher publisher) {
		try {
			publisher.setPublisherId(publisherId);
			publisherService.updatePublisher(publisher);
		} catch (LibraryResourceNotFoundException le) {
			return new ResponseEntity<>(le.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(publisher, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{publisherId}")
	public ResponseEntity<?> deletePublisher(@PathVariable Integer publisherId) {
		try {
			publisherService.deletePublisher(publisherId);
		} catch (LibraryResourceNotFoundException le) {
			return new ResponseEntity<>(le.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path = "/search")
	public ResponseEntity<?> searchPublisher(@RequestParam String name) {
		if (!LibraryAPIUtils.doesStringValueExists(name)) {
			return new ResponseEntity<>("Please enter a name to search Publisher", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(publisherService.searchPublisher(name), HttpStatus.OK);
	}
}
