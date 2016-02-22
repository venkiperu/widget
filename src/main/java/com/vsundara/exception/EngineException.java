
//Namespace;
package com.vsundara.exception;

/**
 * Class that represents the user already exist exception
 */
public class EngineException extends RuntimeException {

    /**
     * Constructor
     * @param message
     */
	public EngineException(String message) {
		super(message);
	}

}
