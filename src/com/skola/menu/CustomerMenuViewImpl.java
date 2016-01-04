package com.skola.menu;

import java.util.Scanner;

import com.skola.helpClasses.AccountType;
import com.skola.helpClasses.customerMenuOptions;

public class CustomerMenuViewImpl extends MenuView
{

	private final Scanner input = new Scanner(System.in);

	/*
	 * set menu text
	 */
	public CustomerMenuViewImpl()
	{
		super("customermenu");
	}

	@Override
	public int displayMenu() throws Exception
	{
		// choices
		int i = 0;

		for (final customerMenuOptions option : customerMenuOptions.values())
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
		return menuChoice;

	}

	/**
	 * displays the customer accounts
	 * 
	 * @param customerAccounts
	 */
	public void displayCustomersAccounts(String customerAccounts)
	{
		System.out.println(customerAccounts);
	}

	/**
	 * returns a valid double, with UI
	 * 
	 * @param question
	 * @return double
	 */
	public double getValidDouble(String question)
	{
		double answear;
		try
		{
			answear = Double.parseDouble(getValidString(question));
		}
		catch (final Exception e)
		{
			displayError("Couldnt validate numbers");
			answear = 0;
			answear = getValidDouble(question);
		}

		return answear;
	}

	/**
	 * return a valid int.
	 * 
	 * @param question
	 * @return int
	 */
	public int getValidInt(String question)
	{
		int answear;
		try
		{
			answear = Integer.parseInt(getValidString(question));
		}
		catch (final Exception e)
		{
			displayError("Couldnt validate numbers");
			answear = 0;
			answear = getValidInt(question);
		}

		return answear;
	}

	/**
	 * returns a valid account type
	 * 
	 * @return
	 */
	public AccountType getAccountType()
	{
		System.out.println("0: Savings account");
		System.out.println("1: Credit account");

		final int choice = getValidInt("What type of account: ");
		if (choice > 1 || choice < 0)
		{
			displayError("Not a valid choice");
			return getAccountType();
		}
		return AccountType.values()[choice];
	}

}
