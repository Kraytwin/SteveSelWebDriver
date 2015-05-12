package com.hardcodeshit;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.core.Screenshot;

public class GTMetrixChecker extends HardCodeTest {
	
	private WebDriver driver;
	private String name, type, directory;
	private Screenshot sc;
	private boolean niContributions = false;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer( );
	private boolean logging = true;
	
	@Override
	public void testNew(String site) throws InterruptedException, IOException {
		driver = this.getWebDriver();
		sc = new Screenshot( site, name, type, directory, driver );
		
		driver.get("http://gtmetrix.com/");
	    driver.findElement(By.name("url")).clear();
	    driver.findElement(By.name("url")).sendKeys(site + ".teamnetsol.com");
	    driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
	    WebElement waitForResultsToLoad = (new WebDriverWait(driver, 40)).until(ExpectedConditions.presenceOfElementLocated(By.className("selected")));
	    driver.findElement(By.linkText("Page Speed")).click();
	    sc.captureScreenshot("PageSpeed");
	    driver.findElement(By.linkText("YSlow")).click();
	    sc.captureScreenshot("YSlow");
	    driver.findElement(By.linkText("Timeline")).click();
	    sc.captureScreenshot("Timeline");
	    driver.findElement(By.linkText("History")).click();
	    sc.captureScreenshot("History");
	    driver.findElement(By.linkText("Download PDF")).click();
	    driver.findElement(By.id("full-1")).click();
	    driver.findElement(By.cssSelector("button.download-button")).click();
	    driver.findElement(By.cssSelector("#pdf-modal > button.modal-close")).click();
	}

}
