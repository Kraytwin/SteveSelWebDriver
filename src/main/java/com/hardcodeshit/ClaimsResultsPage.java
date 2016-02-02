package com.hardcodeshit;

import java.io.IOException;

import org.openqa.selenium.By;

import com.screenshot.Screenshot;
import com.thoughtworks.selenium.SeleniumException;

public class ClaimsResultsPage extends HardCodeTest {

  private String name, type, directory;
  private boolean first;
  private int pageNumber = 1;
  private boolean niContributions = false;


  public void setNIContributions( String niContributions ){
    this.niContributions = Boolean.valueOf( niContributions ).booleanValue( );
  }

  public void testNew( String site ) throws InterruptedException, IOException {
    if ( niContributions ) {
      sc = new Screenshot( "Benefit_NICYes", name, type, directory, driver );
    } else {
      sc = new Screenshot( "Benefit_NICNo", name, type, directory, driver );
    }

    driver.get( "https://10.0.0.32/admin/" );
    if( isElementPresent( By.xpath( "//link[@rel=\"icon\" or @rel=\"shortcut icon\"]" ) ) ) {
      String faviconURL = driver.findElement( By.xpath( "//link[@rel=\"icon\" or @rel=\"shortcut icon\"]" ) ).getAttribute( "href" );
      driver.get( faviconURL );      
    } else {
      //It's going to error anyway so we'll make sure you can see it is favicon.ico
      driver.get( "https://10.0.0.32/admin/favicon.ico" );
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
}