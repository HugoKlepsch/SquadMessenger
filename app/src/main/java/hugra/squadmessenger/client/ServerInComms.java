/*
		 Title: ServerInComms.java
		 Programmer: hugo
		 Date of creation: May 26, 2015
		 Description: 
*/


package hugra.squadmessenger.client;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import hugra.squadmessenger.MainActivity;
import sharedPackages.*;


/**
 * @author hugo
 *
 */
public class ServerInComms extends Thread{
	Socket scSocket;
	ObjectInputStream scStream;
	
	public ServerInComms(Socket socket){
		scSocket = socket;
	}
	
	public void run(){
//		ClientMain returnObj = new ClientMain();
		try {
			ActionRequest actionRequest;
			scStream = new ObjectInputStream(scSocket.getInputStream());
			while(ClientMain.StayAlive()){
				actionRequest = (ActionRequest) scStream.readObject();
				if(actionRequest.getAction() == ActionRequest.SCSENDCURRENTMESSAGEINDEX){
					
					
					ClientMain.setRemoteIndex(actionRequest.getIndex());
					int difference = ClientMain.getRemoteIndex() + 1 - ClientMain.getLocalIndexLength();
					if(difference != 0){
						for (int i = 0; i < difference; i++) {
							ClientMain.localIndexAddIndex();
						}
					}
					
					
				} else if (actionRequest.getAction() == ActionRequest.SCSENDMESSAGE) {
					if(ClientMain.hasMessage(actionRequest.getMessage().getIndex())){ //if we already have the message, 
						//do nothing
					} else { //if we do not
						ClientMain.setLocalIndex(actionRequest.getMessage().getIndex(), true);
						ClientMain.addMessage(actionRequest.getMessage());
					}
					
					
				} else if(actionRequest.getAction() == ActionRequest.SCSENDUSERS){
					MainActivity.users = actionRequest.getUsers();

				} else if(actionRequest.getAction() == ActionRequest.SCKICK){
					ClientMain.setAlive(false);

					Thread.sleep(1500);

					System.exit(0);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
