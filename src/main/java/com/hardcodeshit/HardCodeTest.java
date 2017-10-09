package com.hardcodeshit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.core.DriverHandler;
import com.screenshot.Screenshot;


public abstract class HardCodeTest  {
	
	protected WebDriver driver;
	protected Screenshot sc;
	protected boolean acceptNextAlert = true;
	protected StringBuffer verificationErrors = new StringBuffer( );
	protected boolean logging = true;

	 public void setUp( WebDriver driver ) {
		    this.driver = driver;
		  }

	  public abstract void testNew( String site ) throws InterruptedException, IOException;

	  public void tearDown( ) throws Exception {
	    driver.quit( );
	    String verificationErrorString = verificationErrors.toString( );
	    if ( !"".equals( verificationErrorString ) ) {
	      // fail(verificationErrorString);
	      System.out.println( "error: " + verificationErrorString.toString( ) );
	    }
	  }

	  protected boolean isElementPresent( By by ) {
	    try {
	      driver.findElement( by );
	      return true;
	    } catch ( NoSuchElementException e ) {
	      return false;
	    }
	  }
	  
	  protected boolean doesElementExist( By by) {
		  //this.turnOffImplicitWaits();
      return driver.findElements(by).size() > 0;

    }

	  protected boolean isAlertPresent( ) {
	    try {
	      driver.switchTo( ).alert( );
	      return true;
	    } catch ( NoAlertPresentException e ) {
	      return false;
	    }
	  }

	  protected String closeAlertAndGetItsText( ) {
	    try {
	      Alert alert = driver.switchTo( ).alert( );
	      String alertText = alert.getText( );
	      if ( acceptNextAlert ) {
	        alert.accept( );
	      } else {
	        alert.dismiss( );
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
	  
	  protected void clickLink( By byIn) {
	    if( isElementPresent( byIn ) ) {
	    driver.findElement( byIn ).click( );
	    }
	    
	    if( logging ) {
	      /*try {
	        Thread.sleep( 10000 );
	      } catch ( InterruptedException e ) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      }*/
	    }
	  }
	  
	  protected void selectOption( By byIn, String option) {
	    if( isElementPresent( byIn ) ) {
	    new Select( driver.findElement( byIn )).selectByVisibleText(option) ;
	    }
	    
	    if( logging ) {
	      /*try {
	        Thread.sleep( 10000 );
	      } catch ( InterruptedException e ) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      }*/
	    }
	  }
	  protected void insertText( By byIn, String text) {
	    if( isElementPresent( byIn ) ) {
	    driver.findElement( byIn ).sendKeys(text);
	    }
	    
	    if( logging ) {
	      /*try {
	        Thread.sleep( 10000 );
	      } catch ( InterruptedException e ) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      }*/
	    }
	  }
	  
	  protected  void turnOffImplicitWaits() {
		    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		}

		protected void turnOnImplicitWaits() {
		    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		}
	}

