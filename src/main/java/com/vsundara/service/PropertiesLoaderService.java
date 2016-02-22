
//Namespace
package com.vsundara.service;

/**
 * Class that handles application properties/messages
 */
public interface PropertiesLoaderService {
	String getMessage(String key);
	String getProperty(final String key);
}
