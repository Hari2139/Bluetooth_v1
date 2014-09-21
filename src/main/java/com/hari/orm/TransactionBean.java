package com.hari.orm;

import java.sql.Blob;
import java.util.Date;

/**
 * Description: I am the hibernate DAO for TRANSACTION table.
 * File: $ TransactionBean.java $
 * Module:  com.hari.dao
 * Created: May 19, 2012
 * @author Hari 
 * @version $Revision: 1.1 $
 * Last Changed: $Date: 2013/01/09 03:24:49 $
 * Last Changed By: $ Hari $
 */
public class TransactionBean {
	/** The sysid. */
	private int sysid;
	/** The blob type. */
	private String blobType;
	/** The blob. */
	private Blob blob;
	/** The modified timestamp. */
	private Date modifiedTimestamp;

	/**
	 * Gets the sysid.
	 *
	 * @return the sysid
	 */
	public int getSysid() {
		return sysid;
	}

	/**
	 * Sets the sysid.
	 *
	 * @param sysid the new sysid
	 */
	public void setSysid(int sysid) {
		this.sysid = sysid;
	}

	/**
	 * Gets the blob type.
	 *
	 * @return the blob type
	 */
	public String getBlobType() {
		return blobType;
	}

	/**
	 * Sets the blob type.
	 *
	 * @param blobType the new blob type
	 */
	public void setBlobType(String blobType) {
		this.blobType = blobType;
	}

	/**
	 * Gets the blob.
	 *
	 * @return the blob
	 */
	public Blob getBlob() {
		return blob;
	}

	/**
	 * Sets the blob.
	 *
	 * @param blob the new blob
	 */
	public void setBlob(Blob blob) {
		this.blob = blob;
	}

	/**
	 * Gets the modified timestamp.
	 *
	 * @return the modified timestamp
	 */
	public Date getModifiedTimestamp() {
		if (modifiedTimestamp == null) {
			modifiedTimestamp = new Date();
		}
		return modifiedTimestamp;
	}

	/**
	 * Sets the modified timestamp.
	 *
	 * @param modifiedTimestamp the new modified timestamp
	 */
	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}
}
/**
 *  Modification History:
 *
 *  $Log: TransactionBean.java,v $
 *  Revision 1.1  2013/01/09 03:24:49  Hari
 *  Initial commit to the new CVS server.
 *
 */
