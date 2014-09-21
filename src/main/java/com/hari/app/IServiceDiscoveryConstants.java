package com.hari.app;

/**
 * Description: This interface defines the constants used for service discovery.
 * File: $ IServiceDiscoveryConstants.java $
 * Module:  com.hari.app
 * Created: Oct 23, 2010
 * @author Hari 
 * @version $Revision: 1.1 $
 * Last Changed: $Date: 2013/01/09 03:24:49 $
 * Last Changed By: $ Hari $
 */
public interface IServiceDiscoveryConstants {
	/** Attribute IDs used in ServiceRecord */
	// Atrribute Name = Attribute ID; //Attribute Value Type
	public static int ATTRIBUTE_SERVICE_RECORD_HANDLE = 0x0000; //32-bit unsigned integer
	public static int ATTRIBUTE_SERVICE_CLASS_ID_LIST = 0x0001; //DATSEQ of UUIDs
	public static int ATTRIBUTE_SERVICE_RECORD_STATE = 0x0002; //32-bit unsigned integer
	public static int ATTRIBUTE_SERVICE_ID = 0x0003; //UUID
	public static int ATTRIBUTE_PROTOCOL_DESCRIPTOR_LIST = 0x0004; //DATSEQ of DATSEQ of UUID and optional parameters
	public static int ATTRIBUTE_BROWSE_GROUP_LIST = 0x0005; //DATSEQ of UUIDs
	public static int ATTRIBUTE_LANGUAGE_BASED_ATTRIBUTE_ID_LIST = 0x0006; //DATSEQ of DATSEQ triples
	public static int ATTRIBUTE_SERVICE_INFO_TIME_TO_LIVE = 0x0007; //32-bit unsigned integer
	public static int ATTRIBUTE_SERVICE_AVAILABILITY = 0x0008; //8-bit unsigned integer
	public static int ATTRIBUTE_BLUETOOTH_PROFILE_DESCRIPTOR_LIST = 0x0009; //DATSEQ of DATSEQ pairs
	public static int ATTRIBUTE_DOCUMENTATION_URL = 0x000A; //URL
	public static int ATTRIBUTE_CLIENT_EXECUTABLE_URL = 0x000B; //URL
	public static int ATTRIBUTE_ICON_URL = 0x000C; //URL
	public static int ATTRIBUTE_VERSION_NUMBER_LIST = 0x0200; //DATSEQ of 16-bit unsigned integers
	public static int ATTRIBUTE_SERVICE_DATABSE_STATE = 0x0201; //32-bit unsigned integer
	public static int ATTRIBUTE_SERVICE_NAME = 0x0100; //String
	//TODO: Verify these
	public static int ATTRIBUTE_SERVICE_DESCRIPTION = 0x0101; //String
	public static int ATTRIBUTE_PROVIDER_NAME = 0x0102; //String
}
/**
 *  Modification History:
 *
 *  $Log: IServiceDiscoveryConstants.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 *  Revision 1.1  2010/10/23 23:14:19  Hari
 *  Initial commit.
 *
 */
