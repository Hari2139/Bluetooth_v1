package com.hari.exceptions;

/**
 * The Class DatabaseException.
 */
public class DatabaseException extends Exception {
	
	private static final long serialVersionUID = 1584591127324575143L;
	/** The error message. */
	private String errorMessage;
	
	/**
	 * Instantiates a new database exception.
	 *
	 * @param message the message
	 */
	public DatabaseException(String message) {
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
 *  $Log: DatabaseException.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 */
