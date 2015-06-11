/*
		 Title: ClientMain.java
		 Programmer: hugo
		 Date of creation: May 26, 2015
		 Description: The main entry point for the Squad messenger client
 */


package hugra.squadmessenger.client;

import java.io.IOException;

import hugra.squadmessenger.MainActivity;
import sharedPackages.*;
import java.util.Vector;
import android.app.Activity;


/**
 * @author hugo
 *
 */
public class ClientMain extends Activity{
	private static Vector<Boolean> localIndex; // We use an array of booleans in stead of a
	// single int because we might
	// recieve a higher number message before a lower one, and in that case
	// we would not recieve the lower message ever. In this method, and
	// index we do not have we get.
	private static int remoteIndex = 0; // the highest index of the remote database.
	private static boolean stayAlive = true;  // variable that dictates whether we should keep
	// going or not.
	public static LoginDeets creds; // the credentials of the user.
	public static Queuer<Message> messageOutQueue; // the queue of messages that need to be sent.
	// This uses my own queue
	// class

	public ServerOutComms outComms; //the object that handles all the outward communication
	private static MainActivity activityRef; //the object of mainactivity that allows us to edit
	// the UI elements of mainActivity.
	

	public ClientMain(String userName, String IPAddress, int port, MainActivity activityRef) throws
			IOException {
		messageOutQueue = new Queuer<>(); // init the variables
		localIndex = new Vector<>();

		creds = new LoginDeets(userName, null); //since we do not yet use password, password
		// field is null.
		
		outComms = new ServerOutComms(IPAddress, creds, port); //the object that handles all
		// outwards communication with the server.
		outComms.start(); //start the communication thread.
		this.activityRef = activityRef; //takes in the object of the MainActivity activity so
		// that we have a way to access the thread that created the UI elements. This concept is
		// explained further in MainActivity when we create the ClientMain object.

	}


	/**
	 *
	 * param: The message you want to send.
	 * desc: holds all outward messages until they are sent.
	 */
	public static void enQueueMessage(String message){
		messageOutQueue.enQueue(new Message(creds, message)); //enqueue a message using my queue
		// class with the users credentials
	}


	/**
	 *
	 * takes in a message that the serverincomms has recieved, and runs the method that updates
	 * the UI on the correct activity thread. Android is very specific in making sure only the
	 * thread that ccreates the UI element can access it.
	 */
	public static void addMessage(final Message message){
		activityRef.runOnUiThread(new Runnable() {
			public void run() {
				MainActivity.recieveMessage(message);
			}
		});
	}

	/**
	 *
	 * @author hugo
	 * Date of creation: May 26, 2015
	 * @param: The index you wish to check
	 * @return: boolean value, true if we have it, else false.
	 * @throws IOException
	 * @Description: returns the value from the local list.
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
	 * para: set the local list to a value.
	 */
	public static void setLocalIndex(int index, boolean value) {
		ClientMain.localIndex.set(index, value);
	}

	/**
	 * @return the remote index number
	 */
	public static int getRemoteIndex() {
		return remoteIndex;
	}

	/**
	 * param: update the remote index value
	 */
	public static void setRemoteIndex(int remoteIndex) {
		ClientMain.remoteIndex = remoteIndex;
	}


	/**
	 * @return  stayAlive value, used to determine whether the client should keep running, or
	 * exit gracefully.
	 */
	public static boolean StayAlive() {
		return stayAlive;
	}

	/**
	 * param: the value to set stayalive to
	 */
	public static void setAlive(boolean stayAlive) {
		ClientMain.stayAlive = stayAlive;
	}

}
