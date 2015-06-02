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

import hugra.squadmessenger.MainActivity;
import sharedPackages.*;
import java.util.Vector;
import android.app.Activity;


/**
 * @author hugo
 *
 */
public class ClientMain extends Activity{
	private static BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
	private static Vector<Boolean> localIndex;
	private static int remoteIndex = 0;
	private static boolean stayAlive = true;
	public static LoginDeets creds;
	public static Queuer<Message> messageQueue;
	

	public ClientMain(String userName, String IPAddress, int port) throws IOException {
		messageQueue = new Queuer<>();
		localIndex = new Vector<>();

		creds = new LoginDeets(userName, null);
		
		ServerOutComms outComms = new ServerOutComms(IPAddress, creds, port);
		outComms.start();

	}

	public ClientMain(){ //this one goes out to all those static final califoronia Integers who
	// don't need no object

	}

	public static void enQueueMessage(String message){
		messageQueue.enQueue(new Message(creds, message));
	}

	public static void addMessage(final Message message){
		MainActivity.recieveMessage(message);


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
