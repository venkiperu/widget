
//Namespace
package com.vsundara.domain.message;

//Imports
import org.springframework.http.HttpStatus;

/**
 * Class that represents the response message
 */
public class Message {
    /** The status. */
    protected HttpStatus status;

    /** The message. */
    protected String message;

    /**
     * Constructor
     */
    public Message() {}

    //Getters and Setters

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
