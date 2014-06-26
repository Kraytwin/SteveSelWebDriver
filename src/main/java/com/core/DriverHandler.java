package com.core;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverHandler {

  private static WebDriver driver;

  public static WebDriver init( ) {
    ConfigSettings config = ConfigSettings.getInstance( );
    // TODO Need to add checks to make sure these files are exist.
    File firebug = new File( "firebug-1.13.0a10.xpi" );
    File netExport = new File( "netExport-0.9b6.xpi" );
    File fireStarter = new File( "fireStarter-0.1a6.xpi" ); 
    String logDirString = setupProfileDirectoryString( config.getOSProperty( "FILE_SYSTEM.LOG_LOCATION" ) );
    boolean useSpecificProfile = false;
    
    if( Boolean.parseBoolean( config.getProperty( "SELENIUM.FIREFOX_PROFILE" ) ) ) {
      System.out.println("Parsed boolean " + config.getProperty( "SELENIUM.FIREFOX_PROFILE" ));
      useSpecificProfile = Boolean.valueOf( config.getProperty( "SELENIUM.FIREFOX_PROFILE" ) );
    } else {
      System.out.println("Failed to parse boolean");
    }

    
    makeLogDirectory( logDirString );

    //FirefoxProfile profile = new FirefoxProfile( );
    FirefoxProfile profile;
    try {
      if( useSpecificProfile ) {
        //File firefoxProfile = setupProfileDirectory( config.getProperty( "SELENIUM.FIREFOX_PROFILE_LOCATION" ) );
        //System.out.println("Using existing profile: " + firefoxProfile.getAbsolutePath( ) );
        //profile = new FirefoxProfile( firefoxProfile );
        ProfilesIni allProfiles = new ProfilesIni();
        profile = allProfiles.getProfile( "Selenium" );
        profile.setAcceptUntrustedCertificates( true );
        profile.setAssumeUntrustedCertificateIssuer( false );
        System.out.println("Using profile: " + profile.toString( ) );
      } else {
        System.out.println("No existing profile specified so using new profile.");
        profile = new FirefoxProfile( );
      }
      
      profile.addExtension( firebug );
      profile.addExtension( netExport );
      profile.addExtension( fireStarter );
    

    profile.setPreference( "app.update.enabled", false );

    // Setting Firebug preferences
    profile.setPreference( "extensions.firebug.currentVersion", "1.13.0a10" );
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
    profile.setPreference( "extensions.firebug.netexport.showPreview", true );
    profile.setPreference( "extensions.firebug.netexport.saveFiles", true );
    profile.setPreference("extensions.firebug.netexport.sendToConfirmation", false);
    profile.setPreference( "extensions.firebug.netexport.defaultLogDir", logDirString );
    profile.setPreference( "extensions.firebug.netexport.pageLoadedTimeout", 1500 );
    profile.setPreference( "extensions.firebug.netexport.timeout", 10000 );
    profile.setPreference("extensions.firebug.netexport.viewerURL", "http://www.softwareishard.com/har/viewer-1.1");
    
    DesiredCapabilities capabilities = new DesiredCapabilities( );
    capabilities.setBrowserName( "firefox" );
    capabilities.setPlatform( org.openqa.selenium.Platform.ANY );
    capabilities.setCapability( FirefoxDriver.PROFILE, profile );
    
    new Thread( new FirebugLogWatcher( logDirString ) ).start( );

    driver = new FirefoxDriver( capabilities );
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    } catch ( IOException e ) {
      e.printStackTrace( );
    }
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

  private static void makeLogDirectory( String logDirString ) {
    File logDir = setupProfileDirectory( logDirString );
    if ( !logDir.exists( ) ) {
      System.out.println( "Creating log directory for NetExport in: " + logDirString + "| Success: " + logDir.mkdirs( ));
    }
    else {
      System.out.println( "Log directory for NetExport already exists in: " + logDirString );
    }
  }
  
  private static String setupProfileDirectoryString( String inputFile ) {
    if( inputFile.startsWith( "~/" ) ) {
      inputFile = inputFile.replace( "~", System.getProperty("user.home") );
    }
    return inputFile;
  } 
  private static File setupProfileDirectory( String inputFile ) {
    return new File( setupProfileDirectoryString( inputFile ) );
  }
}