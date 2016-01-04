package com.skola.helpClasses;

/**
 * Customer options
 * 
 * @author Sebastian Börebäck
 * 
 */
public enum customerMenuOptions
{
	CUSTOMER_INFO, EDIT_NAME, EDIT_STREET, EDIT_ZIPCODE, EDIT_CITY, EDIT_CUSTOMER_TYPE, ADD_ACCOUNT, REMOVE_ACCOUNT, SHOW_ACCOUNTS, SELECT_A_ACCOUNT, INSERT_MONEY, WITHDRAW_MONEY, EXIT;

	@Override
	public String toString()
	{
		switch (this)
		{
		case EDIT_NAME:
			return "Edit name";
		case EDIT_STREET:
			return "Edit street";
		case EDIT_ZIPCODE:
			return "Edit zipcode";
		case EDIT_CITY:
			return "Edit city";
		case EDIT_CUSTOMER_TYPE:
			return "Edit type of customer";
		case INSERT_MONEY:
			return "Insert money";
		case WITHDRAW_MONEY:
			return "Withdraw money";
		case ADD_ACCOUNT:
			return "Add a account";
		case REMOVE_ACCOUNT:
			return "Remove a account";
		case SHOW_ACCOUNTS:
			return "Show customers accounts";
		case EXIT:
			return "Exit";
		case SELECT_A_ACCOUNT:
			return "Select a account";
		case CUSTOMER_INFO:
			return "Customer info";
		default:
			break;
		}
		return super.toString();
	}
};
