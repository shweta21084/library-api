package com.api.library.publisher.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.library.publisher.model.Publisher;

@RestController
@RequestMapping(path = "/v1/publishers")
public class PublisherController {
	@GetMapping(path = "/{publisherId}")
	public Publisher getPublisher(@PathVariable String publisherId) {
		return new Publisher(publisherId, "Prentice Hall Publisher", "abc@gmail.com", "04505776732");
	}

}
