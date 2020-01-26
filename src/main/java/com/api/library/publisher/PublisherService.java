package com.api.library.publisher;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.api.library.exception.LibraryResourceAlreadyExistsException;

@Service
public class PublisherService {

	private PublisherRepository publisherRepository;

	public PublisherService(PublisherRepository publisherRepository) {
		this.publisherRepository = publisherRepository;
	}

	public Publisher addPublisher(Publisher publisherToBeAdded) throws LibraryResourceAlreadyExistsException {

		PublisherEntity publisherEntity = new PublisherEntity(publisherToBeAdded.getName(),
				publisherToBeAdded.getEmailId(), publisherToBeAdded.getPhoneNumber());
		
		PublisherEntity addedPublisher = null;
		
		try {
			addedPublisher = publisherRepository.save(publisherEntity);
		} catch (DataIntegrityViolationException de) {
			throw new LibraryResourceAlreadyExistsException("Publisher Already Exists!");
		}
		
		publisherToBeAdded.setPublisherId(addedPublisher.getPublisherId());
		return publisherToBeAdded;
	}

}
