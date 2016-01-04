package com.skola.main;

import java.util.HashMap;

import com.skola.controller.BankController;
import com.skola.menu.CustomerMenuViewImpl;
import com.skola.menu.MainMenuViewImpl;
import com.skola.models.Customer;
import com.skola.test.BankTest;

/**
 * Created with IntelliJ IDEA. User: seb Date: 2013-09-06 Time: 15:38 To change
 * this template use File | Settings | File Templates.
 */
public class _upg2_Main
{
	public static void main(String[] args)
	{

		// loaded from file/database m.m.
		final HashMap<String, Customer> customers = new HashMap<String, Customer>();
		// the main view
		final MainMenuViewImpl theViewMain = new MainMenuViewImpl();
		// customer view
		final CustomerMenuViewImpl theViewCustomer = new CustomerMenuViewImpl();

		// menuController/bank
		final BankController theMenuController = new BankController(customers, theViewMain, theViewCustomer);

		// banktester
		final BankTest myTest = new BankTest(theMenuController);

		myTest.run();

		System.out.println("--------------------");
		System.out.println();
		System.out.println();
		System.out.println("--------------------");
		// end of test

		// Start menu
		System.out.println("Main Menu");
		System.out.println("--------------------");

		theMenuController.start();

	}
}
