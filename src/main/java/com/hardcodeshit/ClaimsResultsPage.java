package com.hardcodeshit;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.core.Screenshot;

public class ClaimsResultsPage {

  private WebDriver driver;
  private String name, type, directory;
  private boolean first;
  private int pageNumber = 1;
  private Screenshot sc;
  private boolean niContributions;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  public void setUp( WebDriver driver ) {
    this.driver = driver;
  }

  public void testNew( String site, String niContributions ) throws InterruptedException, IOException {
    this.niContributions = Boolean.valueOf( niContributions ).booleanValue( );
    if ( this.niContributions ) {
      System.out.println( "If there are NI Contribution questions these will be answered as 'yes'" );
    } else {
      System.out.println( "If there are NI Contribution questions these will be answered as 'no'" );
    }
    this.testNew( site );
  }

  public void testNew( String site ) throws InterruptedException, IOException {
    if ( niContributions ) {
      sc = new Screenshot( "Benefit_NICYes", name, type, directory, driver );
    } else {
      sc = new Screenshot( "Benefit_NICNo", name, type, directory, driver );
    }
    
    driver.get( "https://10.0.0.32/admin/");
    driver.findElement(By.linkText("Logout")).click();
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("user2");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("Pa55w0rd!");
    driver.findElement(By.cssSelector("input.q_button")).click();
    driver.findElement(By.id("becsStartCalculator")).click();
    driver.findElement(By.linkText("Go back to calculator home")).click();
  }


  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      //fail(verificationErrorString);
      System.out.println("error: " + verificationErrorString.toString( ));
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
