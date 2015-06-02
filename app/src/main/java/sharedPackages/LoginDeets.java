/*
		 Title: LoginDeets.java
		 Programmer: hugo
		 Date of creation: May 25, 2015
		 Description: 
*/


package sharedPackages;

import java.io.Serializable;

/**
 * @author hugo
 *
 */
public class LoginDeets implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String userName;
    String password;
    public LoginDeets(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    

    public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String toString(){
        return "userName: " + userName + " password: " + password;
    }
}
