package com.core;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverHandler {

  private static WebDriver driver;
  private static ConfigSettings config;
  private static FirefoxProfile profile;

  public static WebDriver init( ) {
    config = ConfigSettings.getInstance( );
    System.setProperty("webdriver.firefox.bin", "C:\\Users\\Steve-O\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
    String geckoLocation = config.getOSProperty("FILE_SYSTEM.GECKODRIVER_LOCATION");
    System.setProperty("webdriver.gecko.driver", geckoLocation);
    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    //Used this when using different browsers
    // capabilities.setBrowserName( "firefox" );
    capabilities.setPlatform( org.openqa.selenium.Platform.ANY );
    if( capabilities.getBrowserName( ).equals( "firefox" ) ) {

      capabilities.setCapability(FirefoxDriver.PROFILE, getFirefoxProfile());
      capabilities.setCapability("marionette", true);

    }
    // driver = new ScreenCaptureHtmlUnitDriver();
    driver = new FirefoxDriver( );
    driver.manage( ).timeouts( ).implicitlyWait( 1, TimeUnit.SECONDS );
    // driver.manage( ).timeouts( ).implicitlyWait( 0, TimeUnit.SECONDS );
    return driver;

  }

  private static FirefoxProfile getFirefoxProfile( ) {
    if( profile == null ) {
      setupFirefoxProfile();
    }
    return profile;
  }

  private static void setupFirefoxProfile( ) {
    boolean useSpecificProfile = false;

    if ( Boolean.parseBoolean( config.getProperty( "SELENIUM.FIREFOX_PROFILE" ) ) ) {
      System.out.println( "Parsed boolean " + config.getProperty( "SELENIUM.FIREFOX_PROFILE" ) );
      useSpecificProfile = Boolean.valueOf( config.getProperty( "SELENIUM.FIREFOX_PROFILE" ) );
    } else {
      System.out.println( "Failed to parse boolean" );
    }

    // try {
    if ( useSpecificProfile ) {
      ProfilesIni allProfiles = new ProfilesIni( );
      profile = allProfiles.getProfile( "Selenium" );
      if ( profile == null ) {
        System.out.println( "Specified profile not found, using new profile." );
        profile = new FirefoxProfile( );
      } else {
        System.out.println( "Using profile: " + profile.toString( ) );
      }
      profile.setAcceptUntrustedCertificates( true );
      profile.setAssumeUntrustedCertificateIssuer( false );
    } else {
      System.out.println( "No existing profile specified, using new profile." );
      profile = new FirefoxProfile( );
    }
    //setupFirebug();
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

//  private static void setupFirebug( ) {
//     // TODO Need to add checks to make sure these files exist.
//     File firebug = new File( "firebug2.0.xpi" );
//     File netExport = new File( "netExport-0.9b6.xpi" );
//     File fireStarter = new File( "fireStarter-0.1a6.xpi" );
//     String logDirString = setupProfileDirectoryString( config.getOSProperty( "FILE_SYSTEM.LOG_LOCATION" ) );
//     makeLogDirectory( logDirString );
//
//     profile.addExtension( firebug );
//     profile.addExtension( netExport );
//     profile.addExtension( fireStarter );
//
//      profile.setPreference( "app.update.enabled", false );
//      // Setting Firebug preferences
//      profile.setPreference( "extensions.firebug.currentVersion", "1.13.0a10" );
//      profile.setPreference( "extensions.firebug.addonBarOpened", true );
//      profile.setPreference( "extensions.firebug.console.enableSites", true );
//      profile.setPreference( "extensions.firebug.script.enableSites", true );
//      profile.setPreference( "extensions.firebug.net.enableSites", true );
//      profile.setPreference( "extensions.firebug.previousPlacement", 1 );
//      profile.setPreference( "extensions.firebug.allPagesActivation", "on" );
//      profile.setPreference( "extensions.firebug.onByDefault", true );
//      profile.setPreference( "extensions.firebug.defaultPanelName", "net" );
//      // Setting netExport preferences
//      profile.setPreference( "extensions.firebug.netexport.alwaysEnableAutoExport", true );
//      profile.setPreference( "extensions.firebug.netexport.autoExportToFile", true );
//      profile.setPreference( "extensions.firebug.netexport.Automation", true );
//      profile.setPreference( "extensions.firebug.netexport.showPreview", true );
//      profile.setPreference( "extensions.firebug.netexport.saveFiles", true );
//      profile.setPreference("extensions.firebug.netexport.sendToConfirmation", false);
//      profile.setPreference( "extensions.firebug.netexport.defaultLogDir", logDirString );
//      profile.setPreference( "extensions.firebug.netexport.pageLoadedTimeout", 1500 );
//      profile.setPreference( "extensions.firebug.netexport.timeout", 10000 );
//      profile.setPreference("extensions.firebug.netexport.viewerURL",
//      "http://www.softwareishard.com/har/viewer-1.1");
//
//     new Thread( new FirebugLogWatcher( logDirString ) ).start( );
//  }

  private static void makeLogDirectory( String logDirString ) {
    File logDir = setupProfileDirectory( logDirString );
    if ( !logDir.exists( ) ) {
      System.out.println( "Creating log directory for NetExport in: " + logDirString + "| Success: " + logDir.mkdirs( ) );
    } else {
      System.out.println( "Log directory for NetExport already exists in: " + logDirString );
    }
  }

  private static String setupProfileDirectoryString( String inputFile ) {
    if ( inputFile.startsWith( "~/" ) ) {
      inputFile = inputFile.replace( "~", System.getProperty( "user.home" ) );
    }
    return inputFile;
  }

  private static File setupProfileDirectory( String inputFile ) {
    return new File( setupProfileDirectoryString( inputFile ) );
  }

}