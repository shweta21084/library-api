package com.api.library.publisher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.library.exception.LibraryResourceAlreadyExistsException;

@RestController
@RequestMapping(path = "/v1/publishers")
public class PublisherController {
	
	PublisherService publisherService;
	
	public PublisherController(PublisherService publisherService) {
		this.publisherService = publisherService;
	}

	@GetMapping(path = "/{publisherId}")
	public Publisher getPublisher(@PathVariable Integer publisherId) {
		return new Publisher(publisherId, "Prentice Hall Publisher", "abc@gmail.com", "04505776732");
	}
	
	@PostMapping
	public ResponseEntity<?> addPublisher(@RequestBody Publisher publisher) {
		
		try {
			publisher = publisherService.addPublisher(publisher);
		} catch (LibraryResourceAlreadyExistsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(publisher, HttpStatus.CREATED);
	}
}
