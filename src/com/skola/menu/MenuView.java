package com.skola.menu;

import java.util.Scanner;

/**
 * parent view class
 * 
 * @author Sebastian Börebäck
 * 
 */
public abstract class MenuView
{

	private final String menuName;
	// changing string
	private String menuBreadCrumb;
	private final Scanner input = new Scanner(System.in);

	public MenuView(String themenuName)
	{
		menuName = themenuName;
		setMenuBreadCrumb(menuName);
	}

	/**
	 * Displayes the menu
	 * 
	 * @return menu choice depended on choice, need to be used against enum
	 * @throws Exception
	 */
	public abstract int displayMenu() throws Exception;

	/**
	 * Ask user questions
	 * 
	 * @param question
	 *          the question
	 * @return string of the answear from user
	 */
	public String getValidString(String question)
	{
		System.out.print(getMenuBreadCrumb() + " $ " + question);

		final String answear = input.nextLine();
		// TODO kom ihåg controll för att hoppa mellan ord i en rad.
		if (answear == null || answear.isEmpty())
		{
			displayError("Invalid input string");
			return getValidString(question);
		}

		return answear;
	}

	/**
	 * Display errors.
	 * 
	 * @param errorMessage
	 */
	public void displayError(String errorMessage)
	{
		System.err.println(errorMessage);
	}

	/**
	 * Display success
	 * 
	 * @param successMessage
	 */
	public void displaySuccess(String successMessage)
	{
		System.out.println(successMessage);
	}

	/**
	 * @return the menuName
	 */
	public String getMenuBreadCrumb()
	{
		return menuBreadCrumb;
	}

	/**
	 * @param menuName
	 *          the menuName to set
	 */
	public void setMenuBreadCrumb(String menuBreadCrumb)
	{
		this.menuBreadCrumb = menuBreadCrumb;
	}

	/**
	 * @return the menuName
	 */
	public String getMenuName()
	{
		return menuName;
	}

}
