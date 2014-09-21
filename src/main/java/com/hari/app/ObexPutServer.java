package com.hari.app;

import java.io.IOException;
import java.io.InputStream;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.obex.HeaderSet;
import javax.obex.Operation;
import javax.obex.ResponseCodes;
import javax.obex.ServerRequestHandler;
import javax.obex.SessionNotifier;

/**
 * Description: Create and publish OBEX Object Push service.
 * File: $ ObexPutServer.java $
 * Module:  com.hari.app
 * Created: Oct 25, 2010
 * @author Hari 
 * @version $Revision: 1.1 $
 * Last Changed: $Date: 2013/01/09 03:24:49 $
 * Last Changed By: $ Hari $
 */
public class ObexPutServer {
	/** The Constant serverUUID. */
	private static final String serverUUID = "4";
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
			//iMac BT address: D8A25E871F8B
			SessionNotifier serverConnection = (SessionNotifier) Connector
					.open("btgoep://localhost:"
							+ serverUUID
							+ ";name=obexExample;authenticate=true;encrypt=false;master=true");
			int count = 0;
			while (count < 2) {
				RequestHandler request = new RequestHandler();
				request.setConnectionID(count);
				//Open the connection and spawn a thread, that starts a session and listens.
				serverConnection.acceptAndOpen(request);
				System.out.println("Received OBEX connection " + ++count);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Define an event listener that will respond to OBEX requests made to the server.
	 */
	private static class RequestHandler extends ServerRequestHandler {
		/* (non-Javadoc)
		 * @see javax.obex.ServerRequestHandler#onPut(javax.obex.Operation)
		 */
		@Override
		public int onPut(Operation op) {
			try {
				HeaderSet hs = op.getReceivedHeaders();
				String name = (String) hs.getHeader(HeaderSet.NAME);
				System.out.println("Put name = " + name);
				InputStream is = op.openInputStream();
				StringBuffer buf = new StringBuffer();
				int data;
				while ((data = is.read()) != -1) {
					buf.append((char) data);
				}
				System.out.println("Got: " + buf.toString());
				op.close();
				return ResponseCodes.OBEX_HTTP_OK;
			}
			catch (IOException e) {
				e.printStackTrace();
				return ResponseCodes.OBEX_HTTP_UNAVAILABLE;
			}
		}
		
		/* (non-Javadoc)
		 * @see javax.obex.ServerRequestHandler#onConnect(javax.obex.HeaderSet, javax.obex.HeaderSet)
		 */
		@Override
		public int onConnect(HeaderSet request, HeaderSet reply) {
			System.out
					.println("Input connection request is being processed...");
			return ResponseCodes.OBEX_HTTP_OK;
		}
		
		/* (non-Javadoc)
		 * @see javax.obex.ServerRequestHandler#onDisconnect(javax.obex.HeaderSet, javax.obex.HeaderSet)
		 */
		@Override
		public void onDisconnect(HeaderSet request, HeaderSet reply) {
			System.out.println("onDisconnect");
		}
		
		/* (non-Javadoc)
		 * @see javax.obex.ServerRequestHandler#onAuthenticationFailure(byte[])
		 */
		@Override
		public void onAuthenticationFailure(byte[] userName) {
			System.out.println("AuthenticationFailure");
		}
		
		/* (non-Javadoc)
		 * @see javax.obex.ServerRequestHandler#onSetPath(javax.obex.HeaderSet, javax.obex.HeaderSet, boolean, boolean)
		 */
		@Override
		public int onSetPath(HeaderSet request, HeaderSet reply,
				boolean backup, boolean create) {
			System.out.println("onSetPath");
			return ResponseCodes.OBEX_HTTP_OK;
		}
	}
}
/**
 *  Modification History:
 *
 *  $Log: ObexPutServer.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 *  Revision 1.1  2010/10/26 04:12:04  Hari
 *  Initial commit.
 *
 */
