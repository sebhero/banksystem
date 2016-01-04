package com.skola.test;

import java.util.Random;

import com.skola.controller.BankController;
import com.skola.helpClasses.CustomerType;
import com.skola.helpClasses.SortParameter;
import com.skola.helpClasses.exceptions.PersonIDFormatException;
import com.skola.helpClasses.exceptions.ZipcodeFormatException;
import com.skola.models.Account;
import com.skola.models.CreditAccount;
import com.skola.models.Customer;
import com.skola.models.SavingsAccount;

/**
 * Created with IntelliJ IDEA. User: seb Date: 2013-09-12 Time: 11:21 To change
 * this template use File | Settings | File Templates.
 */
public class BankTest
{

	private final BankController theBank;

	public BankTest(BankController theMenuController)
	{
		this.theBank = theMenuController;
	}

	/***
	 * Runs the test
	 */
	public void run()
	{
		String customerID_1 = null;
		try
		{
			customerID_1 = createPrivateCustomerTest("asfd");
		}
		catch (final PersonIDFormatException | ZipcodeFormatException e)
		{
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("should fail custid_1= " + e.getMessage());
			try
			{
				customerID_1 = createPrivateCustomerTest("000113-1234");
			}
			catch (final PersonIDFormatException | ZipcodeFormatException e1)
			{
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				System.out.println("should succed custid_1");
				System.out.println(e.getMessage());
			}
		}

		String customerID_2 = null;
		try
		{
			customerID_2 = createCompanyCustomerTest();
		}
		catch (final PersonIDFormatException | ZipcodeFormatException e)
		{
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("failed custid_2");
			System.out.println(e.getMessage());
		}

		try
		{
			bashCreatCusts();
		}
		catch (final PersonIDFormatException | ZipcodeFormatException e)
		{
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("cust bash failed");
			System.out.println(e.getMessage());
		}

		displayCustomers();

		System.out.println();
		System.out.println("--------------------");
		System.out.println("Creating new accounts");
		// creating saving account
		final Account customerSaving_1 = new SavingsAccount(0, 0.03);
		final int AccountSavingID_1 = customerSaving_1.getAccountID();
		// adding accounts

		// customer 1 private
		// savings account
		final Customer cust1 = theBank.getCustomer(customerID_1);
		theBank.createSavingsAccount(customerSaving_1, cust1);
		// credit account with only parameters
		final int accountCreditID_1 = theBank.createCreditAccount(0, 0.03, 100, .3, customerID_1);

		// customer 2 company
		// savings account
		theBank.createSavingsAccount(100, 0.03, customerID_2);
		final int AccountSavingID_2 = theBank.createSavingsAccount(100, 0.03, customerID_2);

		System.out.println();
		System.out.println("--------------------");
		System.out.println("checking creation of accounts\n");
		theBank.displayCustomer(cust1);
		theBank.displayCustomer(theBank.getCustomer(customerID_2));

		// inserting cash
		theBank.insertMoney(AccountSavingID_1, 100);
		theBank.insertMoney(AccountSavingID_2, 100);
		theBank.insertMoney(accountCreditID_1, 100);

		System.out.println();
		System.out.println("--------------------");
		System.out.println("checking insert of money\n");
		theBank.displayCustomer(cust1);
		theBank.displayCustomer(theBank.getCustomer(customerID_2));

		System.out.println();
		System.out.println("--------------------");
		System.out.println("checking credit accounts");
		final CreditAccount currentAccount = (CreditAccount) theBank.getAccount(accountCreditID_1);

		if (currentAccount == null)
		{
			System.out.println("didnt find the account");
		}
		else
		{
			System.out.println(currentAccount.toString());
		}

		if (theBank.withdrawMoney(accountCreditID_1, 150))
		{
			System.out.println("withdrawel was a Success");
		}
		else
		{
			System.out.println("Failed to withdraw money\n");

		}

		System.out.println();
		System.out.println("--------------------");
		System.out.println("showing the current account status:\n");
		System.out.println(currentAccount.toString());
		if (theBank.withdrawMoney(accountCreditID_1, 100))
		{
			System.out.println("withdrawal was a Success");
		}
		else
		{
			System.out.println("Failed to withdraw money\n");

		}

		System.out.println();
		System.out.println("--------------------");
		System.out.println("final credit account");
		System.out.println(theBank.getAccount(accountCreditID_1).toString());

		System.out.println("--------------------");
		System.out.println("sorted by PersonID");
		System.out.println();

		for (final String info : theBank.getAllCustomers(SortParameter.PERSON_ID))
		{
			System.out.println(info);
		}
		System.out.println();
		System.out.println("--------------------");
		System.out.println("sorted by Lastname");
		System.out.println();
		for (final String info : theBank.getAllCustomers(SortParameter.LASTNAME))
		{
			System.out.println(info);
		}
		System.out.println("--------------------");
		System.out.println("End of Test");

		testPostnumber();
		// /END of test
	}

	private void testPostnumber()
	{
		String zipcode2 = "asfd";
		String street2 = "hindenburgplatz 64";
		String city2 = "malmo";
		String name2 = "leo bjornstrom";
		String personNr2 = "770202-2134";
		String email = "jag@email.com";

		try
		{
			theBank.createCustomer(name2, personNr2, email, street2, zipcode2, city2, CustomerType.COMPANY);
		}
		catch (final PersonIDFormatException | ZipcodeFormatException e)
		{
			System.out.println("Error for zipcode not numbers, should be displayed");
			System.out.println("ERROR: " + e.getMessage());
		}
		zipcode2 = "123";
		street2 = "hindenburgplatz 64";
		city2 = "malmo";
		name2 = "leo bjornstrom";
		personNr2 = "770202-2134";
		email = "jag@email.com";

		try
		{
			theBank.createCustomer(name2, personNr2, email, street2, zipcode2, city2, CustomerType.COMPANY);
		}
		catch (final PersonIDFormatException | ZipcodeFormatException e)
		{
			System.out.println("Error for zipcode not enough numbers, should be displayed");
			System.out.println("ERROR: " + e.getMessage());
		}

	}

	private void bashCreatCusts() throws PersonIDFormatException, ZipcodeFormatException
	{
		final String[] lastnames =
		{ "svensson", "jansson", "eklund", "arvidsson", "lundberg", "bokson", "torsson" };
		final String[] firstnames =
		{ "jonas", "seb", "leo", "patrik", "micke", "sarah", "sofia", "anna", "maria" };

		final String zipcode = "48563";
		final String street = "hindenburgplatz 64";
		final String city = "malmo";
		final String email = "jag@email.com";

		String lastname;
		String first;
		String name;
		String personID;

		final Random rand = new Random(1337);
		// skapa 8 kunder
		for (int i = 0; i != 8; i++)
		{
			first = firstnames[rand.nextInt(firstnames.length - 1)];
			lastname = lastnames[rand.nextInt(lastnames.length - 1)];
			name = first + " " + lastname;
			// fran 1 till 124
			final String idDay = String.valueOf(rand.nextInt(3)) + String.valueOf(rand.nextInt(9));
			final String idMonth = String.valueOf(rand.nextInt(1)) + String.valueOf(rand.nextInt(9));
			final String idYear = String.valueOf(rand.nextInt(9)) + String.valueOf(rand.nextInt(9));
			final String idFour = String.valueOf(rand.nextInt(8000) + 1000);
			personID = idYear + idMonth + idDay + "-" + idFour;
			this.theBank.createCustomer(name, personID, email, street, zipcode, city, CustomerType.PRIVATE_CUSTOMER);
		}

	}

	private void displayCustomers()
	{
		System.out.println("display all custs");
		for (final String currentCust : theBank.getAllCustomers())
		{
			System.out.println(currentCust);
		}
	}

	private String createCompanyCustomerTest() throws PersonIDFormatException, ZipcodeFormatException
	{
		final String zipcode2 = "12312";
		final String street2 = "hindenburgplatz 64";
		final String city2 = "malmo";
		final String name2 = "leo bjornstrom";
		final String personNr2 = "810414-2134";
		final String email = "jag@email.com";

		return theBank.createCustomer(name2, personNr2, email, street2, zipcode2, city2, CustomerType.COMPANY);
	}

	private String createPrivateCustomerTest(String myID) throws PersonIDFormatException, ZipcodeFormatException
	{
		// inputs
		final String name1 = "seb jakobson";
		final String personNr1 = myID;
		final String street1 = "lundbergsgatan 11";
		final String zipcode1 = "21333";
		final String city1 = "malmo";
		final String email = "hej@email.com";

		return theBank.createCustomer(name1, personNr1, email, street1, zipcode1, city1, CustomerType.PRIVATE_CUSTOMER);
	}
}
