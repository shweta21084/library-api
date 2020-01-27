package com.api.library.publisher;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.library.exception.LibraryResourceAlreadyExistsException;
import com.api.library.exception.LibraryResourceBadRequestException;
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
	public ResponseEntity<?> getPublisher(@PathVariable Integer publisherId,
			@RequestHeader(value = "Trace-Id", defaultValue = "") String traceId)
			throws LibraryResourceNotFoundException {
		traceId = getTraceId(traceId);
		return new ResponseEntity<>(publisherService.getPublisher(publisherId, traceId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> addPublisher(@RequestBody Publisher publisher,
			@RequestHeader(value = "Trace-Id", defaultValue = "") String traceId)
			throws LibraryResourceAlreadyExistsException {
		traceId = getTraceId(traceId);
		publisherService.addPublisher(publisher, traceId);
		return new ResponseEntity<>(publisher, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{publisherId}")
	public ResponseEntity<?> updatePublisher(@PathVariable Integer publisherId, @RequestBody Publisher publisher,
			@RequestHeader(value = "Trace-Id", defaultValue = "") String traceId)
			throws LibraryResourceNotFoundException {
		traceId = getTraceId(traceId);
		publisher.setPublisherId(publisherId);
		publisherService.updatePublisher(publisher, traceId);
		return new ResponseEntity<>(publisher, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{publisherId}")
	public ResponseEntity<?> deletePublisher(@PathVariable Integer publisherId,
			@RequestHeader(value = "Trace-Id", defaultValue = "") String traceId)
			throws LibraryResourceNotFoundException {
		traceId = getTraceId(traceId);
		publisherService.deletePublisher(publisherId, traceId);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/search")
	public ResponseEntity<?> searchPublisher(@RequestParam String name,
			@RequestHeader(value = "Trace-Id", defaultValue = "") String traceId)
			throws LibraryResourceBadRequestException {
		traceId = getTraceId(traceId);
		if (!LibraryAPIUtils.doesStringValueExists(name)) {
			throw new LibraryResourceBadRequestException(traceId, "Please enter a name to search Publisher");
		}
		return new ResponseEntity<>(publisherService.searchPublisher(name, traceId), HttpStatus.OK);
	}
	
	private String getTraceId(String traceId) {
		if (!LibraryAPIUtils.doesStringValueExists(traceId)) {
			traceId = UUID.randomUUID().toString();
		}
		return traceId;
	}
}
