package com.skola.models;

import java.util.ArrayList;
import java.util.Comparator;

import com.skola.helpClasses.CustomerType;
import com.skola.helpClasses.SortParameter;
import com.skola.helpClasses.exceptions.EmailFormatException;
import com.skola.helpClasses.exceptions.PersonIDFormatException;

/**
 * Created with IntelliJ IDEA. User: seb Date: 2013-09-06 Time: 15:02 To change
 * this template use File | Settings | File Templates.
 */
public class Customer implements Comparable<Customer>
{
	private String name;
	private String personID; // key
	private String email;
	private Address myAddress;
	private CustomerType myType;
	private final ArrayList<Account> myAccounts;

	/**
	 * Creats a customer with out a account
	 * 
	 * @param name
	 * @param personID
	 * @param myAddress
	 * @param myType
	 * @throws PersonIDFormatException
	 */
	// public Customer(String name, int personalID, Address myAddress, Account
	// newAccount, CustomerType myType) {
	public Customer(String name, String personID, String email, Address myAddress, CustomerType myType)
			throws PersonIDFormatException
	{
		this.setName(name);
		this.setPersonID(personID);
		this.setMyAddress(myAddress);
		this.setMyType(myType);
		this.myAccounts = new ArrayList<Account>();
	}

	/**
	 * add a new account to customer.
	 * 
	 * @param newAccount
	 */
	public void addAccount(Account newAccount)
	{
		this.myAccounts.add(newAccount);
	}

	/**
	 * get all the accounts
	 * 
	 * @return
	 */
	public ArrayList<Account> getAccounts()
	{
		return myAccounts;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPersonID()
	{
		return personID;
	}

	public void setPersonID(String personID) throws PersonIDFormatException
	{

		// digital number{6 of them} minus number{4 of them}
		if (!personID.matches("^\\d{6}-\\d{4}$"))
		{
			throw new PersonIDFormatException("the Personid format is incorrect");
		}
		if (!personID.matches("(\\d\\d)((0?[1-9]|1[012]))(0?[1-9]|[12][0-9]|3[01])-\\d{4}$"))
		{
			throw new PersonIDFormatException("the Personid that format is incorrect");
		}

		// check that legnth is not to short
		final int idlength = personID.trim().replaceFirst("[-]", "").length();
		if (idlength < 10 && idlength > 10)
		{
			throw new PersonIDFormatException("the personID length, need to be at least 10 numbers");
		}

		this.personID = personID.trim();
	}

	public Address getMyAddress()
	{
		return myAddress;
	}

	public void setMyAddress(Address myAddress)
	{
		this.myAddress = myAddress;
	}

	public CustomerType getMyType()
	{
		return myType;
	}

	public void setMyType(CustomerType myType)
	{
		this.myType = myType;
	}

	@Override
	public String toString()
	{
		return "Customer{" + "name='" + name + '\'' + ", personalID=" + personID + ", myAddress=" + myAddress + ", myType="
				+ myType + ", myAccounts=" + myAccounts + '}';
	}

	/**
	 * tries to remove a account if it exists
	 * 
	 * @param accountID
	 *          uniqe id of the account
	 */
	public boolean tryToRemoveAccount(int accountID)
	{

		Account foundIt = null;
		for (final Account currentAccount : myAccounts)
		{
			if (currentAccount.accountID == accountID)
			{
				foundIt = currentAccount;
				break;
			}
		}
		if (foundIt != null)
		{
			myAccounts.remove(foundIt);
			return true;
		}
		if (foundIt == null)
		{
			throw new NullPointerException("Didnt find Account, please details");
		}
		return false;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email) throws EmailFormatException
	{
		if (email.indexOf('@') == -1)
		{
			throw new EmailFormatException("Email is missing @: " + email);
		}

		this.email = email;
	}

	public Account getAccount(int currentAccountID)
	{
		for (final Account acc : this.myAccounts)
		{
			if (acc.getAccountID() == currentAccountID)
			{
				return acc;
			}
		}
		return null;
	}

	/**
	 * Standard compare to other customer. compareing is with the key, personID.
	 */
	@Override
	public int compareTo(Customer otherCust)
	{

		final double myID = Double.parseDouble(this.getPersonID().replaceFirst("[-]", ""));
		final double otherID = Double.parseDouble(otherCust.getPersonID().replaceFirst("[-]", ""));

		return (int) (myID - otherID);
	}

	/**
	 * Compare customers by choosen sortParameter
	 * 
	 * @param sortBy
	 * @return
	 */
	public static Comparator<Customer> sortBy(SortParameter sortBy)
	{
		Comparator<Customer> compareWith;
		switch (sortBy)
		{

		case LASTNAME:
			compareWith = new Comparator<Customer>()
			{

				@Override
				public int compare(Customer myCust, Customer otherCust)
				{
					// assuming everyone has a last name
					final String[] myNameArray = myCust.getName().split(" ");
					final String[] otherNameArray = otherCust.getName().split(" ");
					final int comp = myNameArray[1].compareTo(otherNameArray[1]);

					// efter kollat efternamn dubbelkolla förnamn ifall det är också
					return ((comp == 0) ? myNameArray[0].compareTo(otherNameArray[0]) : comp);

				}
			};
			return compareWith;

		case PERSON_ID:
			compareWith = new Comparator<Customer>()
			{

				@Override
				public int compare(Customer myCust, Customer otherCust)
				{
					// TODO fix error that personID is not a valid int.
					final double myID = Double.parseDouble(myCust.getPersonID().replaceFirst("[-]", ""));
					final double otherID = Double.parseDouble(otherCust.getPersonID().replaceFirst("[-]", ""));

					return (int) (myID - otherID);
				}
			};
			return compareWith;

		default:
			return null;
		}
	}
}
