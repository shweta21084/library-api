package com.api.library.publisher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PUBLISHER")
public class PublisherEntity {
	
	@Id
	@Column (name = "Publisher_Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publisherId_generator")
	@SequenceGenerator(name = "publisherId_generator", sequenceName = "PUBLISHER_SEQUENCE", allocationSize = 50)
	private Integer publisherId;
	
	@Column (name = "Name")
	private String name;
	
	@Column (name = "Email_Id")
	private String emailId;
	
	@Column (name = "Phone_Number")
	private String phoneNumber;
	
	public PublisherEntity() {
	}
	
	public PublisherEntity(String name, String emailId, String phoneNumber) {
		this.name = name;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
	}

	public Integer getPublisherId() {
		return publisherId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
