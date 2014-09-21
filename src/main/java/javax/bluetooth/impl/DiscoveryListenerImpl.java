package javax.bluetooth.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

import com.hari.app.IServiceDiscoveryConstants;

/**
 * Description: Implements the DiscoveryListener interface of the JSR-82 API
 * specifications, that defines methods for device and service discovery. 
 * File: DiscoveryListenerImpl.java $ 
 * Module: javax.bluetooth.impl 
 * Created: Oct 21, 2010
 * @author Hari
 * @version $Revision: 1.1 $ 
 * Last Changed: $Date: 2013/01/09 03:24:49 $ 
 * Last Changed By: $ Hari $
 */
public class DiscoveryListenerImpl implements DiscoveryListener,
		IServiceDiscoveryConstants {
	/** Synchronized object used during remote device discovery */
	private final Object inquiryCompletedEvent;
	/** A Vector of remote devices discovered */
	private final Vector <RemoteDevice> devicesDiscovered;
	/** Synchronized object used during services discovery */
	private final Object servicesSearchCompletedEvent;
	/** A Vector of services discovered */
	private final Vector <String> servicesSearched;
	private static Map <RemoteDevice, Vector <ServiceRecord>> remoteDevicesWithServiceRecords;
	/** The Current Remote Device for which services are being searched. */
	private RemoteDevice currentRemoteDevice;
	
	public DiscoveryListenerImpl(Object inquiryCompletedEvent,
			Vector <RemoteDevice> devicesDiscovered,
			Object servicesSearchCompletedEvent,
			Vector <String> servicesSearched) {
		this.inquiryCompletedEvent = inquiryCompletedEvent;
		this.devicesDiscovered = devicesDiscovered;
		this.servicesSearchCompletedEvent = servicesSearchCompletedEvent;
		this.servicesSearched = servicesSearched;
	}
	
	/**
	 * Called when service(s) are found during a service search.
	 */
	@Override
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
		for (int i = 0; i < servRecord.length; i++) {
			//Second parameter:
			//`true` indicates that this device must play the role of master in connections to this service. 
			//`false` indicates that the local device is willing to be either the master or the slave.
			String url = servRecord[i].getConnectionURL(
					ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
			if (url == null) {
				continue;
			}
			servicesSearched.add(url);
			Vector <ServiceRecord> services = getRemoteDevicesWithServiceRecords()
					.get(currentRemoteDevice);
			if (services == null) {
				services = new Vector <ServiceRecord>();
			}
			services.add(servRecord[i]);
			getRemoteDevicesWithServiceRecords().put(currentRemoteDevice,
					services);
			DataElement serviceNameDataElement = servRecord[i]
					.getAttributeValue(ATTRIBUTE_SERVICE_NAME);
			if (serviceNameDataElement != null) {
				System.out.println("Service: "
						+ serviceNameDataElement.getValue() + " (URL: " + url
						+ ")");
			}
		}
	}
	
	/**
	 * Called when a service search is completed or was terminated because of an
	 * error.
	 * 
	 * @param transID the transaction ID identifying the request which initiated the service search.
	 * @param respCode the response code that indicates the status of the transaction.
	 */
	@Override
	public void serviceSearchCompleted(int transID, int respCode) {
		switch (respCode) {
			case SERVICE_SEARCH_COMPLETED:
				System.out.println("Service search completed normally.");
				break;
			case SERVICE_SEARCH_TERMINATED:
				System.err.println("Service search request was cancelled.");
				break;
			case SERVICE_SEARCH_ERROR:
				System.err
						.println("Service search: error occurred while processing the request.");
				break;
			case SERVICE_SEARCH_NO_RECORDS:
				System.err
						.println("No records were found during the service search.");
				break;
			case SERVICE_SEARCH_DEVICE_NOT_REACHABLE:
				System.err
						.println("The device specified in the search request could not be reached "
								+ "or the local device could not establish a connection to the remote device.");
				break;
			default:
				break;
		}
		synchronized (servicesSearchCompletedEvent) {
			servicesSearchCompletedEvent.notifyAll();
		}
	}
	
	/**
	 * Called when a device is found during an inquiry.
	 * 
	 * @param remoteDevice
	 * @param remoteDeviceClass
	 */
	@Override
	public void deviceDiscovered(RemoteDevice remoteDevice,
			DeviceClass remoteDeviceClass) {
		System.out.println("Device " + remoteDevice.getBluetoothAddress()
				+ " discovered.");
		devicesDiscovered.add(remoteDevice);
		getRemoteDevicesWithServiceRecords().put(remoteDevice, null);
		try {
			System.out.println(remoteDevice.getFriendlyName(false));
		}
		catch (IOException e) {
			System.err
					.println("Failed to get friendly name for the remote BT device.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Called when an inquiry is completed.
	 * 
	 * @param discType the type of request that was completed; 
	 * either INQUIRY_COMPLETED, INQUIRY_TERMINATED, or INQUIRY_ERROR
	 */
	@Override
	public void inquiryCompleted(int discType) {
		switch (discType) {
			case INQUIRY_COMPLETED:
				System.out.println("Device inquiry completed normally.");
				break;
			case INQUIRY_TERMINATED:
				System.err
						.println("Device inquiry completed. Action: Cancelled");
				break;
			case INQUIRY_ERROR:
				System.err.println("Device inquiry completed with ERRORS.");
				break;
			default:
				break;
		}
		synchronized (inquiryCompletedEvent) {
			inquiryCompletedEvent.notifyAll();
		}
	}
	
	/**
	 * Returns the actual Map if available, else an empty Map when the object is null.
	 * 
	 * @return getRemoteDevicesWithServiceRecords
	 */
	public static Map <RemoteDevice, Vector <ServiceRecord>> getRemoteDevicesWithServiceRecords() {
		if (remoteDevicesWithServiceRecords == null) {
			remoteDevicesWithServiceRecords = new HashMap <RemoteDevice, Vector <ServiceRecord>>();
		}
		return remoteDevicesWithServiceRecords;
	}
	
	/**
	 * Gets the Current Remote Device for which services are being searched.
	 */
	public RemoteDevice getCurrentRemoteDevice() {
		return currentRemoteDevice;
	}
	
	/**
	 * Sets the Current Remote Device for which services are being searched.
	 */
	public void setCurrentRemoteDevice(RemoteDevice currentRemoteDevice) {
		this.currentRemoteDevice = currentRemoteDevice;
	}
}
/**
 * Modification History: 
 * 
 * $Log: DiscoveryListenerImpl.java,v $
 * Revision 1.1  2013/01/09 03:24:49  Hari
 * Initial commit to the new CVS server.
 *
 * Revision 1.3  2010/10/24 20:01:41  Hari
 * Introduced a Map that stores all the remote devices that are discovered along with their supported services (which are stored in Vector<ServiceRecord>).
 *
 * Revision 1.2  2010/10/23 23:14:52  Hari
 * Implemented methods for service discovery.
 * 
 * Revision 1.1 2010/10/23 08:00:05 Hari 
 * Initial commit.
 */
