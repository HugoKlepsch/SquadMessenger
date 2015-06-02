package sharedPackages;

import java.io.Serializable;

public class ActionRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int action;
	private Message message;
	private int index;
	
	public ActionRequest(int action){
		this.action = action;
	}
	
	public ActionRequest(int action, Message message){
		this.action = action;
		this.message = message;
	}
	
	public ActionRequest(int action, int index){
		this.action = action;
		this.index = index;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	

}
