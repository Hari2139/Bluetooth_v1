package com.hari.exceptions;

import com.hari.errors.MyError;

/**
 * The Class ServerConnectionException.
 */
public class ServerConnectionException extends Exception {
	
	private static final long serialVersionUID = 3125124642204917559L;
	/** The error message. */
	private String errorMessage = MyError.DATABASE_SERVER_CONNECTION;
	
	/**
	 * Instantiates a new server connection exception.
	 */
	public ServerConnectionException() {
	}
	
	/**
	 * Instantiates a new server connection exception.
	 *
	 * @param message the message
	 */
	public ServerConnectionException(String message) {
		this.errorMessage = message;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return (this.errorMessage);
	}
}
/**
 *  Modification History:
 *
 *  $Log: ServerConnectionException.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 */
