package com.hari.util;

import java.util.Set;

import javax.bluetooth.RemoteDevice;
import javax.bluetooth.impl.DiscoveryListenerImpl;

/**
 * Description: This is the utility class for processing the devices discovered.
 * File: $ DevicesUtil.java $
 * Module:  com.hari.util
 * Created: Oct 24, 2010
 * @author Hari 
 * @version $Revision: 1.1 $
 * Last Changed: $Date: 2013/01/09 03:24:49 $
 * Last Changed By: $ Hari $
 */
public class DevicesUtil {
	/**
	 * Return the remote device objectwhose address is present in the given URL string.
	 * @param url
	 * @return RemoteDevice
	 */
	public static RemoteDevice getRemoteDeviceFromUrl(String url) {
		String[] subStrings = url.split(":");
		if (subStrings != null && subStrings.length >= 2) {
			//Obtain address from the URL string
			String inquiredRemoteDeviceAddress = subStrings[1].substring(2);
			System.out.println("Obtaining Remote Device for: "
					+ inquiredRemoteDeviceAddress);
			Set <RemoteDevice> remoteDevisesSet = DiscoveryListenerImpl
					.getRemoteDevicesWithServiceRecords().keySet();
			if (remoteDevisesSet != null && remoteDevisesSet.size() > 0) {
				for (RemoteDevice remoteDevice : remoteDevisesSet) {
					if (remoteDevice != null
							&& inquiredRemoteDeviceAddress.equals(remoteDevice
									.getBluetoothAddress())) {
						return remoteDevice;
					}
				}
			}
		}
		return null;
	}
}
/**
 *  Modification History:
 *
 *  $Log: DevicesUtil.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 *  Revision 1.2  2011/02/17 15:58:56  Hari
 *  Added null checks for potential null pointer references.
 *
 *  Revision 1.1  2010/10/24 20:04:28  Hari
 *  Initial commit.
 *
 */
