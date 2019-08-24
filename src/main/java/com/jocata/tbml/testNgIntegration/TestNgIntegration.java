package com.jocata.tbml.testNgIntegration;

import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import com.jocata.tbml.framework.CommonFunctions;
import com.jocata.tbml.framework.Driver;
import com.jocata.tbml.framework.TakeAction;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class,	MethodListener.class })
public class TestNgIntegration {
	
	public static boolean verifyTextflag = true;
	{
	//System.setProperty("atu.reporter.config", "D:\\eclipse-SDK-3.4.2-win32\\workspace\\Selenium_v2\\Config\\atu.properties");
	System.setProperty("atu.reporter.config", System.getProperty("user.dir")+"\\Config\\tbml\\atu.properties");
	}
	
	@BeforeTest
	public void BeforeTest(){
	    Properties _log4j = CommonFunctions.loadPropertyFile("Config/tbml/log4j.properties");
		org.apache.log4j.PropertyConfigurator.configure(_log4j);
		String iTimeout = CommonFunctions.getValueFromProperty("TIMEOUTGLOBAL");
		System.out.println(iTimeout);
		TakeAction action = new TakeAction();
		
		
	//	System.out.println(CommonFunctions.getValueFromProperty("AUT"));
	//	System.out.println(CommonFunctions.getValueFromProperty("BROWSER"));
	//	Driver.setDriver(CommonFunctions.getValueFromProperty("BROWSER"));
	}

	@AfterTest
	public void AfterTest(){
		System.out.println(System.getProperty("user.dir"));
		Driver.getDriver().quit();
	}
	
	@BeforeMethod
	public void BeforeMethod(){
		ATUReports.setWebDriver(Driver.getDriver());
		ATUReports.indexPageDescription = CommonFunctions.getValueFromProperty("PROJECTDESC");
		TestNgIntegration.verifyTextflag = true;
		
	}
	
	@AfterMethod
	public void AfterMethod(){
		
	}
}
