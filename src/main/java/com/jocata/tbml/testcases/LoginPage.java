package com.jocata.tbml.testcases;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

import com.jocata.tbml.framework.Driver;
import com.jocata.tbml.testNgIntegration.TestNgIntegration;
import com.jocata.tbml.framework.CommonFunctions;

public class LoginPage extends TestNgIntegration {

	String presentTime = null;
	Driver driver = new Driver();
	WebDriver _driver = Driver.getDriver();

	String AutherName = CommonFunctions.getValueFromProperty("AutherName");
	String BuildNumber = CommonFunctions.getValueFromProperty("BuildNumber");

	@Test(priority = 1)
	public void validuser() throws InterruptedException {
		presentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a").format(Calendar.getInstance().getTime());
		ATUReports.setAuthorInfo(AutherName, presentTime, BuildNumber);
		driver.setCSVFile("Login.csv");

		try {
			driver.runKeyValue("Login");
			driver.runKeyValue("Login1");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			driver.runKeyValue("Logout");
		}
	}
	
	@Test(priority = 2)
	public void invaliduser() throws InterruptedException {
		presentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a").format(Calendar.getInstance().getTime());
		ATUReports.setAuthorInfo(AutherName, presentTime, BuildNumber);
		driver.setCSVFile("Login.csv");

		driver.runKeyValue("Login");

		Boolean ClckHereLnk = _driver.findElements(By.xpath(CommonFunctions.getValueFromObjectRep("hereLnk")))
				.size() != 0;
		if (ClckHereLnk == true) {
			driver.runKeyValue("LogoutPage");
			driver.runKeyValue("Login2");
		} else {
			driver.runKeyValue("Login2");
		}
	}
	
	@Test(priority = 3)
	public void invalidpwd() throws InterruptedException {
		presentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a").format(Calendar.getInstance().getTime());
		ATUReports.setAuthorInfo(AutherName, presentTime, BuildNumber);
		driver.setCSVFile("Login.csv");

		driver.runKeyValue("Login");
		driver.runKeyValue("Login3");
	}
	
	@Test(priority = 4)
	public void invaliduserpwd() throws InterruptedException {
		presentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a").format(Calendar.getInstance().getTime());
		ATUReports.setAuthorInfo(AutherName, presentTime, BuildNumber);
		driver.setCSVFile("Login.csv");

		driver.runKeyValue("Login");
		driver.runKeyValue("Login4");
	}
	
	@Test(priority = 5)
	public void withoutDetails() throws InterruptedException {
		presentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a").format(Calendar.getInstance().getTime());
		ATUReports.setAuthorInfo(AutherName, presentTime, BuildNumber);
		driver.setCSVFile("Login.csv");

		driver.runKeyValue("Login");
		driver.runKeyValue("Login5");
	}
	
	@Test(priority = 6)
	public void clickOnOkBtn() throws InterruptedException {
		presentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a").format(Calendar.getInstance().getTime());
		ATUReports.setAuthorInfo(AutherName, presentTime, BuildNumber);
		driver.setCSVFile("Login.csv");

		driver.runKeyValue("Login");
		driver.runKeyValue("Login6");
		Thread.sleep(2000);
		boolean alertPopUp = _driver.findElements(By.id(CommonFunctions.getValueFromObjectRep("msgPopUp"))).size() != 0;
		if (alertPopUp == true) {
			ATUReports.add("Verifying that the pop-up is closing or not", "", "", "Pop-up is closed", LogAs.PASSED,
					new CaptureScreen(ScreenshotOf.DESKTOP));
		} else {
			ATUReports.add("Verifying that the pop-up is closing or not", "", "", "Pop-up is not closed", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}
}
