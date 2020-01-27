package com.api.library.exception;

public class LibraryResourceAlreadyExistsException extends Exception {
	
	private String traceId;

	public LibraryResourceAlreadyExistsException(String traceId, String message) {
		super(message);
		this.traceId = traceId;
	}

	public String getTraceId() {
		return traceId;
	}
}
