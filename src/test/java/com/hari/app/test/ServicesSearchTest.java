package com.hari.app.test;

import static com.hari.app.IServiceDiscoveryConstants.ATTRIBUTE_SERVICE_NAME;
import static javax.bluetooth.impl.DiscoveryListenerImpl.getRemoteDevicesWithServiceRecords;

import java.io.IOException;

import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hari.app.ServicesSearch;

public class ServicesSearchTest extends TestCase {
	@Before
	public void setUp() throws Exception {
		ServicesSearch.getInstance().getServicesDiscovered();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDiscovery() {
		for (RemoteDevice remoteDevice : getRemoteDevicesWithServiceRecords()
				.keySet()) {
			assertNotNull(remoteDevice);
			if (remoteDevice != null) {
				try {
					System.out.println("____________________________________");
					System.out.println("Listing services for remote device: "
							+ remoteDevice.getFriendlyName(false));
					for (ServiceRecord service : getRemoteDevicesWithServiceRecords()
							.get(remoteDevice)) {
						assertNotNull(service);
						if (service != null) {
							System.out.println("Service: "
									+ service.getAttributeValue(
											ATTRIBUTE_SERVICE_NAME).getValue());
						}
						else {
							System.out
									.println("Services were NOT properly identified. See DiscoveryListenerImpl.java");
						}
					}
					System.out.println("____________________________________");
				}
				catch (IOException e) {
					assertNull(e);
					System.out.println("Operation FAILURE!");
					e.printStackTrace();
				}
			}
		}
	}
}
