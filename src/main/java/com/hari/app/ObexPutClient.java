package com.hari.app;

import java.io.IOException;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.obex.ClientSession;
import javax.obex.HeaderSet;
import javax.obex.Operation;
import javax.obex.ResponseCodes;

/**
 * Description: Transfer a file using OBEX (object exchange) object push service.
 * File: $ ObexPutClient.java $
 * Module:  com.hari.app
 * Created: Oct 23, 2010
 * @author Hari 
 * @version $Revision: 1.1 $
 * Last Changed: $Date: 2013/01/09 03:24:49 $
 * Last Changed By: $ Hari $
 */
public class ObexPutClient {
	private static String macBookUrl = "btgoep://001D4F86194A:10;authenticate=true;encrypt=false;master=true";
	//private static String lGUrl = "btgoep://0025E5C0EFF7:6;authenticate=true;encrypt=false;master=false";
	
	public static void main(String[] args) {
		System.out.println("Connecting to " + macBookUrl);
		try {
			ClientSession session = (ClientSession) Connector.open(macBookUrl);
			HeaderSet reply = session.connect(null);
			if (reply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK) {
				System.err.println("Failed to connect.");
				return;
			}
			HeaderSet requestHeaderSet = session.createHeaderSet();
			requestHeaderSet.setHeader(HeaderSet.NAME, "Hello.txt");
			requestHeaderSet.setHeader(HeaderSet.TYPE, "text");
			//Create PUT operation
			Operation operation = session.put(requestHeaderSet);
			//Send some text
			byte data[] = "This text is sent from Hari's iMac".getBytes();
			OutputStream os = operation.openOutputStream();
			os.write(data);
			os.flush();
			os.close();
			operation.close();
			session.disconnect(null);
			session.close();
		}
		catch (IOException e) {
			System.err.println("Connection FAILURE!");
			e.printStackTrace();
		}
	}
}
/**
 *  Modification History:
 *
 *  $Log: ObexPutClient.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 *  Revision 1.1  2010/10/24 16:52:57  Hari
 *  Initial commit.
 *
 */
