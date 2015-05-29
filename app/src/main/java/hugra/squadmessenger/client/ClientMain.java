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
import hugra.squadmessenger.sharedPackages.*;
import java.util.Vector;


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
	

	public ClientMain() throws IOException {
		messageQueue = new Queuer<>();
		localIndex = new Vector<>();
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
//		System.out.println(message.getCredentials().getUserName() + ": " + message.getMessage());
	}

	public static boolean hasMessage(int index) {
		return ClientMain.localIndex.get(index);
	}


	public static int getLocalIndexLength(){
		return ClientMain.localIndex.size();
	}
	

	public static void localIndexAddIndex(){
		localIndex.addElement(false);
	}
	

	public static void setLocalIndex(int index, boolean value) {
		ClientMain.localIndex.set(index, value);
	}
	

	public static int getRemoteIndex() {
		return remoteIndex;
	}


	public static void setRemoteIndex(int remoteIndex) {
		ClientMain.remoteIndex = remoteIndex;
	}



	public static boolean StayAlive() {
		return stayAlive;
	}


	public static void setAlive(boolean stayAlive) {
		ClientMain.stayAlive = stayAlive;
	}

}
