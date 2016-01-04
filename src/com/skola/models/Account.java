package com.skola.models;

/**
 * Created with IntelliJ IDEA. User: seb Date: 2013-09-06 Time: 15:06 To change
 * this template use File | Settings | File Templates.
 */
public abstract class Account
{

	private static int totalAccounts = 10;
	final int accountID;// key
	double balance;
	double interest;

	/**
	 * Create a a account with start balance and intrest
	 * 
	 * @param balance
	 *          how much money on creation of account
	 * @param intrest
	 *          start intrest.
	 */
	Account(double balance, double intrest)
	{
		this.accountID = totalAccounts;
		totalAccounts++;// increase on creation of new accounts
		this.balance = balance;
		this.interest = intrest;
	}

	/**
	 * returns the account ID
	 * 
	 * @return int account ID
	 */
	public int getAccountID()
	{
		return accountID;
	}

	/**
	 * @return account balance
	 */
	public double getBalance()
	{
		return balance;
	}

	/**
	 * 
	 * @param inputMoney
	 *          sets the balance
	 */
	public void setBalance(double inputMoney)
	{
		this.balance += inputMoney;
	}

	/**
	 * 
	 * @return the interest
	 */
	public double getInterest()
	{
		return interest;
	}

	/**
	 * 
	 * @param intrest
	 *          set the interest
	 */
	public void setInterest(double intrest)
	{
		this.interest = intrest;
	}

	@Override
	public String toString()
	{
		return "{" + "accountID=" + accountID + ", balance=" + balance + ", interest=" + interest + "}";
	}

	/**
	 * abstract method for taking money out from the account. Its depended on
	 * which type of account.
	 * 
	 * @param outputMoney
	 * @return true if managed to remove money. false if exceeded amount
	 */
	public abstract boolean removeMoney(double amount);

	public void insertMoney(double amount)
	{
		this.balance += amount;
	}
}
