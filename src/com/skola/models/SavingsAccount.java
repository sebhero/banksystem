package com.skola.models;


/**
 * Created with IntelliJ IDEA. User: seb Date: 2013-09-06 Time: 15:13 To change
 * this template use File | Settings | File Templates.
 */
public class SavingsAccount extends Account
{

	public SavingsAccount(double balance, double intrest)
	{
		super(balance, intrest);
	}

	@Override
	public String toString()
	{
		return "\nSavingsAccount" + super.toString(); // To change body of
		// overridden methods
		// use File | Settings |
		// File Templates.
	}

	/**
	 * try withdraw money.
	 * 
	 * @param outputMoney
	 *          return true if managed to remove money
	 */
	@Override
	public boolean removeMoney(double outputMoney)
	{
		if (balance > outputMoney)
		{
			balance -= outputMoney;
			return true;
		}
		else
		{
			return false;
		}
	}
}
