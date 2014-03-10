package core;

import hardcodeshit.ClaimsResultsPage;

import java.io.File;

import org.openqa.selenium.WebDriver;


public class Run {

  private static WebDriver driver;
  
  public static void main( String[ ] args ) {
    
    driver = DriverHandler.getDriver( );
    
    SiteList sites = new SiteList( new File( "/Users/stephenfallis/Desktop/sites.txt" ) );
    SiteHandler sh;
    
    
    ClaimsResultsPage st = new ClaimsResultsPage( );
    
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
