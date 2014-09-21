package com.hari.app;

import java.util.Vector;

import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.bluetooth.impl.DiscoveryListenerImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hari.exceptions.DatabaseException;
import com.hari.exceptions.ServerConnectionException;
import com.hari.service.PersistenceService;
import com.intel.bluetooth.BluetoothConsts;

/**
 * Description: Search for required services of all the remote bluetooth devices discovered.
 * File: $ ServicesSearch.java $
 * Module:  com.hari.app
 * Created: Oct 23, 2010
 * @author Hari 
 * @version $Revision: 1.1 $
 * Last Changed: $Date: 2013/01/09 03:24:49 $
 * Last Changed By: $ Hari $
 */
public class ServicesSearch implements IServiceDiscoveryConstants {
	/** The Constant SERVICE_IDENTIFIER. */
	public static final String SERVICE_IDENTIFIER = "SERVICE";
	/** The logger. */
	private static Log logger = LogFactory.getLog(ServicesSearch.class);
	/** The singleton. */
	private static ServicesSearch SINGLETON = new ServicesSearch();
	/** Store the list of services that are discovered. */
	private Vector <String> servicesFound = new Vector <String>();

	/**
	 * Gets the single instance of ServicesSearch.
	 *
	 * @return single instance of ServicesSearch
	 */
	public static ServicesSearch getInstance() {
		return SINGLETON;
	}

	/**
	 * Instantiates a new services search.
	 */
	private ServicesSearch() {
		super();
	}

	/**
	 * Begin services search.
	 */
	private void beginServicesSearch() {
		//First run the device discovery
		servicesFound.clear();
		final Object servicesSearchCompletedEvent = new Object();
		int[] attrSet = new int[] { ATTRIBUTE_SERVICE_NAME };
		UUID[] uuidSet = new UUID[] { BluetoothConsts.L2CAP_PROTOCOL_UUID };
		DiscoveryListenerImpl discoveryListener = new DiscoveryListenerImpl(
				null, null, servicesSearchCompletedEvent, servicesFound);
		String remoteDeviceFriendlyName = null;
		//Loop through all the remote devices and search for services
		for (RemoteDevice remoteDevice : RemoteDeviceDiscovery.getInstance()
				.getDevicesDiscovered()) {
			synchronized (servicesSearchCompletedEvent) {
				try {
					remoteDeviceFriendlyName = remoteDevice
							.getFriendlyName(false);
					logger.info("Searching services for: "
							+ remoteDeviceFriendlyName + "...");
					discoveryListener.setCurrentRemoteDevice(remoteDevice);
					LocalDevice
							.getLocalDevice()
							.getDiscoveryAgent()
							.searchServices(attrSet, uuidSet, remoteDevice,
									discoveryListener);
					servicesSearchCompletedEvent.wait();
				}
				catch (Exception e) {
					logger.error("Failed to retrieve services for the remote BT device: "
							+ remoteDeviceFriendlyName
							+ " ("
							+ remoteDevice.getBluetoothAddress() + ")");
					e.printStackTrace();
				}
			}
		}
		persistDiscoveredServicesInformationInDb();
	}

	/**
	 * Gets the bluetooth services from database.
	 *
	 * @return the bluetooth services from database
	 */
	private Vector <String> getBluetoothServicesFromDatabase() {
		try {
			return PersistenceService.getInstance().getRemoteServices();
		}
		catch (ServerConnectionException e) {
			logger.error("Failed to get Service Information form DB.");
			e.printStackTrace();
		}
		catch (DatabaseException e) {
			logger.error("Failed to get Service Information form DB.");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the services discovered.
	 *
	 * @return the services discovered
	 */
	public Vector <String> getServicesDiscovered() {
		if (servicesFound == null || servicesFound.size() == 0) {
			//Check the DB for any existing services
			servicesFound = getBluetoothServicesFromDatabase();
			if (servicesFound == null || servicesFound.size() == 0) {
				logger.info("There are no services currently in the database.");
				beginServicesSearch();
			}
		}
		return servicesFound;
	}

	/**
	 * Persist discovered services information in db.
	 */
	private void persistDiscoveredServicesInformationInDb() {
		try {
			PersistenceService service = PersistenceService.getInstance();
			for (String serviceUrl : servicesFound) {
				service.saveTransaction(serviceUrl, SERVICE_IDENTIFIER);
			}
		}
		catch (ServerConnectionException e) {
			logger.error("Failed to persist Service Information.");
			e.printStackTrace();
		}
		catch (DatabaseException e) {
			logger.error("Failed to persist Service Information.");
			e.printStackTrace();
		}
	}
}
/**
 *  Modification History:
 *
 *  $Log: ServicesSearch.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 *  Revision 1.2  2010/10/24 20:07:06  Hari
 *  Used DiscoveryListenerImpl from the local implementation instead of DiscoveryListener from the BlueCove implementation (JAR).
 *
 *  Revision 1.1  2010/10/23 23:13:51  Hari
 *  Initial commit.
 *
 */
