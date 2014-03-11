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
    ConfigSettings config = ConfigSettings.getInstance( );
    // TODO Need to add checks to make sure these files are exist.
    File firebug = new File( "firebug-1.12.7.xpi" );
    File netExport = new File( "netExport-0.9b4.xpi" );
    String logDirString = config.getOSProperty( "FILE_SYSTEM.LOG_LOCATION" );
    System.out.println( "Creating log directory for NetExport in: " + logDirString + "| Success: "
        + makeLogDirectory( logDirString ) );

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
    profile.setPreference( "extensions.firebug.netexport.defaultLogDir", logDirString );

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

  private static boolean makeLogDirectory( String logDirString ) {
    File logDir = new File( logDirString );
    if ( !logDir.exists( ) ) {
      return logDir.mkdirs( );
    }
    return false;
  }
}