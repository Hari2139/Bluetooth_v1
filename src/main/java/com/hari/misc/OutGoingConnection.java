package com.hari.misc;

import java.io.IOException;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connection;
import javax.microedition.io.Connector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hari.app.ServicesSearch;
import com.hari.util.DevicesUtil;

/**
 * Description: Test all outgoing connections to LG cell phone.
 * File: $ OutGoingConnection.java $
 * Module:  com.hari.misc
 * Created: Oct 24, 2010
 * @author Hari 
 * @version $Revision: 1.1 $
 * Last Changed: $Date: 2013/01/09 03:24:49 $
 * Last Changed By: $ Hari $
 */
public class OutGoingConnection {
	private static final Log logger = LogFactory
			.getLog(OutGoingConnection.class);
	//URLs for the services supported by iPhone via bluetooth.
	/** The phonebook url. */
	private static String phonebookUrl = "btgoep://60C54763C025:13;authenticate=false;encrypt=false;master=false";
	/** The pan network access profile url. */
	private static String panNetworkAccessProfileUrl = "btl2cap://60C54763C025:000f;authenticate=false;encrypt=false;master=false";
	/** The avrcp device url. */
	private static String avrcpDeviceUrl = "btl2cap://60C54763C025:0017;authenticate=false;encrypt=false;master=false";
	/** The handsfree gateway url. */
	private static String handsfreeGatewayUrl = "btspp://60C54763C025:8;authenticate=false;encrypt=false;master=false";

	/** The audio source url. */
	//private static String audioSourceUrl = "btl2cap://60C54763C025:0019;authenticate=false;encrypt=false;master=false";
	/** The wireless_i a p_ url. */
	//private static String wirelessIapUrl = "btspp://60C54763C025:1;authenticate=false;encrypt=false;master=false";
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		String url = null;
		url = phonebookUrl;
		url = panNetworkAccessProfileUrl;
		url = avrcpDeviceUrl;
		url = handsfreeGatewayUrl; //worked
		//url = audioSourceUrl;
		//url = wirelessIapUrl;
		ServicesSearch.getInstance().getServicesDiscovered();
		//Thread.currentThread().join();
		try {
			RemoteDevice remoteDevice = DevicesUtil.getRemoteDeviceFromUrl(url);
			if (remoteDevice != null) {
				logger.info("Connecting to "
						+ remoteDevice.getFriendlyName(false));
				Connection session = Connector.open(url);
				session.close();
			}
			else {
				logger.error("ERROR connecting to " + url);
			}
		}
		catch (IOException e) {
			logger.error("ERROR connecting to " + url);
			e.printStackTrace();
		}
		logger.info("**DONE**");
	}
}
/**
 *  Modification History:
 *
 *  $Log: OutGoingConnection.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 *  Revision 1.1  2010/10/24 19:59:17  Hari
 *  Initial commit.
 *
 */
