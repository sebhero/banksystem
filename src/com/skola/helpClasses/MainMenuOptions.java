package com.skola.helpClasses;

/**
 * MainMenuOptions
 * 
 * @author Sebastian Börebäck
 * 
 */
public enum MainMenuOptions
{
	CREATE_CUSTOMER, READ_CUSTOMER, UPDATE_CUSTOMER, DELETE_CUSTOMER, GET_ALL_CUSTOMERS, EXIT;

	@Override
	public String toString()
	{
		switch (this)
		{
		case CREATE_CUSTOMER:
			return "Create customer";
		case READ_CUSTOMER:
			return "Get customer";
		case UPDATE_CUSTOMER:
			return "Edit customer";
		case DELETE_CUSTOMER:
			return "Delete customer";
		case GET_ALL_CUSTOMERS:
			return "Get all customers";
		case EXIT:
			return "Exit";
		default:
			return "";
		}
	};
}