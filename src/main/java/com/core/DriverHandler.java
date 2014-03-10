package com.core;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverHandler {

  private static WebDriver driver;

  public static WebDriver init( ) {
    System.out.println( System
        .getProperty( "/Users/stephenfallis/Library/Application\\ Support/Firefox/Profiles/qcb9xy5k.Selenium/" ) );

    File firebug = new File( System.getProperty( "/Users/stephenfallis/work/eclipse-workspace/SeleniumWebDriver" )
        + "/resources/firebug-1.12.7.xpi" );
    File netExport = new File( System.getProperty( "/Users/stephenfallis/work/eclipse-workspace/SeleniumWebDriver" )
        + "/resources/netExport-0.9b4.xpi" );

    FirefoxProfile profile = new FirefoxProfile( );
    try {
      profile.addExtension( firebug );
      profile.addExtension( netExport );
    } catch ( IOException e ) {
      e.printStackTrace( );
    }

    profile.setPreference( "app.update.enabled", false );

    // Setting Firebug preferences
    profile.setPreference( "extensions.firebug.currentVersion", "2.0" );
    profile.setPreference( "extensions.firebug.addonBarOpened", true );
    profile.setPreference( "extensions.firebug.console.enableSites", true );
    profile.setPreference( "extensions.firebug.script.enableSites", true );
    profile.setPreference( "extensions.firebug.net.enableSites", true );
    profile.setPreference( "extensions.firebug.previousPlacement", 1 );
    profile.setPreference( "extensions.firebug.allPagesActivation", "on" );
    profile.setPreference( "extensions.firebug.onByDefault", true );
    profile.setPreference( "extensions.firebug.defaultPanelName", "net" );

    // Setting netExport preferences
    profile.setPreference( "extensions.firebug.netexport.alwaysEnableAutoExport", true );
    profile.setPreference( "extensions.firebug.netexport.autoExportToFile", true );
    profile.setPreference( "extensions.firebug.netexport.Automation", true );
    profile.setPreference( "extensions.firebug.netexport.showPreview", false );
    profile.setPreference( "extensions.firebug.netexport.defaultLogDir", "/Users/stephenfallis/Desktop/NetExportLog/" );

    DesiredCapabilities capabilities = new DesiredCapabilities( );
    capabilities.setBrowserName( "firefox" );
    capabilities.setPlatform( org.openqa.selenium.Platform.ANY );
    capabilities.setCapability( FirefoxDriver.PROFILE, profile );

    driver = new FirefoxDriver( capabilities );
    return driver;
  }

  public static WebDriver getDriver( ) {
    if ( driver != null ) {
      return driver;
    } else {
      return DriverHandler.init( );
    }
  }
  
  public static void closeDriver( ) {
    if ( driver != null ) {
      driver.close( );
    }
  }
}