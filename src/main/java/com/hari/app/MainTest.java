package com.hari.app;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Description: CLient.
 * File: $ MainTest.java $
 * Module:  com.hari.app
 * Created: Dec 31, 2012
 * @author Hari 
 * @version $Revision: 1.1 $
 * Last Changed: $Date: 2013/01/09 03:24:49 $
 * Last Changed By: $ Hari $
 */
public class MainTest {
	/** The logger. */
	private static Log logger = LogFactory.getLog(MainTest.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Vector <String> services = ServicesSearch.getInstance()
				.getServicesDiscovered();
		logger.info("************************************************************");
		logger.info("************************************************************");
		if (services == null || services.size() == 0) {
			logger.info("ZERO services are available");
		}
		else {
			logger.info(services.size() + " services are available");
			for (String url : services) {
				logger.info(url);
			}
		}
		logger.info("************************************************************");
		logger.info("                       END                                  ");
		logger.info("************************************************************");
	}
}
/**
 *  Modification History:
 *
 *  $Log: MainTest.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 *
 */
