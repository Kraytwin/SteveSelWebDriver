package com.hardcodeshit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

	@Override
	public void testNew(String site) throws InterruptedException, IOException {
		sc = new Screenshot("Headless", "", "", "", driver);

		ArrayList<Answer> answerList = AnswerListReader.readAnswerList(new File("C:\\Users\\Steve-O\\Desktop\\answerlist.txt"));

		driver.get("https://hbclaims.teamnetsol.com/servlet/QuestEngine?TNSA_A=claim&TNSA_S=html&TNS_LI=false");
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
				//Need to do it this horrible way as we were finding hidden labels.  In general we will only want to search inside the questionnaire section anyway
				String textToCheck = driver.findElement(By.id( "questionnaire" )).getText();
				System.out.println(textToCheck);
				if( textToCheck.contains( answer.getSpecialValue() ) )  {
					//Set something to pass
				} else {
					//Set something to fail
				}
			}
		}
	}
}
