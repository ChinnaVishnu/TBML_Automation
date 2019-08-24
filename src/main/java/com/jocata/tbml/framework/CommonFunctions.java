package com.jocata.tbml.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.jocata.tbml.framework.CSVColumns;

import au.com.bytecode.opencsv.CSVReader;

public class CommonFunctions {

	public static boolean isValidCSV = true;
	public Properties ENVCONFIG = null;
	public Properties OBJREP = null;
	public Properties ATU = null;
	
	CommonFunctions(){
		try {
			// envconfig
			ENVCONFIG = new Properties();
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+ "\\Config\\tbml\\MainConfig.properties");
			ENVCONFIG.load(fs);
			// objrep
			OBJREP = new Properties();
			fs = new FileInputStream(System.getProperty("user.dir")+ "\\Config\\tbml\\objectRep.properties");
			OBJREP.load(fs);
			//atu.properties
			ATU = new Properties();
			fs = new FileInputStream(System.getProperty("user.dir")+ "\\Config\\tbml\\atu.properties");
			ATU.load(fs);
			
		} catch (Exception e) {
	        Reporter.log("Excepntion in reading configuration files");
			e.printStackTrace();
		}
		
	}
	public String parseObjectRep(String webObj){
		if(webObj.contains("getObjValue")){
			List<String> list = new ArrayList<String>(20);  
			Pattern p = Pattern.compile("getObjValue\\((.+?)\\)");  
			Matcher m = p.matcher(webObj);  
			while (m.find()){  
				list.add(m.group(1));  
			}

			for(int i =0; i<list.size();i++){
				String Retrieved = OBJREP.getProperty(list.get(i));
				if (Retrieved == null || Retrieved.equals(""))
					return null;
				webObj = webObj.replace("getObjValue("+list.get(i)+")", Retrieved);
			}
		}
		return webObj;
	}
	
	public String parseEnvConfig(String envValue){
		if(envValue.contains("getEnvValue")){
		List<String> list = new ArrayList<String>(20);  
		Pattern p = Pattern.compile("getEnvValue\\((.+?)\\)");  
		Matcher m = p.matcher(envValue);  
		while (m.find()){  
			list.add(m.group(1));  
		}

		for(int i =0; i<list.size();i++){
			String Retrieved = ENVCONFIG.getProperty(list.get(i));
			if (Retrieved == null || Retrieved.equals(""))
				return null;
			envValue = envValue.replace("getEnvValue("+list.get(i)+")", Retrieved);
			//Reporter.log(envValue);
			}
		}
		return envValue;
		 
	}
	
	public static List<List<String>> readCSV(String sCSVFile) {
		//Constructing file paths
		String sFullPath = sCSVFile;
		System.out.println("sFullPath : "+ sFullPath);
		if(new File(sCSVFile).exists())
			System.out.println("CSV file ("+sCSVFile+") exists");
		else if(new File(sFullPath).exists()){
			System.out.println("CSV file ("+sFullPath+") exists");
			sCSVFile = sFullPath;
		}
		else{
			System.out.println("CSV file ("+sCSVFile+") doesnot exists in the specified location");
			System.out.println("Give correct CSV file path in the script constructor, exiting the test..");
			isValidCSV = false;
		}
		ArrayList<List<String>> llCsvData = new ArrayList<List<String>>();
		try {
			CSVReader csvReader = new CSVReader(new FileReader(sCSVFile));
			String[] line = null;
			while((line = csvReader.readNext()) != null) {
				ArrayList<String> rowData = new ArrayList<String>(10);
				for(int i=0;i<line.length;i++){
					rowData.add(line[i]);
				}
				if(rowData.size()>1){
					llCsvData.add(rowData);
				}
			}
		}
		catch (IOException e) {
	}
		return llCsvData;
	}
	public static List<List<String>> readStepKeyData(List<List <String>>llData, String stepKeyName){
		System.out.println("lData :"+llData.size());
		ArrayList<List<String>> llStepData = new ArrayList<List<String>>(100);
		for(int i=0;i<llData.size();i++){
			if(llData.get(i).get(CSVColumns.KEYVALUE_COL).equals(stepKeyName)){
				llStepData.add(llData.get(i));
			}
		}
		return llStepData;
	}
	public static boolean is64Bit() {
		String arch = System.getenv("PROCESSOR_ARCHITECTURE");
		if (arch.endsWith("64")) {
			return true;
		} else {
			return false;
		}
	}
	public static Properties loadPropertyFile(String path){
		Properties _props = new Properties();
		try{
			_props.load(new FileInputStream(path));
		} catch(IOException ioe){
			System.err.println("IE exception occured "+ioe.getMessage());
		}
		return _props;
	}
	public static String getValueFromProperty(String key){

		Properties _props = loadPropertyFile("Config/tbml/MainConfig.properties");
		String value = null;
		if(_props != null){
			value = _props.getProperty(key);
		} else {
			System.err.println("Key "+key+" not found in the properties file");
		}
		return value;
	}
	public static String getValueFromObjectRep(String key){

		Properties _props = loadPropertyFile("Config/tbml/objectRep.properties");
		String value = null;
		if(_props != null){
			value = _props.getProperty(key);
		} else {
			System.err.println("Key "+key+" not found in the properties file");
		}
		return value;
	}
	public static String getValueFromAtu(String key){

		Properties _props = loadPropertyFile("Config/tbml/atu.properties");
		String value = null;
		if(_props != null){
			value = _props.getProperty(key);
		} else {
			System.err.println("Key "+key+" not found in the properties file");
		}
		return value;
	}
	
	public static String getRandomString(int length) {
		String result = new BigInteger(Long.SIZE * length, new SecureRandom()).toString(32);
		return result.substring(0, length);
	}
	public static String getDate(String dateFormat, int difference, String type) {
		Calendar cal = Calendar.getInstance();
		try {
			if (difference != 0) {
				if (type.equalsIgnoreCase("days")) {
					cal.add(Calendar.DATE, difference);
				} else if (type.equalsIgnoreCase("months")) {
					cal.add(Calendar.MONTH, difference);
				} else if (type.equalsIgnoreCase("years")) {
					cal.add(Calendar.YEAR, difference);
				}
			}
			Date updatedDate = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			return format.format(updatedDate);
		} catch (IllegalArgumentException e) {
			System.err.println("Error on finding date "+e.getMessage());
			return null;
		}
	}
	public static WebElement clickButton(String sValue){
		WebElement _button = null;
		try{
			WebDriver _driver = Driver.getDriver();
			List<WebElement> _allButtons = _driver.findElements(By.tagName("button"));
			for(WebElement _eachButton : _allButtons){
				System.out.println("Button Text "+_eachButton.getText());
				if(_eachButton.getText().equalsIgnoreCase(sValue)){
					System.out.println("required button found");
					_button = _eachButton;
					CommonFunctions.highlightElement(_button);
					break;
				}
			}
		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
			_button = null;
		}
		return _button;
	}
	public static void highlightElement(WebElement element) { 
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver(); 
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: yellow; border: 2px solid yellow;"); 
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, ""); 
			Driver.pause(1);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid yellow;"); 
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, ""); 
			Driver.pause(1);

		}
	}
	public static String getPresentTime(){
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
	}
}

