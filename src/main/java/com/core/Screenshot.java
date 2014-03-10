package core;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

public class Screenshot {

  private String browser, browserName, siteName, directory;
  private WebDriver augmentedDriver;
  private int i;

  // Addition of Selenium being added is only temporary as an instance of selenium will be used
  public Screenshot( String site, String browserUsed, String browserType, String screenshotLocation, WebDriver sel ) {
    browser = browserType;
    browserName = browserUsed;
    siteName = site;
    directory = screenshotLocation;
    i = 0;
    augmentedDriver = new Augmenter().augment(sel);

  }

  public void captureScreenshot( String page ) {
    try {
      File directoryFile = new File( directory );
        Thread.sleep( 500 );
        System.out.println( directoryFile.getAbsolutePath( ) );
        File source = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File(directoryFile.getAbsolutePath( ) + "//" + siteName + "_" + page + ".png")); 
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