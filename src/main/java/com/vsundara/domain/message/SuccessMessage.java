
//Namespace
package com.vsundara.domain.message;

//Imports
import org.springframework.http.HttpStatus;

/**
 * Class that represents the a success message
 */
public class SuccessMessage extends Message{


	/**
	 * Constructor
	 * @param status
	 * @param message
	 */
	public SuccessMessage(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}



}
