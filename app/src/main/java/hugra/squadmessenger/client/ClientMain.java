/*
		 Title: ClientMain.java
		 Programmer: hugo
		 Date of creation: May 26, 2015
		 Description: A quick and dirty computer client for the squad messenger beta
*/


package hugra.squadmessenger.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import sharedPackages.LoginDeets;
import sharedPackages.Message;
import sharedPackages.Queuer;

/**
 * @author hugo
 *
 */
public class ClientMain {
	private static BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
	private static Vector<Boolean> localIndex;
	private static int remoteIndex = 0;
	private static boolean stayAlive = true;
	public static Queuer<Message> messageQueue;
	
	/**
		 * @author hugo
		 * Date of creation: May 26, 2015 
		 * @param: None
		 * @return: None
	 * @throws IOException 
		 * @Description: ( ͡° ͜ʖ ͡°)
		 */
	public static void main(String[] args) throws IOException {
		messageQueue = new Queuer<Message>();
		localIndex = new Vector<Boolean>();
		System.out.println("Enter username to connect as: ");
		String userName = userIn.readLine();
		System.out.println("Enter the IP address to connect to: ");
		String address = userIn.readLine();
		LoginDeets creds = new LoginDeets(userName, null);
		
		ServerOutComms outComms = new ServerOutComms(address, creds);
		outComms.start();
		
		System.out.println("Type \"Exit\" to close");
		String messageContent =  "";
		Message message;
		while(!(messageContent.equals("Exit"))){
			messageContent = userIn.readLine();
			message = new Message(creds, messageContent);
			if (!(messageContent.equals("Exit"))) {
				messageQueue.enQueue(message); 
			} else {
				setAlive(false);
			}
		}
	
	}

	public static void addMessage(Message message){
		System.out.println(message.getCredentials().getUserName() + ": " + message.getMessage());
	}
	
	/**
	 * @return true if we have the message at that index
	 */
	public static boolean hasMessage(int index) {
		return ClientMain.localIndex.get(index);
	}

	/**
	 * @return the length of the local message array
	 */
	public static int getLocalIndexLength(){
		return ClientMain.localIndex.size();
	}
	
	/**
	 * @descrip adds an index to the localIndex array, with status false
	 */
	public static void localIndexAddIndex(){
		localIndex.addElement(false);
	}
	
	/**
	 * @param localIndex the localIndex to set
	 */
	public static void setLocalIndex(int index, boolean value) {
		ClientMain.localIndex.set(index, value);
	}
	
	/**
	 * @return the localIndex
	 */
	public static int getRemoteIndex() {
		return remoteIndex;
	}

	/**
	 * @param localIndex the localIndex to set
	 */
	public static void setRemoteIndex(int remoteIndex) {
		ClientMain.remoteIndex = remoteIndex;
	}
	
	public static Vector<Integer> getMissingIndices(){
		Vector<Integer> missingIndices = new Vector<Integer>();
		for (int i = 0; i < localIndex.size(); i++) {
			if(!localIndex.get(i)){
				missingIndices.add(i);
			}
		}
		return missingIndices;
	}

	/**
	 * @return the stayAlive
	 */
	public static boolean StayAlive() {
		return stayAlive;
	}

	/**
	 * @param stayAlive the stayAlive to set
	 */
	public static void setAlive(boolean stayAlive) {
		ClientMain.stayAlive = stayAlive;
	}

}
