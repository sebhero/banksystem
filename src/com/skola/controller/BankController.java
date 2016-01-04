package com.skola.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.skola.helpClasses.AccountType;
import com.skola.helpClasses.CustomerType;
import com.skola.helpClasses.MainMenuOptions;
import com.skola.helpClasses.Pair;
import com.skola.helpClasses.SortParameter;
import com.skola.helpClasses.customerMenuOptions;
import com.skola.helpClasses.exceptions.PersonIDFormatException;
import com.skola.helpClasses.exceptions.ZipcodeFormatException;
import com.skola.menu.CustomerMenuViewImpl;
import com.skola.menu.MainMenuViewImpl;
import com.skola.models.Account;
import com.skola.models.Address;
import com.skola.models.CreditAccount;
import com.skola.models.Customer;
import com.skola.models.SavingsAccount;

/**
 * Controller class, handles all the inputs from view and hold the connection to
 * the EntityManager.
 * 
 * @author Sebastian Börebäck
 * 
 */
public class BankController
{

	// mainview
	private final MainMenuViewImpl theViewMain;
	// customerview
	private final CustomerMenuViewImpl theViewCustomer;
	// entitymanager in my case a hashmap.
	private final HashMap<String, Customer> theEntiyManagerForCustomers;

	// current accountid, since i have only one controller for 2 view.
	private int currentAccountID = 0;

	public BankController(HashMap<String, Customer> customers, MainMenuViewImpl mainView,
			CustomerMenuViewImpl theViewCustomer)
	{
		this.theEntiyManagerForCustomers = customers;
		this.theViewMain = mainView;
		this.theViewCustomer = theViewCustomer;

	}

	/**
	 * Handles all the interaction on the main menu.
	 * 
	 * @return false if user wants to exit
	 */
	public boolean mainMenuHandler()
	{

		MainMenuOptions mainChoice;
		final customerMenuOptions customerChoice;
		try
		{
			mainChoice = MainMenuOptions.values()[this.theViewMain.displayMenu()];
			switch (mainChoice)
			{
			case CREATE_CUSTOMER:
				createCustomer();
				break;
			case READ_CUSTOMER:
				readCustomer();
				break;
			case UPDATE_CUSTOMER:
				updateCustomer();
				break;
			case DELETE_CUSTOMER:
				deleteCustomer();
				break;
			case GET_ALL_CUSTOMERS:
				theViewMain.displayAllCustomers(getAllCustomersUI());
				break;
			case EXIT:
				// doNotExit=false
				System.out.println("exit is called");
				return false;
			default:
				break;
			}
		}
		catch (final Exception e)
		{
			System.err.println(e.getMessage());
			// maybe exit..
		}
		// if not exit allways return true;
		return true;
	}

	/**
	 * Update the customer. loads the customerMenuHanlder
	 * 
	 * @throws Exception
	 */
	private void updateCustomer() throws Exception
	{
		customerMenuOptions customerChoice;
		final Customer current = readCustomer();
		boolean stayInCustomerMenu = true;
		while (stayInCustomerMenu)
		{
			customerChoice = customerMenuOptions.values()[theViewCustomer.displayMenu()];
			stayInCustomerMenu = customerMenuHandler(customerChoice, current);
		}
	}

	/**
	 * Handles the customer entity edititing.
	 * 
	 * @param displayMenu
	 *          menu option
	 * @param current
	 *          customer
	 * @return false to exit
	 * @throws ZipcodeFormatException
	 */
	private boolean customerMenuHandler(customerMenuOptions displayMenu, Customer current) throws ZipcodeFormatException
	{
		final String question;
		final String answer;
		final Pair<Integer, Double> answearMoney;
		switch (displayMenu)
		{

		case EDIT_NAME:
			editName(current);
			break;
		case EDIT_STREET:
			editStreet(current);
			break;
		case EDIT_ZIPCODE:
			editZipcode(current);
			break;
		case EDIT_CITY:
			editCity(current);
			break;
		case EDIT_CUSTOMER_TYPE:
			editCustomertype(current);
			break;
		case INSERT_MONEY:
			insertMoney(current);
			break;
		case WITHDRAW_MONEY:
			withdrawMoney(current);
			break;
		case ADD_ACCOUNT:
			createAccount(current);
			break;
		case REMOVE_ACCOUNT:
			removeAccount(current);
			break;
		case SHOW_ACCOUNTS:
			showAccounts(current);
			break;
		case EXIT:
			return false;
		case SELECT_A_ACCOUNT:
			selectAAccount(current);
			break;
		case CUSTOMER_INFO:
			displayCustomer(current);
		default:
			break;
		}
		// return true until return false
		return true;
	}

	/**
	 * Display accouns of current customer
	 * 
	 * @param current
	 */
	private void showAccounts(Customer current)
	{
		this.theViewCustomer.displayCustomersAccounts(current.getAccounts().toString());
	}

	/**
	 * Create a account for current customer. With UI.
	 * 
	 * @param current
	 *          customer
	 */
	private void createAccount(Customer current)
	{

		final double balance = theViewCustomer.getValidDouble("starting amount: ");
		final double interest = theViewCustomer.getValidDouble("interest: ");
		final AccountType type = theViewCustomer.getAccountType();
		double creditLimit;
		final double creditInterest;
		switch (type)
		{
		case CREDIT:
			creditLimit = theViewCustomer.getValidDouble("Credit limit: ");
			creditInterest = theViewCustomer.getValidDouble("Credit interest: ");
			createCreditAccount(balance, interest, creditLimit, creditInterest, current.getPersonID());
			break;
		case SAVINGS:
			createSavingsAccount(balance, interest, current.getPersonID());
			break;
		default:
			break;
		}
		showAccounts(current);
	}

	/**
	 * Create a creditaccount
	 * 
	 * @param balance
	 * @param intrest
	 * @param creditLimit
	 * @param creditInterest
	 * @param personID
	 * @return accountID
	 */
	public int createCreditAccount(double balance, double intrest, double creditLimit, double creditInterest,
			String personID)
	{
		final Customer current = getCustomer(personID);
		final Account newAccount = new CreditAccount(balance, intrest, creditLimit, creditInterest);
		current.addAccount(newAccount);
		return newAccount.getAccountID();

	}

	/**
	 * Create a savingsAccount
	 * 
	 * @param balance
	 * @param interest
	 * @param personID
	 * @return accountID
	 */
	public int createSavingsAccount(double balance, double interest, String personID)
	{
		final Customer current = getCustomer(personID);
		final Account newAccount = new SavingsAccount(balance, interest);
		current.addAccount(newAccount);
		return newAccount.getAccountID();
	}

	/**
	 * Remove account UI
	 * 
	 * @param current
	 *          customer
	 */
	private void removeAccount(Customer current)
	{
		showAccounts(current);
		final String accountIDInput = theViewCustomer.getValidString("Wich accountID: ");
		final int accountID = Integer.parseInt(accountIDInput);
		if (this.removeAccount(accountID, current))
		{
			theViewCustomer.displaySuccess("Managed to remove the account ID: " + accountID);
		}
		else
		{
			theViewCustomer.displayError("Failed to remove the account ID: " + accountID);
		}
		showAccounts(current);
	}

	/**
	 * edit name UI
	 * 
	 * @param current
	 */
	private void editName(Customer current)
	{
		String question;
		String answer;
		question = "Change name from " + current.getName() + " to: ";
		answer = this.theViewCustomer.getValidString(question);
		current.setName(answer);
	}

	/**
	 * edit street UI
	 * 
	 * @param current
	 */
	private void editStreet(Customer current)
	{
		String question;
		String answer;
		question = "Change street from " + current.getMyAddress().getStreet() + " to: ";
		answer = theViewCustomer.getValidString(question);
		current.getMyAddress().setStreet(answer);
	}

	/**
	 * edit zipcode UI
	 * 
	 * @param current
	 *          customer
	 * @throws ZipcodeFormatException
	 */
	private void editZipcode(Customer current) throws ZipcodeFormatException
	{
		String question;
		String answer;
		question = "Change zipcode from " + current.getMyAddress().getZipCode() + " to: ";
		answer = theViewCustomer.getValidString(question);
		current.getMyAddress().setZipCode(answer);
	}

	/**
	 * edit city UI
	 * 
	 * @param current
	 *          customer
	 */
	private void editCity(Customer current)
	{
		String question;
		String answer;
		question = "Change city from " + current.getMyAddress().getCity() + " to: ";
		answer = theViewCustomer.getValidString(question);
		current.getMyAddress().setCity(answer);
	}

	/**
	 * edit customerType UI
	 * 
	 * @param current
	 *          customer
	 */
	private void editCustomertype(Customer current)
	{
		System.out.println("Change customer type from " + current.getMyType() + " to, ");
		// get from main menu
		final CustomerType type = theViewMain.getCustomerType();
		current.setMyType(type);
	}

	/**
	 * Inset money to account UI
	 * 
	 * @param current
	 *          customer
	 */
	private void insertMoney(Customer current)
	{
		// plocka upp kontot ifall det inte är redan gjort
		if (currentAccountID < 10)// id startar på 10
		{
			selectAAccount(current);
		}
		final double amount = this.theViewCustomer.getValidDouble("Amount: ");

		final Account theAccount = current.getAccount(currentAccountID);
		theAccount.insertMoney(amount);

		theViewCustomer.displaySuccess("Inserted " + amount + " to account: " + currentAccountID);
		// display Account info
		System.out.println(current.getAccount(currentAccountID));

	}

	/**
	 * Insert money.
	 * 
	 * @param accountID
	 * @param amount
	 */
	public void insertMoney(int accountID, int amount)
	{
		final Account theAccount = getAccount(accountID);
		theAccount.insertMoney(amount);
	}

	/**
	 * get account based on id
	 * 
	 * @param accountID
	 * @return the account or null if not found
	 */
	public Account getAccount(int accountID)
	{
		Account temp = null;
		boolean found = false;
		for (final Customer currentCustomer : theEntiyManagerForCustomers.values())
		{
			for (final Account currentAccount : currentCustomer.getAccounts())
			{
				if (currentAccount.getAccountID() == accountID)
				{
					temp = currentAccount;
					found = true;
					break;
				}

			}
			if (found)
			{
				break;
			}
		}

		return temp;

	}

	/**
	 * Withdraw money UI
	 * 
	 * @param current
	 *          Customer
	 */
	private void withdrawMoney(Customer current)
	{

		// plocka upp kontot ifall det inte är redan gjort
		if (currentAccountID < 10)// id startar på 10
		{
			selectAAccount(current);
		}
		final double amount = this.theViewCustomer.getValidDouble("Amount: ");

		final Account theAccount = current.getAccount(currentAccountID);

		if (theAccount.removeMoney(amount))
		{
			theViewCustomer.displaySuccess("Withdrew " + amount + " from account: " + currentAccountID);
			// display Account info
			System.out.println(current.getAccount(currentAccountID));
		}
		else
		{
			theViewCustomer.displayError("failed to withdraw " + amount + " from account: " + currentAccountID);
		}
	}

	/**
	 * Withdraw money
	 * 
	 * @param accountID
	 * @param amount
	 * @return true if success
	 */
	public boolean withdrawMoney(int accountID, int amount)
	{
		final Account theAccount = getAccount(accountID);
		return theAccount.removeMoney(amount);
	}

	/**
	 * Select a customer UI
	 * 
	 * @param current
	 *          Customer
	 */
	private void selectAAccount(Customer current)
	{
		showAccounts(current);
		this.currentAccountID = theViewCustomer.getValidInt("accountID: ");
		theViewCustomer.setMenuBreadCrumb("account: [" + currentAccountID + "] - " + theViewCustomer.getMenuName());
	}

	/**
	 * Remove account
	 * 
	 * @param accountID
	 * @param current
	 * @return true if success
	 */
	private boolean removeAccount(int accountID, Customer current)
	{
		return current.tryToRemoveAccount(accountID);
	}

	/**
	 * Get all customers. default with Person ID
	 * 
	 * @return string array sorted by person ID.
	 */
	public ArrayList<String> getAllCustomers()
	{
		return getAllCustomers(SortParameter.PERSON_ID);
	}

	/**
	 * get all customers with UI
	 * 
	 * @return string array sorted.
	 */
	private ArrayList<String> getAllCustomersUI()
	{
		final SortParameter sortBy = theViewMain.getSortedBy();
		return getAllCustomers(sortBy);
	}

	/**
	 * get all customers
	 * 
	 * @param sortBy
	 *          sortparameter.
	 * @return string array sorted.
	 */
	public ArrayList<String> getAllCustomers(SortParameter sortBy)
	{
		final ArrayList<String> customersInfo = new ArrayList<>();
		// due to Hashmap is a unsorted map, om jag ska kunna sortera den
		// måste jag använda mig utav treemap.
		final ArrayList<Customer> tempList = new ArrayList<Customer>(theEntiyManagerForCustomers.values());

		// default sort method is
		Collections.sort(tempList, Customer.sortBy(sortBy));

		for (final Customer cus : tempList)
		{
			customersInfo.add(cus.getName() + "\t" + cus.getPersonID());
		}

		return customersInfo;

	}

	/**
	 * delete a customer with UI
	 */
	public void deleteCustomer()
	{
		final String personID = theViewMain.getValidString("please input customers PersonalID: ");
		this.theEntiyManagerForCustomers.remove(this.getCustomer(personID));
		this.theViewMain.displaySuccess("Succesfully deleted customer: " + personID);
	}

	/**
	 * create customer with a new Address
	 * 
	 * @param name
	 * @param personID
	 * @param email
	 * @param theAddress
	 * @param myType
	 * @return personID
	 * @throws PersonIDFormatException
	 */
	public String createCustomer(String name, String personID, String email, Address theAddress, CustomerType myType)
			throws PersonIDFormatException
	{
		theEntiyManagerForCustomers.put(personID, new Customer(name, personID, email, theAddress, myType));
		return personID;
	}

	/**
	 * creat customer with only variables
	 * 
	 * @param name
	 * @param personID
	 * @param email
	 * @param street
	 * @param zipcode
	 * @param city
	 * @param myType
	 * @return personID
	 * @throws PersonIDFormatException
	 * @throws ZipcodeFormatException
	 * @throws Exception
	 */
	public String createCustomer(String name, String personID, String email, String street, String zipcode, String city,
			CustomerType myType) throws PersonIDFormatException, ZipcodeFormatException
	{
		if (theEntiyManagerForCustomers.containsKey(personID))
		{
			throw new PersonIDFormatException("Customer exist allready in database");
		}
		theEntiyManagerForCustomers.put(personID, new Customer(name, personID, email, new Address(street, zipcode, city),
				myType));
		return personID;
	}

	/**
	 * Create customer with UI. doesnt create accounts
	 * 
	 * @throws PersonIDFormatException
	 * @throws ZipcodeFormatException
	 */
	private void createCustomer() throws PersonIDFormatException, ZipcodeFormatException
	{

		final String name = theViewMain.getValidString("Name: ");
		final String personID = theViewMain.getValidString("ID: ");
		final String email = theViewMain.getValidString("Email: ");
		final String street = theViewMain.getValidString("Street: ");
		final String zipcode = theViewMain.getValidString("Zipcode: ");
		final String city = theViewMain.getValidString("City: ");
		final CustomerType myType = theViewMain.getCustomerType();
		// create the customer
		this.createCustomer(name, personID, email, new Address(street, zipcode, city), myType);
		// final String idInBank = this.createCustomer(name, personalID, email,
		// street, zipcode, city, myType);
		theViewMain.displaySuccess("Successfully created customer: " + personID);
		this.displayCustomer(getCustomer(personID));

	}

	/**
	 * Get a customer based on personID
	 * 
	 * @param personID
	 * @return the customer
	 */
	public Customer getCustomer(String personID)
	{
		final Customer current = this.theEntiyManagerForCustomers.get(personID);
		// hitta inte.
		if (current == null)
		{
			throw new NullPointerException("Didnt find the customer");
		}
		else
		{
			return current;
		}
	}

	/**
	 * Get the user info with UI
	 * 
	 * @return the customer
	 */
	public Customer readCustomer()
	{
		final String personID = theViewMain.getValidString("ID: ");
		final Customer current = getCustomer(personID);

		displayCustomer(current);
		return current;
	}

	/**
	 * display customer
	 * 
	 * @param current
	 *          customer
	 */
	public void displayCustomer(Customer current)
	{
		theViewMain.setCustomer(current.toString());
	}

	/**
	 * creates a saivings account
	 * 
	 * @param account
	 * @param current
	 */
	public void createSavingsAccount(Account account, Customer current)
	{
		current.addAccount(account);
	}

	/**
	 * Start the Bank
	 */
	public void start()
	{

		boolean doNotExit = true;// should be true

		while (doNotExit)
		{
			doNotExit = this.mainMenuHandler();
		}

	}

}
