package com.api.library.publisher;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.api.library.exception.LibraryResourceAlreadyExistsException;
import com.api.library.exception.LibraryResourceNotFoundException;

@Service
public class PublisherService {

	private PublisherRepository publisherRepository;

	public PublisherService(PublisherRepository publisherRepository) {
		this.publisherRepository = publisherRepository;
	}

	public void addPublisher(Publisher publisherToBeAdded) throws LibraryResourceAlreadyExistsException {

		PublisherEntity publisherEntity = new PublisherEntity(publisherToBeAdded.getName(),
				publisherToBeAdded.getEmailId(), publisherToBeAdded.getPhoneNumber());
		
		PublisherEntity addedPublisher = null;
		
		try {
			addedPublisher = publisherRepository.save(publisherEntity);
		} catch (DataIntegrityViolationException de) {
			throw new LibraryResourceAlreadyExistsException("Publisher Already Exists!");
		}
		
		publisherToBeAdded.setPublisherId(addedPublisher.getPublisherId());
	}

	public Publisher getPublisher(Integer publisherId) throws LibraryResourceNotFoundException {
		Optional<PublisherEntity> publisherEntity = publisherRepository.findById(publisherId);
		Publisher publisher = null;
		
		if (publisherEntity.isPresent()) {
			PublisherEntity pe = publisherEntity.get();
			publisher = createPublisherFromEntity(pe);
		} else {
			throw new LibraryResourceNotFoundException("Publisher Id : " + publisherId + " not found");
		}
		return publisher;
	}

	private Publisher createPublisherFromEntity(PublisherEntity pe) {
		return new Publisher(pe.getPublisherId(), pe.getName(), pe.getEmailId(), pe.getPhoneNumber());
	}

}
