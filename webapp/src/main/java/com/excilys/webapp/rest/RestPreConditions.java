package com.excilys.webapp.rest;

import org.springframework.web.client.ResourceAccessException;

public class RestPreConditions {
	
	RestPreConditions() {
	}
	
    public static <T> T checkFound(T resource) {
        if (resource == null) {
            throw new ResourceAccessException("Can't find the resource");
        }
        return resource;
    }
}
