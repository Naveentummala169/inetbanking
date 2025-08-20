package com.inetbanking.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.inetbanking.pageObjects.AddNewCustomerPage;
import com.inetbanking.pageObjects.LoginPage;

public class TC_AddNewCustomerTest_003 extends BaseClass
{
	@Test
	public void addNewCustomer() throws InterruptedException, IOException  
	{
		
		LoginPage lp=new LoginPage(driver);
		lp.setUserName(username);
		logger.info("username provided");
		lp.setPassword(password);
		logger.info("password provided");
		lp.clickSubmit();
		
		Thread.sleep(3000);
		
		AddNewCustomerPage addcust= new AddNewCustomerPage(driver);
		addcust.clickAddNewCustomer();
		logger.info("providing customer details.......");
		addcust.custName("Naveen");
		addcust.custgender("Male");
		addcust.custdob("10", "06", "1997");
		Thread.sleep(3000);
		addcust.custaddress("INDIA");
		addcust.custcity("Nellore");
		addcust.custstate("Andhra");
		addcust.custpinno("524305");
		addcust.custtelephoneno("9876549876");
		
		String email=randomstring()+"@gmail.com";
		addcust.custemailid(email);
		addcust.custpassword("abcdf");
		addcust.custsubmit();
		
		Thread.sleep(3000);
		logger.info("validation started.....");
		
		boolean res=driver.getPageSource().contains("Customer Registered Successfully");
		if(res==true) 
		{
			Assert.assertTrue(true);
			logger.info("test case passed...");
		}
		else 
		{
			logger.info("test case failed...");
			captureScreen(driver,"addNewCustomer");
			Assert.assertTrue(false);
		}
		
	}
	
	
}
