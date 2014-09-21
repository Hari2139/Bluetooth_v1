/*
 * This package defines all the Errors generated in this project.
 */
package com.hari.errors;

/**
 * The Class MyError.
 *
 * @author Harikrishna Samala
 * @date March 8, 2010
 */
public class MyError {
	/** The Constant DATABASE_SERVER_CONNECTION. */
	public static final String DATABASE_SERVER_CONNECTION = "ERROR 101: Unable to connect to the database server.";
	//public static final String DRIVER_MANAGER_INIT = "ERROR 102: DriverManager failed to create a Connection.";
	/** The Constant DRIVER_MANAGER_DESTROY. */
	public static final String DRIVER_MANAGER_DESTROY = "ERROR 103: DriverManager failed to destroy a Connection.";
	/** The Constant SQL_QUERY_PREP_STMT. */
	public static final String SQL_QUERY_PREP_STMT = "ERROR 104: Failed to execute SQL query using Prepared Statement.";
	/** The Constant SQL_QUERY_STMT. */
	public static final String SQL_QUERY_STMT = "ERROR 105: Failed to execute SQL query using Statement.";
	/** The Constant DUPLICATE_RECORD. */
	public static final String DUPLICATE_RECORD = "ERROR 106: Duplicate record exception. User constraint Violation.";
	/** The Constant SERVLET_REQUEST_DISPATCH. */
	public static final String SERVLET_REQUEST_DISPATCH = "ERROR 201: Servlet has failed to create RequestDispatcher for the request to";
}
/**
 *  Modification History:
 *
 *  $Log: MyError.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 */
