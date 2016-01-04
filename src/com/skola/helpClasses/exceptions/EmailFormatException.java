package com.skola.helpClasses.exceptions;

public class EmailFormatException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailFormatException()
	{
		super("This isnt a valid Email");
	}

	public EmailFormatException(String msg)
	{
		super(msg);
	}

}
