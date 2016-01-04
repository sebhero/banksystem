package com.skola.menu;

import java.util.ArrayList;
import java.util.Scanner;

import com.skola.helpClasses.CustomerType;
import com.skola.helpClasses.MainMenuOptions;
import com.skola.helpClasses.SortParameter;

/**
 * A view that display the main menu
 * 
 * @author Sebastian Börebäck
 * 
 */
public class MainMenuViewImpl extends MenuView
{

	private final Scanner input = new Scanner(System.in);

	public MainMenuViewImpl()
	{
		super("mainmenu");
	}

	@Override
	public int displayMenu() throws Exception
	{
		// choices
		int i = 0;

		for (final MainMenuOptions option : MainMenuOptions.values())
		{
			System.out.println(i + " : " + option);
			i++;
		}
		System.out.print(getMenuBreadCrumb() + " $ ");
		final int menuChoice = input.nextInt();

		if (menuChoice < 0 || menuChoice > i)
		{
			throw new Exception("Invalid menu choice");
		}

		// return to controll to run option
		// return MainMenuOptions.values()[menuChoice];
		return menuChoice;
	}

	/**
	 * Handles displaying erros
	 * 
	 * @param errorMessage
	 *          the error message.
	 */
	@Override
	public void displayError(String errorMessage)
	{
		System.err.println(errorMessage);

	}

	public SortParameter getSortedBy()
	{
		System.out.println("Sorted by?");
		System.out.println("1 : PersonID");
		System.out.println("2 : Lastname");
		switch (input.nextInt())
		{
		case 1:
			return SortParameter.PERSON_ID;
		case 2:
			return SortParameter.LASTNAME;
		default:
			displayError("Error on selecting a Customer type");
			return getSortedBy();
		}
	}

	public CustomerType getCustomerType()
	{
		System.out.println("What type of customer?");
		System.out.println("1 : Private customer");
		System.out.println("2 : Company customer");
		switch (input.nextInt())
		{
		case 1:
			return CustomerType.PRIVATE_CUSTOMER;
		case 2:
			return CustomerType.COMPANY;
		default:
			displayError("Error on selecting a Customer type");
			return getCustomerType();
		}
	}

	public void setCustomer(String customerAsString)
	{
		System.out.println(customerAsString);
	}

	@Override
	public void displaySuccess(String successMessage)
	{
		System.out.println(successMessage);
	}

	public void displayAllCustomers(ArrayList<String> customersInfo)
	{
		for (final String info : customersInfo)
		{
			System.out.println(info);
		}

	}

}
