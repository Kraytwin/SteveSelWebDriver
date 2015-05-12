package com.hardcodeshit;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.core.Screenshot;

public class GTMetrixChecker extends HardCodeTest {
	
	private String name, type, directory;
	
	@Override
	public void testNew(String site) throws InterruptedException, IOException {

		sc = new Screenshot( site, name, type, directory, driver );
		
		driver.get("http://gtmetrix.com/");
		if(isElementPresent(By.linkText("Login"))) {
	    driver.findElement(By.linkText("Login")).click();
	    driver.findElement(By.id("login-email")).clear();
	    driver.findElement(By.id("login-email")).sendKeys("stephen@teamnetsol.com");
	    driver.findElement(By.id("login-password")).clear();
	    driver.findElement(By.id("login-password")).sendKeys("tn5@dm1n");
	    driver.findElement(By.cssSelector("button.login-button-big")).click();
	    Thread.sleep(1000);
		}
	    driver.findElement(By.name("url")).clear();
	    driver.findElement(By.name("url")).sendKeys(site + ".teamnetsol.com");
	    driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
	    WebElement waitForResultsToLoad = (new WebDriverWait(driver, 120)).until(ExpectedConditions.presenceOfElementLocated(By.className("selected")));
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
