package com.api.library.exception;

public class LibraryResourceBadRequestException extends Exception {
	
	private String traceId;

	public LibraryResourceBadRequestException(String traceId, String message) {
		super(message);
		this.traceId = traceId;
	}

	public String getTraceId() {
		return traceId;
	}
}
