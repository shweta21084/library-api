package com.api.library.publisher;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.api.library.exception.LibraryResourceAlreadyExistsException;
import com.api.library.exception.LibraryResourceNotFoundException;
import com.api.library.util.LibraryAPIUtils;

@Service
public class PublisherService {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(PublisherService.class);

	private PublisherRepository publisherRepository;

	public PublisherService(PublisherRepository publisherRepository) {
		this.publisherRepository = publisherRepository;
	}

	public void addPublisher(Publisher publisherToBeAdded, String traceId) throws LibraryResourceAlreadyExistsException {

		PublisherEntity publisherEntity = new PublisherEntity(publisherToBeAdded.getName(),
				publisherToBeAdded.getEmailId(), publisherToBeAdded.getPhoneNumber());

		PublisherEntity addedPublisher = null;

		try {
			addedPublisher = publisherRepository.save(publisherEntity);
		} catch (DataIntegrityViolationException de) {
			logger.error("TraceId: "+ traceId + ", Publisher Already Exists!", de);
			throw new LibraryResourceAlreadyExistsException(traceId , "Publisher Already Exists!");
		}

		publisherToBeAdded.setPublisherId(addedPublisher.getPublisherId());
	}

	public Publisher getPublisher(Integer publisherId, String traceId) throws LibraryResourceNotFoundException {
		Optional<PublisherEntity> publisherEntity = publisherRepository.findById(publisherId);
		Publisher publisher = null;
		if (publisherEntity.isPresent()) {
			PublisherEntity pe = publisherEntity.get();
			publisher = createPublisherFromEntity(pe);
		} else {
			throw new LibraryResourceNotFoundException(traceId, "Publisher Id : " + publisherId + " not found");
		}
		return publisher;
	}

	public void updatePublisher(Publisher publisherToBeUpdated, String traceId) throws LibraryResourceNotFoundException {
		Optional<PublisherEntity> publisherEntity = publisherRepository.findById(publisherToBeUpdated.getPublisherId());
		if (publisherEntity.isPresent()) {
			PublisherEntity pe = publisherEntity.get();
			if (LibraryAPIUtils.doesStringValueExists(publisherToBeUpdated.getEmailId())) {
				pe.setEmailId(publisherToBeUpdated.getEmailId());
			}
			if (LibraryAPIUtils.doesStringValueExists(publisherToBeUpdated.getPhoneNumber())) {
				pe.setPhoneNumber(publisherToBeUpdated.getPhoneNumber());
			}
			publisherRepository.save(pe);
			publisherToBeUpdated = createPublisherFromEntity(pe);
		} else {
			throw new LibraryResourceNotFoundException(traceId, "Publisher Id : " + publisherToBeUpdated.getPublisherId() + " not found");
		}
	}

	public void deletePublisher(Integer publisherId, String traceId) throws LibraryResourceNotFoundException {
		try {
			publisherRepository.deleteById(publisherId);
		} catch (EmptyResultDataAccessException e) {
			throw new LibraryResourceNotFoundException(traceId, "Publisher Id : " + publisherId + " not found");
		}
	}

	public List<Publisher> searchPublisher(String name, String traceId) {
		List<PublisherEntity> publisherEntities = null;
		if (LibraryAPIUtils.doesStringValueExists(name)) {
			publisherEntities = publisherRepository.findByNameContaining(name);
		}
		if (publisherEntities != null && !publisherEntities.isEmpty()) {
			return createPublishersForSearchResponse(publisherEntities);
		} else {
			return Collections.emptyList();
		}
	}
	
	private Publisher createPublisherFromEntity(PublisherEntity pe) {
		return new Publisher(pe.getPublisherId(), pe.getName(), pe.getEmailId(), pe.getPhoneNumber());
	}

	private List<Publisher> createPublishersForSearchResponse(List<PublisherEntity> publisherEntities) {
		return publisherEntities.stream().map(pe -> createPublisherFromEntity(pe)).collect(Collectors.toList());
	}

}
