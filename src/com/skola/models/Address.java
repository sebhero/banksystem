package com.skola.models;

import com.skola.helpClasses.exceptions.ZipcodeFormatException;

/**
 * Created with IntelliJ IDEA. User: seb Date: 2013-09-06 Time: 15:03 To change
 * this template use File | Settings | File Templates.
 */
public class Address
{
	private String street;
	private String zipCode;
	private String city;

	/**
	 * normal model params for the users address.
	 * 
	 * @param street
	 * @param zipCode
	 * @param city
	 * @throws ZipcodeFormatException
	 */
	public Address(String street, String zipcode, String city) throws ZipcodeFormatException
	{
		this.setStreet(street);
		this.setZipCode(zipcode);
		this.setCity(city);
	}

	/**
	 * gets the street
	 * 
	 * @return
	 */
	public String getStreet()
	{
		return street;
	}

	/**
	 * sets the street
	 */
	public void setStreet(String street)
	{
		this.street = street;
	}

	/**
	 * get the zip
	 * 
	 * @return
	 */
	public String getZipCode()
	{
		return zipCode;
	}

	/**
	 * set the zip
	 * 
	 * @param
	 * @throws ZipcodeFormatException
	 */
	public void setZipCode(String zipCode) throws ZipcodeFormatException
	{
		if (zipCode.trim().matches("\\d{5}$"))
		{
			this.zipCode = zipCode;
		}
		else
		{
			throw new ZipcodeFormatException("There is not 5 numbers i the zipcode");
		}
	}

	/**
	 * get the city
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * set the city
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	@Override
	public String toString()
	{
		return "Address{" + "street='" + street + '\'' + ", zipCode='" + zipCode + '\'' + ", city='" + city + '\'' + '}';
	}
}
