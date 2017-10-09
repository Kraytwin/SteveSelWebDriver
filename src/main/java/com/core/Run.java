package com.core;

import com.hardcodeshit.TestRunThrough;
import org.openqa.selenium.WebDriver;

import com.hardcodeshit.AutoAnswerTest;
import com.hardcodeshit.HardCodeTest;

public class Run {

  private static WebDriver driver;

  public static void main( String[ ] args ) {
    ConfigSettings config = ConfigSettings.getInstance( );
    driver = DriverHandler.getDriver( );

    // XXX Will need to add in code to deal with this file not existing.
    // SiteList sites = new SiteList( new File( config.getOSProperty(
    // "FILE_SYSTEM.SITE_FILE_LOCATION" ) ) );
    // SiteHandler sh;

    HardCodeTest st = new TestRunThrough( );
    st.setUp( driver );
    try {
      // for ( String site : sites.getSiteList( ) ) {
      // sh = new SiteHandler( site );
      try {
        // sh.start( );
        st.testNew( "http://10.0.0.32" );
        // sh.stop( );
      } catch ( Exception e ) {
        e.printStackTrace( );
        // If anything happens while when it is running through a site we
        // just want it to stop the site and then move to the next one.
        // sh.stop( );
      }
      // }
    } catch ( Exception e ) {
      e.printStackTrace( );
    }
    DriverHandler.closeDriver( );

  }
}
