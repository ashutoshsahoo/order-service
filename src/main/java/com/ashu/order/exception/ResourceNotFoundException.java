package com.ashu.order.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7692954921080847121L;

	public ResourceNotFoundException(String resourceType, String id) {
		super(resourceType + " not found for requested id=" + id);
	}

}
