package com.skola.helpClasses.exceptions;

public class PersonIDFormatException extends Exception
{

	public PersonIDFormatException(String message)
	{
		super(message);
	}

	public PersonIDFormatException()
	{
		super("This isnt a valid personID");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
