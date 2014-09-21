package com.hari.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hari.errors.MyError;
import com.hari.exceptions.ServerConnectionException;

/**
 * Description:
 * File: $ DatabaseJDBCHelper.java $
 * Module:  com.hari.jdbc
 * Created: May 19, 2012
 * @author Hari 
 * @version $Revision: 1.1 $
 * Last Changed: $Date: 2013/01/09 03:24:49 $
 * Last Changed By: $ Hari $
 */
public class DatabaseJDBCHelper {
	/** The user connection. */
	private static Connection userConnection;
	/** The Oracle database url. */
	private static String OracleDatabaseURL = "//localhost:9001/samaladb";
	/** The admin name. */
	private static String adminName = "sa";
	/** The admin password. */
	private static String adminPassword = "";
	/** The SINGLETON. */
	private static DatabaseJDBCHelper SINGLETON;
	
	/**
	 * Gets the single instance of DatabaseJDBCHelper.
	 *
	 * @return single instance of DatabaseJDBCHelper
	 * @throws ServerConnectionException the server connection exception
	 */
	public static DatabaseJDBCHelper getInstance()
			throws ServerConnectionException {
		if (SINGLETON == null) {
			SINGLETON = new DatabaseJDBCHelper();
			init();
		}
		return SINGLETON;
	}
	
	public static void destroyInstance() throws ServerConnectionException {
		destroy();
	}
	
	/**
	 * Instantiates a new database jdbc helper.
	 */
	protected DatabaseJDBCHelper() {
		super();
	}
	
	//Initialization method to open a connection
	/**
	 * Inits the.
	 *
	 * @throws ServerConnectionException the server connection exception
	 */
	private static void init() throws ServerConnectionException {
		try {
			userConnection = DriverManager.getConnection(OracleDatabaseURL,
					adminName, adminPassword);
		}
		catch (SQLException ex) {
			throw new ServerConnectionException(
					MyError.DATABASE_SERVER_CONNECTION + ex.getMessage());
		}
	}//End of init()
	
	//Method to destroy connection
	/**
	 * Destroy.
	 *
	 * @throws ServerConnectionException the server connection exception
	 */
	private static void destroy() throws ServerConnectionException {
		try {
			if (userConnection != null)
				userConnection.close();
		}
		catch (SQLException ex) {
			throw new ServerConnectionException(MyError.DRIVER_MANAGER_DESTROY
					+ ex.getMessage());
		}
	}//End of destroy()
}
/**
 *  Modification History:
 *
 *  $Log: DatabaseJDBCHelper.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 */
