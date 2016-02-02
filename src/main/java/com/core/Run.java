package com.core;

import java.io.File;

import org.openqa.selenium.WebDriver;

import com.hardcodeshit.AutoAnswerTest;
import com.hardcodeshit.ClaimsResultsPage;
import com.hardcodeshit.GTMetrixChecker;
import com.hardcodeshit.HardCodeTest;


public class Run {

  private static WebDriver driver;
  
  public static void main( String[ ] args ) {
    ConfigSettings config = ConfigSettings.getInstance( );
    driver = DriverHandler.getDriver( );
    
    //XXX Will need to add in code to deal with this file not existing.
   // SiteList sites = new SiteList( new File( config.getOSProperty( "FILE_SYSTEM.SITE_FILE_LOCATION" ) ) );
  //  SiteHandler sh;
    
    
    //HardCodeTest st = new GTMetrixChecker( );
    HardCodeTest st = new AutoAnswerTest( );
    st.setUp(driver);
    try {
     // for ( String site : sites.getSiteList( ) ) {
        //sh = new SiteHandler( site );
        try {
          //sh.start( );
          st.testNew( "hbclaims.teamnetsol.com" );
          //sh.stop( );
        } catch ( Exception e ) {
          e.printStackTrace( );
          // If anything happens while when it is running through a site we
          // just want it to stop the site and then move to the next one.
          //sh.stop( );
        }
     // }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    DriverHandler.closeDriver( );
    
   /* HardCodeTest st = new GTMetrixChecker( );
    st.setUp(driver);
    try {
      st.testNew( "test" );
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    DriverHandler.closeDriver( );*/
  }
}
