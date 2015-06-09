package sharedPackages;

import java.io.Serializable;
import java.util.Vector;

public class ActionRequest implements Serializable {
	/**
	 *
	 */
	public static final int CSGETCURRENTMESSAGEINDEX = 1;
	public static final int CSGETMESSAGE = 2;
	public static final int CSSENDMESSAGE = 3;
	public static final int CSDISCONNECT = 4;
	public static final int CSCONNECT = 5;
	public static final int SCSENDCURRENTMESSAGEINDEX = 6;
	public static final int SCSENDMESSAGE = 7;
	public static final int CSPING = 8;
	public static final int SCSENDUSERS = 9;
	public static final int SCKICK = 10;

	private static final long serialVersionUID = 1L;
	private int action;
	private Message message;
	private int index;
	private Vector<String> users;

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

	public ActionRequest(int action, Vector<String> users){
		this.action = action;
		this.users = users;
	}

	public ActionRequest(int action, Message message, Vector<String> users) {
		super();
		this.action = action;
		this.message = message;
		this.users = users;
	}

	public ActionRequest(int action, int index, Vector<String> users) {
		super();
		this.action = action;
		this.index = index;
		this.users = users;
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

	public Vector<String> getUsers() {
		return users;
	}

	public void setUsers(Vector<String> users) {
		this.users = users;
	}

}
