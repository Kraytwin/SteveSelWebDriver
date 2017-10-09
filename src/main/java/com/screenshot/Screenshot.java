package com.screenshot;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.Augmenter;



public class Screenshot {

  private String browser, browserName, siteName, directory;
  private String extension;
  private WebDriver augmentedDriver;
  private int i;

  // Addition of Selenium being added is only temporary as an instance of selenium will be used
  public Screenshot( String site, String browserUsed, String browserType, String screenshotLocation, WebDriver sel ) {
    browser = browserType;
    browserName = browserUsed;
    siteName = site;
    directory = screenshotLocation;
    i = 0;
    
    if( sel instanceof HtmlUnitDriver ) {
    	augmentedDriver = sel;
    	extension = "zip";
    } else {
    	augmentedDriver = new Augmenter().augment(sel);
    	extension = "png";
    }

  }

  public void captureScreenshot( String page ) {
    File directoryFile;
    try {
      if( directory == null || directory.equals( "" ) ) {
        directoryFile = new File(".");
      } else {
        directoryFile = new File( directory );        
      }
        Thread.sleep( 500 );
        System.out.println( directoryFile.getAbsolutePath( ) );
        File source = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File(directoryFile.getAbsolutePath( ) + "//" + siteName + "_" + page + "." + extension)); 
        System.out.println( "Taking fullscreen screenshot in " + directory + " " + siteName + "_" + page );
        Thread.sleep( 500 );
      i++;
    } catch ( IOException e ) {
      e.printStackTrace( );
    } catch ( InterruptedException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace( );
    }
  }

  public void captureScreenshot( ) {
    this.captureScreenshot( Integer.toString( i ) );
  }

}
