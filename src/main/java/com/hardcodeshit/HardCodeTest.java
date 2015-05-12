package com.hardcodeshit;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.core.Screenshot;
import com.thoughtworks.selenium.SeleniumException;


public abstract class HardCodeTest {
	
	protected WebDriver driver;
	protected Screenshot sc;
	protected boolean acceptNextAlert = true;
	protected StringBuffer verificationErrors = new StringBuffer( );
	protected boolean logging = true;

	 public void setUp( WebDriver driver ) {
		    this.driver = driver;
		  }
	 
	 public WebDriver getWebDriver() {
		 return driver;
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
	}

