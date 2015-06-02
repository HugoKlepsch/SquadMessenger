/*
		 Title: Queuer.java
		 Programmer: hugo
		 Date of creation: May 4, 2015
		 Description: A class that facilitates the creation and management of a queue. 
*/


package sharedPackages;

import java.util.Vector;

/**
 * @author hugo
 * @note The value inside the angle brackets <> is the class / datatype of the variable you want to hold in the queue. It should be noted that primitive types like int and float are from the Integer and Float class, use the class name, not int.
 */
public class Queuer <T>{ // T is the datatype that will be passed to the class when making this object.
	private Vector<T> queue; // A Vector is like an array, but can be extended. (It is not a fixed length.) Notice how
							 // it is of type "T", the type that is passed when making the object.
	
	public Queuer() { // the constructor. It does not need a parameter for length of the array because vector are
						// variable size.
		queue = new Vector<T>(); //Initializes the queue variable with the correct type. 
	}
	
	/**
	 * 
		 * @author hugo
		 * Date of creation: May 8, 2015 
		 * @param: None
		 * @return: The variable at the front of the queue and does not remove it. It is of the type that is specified in the constructor. 
		 * @Description: gets the front of the queue, does not remove the variable.
	 */
	public T front(){
		return queue.get(size() - 1); // return the item at the last index.
	}
	
	/**
	 * 
		 * @author hugo
		 * Date of creation: May 8, 2015 
		 * @param: None
		 * @return: The size of the vector array. 
		 * @Description: gets the size of the vector array. 
	 */
	public int size(){ // note that it does not used the passed type, because the length of an array is not affected by
						// the types it holds.
		return queue.size();
	}
	
	/**
	 * 
		 * @author hugo
		 * Date of creation: May 8, 2015 
		 * @param: None
		 * @return: the item at the front of the queue, removes it from the queue. 
		 * @Description: Like front(), returns the front item, but also removes the item from the queue
	 */
	public T deQueue(){
		return queue.remove(size() - 1); // the remove function reduces the size of the vector array by one.
	}
	
	/**
	 * 
		 * @author hugo
		 * Date of creation: May 8, 2015 
		 * @param: The variable to add to the queue, must be of the type specified in the constructor. 
		 * @return: None
		 * @Description: Increases the size of the vector array by one by adding the given item to the back of the queue. 
	 */
	public void enQueue(T number){
		queue.insertElementAt(number, 0); //insert increases the size of the vector array by one. inserted at the back. 
	}
	
	/**
	 * 
		 * @author hugo
		 * Date of creation: May 8, 2015 
		 * @param: None
		 * @return: true if the size of the array is zero, false overwise
		 * @Description: gets the size of the array, then determines if it's empty
	 */
	public boolean isEmpty(){
		return (queue.size()) == 0 ? true : false;  //one line if else; if size == 0 -> true; else -> false;
	}
	
	/**
	 * 
		 * @author hugo
		 * Date of creation: May 8, 2015 
		 * @param: None
		 * @return: None
		 * @Description: Sets the size of the vector array to zero. 
	 */
	public void makeEmtpy(){
		queue.clear();
	}
	
}
