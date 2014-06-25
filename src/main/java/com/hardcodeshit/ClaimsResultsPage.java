package com.hardcodeshit;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.core.Screenshot;
import com.thoughtworks.selenium.SeleniumException;

public class ClaimsResultsPage {

  private WebDriver driver;
  private String name, type, directory;
  private boolean first;
  private int pageNumber = 1;
  private Screenshot sc;
  private boolean niContributions;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer( );
  private boolean logging = true;

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

    driver.get( "https://10.0.0.32/admin/" );
    driver.manage( ).window( ).maximize( );
    driver.findElement( By.id( "j_username" ) ).clear( );
    driver.findElement( By.id( "j_username" ) ).sendKeys( "user2" );
    driver.findElement( By.id( "j_password" ) ).clear( );
    driver.findElement( By.id( "j_password" ) ).sendKeys( "Pa55w0rd!" );
    driver.findElement( By.cssSelector( "input.q_button" ) ).click( );
    driver.findElement( By.linkText( "View Incident Reports" ) ).click( );
    do {
      System.out.println( "On Page " + pageNumber );
      this.completeIncidentReports( );
      pageNumber++;
    } while ( this.goToNextPage( pageNumber ) );
  }

  private boolean goToNextPage( int i ) {
    try {
      driver.get( "https://10.0.0.32/incidents?TNSA_A=claim&TNSA_S=html&TNSA_ACTION=incidents.list&TNSA_OFFSET="
          + ( i - 1 ) + "0#results" );
      //If the main element isn't there then there are no incident reports left.
      return isElementPresent( By.xpath("/html/body/div[2]/div[2]/div/table/tbody/tr[2]/td[2]/form/input[5]") );
    } catch ( SeleniumException e ) {
      return false;
    }
  }

  private void completeIncidentReports( ) throws InterruptedException, IOException {
    first = true; // /*
    String title = "";
    boolean skipClaims = false;
    if ( !skipClaims ) {
      for ( int i = 1; i <= 10; i++ ) {
        if ( isElementPresent( By.xpath( "/html/body/div[2]/div[2]/div/table[" + i
            + "]/tbody/tr[2]/td[2]/form/input[5]" ) ) ) {
          String incidentTitle = driver.findElement(
              By.xpath( "/html/body/div[2]/div[2]/div/table[" + i + "]/tbody/tr[2]/td/i" ) ).getText( );
          title = incidentTitle.split( "-" )[ 0 ].replaceAll( " ", "" );
          System.out.println( "title: " + title );
          this.clickLink(By.xpath( "(//input[@value='Recreate Path'])[" + i + "]" ) );

          System.out.println( "Claim " + ( 11 - i ) );

          if ( first ) {
            System.out.println( "First incident report loading" );
            first = false;
          } else {
            System.out.println( i + " incident report loading" );
          }

          if ( isElementPresent( By.id( "STUDENT_YOU_false" ) ) ) {
            this.clickLink(By.id( "STUDENT_YOU_false" ) );
            this.clickLink(By.id( "btn_next" ) );
          }
          if ( isElementPresent( By.id( "STUDENT_PARTNER_false" ) ) ) {
            this.clickLink(By.id( "STUDENT_PARTNER_false" ) );
            this.clickLink(By.id( "btn_next" ) );
          }
          if ( isElementPresent( By.id( "ESA_C_ENTITLED_YOU_false" ) ) ) {
            this.clickLink(By.id( "ESA_C_ENTITLED_YOU_false" ) );
            this.clickLink(By.id( "btn_next" ) );
          }
          if ( isElementPresent( By.id( "NON_DEP_PENSION_CREDIT_false" ) ) ) {
            this.clickLink(By.id( "NON_DEP_PENSION_CREDIT_false" ) );
            this.clickLink(By.id( "btn_next" ) );
          }
          if ( isElementPresent( By.id( "NON_DEP_PENSION_CREDIT_false" ) ) ) {
            this.clickLink(By.id( "NON_DEP_PENSION_CREDIT_false" ) );
            this.clickLink(By.id( "btn_next" ) );
          }
          if ( isElementPresent( By.id( "NI_CONTRIBUTION_FIRST_CONDITION_YOU_false" ) ) ) {
            this.clickLink(By.id( "NI_CONTRIBUTION_FIRST_CONDITION_YOU_false" ) );
            if ( isElementPresent( By.id( "NI_CONTRIBUTION_FIRST_CONDITION_PARTNER_false" ) ) ) {
              this.clickLink(By.id( "NI_CONTRIBUTION_FIRST_CONDITION_PARTNER_false" ) );
            }
            if ( isElementPresent( By.id( "NI_CONTRIBUTION_SECOND_CONDITION_YOU_false" ) ) ) {
              this.clickLink(By.id( "NI_CONTRIBUTION_SECOND_CONDITION_YOU_false" ) );
              if ( isElementPresent( By.id( "NI_CONTRIBUTION_SECOND_CONDITION_PARTNER_false" ) ) ) {
                this.clickLink(By.id( "NI_CONTRIBUTION_SECOND_CONDITION_PARTNER_false" ) );
              }
            }
            this.clickLink(By.id( "btn_next" ) );
          }
          if ( isElementPresent( By.id( "NI_CONTRIBUTION_SECOND_CONDITION_YOU_false" ) ) ) {
            this.clickLink(By.id( "NI_CONTRIBUTION_SECOND_CONDITION_YOU_false" ) );
            if ( isElementPresent( By.id( "NI_CONTRIBUTION_SECOND_CONDITION_PARTNER_false" ) ) ) {
              this.clickLink(By.id( "NI_CONTRIBUTION_SECOND_CONDITION_PARTNER_false" ) );
            }
            this.clickLink(By.id( "btn_next" ) );
          }

          sc.captureScreenshot( title );
          this.clickLink(By.id( "q_sya" ) );
          sc.captureScreenshot( title + "_SYA" );
          this.goToNextPage( pageNumber );
        } else {
          System.out.println( " Incident report element does not exist. " );
        }
      }
    }
  }

  public void tearDown( ) throws Exception {
    driver.quit( );
    String verificationErrorString = verificationErrors.toString( );
    if ( !"".equals( verificationErrorString ) ) {
      // fail(verificationErrorString);
      System.out.println( "error: " + verificationErrorString.toString( ) );
    }
  }

  private boolean isElementPresent( By by ) {
    try {
      driver.findElement( by );
      return true;
    } catch ( NoSuchElementException e ) {
      return false;
    }
  }

  private boolean isAlertPresent( ) {
    try {
      driver.switchTo( ).alert( );
      return true;
    } catch ( NoAlertPresentException e ) {
      return false;
    }
  }

  private String closeAlertAndGetItsText( ) {
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
  
  private void clickLink( By byIn) {
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
