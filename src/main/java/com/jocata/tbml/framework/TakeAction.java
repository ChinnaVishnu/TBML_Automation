package com.jocata.tbml.framework;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.apache.log4j.Logger;

import com.jocata.tbml.framework.CommonFunctions;
import com.jocata.tbml.framework.Driver;
import com.jocata.tbml.framework.TakeAction;
import com.jocata.tbml.testNgIntegration.TestNgIntegration;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import static org.testng.Assert.*;

public class TakeAction {
	private static WebDriverWait wait;
	private static Logger Log = null;

	public TakeAction(){
		int globalTime = Integer.parseInt(CommonFunctions.getValueFromProperty("TIMEOUTGLOBAL"));
		wait = new WebDriverWait(Driver.getDriver(),globalTime);
		Log = Logger.getLogger(TakeAction.class.getName());
		Driver.getDriver().manage().timeouts().implicitlyWait(globalTime, TimeUnit.SECONDS);
	}
	public static void urlOpen(String url,String sMessage){
		try {
		System.out.println("URL :"+url);
		Driver.getDriver().get(url);
		if(!sMessage.isEmpty()){
			if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
				ATUReports.add(sMessage, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}else{
				Reporter.log(sMessage);
			}
			System.out.println(sMessage);
		}
		
	} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static void pause(String sMessage) throws InterruptedException{

		Thread.sleep(2000);
		if(!sMessage.isEmpty()){
			if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
				ATUReports.add(sMessage, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}else{
				Reporter.log(sMessage);
			}
			System.out.println(sMessage);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}

	public static void pageLoad(String sMessage) throws InterruptedException{

		int pageLoadTime = Integer.parseInt(CommonFunctions.getValueFromProperty("waitforPageLoad"));
		Driver.getDriver().manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);
		if(!sMessage.isEmpty()){
			if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
				ATUReports.add(sMessage, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}else{
				Reporter.log(sMessage);
			}
			System.out.println(sMessage);
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	public static void geturl(String sMessage){
		String actualValue = null;
		try{
			actualValue = Driver.getDriver().getCurrentUrl();
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"",actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}else{
							Reporter.log(sMessage);
							}
					System.out.println(sMessage);
						}else{
			ATUReports.add(sMessage,"",actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			assertTrue(false);
		}
	}
			catch(Exception ex){
				ATUReports.add(sMessage,"",actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				System.out.println("Exception - "+sMessage);
				assertTrue(false);
			}
		}
	
	public static void nextwindow(String sMessage){
		try{
				Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				String mw = Driver.getDriver().getWindowHandle();
				Set<String> windowids = Driver.getDriver().getWindowHandles();
				Iterator<String> it = windowids.iterator();
				while (it.hasNext()) {
					String nextwindow = it.next();
						if(!nextwindow.equals(mw)){
						Driver.getDriver().switchTo().window(nextwindow);
						System.out.println("Current window title : "+Driver.getDriver().getTitle());
						}
						if(!sMessage.isEmpty()){
						ATUReports.add(sMessage, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						System.out.println(sMessage);
					}else{
					ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}
			}
			}
			catch(Exception ex){
				System.err.println(ex.getMessage());
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				System.out.println("Exception - "+sMessage);
				assertTrue(false);
			}
		}
	
	public static void click(String sWebObj,String sWebElement,String sMessage){
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
				WebElement _element = isElementExists(by);
				if(null != _element){
					_element.click();
					if(!sMessage.isEmpty()){
						if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}else{
							Reporter.log(sMessage);
						}
						
						System.out.println(sMessage);
					}
				}
				else{
					ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}

			}else{
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
			ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	public static void click(By by,String sMessage){
		try{
			WebElement _element = isElementExists(by);
			if(null != _element){
				_element.click();
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}

		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
			ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	public static void click(WebElement _element,String sMessage){
		try{
			if(null != _element){
				_element.click();
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}

		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
			ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}

	public static void doubleClick(String sWebObj,String sWebElement,String sMessage){
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
				WebElement _element = isElementExists(by);
				if(null != _element){
				Actions action = new Actions(Driver.getDriver());
				action.moveToElement(_element).doubleClick().build().perform();
					if(!sMessage.isEmpty()){
						if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}else{
							Reporter.log(sMessage);
						}
						
						System.out.println(sMessage);
					}
				}
				else{
					ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}

			}else{
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
			ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	
	public static void isDisplayed(String sWebObj, String sWebElement, String sMessage){
		boolean displayed;
		try {
			By by = TakeAction.elementType(sWebObj, sWebElement);
			if (by != null) {
				WebElement _element = isElementExists(by);
				if (null != _element) {
					displayed = _element.isDisplayed();
					if (!sMessage.isEmpty()) {
						if (displayed == true) {
							ATUReports.add(sMessage,"","","Element is displayed", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						} else {
							ATUReports.add(sMessage,"","","Element does not exists", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}
					} else {

					}
				} else {
					ATUReports.add(sMessage,"","","Element does not exists", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				}
			} else {
				ATUReports.add(sMessage,"","","Element does not exists", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			ATUReports.add(sMessage,"","","Element does not exists", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	

	
	public static void doubleClick(By by,String sMessage){
		try{
			WebElement _element = isElementExists(by);
			if(null != _element){	
				Actions action = new Actions(Driver.getDriver());
				action.moveToElement(_element).doubleClick().build().perform();
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}

		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
			ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void doubleClick(WebElement _element,String sMessage){
		try{
			if(null != _element){
				Actions action = new Actions(Driver.getDriver());
				action.moveToElement(_element).doubleClick().build().perform();
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}

		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
			ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}

	public static void mouseOver(String sWebObj,String sWebElement,String sMessage){
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
			WebElement _element = isElementExists(by);
			if(null != _element){
				//CommonFunctions.highlightElement(_element);
				new Actions(Driver.getDriver()).moveToElement(_element).perform();
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}
						else{
							Reporter.log(sMessage);
						}
						
						System.out.println(sMessage);
					}
				}
				else{
					ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}

			}else{
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
			ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void mouseOverText(String sWebObj,String sWebElement, String sMessage){
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
			WebElement _element = isElementExists(by);
			if(null != _element){
				//CommonFunctions.highlightElement(_element);
				Actions builder = new Actions(Driver.getDriver());
				Action mouseover = builder.clickAndHold(_element).build();
				mouseover.perform();
				String title = _element.getAttribute("title");
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage,"","",title, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}
						else{
							Reporter.log(sMessage);
						}
						
						System.out.println(sMessage);
					}
				}
				else{
					ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}

			}else{
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
			ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void mouseOverText(By by,String sMessage){
		try{
			if(by != null){
			WebElement _element = isElementExists(by);
			if(null != _element){
				//CommonFunctions.highlightElement(_element);
				Actions builder = new Actions(Driver.getDriver());
				Action mouseover = builder.clickAndHold(_element).build();
				mouseover.perform();
				String title = _element.getAttribute("title");
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage,"","",title,LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}
						else{
							Reporter.log(sMessage);
						}
						
						System.out.println(sMessage);
					}
				}
				else{
					ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}

			}else{
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
			ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void dropDownSelectedValue(String sWebObj, String sWebElement,String sMessage){
		String actualValue = null;
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
			WebElement _element = isElementExists(by);
			if(null != _element){
				//CommonFunctions.highlightElement(_element);
				new Actions(Driver.getDriver()).moveToElement(_element).perform();
				
					Select archiveList = new Select(_element);
					actualValue = archiveList.getFirstSelectedOption().getText();
				
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage,"","",actualValue,LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}
						else{
							Reporter.log(sMessage);
						}
						
						System.out.println(sMessage);
					}
				}else{
						ATUReports.add(sMessage,"","",actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
						TestNgIntegration.verifyTextflag = false;
					}
				}
				else{
					ATUReports.add(sMessage,"","",actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}
			}
		catch(Exception ex){
			System.err.println(ex.getMessage());
			ATUReports.add(sMessage,"","",actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void select(String sWebObj,String sWebElement,String sText,String sMessage){
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			WebElement _element = isElementExists(by);
			if(null != _element){
				Select _sel = new Select(_element);
				_sel.selectByVisibleText(sText);
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,sText, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void select(By by,String sOption, String sMessage){
		try{
			WebElement _element = isElementExists(by);
			if(null != _element){
				Select _sel = new Select(_element);
				_sel.selectByVisibleText(sOption);
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,sOption, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	public static void select(WebElement _element,String sOption, String sMessage){
		try{
			if(null != _element){
				Select _sel = new Select(_element);
				_sel.selectByVisibleText(sOption);
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,sOption, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);

				}
			}
			else{
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void selectByText(String sWebObj,String sWebElement,String sText,String sMessage){
		  try{
		   By by= TakeAction.elementType(sWebObj,sWebElement);
		   WebElement _element = isElementExists(by);
		   if(null != _element){
		    Select _sel = new Select(_element);
			String value = sText;
		    _sel.selectByVisibleText(value);
		    if(!sMessage.isEmpty()){
		     if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
		      ATUReports.add(sMessage,sText, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
		     }else{
		      Reporter.log(sMessage);
		     }
		     System.out.println(sMessage);
		    }
		   }
		   else{
		    ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		    assertTrue(false);
		   }
		  }
		  catch(Exception ex){
		   ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		   System.out.println("Exception - "+sMessage);
		   assertTrue(false);
		  }
		 }

	public static void selectByIndex(String sWebObj,String sWebElement,String sText,String sMessage){
		  try{
		   By by= TakeAction.elementType(sWebObj,sWebElement);
		   WebElement _element = isElementExists(by);
		   if(null != _element){
		    Select _sel = new Select(_element);
		 int value = Integer.parseInt(sText);
		    _sel.selectByIndex(value);
		    if(!sMessage.isEmpty()){
		     if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
		      ATUReports.add(sMessage,sText, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
		     }else{
		      Reporter.log(sMessage);
		     }
		     System.out.println(sMessage);
		    }
		   }
		   else{
		    ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		    assertTrue(false);
		   }
		  }
		  catch(Exception ex){
		   ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		   System.out.println("Exception - "+sMessage);
		   assertTrue(false);
		  }
		 }
		 
		 public static void selectByIndex(By by,String sOption, String sMessage){
		  try{
		   WebElement _element = isElementExists(by);
		   if(null != _element){
		    Select _sel = new Select(_element);
		 int value = Integer.parseInt(sOption);
		    _sel.selectByIndex(value);
		    if(!sMessage.isEmpty()){
		     if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
		      ATUReports.add(sMessage,sOption, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
		     }else{
		      Reporter.log(sMessage);
		     }
		     System.out.println(sMessage);
		    }
		   }
		   else{
		    ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		    assertTrue(false);
		   }
		  }
		  catch(Exception ex){
		   ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		   System.out.println("Exception - "+sMessage);
		   assertTrue(false);
		  }
		 }
		 public static void selectByIndex(WebElement _element,String sOption, String sMessage){
		  try{
		   if(null != _element){
		    Select _sel = new Select(_element);
		 int value = Integer.parseInt(sOption);
		    _sel.selectByIndex(value);
		    if(!sMessage.isEmpty()){
		     if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
		      ATUReports.add(sMessage,sOption, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
		     }else{
		      Reporter.log(sMessage);
		     }
		     System.out.println(sMessage);

		    }
		   }
		   else{
		    ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		    assertTrue(false);
		   }
		  }
		  catch(Exception ex){
		   ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		   System.out.println("Exception - "+sMessage);
		   assertTrue(false);
		  }
		 }
	
	
	public static void selectimage(String sWebObj,String sWebElement,String sMessage) throws AWTException{
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			WebElement _element = isElementExists(by);
			if(null != _element){
				
		StringSelection s = new StringSelection(System.getProperty("user.dir")+ "\\Photos\\aa.jpg");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

//		Robot r1 = new Robot();
//				
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.keyRelease(KeyEvent.VK_ENTER);
//		r1.keyPress(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.delay(50);
//		r1.keyRelease(KeyEvent.VK_ENTER);
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    Thread.sleep(3000);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, "",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}

	public static void selectimage1(String sWebObj,String sWebElement,String sMessage) throws AWTException{
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			WebElement _element = isElementExists(by);
			if(null != _element){
				
		StringSelection s = new StringSelection(System.getProperty("user.dir")+ "\\Photos\\employee.jpg");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

//		Robot r1 = new Robot();
//				
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.keyRelease(KeyEvent.VK_ENTER);
//		r1.keyPress(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.delay(50);
//		r1.keyRelease(KeyEvent.VK_ENTER);
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    Thread.sleep(3000);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, "",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	

	public static void selectimagePNG(String sWebObj,String sWebElement,String sMessage) throws AWTException{
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			WebElement _element = isElementExists(by);
			if(null != _element){
				
		StringSelection s = new StringSelection(System.getProperty("user.dir")+ "\\Photos\\Logo2.png");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

		Thread.sleep(2000);
		Robot robot = new Robot();
		
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    Thread.sleep(3000);
	    robot.keyPress(KeyEvent.VK_ENTER);
		
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, "",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}


	public static void selectfile(String sWebObj,String sWebElement,String sMessage) throws AWTException{
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			WebElement _element = isElementExists(by);
			if(null != _element){
				
		StringSelection s = new StringSelection(System.getProperty("user.dir")+ "\\Files\\4325379_13_1415812025707.docx");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

//		Robot r1 = new Robot();
//				
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.keyRelease(KeyEvent.VK_ENTER);
//		r1.keyPress(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.delay(50);
//		r1.keyRelease(KeyEvent.VK_ENTER);
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    Thread.sleep(3000);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
		
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, "",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}

	public static void selectfile1(String sWebObj,String sWebElement,String sMessage) throws AWTException{
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			WebElement _element = isElementExists(by);
			if(null != _element){
				
		StringSelection s = new StringSelection(System.getProperty("user.dir")+ "\\Files\\Sample_Upload_Template_Visitor_Template.csv");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

//		Robot r1 = new Robot();
//				
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.keyRelease(KeyEvent.VK_ENTER);
//		r1.keyPress(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.delay(50);
//		r1.keyRelease(KeyEvent.VK_ENTER);
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    Thread.sleep(3000);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, "",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void selectfile2(String sWebObj,String sWebElement,String sMessage) throws AWTException{
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			WebElement _element = isElementExists(by);
			if(null != _element){
				
		StringSelection s = new StringSelection(System.getProperty("user.dir")+ "\\Files\\Sample_Upload_Template_Sponsor.csv");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

//		Robot r1 = new Robot();
//				
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.keyRelease(KeyEvent.VK_ENTER);
//		r1.keyPress(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.delay(50);
//		r1.keyRelease(KeyEvent.VK_ENTER);
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    Thread.sleep(3000);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, "",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void selectfile3(String sWebObj,String sWebElement,String sMessage) throws AWTException{
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			WebElement _element = isElementExists(by);
			if(null != _element){
				
		StringSelection s = new StringSelection(System.getProperty("user.dir")+ "\\Files\\Sample_Upload_Basic_Visitor_Template.csv");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

//		Robot r1 = new Robot();
//				
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.keyRelease(KeyEvent.VK_ENTER);
//		r1.keyPress(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.delay(50);
//		r1.keyRelease(KeyEvent.VK_ENTER);
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    Thread.sleep(3000);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, "",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void selectfile4(String sWebObj,String sWebElement,String sMessage) throws AWTException{
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			WebElement _element = isElementExists(by);
			if(null != _element){
				
		StringSelection s = new StringSelection(System.getProperty("user.dir")+ "\\Files\\Sample_Upload_Template_VIP.csv");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

//		Robot r1 = new Robot();
//				
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.keyRelease(KeyEvent.VK_ENTER);
//		r1.keyPress(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.delay(50);
//		r1.keyRelease(KeyEvent.VK_ENTER);
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    Thread.sleep(3000);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, "",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}

	public static void selectfile5(String sWebObj,String sWebElement,String sMessage) throws AWTException{
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			WebElement _element = isElementExists(by);
			if(null != _element){
				
		StringSelection s = new StringSelection(System.getProperty("user.dir")+ "\\Files\\Sample_Upload_Template_WatchList.csv");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

//		Robot r1 = new Robot();
//				
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.keyRelease(KeyEvent.VK_ENTER);
//		r1.keyPress(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.delay(50);
//		r1.keyRelease(KeyEvent.VK_ENTER);
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    Thread.sleep(3000);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, "",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void selectpdffile(String sWebObj,String sWebElement,String sMessage) throws AWTException{
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			WebElement _element = isElementExists(by);
			if(null != _element){
				
		StringSelection s = new StringSelection(System.getProperty("user.dir")+ "\\Files\\terms-and-conditions.pdf");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

//		Robot r1 = new Robot();
//				
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.keyRelease(KeyEvent.VK_ENTER);
//		r1.keyPress(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.delay(50);
//		r1.keyRelease(KeyEvent.VK_ENTER);
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    Thread.sleep(3000);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, "",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void VndrCoOrdntrImprt(String sWebObj,String sWebElement,String sMessage) throws AWTException{
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			WebElement _element = isElementExists(by);
			if(null != _element){
				
		StringSelection s = new StringSelection(System.getProperty("user.dir")+ "\\Files\\Sample_Vendor_And_Coordinators_Template.csv");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

//		Robot r1 = new Robot();
//				
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.keyRelease(KeyEvent.VK_ENTER);
//		r1.keyPress(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_V);
//		r1.keyRelease(KeyEvent.VK_CONTROL);
//		r1.keyPress(KeyEvent.VK_ENTER);
//		r1.delay(50);
//		r1.keyRelease(KeyEvent.VK_ENTER);
		
		Thread.sleep(2000);
		Robot robot = new Robot();
		
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    robot.keyPress(KeyEvent.VK_TAB);
	    robot.keyRelease(KeyEvent.VK_TAB);
	    
	    Thread.sleep(3000);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage, "",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void type(String sWebObj,String sWebElement,String sText, String sMessage){
		System.out.println("i am at :type");
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
				WebElement _element = isElementExists(by);
				if(null != _element){
					_element.clear();
					_element.sendKeys(sText);
					if(!sMessage.isEmpty()){
						if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage,sText, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}else{
							Reporter.log(sMessage);
						}
						System.out.println(sMessage);
					}
				}
				else{
					ATUReports.add(sMessage,sText,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}
			}else{
				ATUReports.add(sMessage,sText,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage,sText,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	public static void type(By by,String sText, String sMessage){
		try{
			WebElement _element = isElementExists(by);
			if(null != _element){
				_element.clear();
				_element.sendKeys(sText);
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,sText, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,sText,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage,sText,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	public static void type(WebElement _element,String sText, String sMessage){
		try{
			if(null != _element){
				_element.clear();
				_element.sendKeys(sText);
				System.out.println(sMessage);
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,sText, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,sText, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage,sText, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	public static void password(String sWebObj,String sWebElement,String sText, String sMessage){
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
				WebElement _element = isElementExists(by);
				if(null != _element){
//					_element.clear();
					_element.sendKeys(sText);
					if(!sMessage.isEmpty()){
						if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage,"*****", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}else{
							Reporter.log(sMessage);
						}
						System.out.println(sMessage);
					}
				}
				else{
					ATUReports.add(sMessage,"*****",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}
			}else{
				ATUReports.add(sMessage,"*****",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"*****",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	public static void password(By by,String sText, String sMessage){
		try{
			WebElement _element = isElementExists(by);
			if(null != _element){
				_element.clear();
				_element.sendKeys(sText);
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"*****", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,"*****",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"*****",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	public static void password(WebElement _element,String sText, String sMessage){
		try{
			if(null != _element){
				_element.clear();
				_element.sendKeys(sText);
				if(!sMessage.isEmpty()){
					if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
						ATUReports.add(sMessage,"*****", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}else{
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			}
			else{
				ATUReports.add(sMessage,"*****", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"*****", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void displayText(String sWebObj,String sWebElement, String sMessage){
		String value = null;
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
				WebElement _element = isElementExists(by);
				if(null != _element){
					value = _element.getText().trim();
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"","",value, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
						}
					}else{
						ATUReports.add(sMessage,"","",value, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
					}
				}
				else{
					ATUReports.add(sMessage,"","",value, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
					
				}
			}else{
				ATUReports.add(sMessage,"","",value, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
			
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"","",value, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void displayText(By by,String sMessage){

		String value = null;
		try{
			WebElement _element = isElementExists(by);
			if(null != _element){
				value = _element.getText().trim();
					if(!sMessage.isEmpty()){
						if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage,"","",value, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}else{
							Reporter.log(sMessage);
					}
				}else{
					ATUReports.add(sMessage,"","",value, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
				}
			}
			else{
			ATUReports.add(sMessage,"","",value, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			assertTrue(false);
		}
		
	}
	catch(Exception ex){
		ATUReports.add(sMessage,"","",value, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		System.out.println("Exception - "+sMessage);
		assertTrue(false);
	}
}
	
	public static void DisplayMessage(String value,String sMessage) {

		
		try {

			if (!sMessage.isEmpty()) {
				if (CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")) 
				{
					ATUReports.add(sMessage, "","", value, LogAs.PASSED,new CaptureScreen(ScreenshotOf.DESKTOP));
				} else {
					Reporter.log(sMessage);
				}
			} else {
				ATUReports.add(sMessage, "","", value, LogAs.FAILED,new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}

		} catch (Exception ex) {
			ATUReports.add(sMessage, "","", sMessage, LogAs.FAILED,new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - " + sMessage);
			assertTrue(false);
		}
	}

	public static void verifyTextAtLocation(String sWebObj,String sWebElement,String expectedValue, String sMessage){
		String actualValue = null;
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
				WebElement _element = isElementExists(by);
				if(null != _element){
					actualValue = _element.getText().trim();
					if(actualValue.equals(expectedValue)){
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
								TestNgIntegration.verifyTextflag = false;
							}
						}else{
							ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
							TestNgIntegration.verifyTextflag = false;
						}
					}else{
						ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
						TestNgIntegration.verifyTextflag = false;
					}
				}else{
					ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
					TestNgIntegration.verifyTextflag = false;
				}
			}else{
				ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
			
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void verifyTextAtLocation(By by,String expectedValue, String sMessage){
		String actualValue = null;
		try{
			WebElement _element = isElementExists(by);
			if(null != _element){
				actualValue = _element.getText().trim();
				if(actualValue.equals(expectedValue)){
					if(!sMessage.isEmpty()){
						if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}else{
							ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
							TestNgIntegration.verifyTextflag = false;
						}
					}
				}else{
					ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
					TestNgIntegration.verifyTextflag = false;
				}
			}
			else{
				ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
				TestNgIntegration.verifyTextflag = false;
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void verifyFieldText(String sWebObj,String sWebElement,String expectedValue, String sMessage){
		String actualValue = null;
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
				WebElement _element = isElementExists(by);
				if(null != _element){
					actualValue = _element.getAttribute("value");
					if(actualValue.equals(expectedValue)){
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
								TestNgIntegration.verifyTextflag = false;
							}
						}
					}else{
						ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
						TestNgIntegration.verifyTextflag = false;
					}
				}
				else{
					ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					TestNgIntegration.verifyTextflag = false;
				}
			}else{
				ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
			
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void verifyFieldText(By by,String expectedValue, String sMessage){
		String actualValue = null;
		try{
			WebElement _element = isElementExists(by);
			if(null != _element){
				actualValue = _element.getAttribute("value");
				if(actualValue.equals(expectedValue)){
					if(!sMessage.isEmpty()){
						if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}else{
							ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
							TestNgIntegration.verifyTextflag = false;
						}
					}
				}else{
					ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
					TestNgIntegration.verifyTextflag = false;
				}
			}
			else{
				ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				TestNgIntegration.verifyTextflag = false;
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void verifyButtonText(By by,String expectedValue, String sMessage){
		String actualValue = null;
		try{
			WebElement _element = isElementExists(by);
			if(null != _element){
					actualValue = _element.getAttribute("value");
					if(actualValue.equals(expectedValue)){
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
							}
							System.out.println(sMessage);
						}
					}else{
						ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
						TestNgIntegration.verifyTextflag = false;
					}
				}
				else{
					ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					TestNgIntegration.verifyTextflag = false;
				}
			}
			catch(Exception ex){
				ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				System.out.println("Exception - "+sMessage);
				assertTrue(false);
			}
		}
	
	public static void verifyButtonText(String sWebObj,String sWebElement,String expectedValue, String sMessage){
		String actualValue = null;
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
				WebElement _element = isElementExists(by);
				if(null != _element){
					actualValue = _element.getAttribute("value");
					if(actualValue.equals(expectedValue)){
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
							}
							System.out.println(sMessage);
						}
					}else{
						ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
						TestNgIntegration.verifyTextflag = false;
					}
				}
				else{
					ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
					TestNgIntegration.verifyTextflag = false;
				}
			}else{
				ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
			
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void DisplayFieldText(By by, String sMessage) {
		String actualValue = null;
		try {
			WebElement _element = isElementExists(by);
			if (null != _element) {
				actualValue = _element.getAttribute("value");
				if (!sMessage.isEmpty()) {
					if (CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")) 
					{
						ATUReports.add(sMessage, "", actualValue, LogAs.PASSED,new CaptureScreen(ScreenshotOf.DESKTOP));
					} else {
						Reporter.log(sMessage);
					}
					System.out.println(sMessage);
				}
			} else {
				ATUReports.add(sMessage, "", actualValue, LogAs.FAILED,new CaptureScreen(ScreenshotOf.DESKTOP));
				TestNgIntegration.verifyTextflag = false;
			}
		} catch (Exception ex) {
			ATUReports.add(sMessage, "", actualValue, LogAs.FAILED,new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - " + sMessage);
			assertTrue(false);
		}
	}
	
	public static void DisplayFieldText(String sWebObj, String sWebElement,String sMessage) 
	{
		String actualValue = null;
		try {
			By by = TakeAction.elementType(sWebObj, sWebElement);
			if (by != null) {
				WebElement _element = isElementExists(by);
				if (null != _element) {
					actualValue = _element.getAttribute("value");
					if (!sMessage.isEmpty()) {
						if (CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")) 
						{
							ATUReports.add(sMessage, "", actualValue,LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						} else {
							Reporter.log(sMessage);
						}
						System.out.println(sMessage);
					}
				}
			} else {
				ATUReports.add(sMessage, "", actualValue, LogAs.FAILED,new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}

		} catch (Exception ex) {
			ATUReports.add(sMessage, "", actualValue, LogAs.FAILED,new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - " + sMessage);
			assertTrue(false);
		}
	}
	
	public static void verifyTextColor(By by,String expectedValue, String sMessage){
		String actualValue = null;
		try{
			WebElement _element = isElementExists(by);
			if(null != _element){
					actualValue = _element.getCssValue("color");
					if(actualValue.equals(expectedValue)){
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
							}
							System.out.println(sMessage);
						}
					}else{
						ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
						TestNgIntegration.verifyTextflag = false;
					}
				}
				else{
					ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					TestNgIntegration.verifyTextflag = false;
				}
			}
			catch(Exception ex){
				ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				System.out.println("Exception - "+sMessage);
				assertTrue(false);
			}
		}

	public static void verifyTextColor(String sWebObj,String sWebElement,String expectedValue, String sMessage){
		String actualValue = null;
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
				WebElement _element = isElementExists(by);
				if(null != _element){
					actualValue = _element.getCssValue("color");
					if(actualValue.equals(expectedValue)){
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
							}
							System.out.println(sMessage);
						}
					}else{
						ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
						TestNgIntegration.verifyTextflag = false;
					}
				}
				else{
					ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
					TestNgIntegration.verifyTextflag = false;
				}
			}else{
				ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
			
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void assertTextAtLocation(String sWebObj,String sWebElement,String expectedValue, String sMessage){
		String actualValue = null;
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
				WebElement _element = isElementExists(by);
				if(null != _element){
					actualValue = _element.getText().trim();
					if(actualValue.equals(expectedValue)){
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
							}
							System.out.println(sMessage);
						}
					}else{
						ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
						assertEquals(actualValue,expectedValue);
					}
				}
				else{
					ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}
			}else{
				ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
			
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	public static void assertTextAtLocation(By by,String expectedValue, String sMessage){
		String actualValue = null;
		try{
			WebElement _element = isElementExists(by);
			if(null != _element){
				actualValue = _element.getText().trim();
				if(actualValue.equals(expectedValue)){
					if(!sMessage.isEmpty()){
						if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}else{
							Reporter.log(sMessage);
						}
						System.out.println(sMessage);
					}
				}else{
					ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertEquals(actualValue,expectedValue);
				}
			}
			else{
				ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void verifyelementAttributeValue(String sWebObj,String sWebElement,String expectedValue, String sMessage){
		String actualValue = null;
		String keyValue = "placeholder";
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
				WebElement _element = isElementExists(by);
				if(null != _element){
					actualValue = _element.getAttribute(keyValue).trim();
					if(actualValue.equals(expectedValue)){
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
							}
							System.out.println(sMessage);
						}
					}else{
						ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
						TestNgIntegration.verifyTextflag = false;
					}
				}
				else{
					ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
					TestNgIntegration.verifyTextflag = false;
				}
			}else{
				ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
			
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	public static void verifyelementAttributeValue(By by,String keyValue,String expectedValue,String sMessage){
		String actualValue = null;
		try{
			WebElement _element = isElementExists(by);
			if(null != _element){
				actualValue = _element.getAttribute(keyValue).trim();
				if(actualValue.equals(expectedValue)){
					if(!sMessage.isEmpty()){
						if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}else{
							Reporter.log(sMessage);
						}
						System.out.println(sMessage);
					}
				}else{
					ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertEquals(actualValue,expectedValue);
				}
			}
			else{
				ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}

	public static void isSelected(String sWebObj,String sWebElement,String sMessage){
		try{
			By by= TakeAction.elementType(sWebObj,sWebElement);
			if(by != null){
				WebElement _element = isElementExists(by);
				if(null != _element){
					_element.isSelected();
					if(!sMessage.isEmpty()){
						if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
							ATUReports.add(sMessage, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						}else{
							Reporter.log(sMessage);
						}
						
						System.out.println(sMessage);
					}
				}
				else{
					ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}

			}else{
				ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
			ATUReports.add(sMessage, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void CurrentDay(String sWebObj,String sWebElement,String sMessage){
		
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		//get current date time with Date()
		Date date = new Date();
		// Now format the date
		String date1= dateFormat.format(date);
		
			try{
				By by= TakeAction.elementType(sWebObj,sWebElement);
				if(by != null){
					System.out.println("i am at :type1"+by);
					WebElement _element = isElementExists(by);
					if(null != _element){
						System.out.println("i am at :type2");
						_element.clear();
						_element.sendKeys(date1);
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,date1, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
							}
							System.out.println(sMessage);
						}
					}
					else{
						ATUReports.add(sMessage,date1,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
						assertTrue(false);
					}
				}else{
					ATUReports.add(sMessage,date1,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}
			}
			catch(Exception ex){
				ATUReports.add(sMessage,date1,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				System.out.println("Exception - "+sMessage);
				assertTrue(false);
			}
		}
	
	public static void FutureDate(String sWebObj,String sWebElement,String sMessage){
		
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		//get current date time with Date()
		Date dt = new Date();
		// Using the Calendar here
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		// Now format the date
		String futuredate= dateFormat.format(dt);
		
			try{
				By by= TakeAction.elementType(sWebObj,sWebElement);
				if(by != null){
					System.out.println("i am at :type1"+by);
					WebElement _element = isElementExists(by);
					if(null != _element){
						System.out.println("i am at :type2");
						_element.clear();
						_element.sendKeys(futuredate);
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,futuredate, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
							}
							System.out.println(sMessage);
						}
					}
					else{
						ATUReports.add(sMessage,futuredate,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
						assertTrue(false);
					}
				}else{
					ATUReports.add(sMessage,futuredate,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}
			}
			catch(Exception ex){
				ATUReports.add(sMessage,futuredate,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				System.out.println("Exception - "+sMessage);
				assertTrue(false);
			}
		}

	
	public static void PrvsYearDay(String sWebObj,String sWebElement,String sMessage){
		
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		//get current date time with Date()
		Date dt = new Date();
		// Using the Calendar here
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.YEAR, -1);
		dt = c.getTime();
		// Now format the date
		String futuredate= dateFormat.format(dt);
		
			try{
				By by= TakeAction.elementType(sWebObj,sWebElement);
				if(by != null){
					System.out.println("i am at :type1"+by);
					WebElement _element = isElementExists(by);
					if(null != _element){
						System.out.println("i am at :type2");
						_element.clear();
						_element.sendKeys(futuredate);
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,futuredate, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
							}
							System.out.println(sMessage);
						}
					}
					else{
						ATUReports.add(sMessage,futuredate,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
						assertTrue(false);
					}
				}else{
					ATUReports.add(sMessage,futuredate,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}
			}
			catch(Exception ex){
				ATUReports.add(sMessage,futuredate,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				System.out.println("Exception - "+sMessage);
				assertTrue(false);
			}
		}

	public static void CurrentDate(String sWebObj,String sWebElement,String sMessage){
		
		DateFormat dateFormat = new SimpleDateFormat("dd");
		//get current date time with Date()
		Date date = new Date();
		// Now format the date
		String expectedValue= dateFormat.format(date);
		String actualValue = null;
			try{
				By by= TakeAction.elementType(sWebObj,sWebElement);
				if(by != null){
					System.out.println("i am at :type1"+by);
					WebElement _element = isElementExists(by);
					if(null != _element){
						actualValue = _element.getText().trim();
						if(actualValue.equals(expectedValue)){
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								ATUReports.add(sMessage,"",actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
								TestNgIntegration.verifyTextflag = false;
							}
						}else{
							ATUReports.add(sMessage,"",actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
							TestNgIntegration.verifyTextflag = false;
						}
					}else{
						ATUReports.add(sMessage,"",actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
						TestNgIntegration.verifyTextflag = false;
					}
				}else{
					ATUReports.add(sMessage,"",actualValue, LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
					TestNgIntegration.verifyTextflag = false;
				}
			}else{
				ATUReports.add(sMessage,"",actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}
			
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"",expectedValue,actualValue, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}

	public static void ExplicitWait(String sWebObj,String sWebElement,String sMessage){
		
		WebDriverWait wait;
		
		int globalTime = Integer.parseInt(CommonFunctions.getValueFromProperty("ExplicitWait"));
		wait = new WebDriverWait(Driver.getDriver(),globalTime);
		Log = Logger.getLogger(TakeAction.class.getName());
		//Driver.getDriver().manage().timeouts().implicitlyWait(globalTime, TimeUnit.SECONDS);
			try{
				By by= TakeAction.elementType(sWebObj,sWebElement);
				if(by != null){
					System.out.println("i am at :type1"+by);
					WebElement _element = isElementExists(by);
					if(_element != null){
						System.out.println("i am at :type2");
//						wait.until(ExpectedConditions.visibilityOfElementLocated(by));
						wait.until(ExpectedConditions.presenceOfElementLocated(by));
						
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
							}
							System.out.println(sMessage);
						}
					}
					else{
						ATUReports.add(sMessage,"",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
						assertTrue(false);
					}
				}else{
					ATUReports.add(sMessage,"",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}
			}
			catch(Exception ex){
				ATUReports.add(sMessage,"",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				System.out.println("Exception - "+sMessage);
				assertTrue(false);
			}
		}
	
	public static void UploadWait(String sWebObj,String sWebElement,String sMessage){
		
		WebDriverWait wait;
		
		int globalTime = Integer.parseInt(CommonFunctions.getValueFromProperty("WaitForUpload"));
		wait = new WebDriverWait(Driver.getDriver(),globalTime);
		Log = Logger.getLogger(TakeAction.class.getName());
		//Driver.getDriver().manage().timeouts().implicitlyWait(globalTime, TimeUnit.SECONDS);
			try{
				By by= TakeAction.elementType(sWebObj,sWebElement);
				if(by != null){
					System.out.println("i am at :type1"+by);
					WebElement _element = isElementExists(by);
					if(_element != null){
						System.out.println("i am at :type2");
						//wait.until(ExpectedConditions.visibilityOfElementLocated(by));
						wait.until(ExpectedConditions.presenceOfElementLocated(by));
						
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
							}
							System.out.println(sMessage);
						}
					}
					else{
						ATUReports.add(sMessage,"",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
						assertTrue(false);
					}
				}else{
					ATUReports.add(sMessage,"",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}
			}
			catch(Exception ex){
				ATUReports.add(sMessage,"",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				System.out.println("Exception - "+sMessage);
				assertTrue(false);
			}
		}

	public static void UploadWait1(String sWebObj,String sWebElement,String sMessage){
		
		WebDriverWait wait;
		
		int globalTime = Integer.parseInt(CommonFunctions.getValueFromProperty("WaitForUpload"));
		wait = new WebDriverWait(Driver.getDriver(),globalTime);
		Log = Logger.getLogger(TakeAction.class.getName());
		//Driver.getDriver().manage().timeouts().implicitlyWait(globalTime, TimeUnit.SECONDS);
			try{
				By by= TakeAction.elementType(sWebObj,sWebElement);
				if(by != null){
					System.out.println("i am at :type1"+by);
					WebElement _element = isElementExists(by);
					if(_element != null){
						System.out.println("i am at :type2");
//						wait.until(ExpectedConditions.visibilityOfElementLocated(by));
						wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
							}
							System.out.println(sMessage);
						}
					}
					else{
						ATUReports.add(sMessage,"",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
						assertTrue(false);
					}
				}else{
					ATUReports.add(sMessage,"",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}
			}
			catch(Exception ex){
				ATUReports.add(sMessage,"",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				System.out.println("Exception - "+sMessage);
				assertTrue(false);
			}
		}
	
	public static void WaitSpinner(String sWebObj,String sWebElement,String sMessage){
		
		WebDriverWait wait;
		
		int globalTime = Integer.parseInt(CommonFunctions.getValueFromProperty("ExplicitWait"));
		wait = new WebDriverWait(Driver.getDriver(),globalTime);
		Log = Logger.getLogger(TakeAction.class.getName());
		//Driver.getDriver().manage().timeouts().implicitlyWait(globalTime, TimeUnit.SECONDS);
			try{
				By by= TakeAction.elementType(sWebObj,sWebElement);
				if(by != null){
					System.out.println("i am at :type1"+by);
					WebElement _element = isElementExists(by);
					if(_element != null){
						System.out.println("i am at :type2");
//						wait.until(ExpectedConditions.visibilityOfElementLocated(by));
						wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								Reporter.log(sMessage);
							}
							System.out.println(sMessage);
						}
					}
					else{
						TakeAction.DisplayMessage("Spinner is not available", "Is spinner available or not");
					}
				}else{
					ATUReports.add(sMessage,"",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					assertTrue(false);
				}
			}
			catch(Exception ex){
				ATUReports.add(sMessage,"",LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				System.out.println("Exception - "+sMessage);
				assertTrue(false);
			}
		}
	
	public static void Date(String sWebObj,String sWebElement,String sMessage){
		
		DateFormat dateFormat = new SimpleDateFormat("d");
		//get current date time with Date()
		Date date = new Date();
		// Now format the date
		String today= dateFormat.format(date);
			try{
				By by= TakeAction.elementType(sWebObj,sWebElement);
				if(by != null){
					System.out.println("i am at :type1"+by);
					WebElement _element = isElementExists(by);
					List<WebElement> columns=_element.findElements(By.tagName("td")); 
					if(null != _element){
					for (WebElement cell : columns)
						{
							if (cell.getText().equals(today))
							{
								cell.click();
								break;
							}
						}
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								ATUReports.add(sMessage,"", LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
								TestNgIntegration.verifyTextflag = false;
							}
						}else{
							ATUReports.add(sMessage,"", LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
							TestNgIntegration.verifyTextflag = false;
						}
					}
				}else{
					ATUReports.add(sMessage,"", LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
					TestNgIntegration.verifyTextflag = false;
				}			
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}

	public static void FutureDay(String sWebObj,String sWebElement,String sMessage){
		
		DateFormat dateFormat = new SimpleDateFormat("d");
		//get current date time with Date()
		Date dt = new Date();
		// Using the Calendar here
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		// Now format the date
		String previous= dateFormat.format(dt);
			try{
				By by= TakeAction.elementType(sWebObj,sWebElement);
				if(by != null){
					System.out.println("i am at :type1"+by);
					WebElement _element = isElementExists(by);
					List<WebElement> columns=_element.findElements(By.tagName("td")); 
					if(null != _element){
					for (WebElement cell : columns)
						{
							if (cell.getText().equals(previous))
							{
								cell.click();
								break;
							}
						}
						if(!sMessage.isEmpty()){
							if(CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")){
								ATUReports.add(sMessage,"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
							}else{
								ATUReports.add(sMessage,"", LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
								TestNgIntegration.verifyTextflag = false;
							}
						}else{
							ATUReports.add(sMessage,"", LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
							TestNgIntegration.verifyTextflag = false;
						}
					}
				}else{
					ATUReports.add(sMessage,"", LogAs.WARNING, new CaptureScreen(ScreenshotOf.DESKTOP));
					TestNgIntegration.verifyTextflag = false;
				}			
		}
		catch(Exception ex){
			ATUReports.add(sMessage,"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - "+sMessage);
			assertTrue(false);
		}
	}
	
	public static void ReadOnly(String sWebObj, String sWebElement,String sMessage) {
		String actualValue = null;
		try {
			By by = TakeAction.elementType(sWebObj, sWebElement);
			if (by != null) {
				WebElement _element = isElementExists(by);
				if (null != _element) {
					actualValue = _element.getAttribute("readOnly");
					if (!sMessage.isEmpty()) {
						if (CommonFunctions.getValueFromProperty("SCREENSHOT").equalsIgnoreCase("yes")) 
						{
							ATUReports.add(sMessage, "", actualValue,LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
						} else {
							Reporter.log(sMessage);
						}
						System.out.println(sMessage);
					}
				}
			} else {
				ATUReports.add(sMessage, "", actualValue, LogAs.FAILED,new CaptureScreen(ScreenshotOf.DESKTOP));
				assertTrue(false);
			}

		} catch (Exception ex) {
			ATUReports.add(sMessage, "", actualValue, LogAs.FAILED,new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("Exception - " + sMessage);
			assertTrue(false);
		}
	}
	
	public static void highlightElement(WebElement element) { 
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver(); 
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: yellow; border: 2px solid yellow;"); 
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, ""); 
		}
	}
	public static WebElement isElementExists(By by){
		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			
			Log.debug("Element is found : "+element);
			System.out.println("Element is found ");
			return element;
		} catch(Exception e){
			Log.debug("Element is not found ");
			System.out.println("Element is not found ");
			return null;
		}
	}
	public static By elementType(String sWebObj,String sWebElement){
		try {
			if(sWebElement.equalsIgnoreCase("xpath"))
			{
				return By.xpath(sWebObj);
			}else if(sWebElement.equalsIgnoreCase("id")){
				return By.id(sWebObj);
			}else if(sWebElement.equalsIgnoreCase("name")){
				return By.name(sWebObj);
			}else if(sWebElement.equalsIgnoreCase("tagName")){
				return By.tagName(sWebObj);
			} else if(sWebElement.equalsIgnoreCase("cssSelector")){
				return By.cssSelector(sWebObj);
			}else if(sWebElement.equalsIgnoreCase("linkText")){
				return By.linkText(sWebObj);
			}else if(sWebElement.equalsIgnoreCase("partialLinkText")){
				return By.partialLinkText(sWebObj);
			}else if(sWebElement.equalsIgnoreCase("className")){
				return By.className(sWebObj);
			}
			
			}catch(Exception e){
				return null;
			}
		return null;
	}
}
