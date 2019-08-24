package com.jocata.tbml.framework;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.jocata.tbml.framework.CSVColumns;
import com.jocata.tbml.framework.CommonFunctions;
import com.jocata.tbml.framework.Driver;
import com.jocata.tbml.framework.TakeAction;

public class Driver {
	private static WebDriver _driver = null;
	public List<List<String>> csvData;
	public List<List<String>> IncludeStepKeys = new LinkedList<List<String>>();
	public List<List<String>> includeCSVFileData;
	public List<List<String>> StepKeyList = new LinkedList<List<String>>();
	//public CommonFunctions Csv = null;
	public CommonFunctions config = new CommonFunctions();
	
	public void setCSVFile(String filePath){
		String Path = System.getProperty("user.dir");
		loadCSV(Path+"\\csv\\tbml\\"+filePath);
	}
	
	public static void setDriver(String browser) {
		if (browser.equalsIgnoreCase("ie")) {
			if (CommonFunctions.is64Bit()) {
				File _file = new File("Drivers/IEDriverServer_64.exe");
				System.setProperty("webdriver.ie.driver", _file
						.getAbsolutePath());
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				_driver = new InternetExplorerDriver();
			} else {
				File _file = new File("Drivers/IEDriverServer_32.exe");
				System.setProperty("webdriver.ie.driver", _file
						.getAbsolutePath());
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				_driver = new InternetExplorerDriver();
				_driver.manage().window().maximize();
			}
		} else if (browser.equalsIgnoreCase("chrome")) {
			File _file = new File("Drivers/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", _file
					.getAbsolutePath());
			DesiredCapabilities desiredCapabilities= DesiredCapabilities.chrome();
			ChromeOptions options=new ChromeOptions();
			desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
			_driver = new ChromeDriver();
			_driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("gecko")) {
			if (CommonFunctions.is64Bit()) {
				File _file = new File("Drivers/geckodriver.exe");
				System.setProperty("webdriver.firefox.marionette", _file
						.getAbsolutePath());
				//DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				//capabilities.setCapability("marionette",true);
				//_driver = new FirefoxDriver(capabilities);
				_driver = new FirefoxDriver();
				_driver.manage().window().maximize();
			} 
		}else if (browser.equalsIgnoreCase("firefox")) {
			_driver = new FirefoxDriver();
			_driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("hud")) {
			_driver = new HtmlUnitDriver();
			_driver.manage().window().maximize();
		} else {
			System.err.println(browser.toUpperCase()
					+ " is not supported in framework");
			System.exit(0);
		}
	}
	
	public static WebDriver getDriver() {
		try {
			if (_driver != null) {
				_driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
				return _driver;
			} else {
				setDriver(CommonFunctions.getValueFromProperty("BROWSER"));
				return _driver;
			}
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
			System.exit(0);
			return null;
		}
	}

	public static void pause(int iTime){
		try{
			Thread.sleep(iTime*1000);
		}
		catch(Exception e){
			System.out.println("nothing");
		}
	}

	public void readStepKeysfromIncludedCsvFiles() {
		System.out.println("i am at readStepKeysfromIncludedCsvFiles");
		System.out.println("check1 :"+ csvData.size());
		IncludeStepKeys = CommonFunctions.readStepKeyData(csvData, "CSVInclude");
		for(int IncludeFileCount =0; IncludeFileCount<IncludeStepKeys.size();IncludeFileCount++){

			//includeCSVFileData= Csv.readCSV(mainCsvPath+"\\"+IncludeStepKeys.get(IncludeFileCount).get(CSVColumns.VALUETOSET_COL));
			includeCSVFileData= CommonFunctions.readCSV(IncludeStepKeys.get(IncludeFileCount).get(CSVColumns.ELEMENTTYPE_COL));
			csvData.addAll(includeCSVFileData);

		}
	}   

	public void loadCSV(String sCsvFile) {
		System.out.println("sCsvFile vlaue :"+sCsvFile);
		File csvFile = new File(sCsvFile);
		if (!csvFile.exists())
		{
			org.testng.Assert.fail("Invalid CSV Path: "+ csvFile.getAbsolutePath());
		}
		csvData = CommonFunctions.readCSV(sCsvFile);
		System.out.println("check1 :"+ csvData.size());
		readStepKeysfromIncludedCsvFiles();
	}

	public void runKeyValue(String stepKey){
		StepKeyList = CommonFunctions.readStepKeyData(csvData, stepKey);
		System.out.println("csvData : "+csvData.size());
		System.out.println("stepKey : "+stepKey);
		if(StepKeyList.size()==0){
		}
		else{
			for(int i=0;i<StepKeyList.size();i++){
				try{
					/* passing row data */
					System.out.println(StepKeyList.get(i));
					doAction(StepKeyList.get(i));
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}         			       		
		}
		StepKeyList.clear();
	}
	void doAction(List<String> RowData) throws InterruptedException, AWTException{
		
		String Method = RowData.get(CSVColumns.FUNCTION_COL);
		System.out.println("-------Function-------"+Method);
			if(Method.length()<1){
			assertTrue(false);
		}
		else{
			if(Method.equals("open")){
				String URL = config.parseEnvConfig(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
				String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
				TakeAction.urlOpen(URL,sLogMessage);
			}
			else if(Method.equals("geturl")){
				String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
				TakeAction.geturl(sLogMessage);
			}
			else if(Method.equals("pageLoad")){
				String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
				TakeAction.pageLoad(sLogMessage);
			}
			else if(Method.equals("pause")){
				String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
				TakeAction.pause(sLogMessage);
			}
			else if(Method.equals("nextwindow")){
				String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
				TakeAction.nextwindow(sLogMessage);
			}
			else if(Method.equals("type")){
				System.out.println("i am at type");
				String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
				String sValueToSet = config.parseEnvConfig(RowData.get(CSVColumns.INPUTVALUE_COL));
				String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
				String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
				TakeAction.type(sWebObj,sWebElement,sValueToSet,sLogMessage);
			}
			else if(Method.equals("password")){
				
				String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
				String sValueToSet = config.parseEnvConfig(RowData.get(CSVColumns.INPUTVALUE_COL));
				String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
				String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
				TakeAction.password(sWebObj,sWebElement,sValueToSet,sLogMessage);
			}
		else if(Method.equals("click")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.click(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("doubleClick")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.doubleClick(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("mouseover")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.mouseOver(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("mouseOverText")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.mouseOverText(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("dropDownSelectedValue")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.dropDownSelectedValue(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("select")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sValueToSet = config.parseEnvConfig(RowData.get(CSVColumns.INPUTVALUE_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.select(sWebObj,sWebElement,sValueToSet,sLogMessage);
		}else if(Method.equals("isDisplayed")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.isDisplayed(sWebObj, sWebElement, sLogMessage);
		}else if(Method.equals("selectByIndex")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sValueToSet = config.parseEnvConfig(RowData.get(CSVColumns.INPUTVALUE_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.selectByIndex(sWebObj,sWebElement,sValueToSet,sLogMessage);
		}
		else if(Method.equals("selectByText")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sValueToSet = config.parseEnvConfig(RowData.get(CSVColumns.INPUTVALUE_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.selectByText(sWebObj,sWebElement,sValueToSet,sLogMessage);
		}
			
		else if(Method.equals("selectimage")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.selectimage(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("selectimage1")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.selectimage1(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("selectimagePNG")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.selectimagePNG(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("selectfile")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.selectfile(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("selectfile1")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.selectfile1(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("selectfile2")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.selectfile2(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("selectfile3")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.selectfile3(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("selectfile4")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.selectfile4(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("selectfile5")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.selectfile5(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("selectpdffile")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.selectpdffile(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("VndrCoOrdntrImprt")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.VndrCoOrdntrImprt(sWebObj,sWebElement,sLogMessage);
		}	
		else if(Method.equals("verifyTextAtLocation")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sexpectedValue = config.parseEnvConfig(RowData.get(CSVColumns.INPUTVALUE_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.verifyTextAtLocation(sWebObj,sWebElement,sexpectedValue,sLogMessage);
		}
		else if(Method.equals("verifyFieldText")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sexpectedValue = config.parseEnvConfig(RowData.get(CSVColumns.INPUTVALUE_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.verifyFieldText(sWebObj,sWebElement,sexpectedValue,sLogMessage);
		}
		else if(Method.equals("verifyButtonAndFieldText")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sexpectedValue = config.parseEnvConfig(RowData.get(CSVColumns.INPUTVALUE_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.verifyButtonText(sWebObj,sWebElement,sexpectedValue,sLogMessage);
		}
		else if(Method.equals("DisplayFieldText")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.DisplayFieldText(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("verifyTextColor")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sexpectedValue = config.parseEnvConfig(RowData.get(CSVColumns.INPUTVALUE_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.verifyTextColor(sWebObj,sWebElement,sexpectedValue,sLogMessage);
		}
		else if(Method.equals("displayText")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.displayText(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("DisplayMessage")){
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.DisplayMessage(sWebElement,sLogMessage);
		}
		else if(Method.equals("assertTextAtLocation")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sexpectedValue = config.parseEnvConfig(RowData.get(CSVColumns.INPUTVALUE_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.assertTextAtLocation(sWebObj,sWebElement,sexpectedValue,sLogMessage);
		}
		else if(Method.equals("verifyelementAttributeValue")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sexpectedValue = config.parseEnvConfig(RowData.get(CSVColumns.INPUTVALUE_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.verifyelementAttributeValue(sWebObj,sWebElement,sexpectedValue,sLogMessage);
		}
		else if(Method.equals("isSelected")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.isSelected(sWebObj, sWebElement, sLogMessage);
		}
		else if(Method.equals("CurrentDay")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.CurrentDay(sWebObj,sWebElement,sLogMessage);
		}	
		else if(Method.equals("FutureDay")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.FutureDate(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("PrvsYearDay")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.PrvsYearDay(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("CurrentDate")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.CurrentDate(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("ExplicitWait")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.ExplicitWait(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("WaitForSpinner")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.WaitSpinner(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("UploadWait")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.UploadWait(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("UploadWait1")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.UploadWait1(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("Date")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.Date(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("ReadOnly")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.ReadOnly(sWebObj,sWebElement,sLogMessage);
		}
		else if(Method.equals("FutureDate")){
			String sWebObj = config.parseObjectRep(RowData.get(CSVColumns.ELEMENTADDRESS_COL));
			String sWebElement = RowData.get(CSVColumns.ELEMENTTYPE_COL);
			String sLogMessage = RowData.get(CSVColumns.LOGMESSAGE_COL);
			TakeAction.FutureDay(sWebObj,sWebElement,sLogMessage);
		}
		}
	}
}
