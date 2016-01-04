package com.skola.models;

/**
 * Created with IntelliJ IDEA. User: seb Date: 2013-09-06 Time: 15:24 To change
 * this template use File | Settings | File Templates.
 */
public class CreditAccount extends Account
{

	private final double creditLimit;
	private final double creditInterest;

	public CreditAccount(double balance, double intrest, double creditLimit, double creditInterest)
	{
		super(balance, intrest);
		this.creditLimit = creditLimit;
		this.creditInterest = creditInterest;
	}

	@Override
	public String toString()
	{
		return "\nCreditAccount{" + "accountID=" + accountID + ", balance=" + balance + ", interest=" + interest
				+ ", creditLimit=" + creditLimit + ", creditInterest=" + creditInterest + "} ";
	}

	/**
	 * remove money if it doesnt go over the balance + the creditlimit
	 * 
	 * @param outputMoney
	 * @return true if managed to remove money. false if exceeded credit limit
	 */
	@Override
	public boolean removeMoney(double outputMoney)
	{
		if ((balance + creditLimit) >= outputMoney)
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
