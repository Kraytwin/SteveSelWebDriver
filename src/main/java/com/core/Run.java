package com.core;

import com.hardcodeshit.ClaimsResultsPage;

import java.io.File;

import org.openqa.selenium.WebDriver;


public class Run {

  private static WebDriver driver;
  
  public static void main( String[ ] args ) {
    ConfigSettings config = ConfigSettings.getInstance( );
    driver = DriverHandler.getDriver( );
    
    SiteList sites = new SiteList( new File( config.getProperty( "FILE_SYSTEM.SITE_FILE_LOCATION" ) ) );
    SiteHandler sh;
    
    
    ClaimsResultsPage st = new ClaimsResultsPage( );
    st.setUp(driver);
    try {
      for ( String site : sites.getSiteList( ) ) {
        sh = new SiteHandler( site );
        try {
          sh.start( );
          st.testNew( site );
          sh.stop( );
        } catch ( Exception e ) {
          e.printStackTrace( );
          // If anything happens while when it is running through a site we
          // just want it to stop the site and then move to the next one.
          sh.stop( );
        }
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    DriverHandler.closeDriver( );
  }
}
