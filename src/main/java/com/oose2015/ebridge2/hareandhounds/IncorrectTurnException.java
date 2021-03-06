package com.oose2015.ebridge2.hareandhounds;

import java.util.HashMap;

/**
 * a class to describe an incorrect turn exception
 * @author eric
 *
 */
public class IncorrectTurnException extends Exception {
	/** the formatted response hash map.*/
	private HashMap<String, String> response;
	/**
	 * the constructor for the exception
	 * @param message the message with some context
	 */
	public IncorrectTurnException(String message) {
		super(message);
		this.response = new HashMap<>();
		this.response.put("reason", "INCORRECT_TURN");
	}
	/**
	 * a getter for the hashmap
	 * @return the hash map to be transformed to json
	 */
	public HashMap<String, String> getHash() {
		return this.response;
	}
}
