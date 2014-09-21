package com.hari.app;

import java.util.Vector;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.impl.DiscoveryListenerImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hari.exceptions.DatabaseException;
import com.hari.exceptions.ServerConnectionException;
import com.hari.service.PersistenceService;

/**
 * Description: Discover remote bluetooth devices.
 * File: $ RemoteDeviceDiscovery.java $
 * Module:  com.hari.app
 * Created: Oct 21, 2010
 * @author Hari 
 * @version $Revision: 1.1 $
 * Last Changed: $Date: 2013/01/09 03:24:49 $
 * Last Changed By: $ Hari $
 */
public class RemoteDeviceDiscovery {
	/** The logger. */
	private static Log logger = LogFactory.getLog(RemoteDeviceDiscovery.class);
	/** The singleton. */
	private static RemoteDeviceDiscovery SINGLETON = new RemoteDeviceDiscovery();
	/** Store the list of remote devices that are discovered. */
	private Vector <RemoteDevice> devicesDiscovered = new Vector <RemoteDevice>();

	/**
	 * Gets the single instance of RemoteDeviceDiscovery.
	 *
	 * @return single instance of RemoteDeviceDiscovery
	 */
	public static RemoteDeviceDiscovery getInstance() {
		return SINGLETON;
	}

	private RemoteDeviceDiscovery() {
		super();
	}

	/**
	 * Gets the devices discovered.
	 *
	 * @return the devices discovered
	 */
	public Vector <RemoteDevice> getDevicesDiscovered() {
		if (devicesDiscovered == null || devicesDiscovered.size() == 0) {
			//Check the DB for any existing devices
			//devicesDiscovered = getBluetoothDevicesFromDatabase();
			//if (devicesDiscovered == null || devicesDiscovered.size() == 0) {
			beginDeviceDiscovery();
			//}
		}
		return devicesDiscovered;
	}

	/**
	 * Gets the bluetooth devices from database.
	 *
	 * @return the bluetooth devices from database
	 */
	private Vector <RemoteDevice> getBluetoothDevicesFromDatabase() {
		try {
			return PersistenceService.getInstance().getRemoteDevices();
		}
		catch (ServerConnectionException e) {
			logger.error("Failed to get Device Information...");
			e.printStackTrace();
		}
		catch (DatabaseException e) {
			logger.error("Failed to get Device Information...");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Begin device discovery.
	 */
	private void beginDeviceDiscovery() {
		final Object inquiryCompletedEvent = new Object();
		devicesDiscovered.clear();
		DiscoveryListener discoveryListener = new DiscoveryListenerImpl(
				inquiryCompletedEvent, devicesDiscovered, null, null);
		synchronized (inquiryCompletedEvent) {
			try {
				//Start inquiry for remote devices (hook the listener to the local devices's discovery agent
				boolean started = LocalDevice.getLocalDevice()
						.getDiscoveryAgent()
						.startInquiry(DiscoveryAgent.GIAC, discoveryListener);
				if (started) {
					logger.info("Wait for device inquiry to complete...");
					inquiryCompletedEvent.wait();
					logger.info(devicesDiscovered.size() + " device(s) found.");
				}
			}
			catch (Exception e) {
				logger.error("Failed to discover remote BT devices.");
				e.printStackTrace();
			}
		}
		//persistDiscoveredDevicesInformationInDb();
	}

	/**
	 * Persist discovered devices information in db.
	 */
	private void persistDiscoveredDevicesInformationInDb() {
		try {
			PersistenceService service = PersistenceService.getInstance();
			for (RemoteDevice remoteDevice : devicesDiscovered) {
				service.saveTransaction(remoteDevice,
						RemoteDevice.class.getName());
			}
		}
		catch (ServerConnectionException e) {
			logger.error("Failed to persist Device Information...");
			e.printStackTrace();
		}
		catch (DatabaseException e) {
			logger.error("Failed to persist Device Information...");
			e.printStackTrace();
		}
	}
}
/**
 *  Modification History:
 *
 *  $Log: RemoteDeviceDiscovery.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 *  Revision 1.2  2010/10/23 23:16:25  Hari
 *  Added description.
 *
 *  Revision 1.1  2010/10/23 08:00:05  Hari
 *  Initial commit.
 *
 */
