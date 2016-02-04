package com.hardcodeshit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.core.Answer;
import com.core.AnswerListReader;
import com.core.AnswerType;
import com.core.FindMethod;
import com.core.Special;
import com.screenshot.Screenshot;
import com.thoughtworks.selenium.SeleniumException;

public class AutoAnswerTest extends HardCodeTest {
	private ArrayList<Answer> answerList = AnswerListReader.readAnswerList(new File("C:\\Users\\Steve-O\\Desktop\\answerlist.txt"));
	private ArrayList<Answer> verifyAnswerList = new ArrayList<Answer>();
	@Test
	public void testNew(String site) throws InterruptedException, IOException {
		sc = new Screenshot("Headless", "", "", "", driver);

		

		driver.get("http://10.0.0.32/admin");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("admin");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("Password1");
	    driver.findElement(By.cssSelector("input.q_button")).click();
	    driver.findElement(By.linkText("Go back to calculator home")).click();
	    driver.findElement(By.id("becsStartCalculator")).click();
		while (isElementPresent(By.id("QUESTFORM"))) {
			for (Answer answer : answerList) {
				answerQuestions(answer);
			}
			if (isElementPresent(By.id("btn_next"))) {
				driver.findElement(By.id("btn_next")).click();
			}
		}

	}

	private void answerQuestions(Answer answer) {
		boolean elementFound = false;
		if (answer.getFindMethod().equals(FindMethod.ID) || answer.getFindMethod().equals(FindMethod.NAME)) {
			AnswerType answerType = answer.getAnswerType();
			if (doesElementExist(answer.getByStatement())) {
				System.out.println(answer.getName() + " was found");
				elementFound = true;
				// First we want to select the object
				if (answerType.equals(AnswerType.CHECKBOX) || answerType.equals(AnswerType.RADIO)) {
					driver.findElement(answer.getByStatement()).click();
				} else if (answerType.equals(AnswerType.SELECT)) {
					new Select(driver.findElement(answer.getByStatement())).selectByVisibleText(answer.getValue());
				} else if (answerType.equals(AnswerType.TEXT)) {
					driver.findElement(answer.getByStatement()).sendKeys(answer.getValue());
				} else { // answer.getAnswerType().equals(AnswerType.NONE)

				}
			}
		} else if (answer.getFindMethod().equals(FindMethod.VALUE)) {
			// Always going to have AnswerType.NONE
			if (doesElementExist(answer.getByStatement())) {
				if (driver.findElement(answer.getByStatement()).getAttribute("value").equals(answer.getName())) {
					System.out.println(answer.getName() + " was found");
					elementFound = true;
				}
			}
		}

		if (elementFound && answer.hasSpecial()) {
			if (answer.getSpecialCommand().equals(Special.SCREENSHOT)) {
				sc.captureScreenshot(answer.getSpecialValue());
			} else if (answer.getSpecialCommand().equals(Special.ASSERT)) {
				// DO JUNIT testing
			} else if (answer.getSpecialCommand().equals(Special.VERIFY)) {
				String textToCheck;
				//Need to do it this horrible way as we were finding hidden labels.  In general we will only want to search inside the questionnaire section anyway
				if( answer.getFindMethod().equals(FindMethod.VALUE) ) {
					textToCheck = driver.findElement(By.id( "questionnaire" )).getText();
				} else {
					textToCheck = driver.findElement(answer.getByStatement()).getText();
				}
				System.out.println(textToCheck);
				answer.getLocalisationItem().setSearchedFor();
				if( textToCheck.contains( answer.getLocalisationItem().getValue() ) )  {
					answer.getLocalisationItem().setFound();
				} else {
					//Set something to fail
				}
			}
		}
	}
	
	@After
	public void findVerifys() {
		for (Answer answer : answerList) {
			if( answer.hasSpecial() && answer.getSpecialValue().equals(Special.VERIFY) ) {
				verifyAnswerList.add( answer );
			}
		}
		for(Answer answer : verifyAnswerList) {
			System.out.println(answer.toString());
		}
	}
}
