package com.hari.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.util.List;
import java.util.Vector;

import javax.bluetooth.RemoteDevice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.hari.exceptions.DatabaseException;
import com.hari.exceptions.ServerConnectionException;
import com.hari.orm.TransactionBean;

/**
 * Description:
 * File: $ PersistenceService.java $
 * Module:  com.hari.service
 * Created: May 19, 2012
 * @author Hari 
 * @version $Revision: 1.1 $
 * Last Changed: $Date: 2013/01/09 03:24:49 $
 * Last Changed By: $ Hari $
 */
public class PersistenceService {
	/** The logger. */
	private static Log logger = LogFactory.getLog(PersistenceService.class);
	/** The singleton. */
	private static PersistenceService SINGLETON = new PersistenceService();

	/**
	 * Gets the single instance of PersistenceService.
	 *
	 * @return single instance of PersistenceService
	 */
	public static PersistenceService getInstance() {
		return SINGLETON;
	}

	/**
	 * Instantiates a new persistence service.
	 */
	private PersistenceService() {
		super();
	}

	/**
	 * Gets the byte array from.
	 *
	 * @param object the object
	 * @return the byte array from
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private byte[] getByteArrayFrom(Object object) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);
		objectOutputStream.writeObject(object);
		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * Gets the object from.
	 *
	 * @param byteArray the byte array
	 * @return the object from
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	/*private Object getObjectFrom(byte[] byteArray) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				byteArray);
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream);
		return objectInputStream.readObject();
	}*/
	/**
	 * Gets the remote devices.
	 *
	 * @return the remote devices
	 * @throws ServerConnectionException the server connection exception
	 * @throws DatabaseException the database exception
	 */
	@SuppressWarnings("unchecked")
	public Vector <RemoteDevice> getRemoteDevices()
			throws ServerConnectionException, DatabaseException {
		Vector <RemoteDevice> remoteDevices = new Vector <RemoteDevice>();
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			logger.info("Fetching remote devices from DB.");
			Criteria criteria = session
					.createCriteria(TransactionBean.class)
					.add(Restrictions.isNotNull("blob"))
					.add(Restrictions.eq("blobType",
							RemoteDevice.class.getName()));
			List <TransactionBean> results = (List <TransactionBean>) criteria
					.list();
			if (results != null && results.size() > 0) {
				for (TransactionBean entry : results) {
					if (entry != null) {
						Blob blob = entry.getBlob();
						/*remoteDevices.add((RemoteDevice) getObjectFrom(blob
								.getBytes((long) 0, (int) blob.length())));*/
						InputStream inputStream = blob.getBinaryStream();
						ObjectInputStream objectInputStream = new ObjectInputStream(
								inputStream);
						RemoteDevice remoteDevice = (RemoteDevice) objectInputStream
								.readObject();
						remoteDevices.add(remoteDevice);
					}
				}
			}
			transaction.commit();
		}
		catch (Exception ex) {
			logger.error("ERROR while fetching remote devices from DB.");
			throw new DatabaseException(ex.getMessage());
		}
		finally {
			if (session != null) {
				session.close();
			}
		}
		return remoteDevices;
	}

	/**
	 * Gets the remote services.
	 *
	 * @return the remote services
	 * @throws ServerConnectionException the server connection exception
	 * @throws DatabaseException the database exception
	 */
	@SuppressWarnings("unchecked")
	public Vector <String> getRemoteServices()
			throws ServerConnectionException, DatabaseException {
		Vector <String> remoteDeviceServices = new Vector <String>();
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			logger.info("Fetching services from DB.");
			Criteria criteria = session.createCriteria(TransactionBean.class)
					.add(Restrictions.isNotNull("blob"))
					.add(Restrictions.eq("blobType", "SERVICE"));
			List <TransactionBean> results = (List <TransactionBean>) criteria
					.list();
			if (results != null && results.size() > 0) {
				for (TransactionBean entry : results) {
					if (entry != null) {
						Blob blob = entry.getBlob();
						/*remoteDeviceServices.add((String) getObjectFrom(blob
								.getBytes((long) 1, (int) blob.length())));*/
						InputStream inputStream = blob.getBinaryStream();
						ObjectInputStream objectInputStream = new ObjectInputStream(
								inputStream);
						String service = (String) objectInputStream
								.readObject();
						remoteDeviceServices.add(service);
					}
				}
			}
			transaction.commit();
		}
		catch (Exception ex) {
			logger.error("ERROR while fetching services.");
			throw new DatabaseException(ex.getMessage());
		}
		finally {
			if (session != null) {
				session.close();
			}
		}
		return remoteDeviceServices;
	}

	/**
	 * Gets the session factory.
	 *
	 * @return the session factory
	 */
	private SessionFactory getSessionFactory() {
		return new Configuration().configure().buildSessionFactory();
		//return new Configuration().configure().buildSessionFactory();
		/*InitialContext ctx = new InitialContext();
		SessionFactory sessionFactory = (SessionFactory) ctx
				.lookup("java:hibernate/SessionFactory");
		return sessionFactory;*/
	}

	/**
	 * Save transaction.
	 *
	 * @param object the object
	 * @param objectType the object type
	 * @throws ServerConnectionException the server connection exception
	 * @throws DatabaseException the database exception
	 */
	public void saveTransaction(Object object, String objectType)
			throws ServerConnectionException, DatabaseException {
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			logger.info("Saving transaction bean in DB.");
			TransactionBean bean = new TransactionBean();
			bean.setBlobType(objectType);
			//Blob blob = new SerialBlob(getByteArrayFrom(object));
			//or
			Blob blob = Hibernate.getLobCreator(session).createBlob(
					getByteArrayFrom(object));
			bean.setBlob(blob);
			session.save(bean);
			transaction.commit();
		}
		catch (Exception ex) {
			logger.error("ERROR while saving transaction bean in DB.");
			throw new DatabaseException(ex.getMessage());
		}
		finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
/**
 *  Modification History:
 *
 *  $Log: PersistenceService.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 */
