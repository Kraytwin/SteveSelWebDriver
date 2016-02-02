package com.hardcodeshit;

import java.io.IOException;

import org.openqa.selenium.By;


public class TestRunThrough extends HardCodeTest {
  
  private String name, type, directory;

  @Override
  public void testNew( String site ) throws InterruptedException, IOException {
    driver.get("http://bbc.co.uk/news");
    driver.findElement(By.cssSelector("div.pigeon-item__body > a.title-link > h3.title-link__title > span.title-link__title-text")).click();
    sc.captureScreenshot( "News1" );
    driver.findElement(By.xpath("//div[@id='site-container']/div/div[2]/ul/li[3]/a/span")).click();
    sc.captureScreenshot( "Content1" );
    driver.findElement(By.cssSelector("span.title-link__title-text")).click();
    sc.captureScreenshot( "News2" );
    driver.findElement(By.xpath("//div[@id='site-container']/div/div[2]/ul/li[4]/a/span")).click();
    sc.captureScreenshot( "Content2" );
}
}
