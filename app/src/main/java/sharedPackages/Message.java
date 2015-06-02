/*
		 Title: Message.java
		 Programmer: hugo
		 Date of creation: May 26, 2015
		 Description: 
*/


package sharedPackages;

import java.io.Serializable;

/**
 * @author hugo
 *
 */
public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LoginDeets credentials;
	String message;
	int index;
	
	public Message(LoginDeets credentials, String message){
		this.credentials = credentials;
		this.message = message;
		this.index = 0; //when the client generates, doesn't have an index
	}

	/**
	 * @return the index of the message 
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param the server can set the index of the message
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the credentials
	 */
	public LoginDeets getCredentials() {
		return credentials;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	

}
