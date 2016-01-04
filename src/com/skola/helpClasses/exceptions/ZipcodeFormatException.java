package com.skola.helpClasses.exceptions;

public class ZipcodeFormatException extends Exception
{

	public ZipcodeFormatException()
	{
		super("This isnt a valid zipcode");
	}

	public ZipcodeFormatException(String message)
	{
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
