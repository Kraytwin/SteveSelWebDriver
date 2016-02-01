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
import com.core.Screenshot;
import com.thoughtworks.selenium.SeleniumException;

public class AutoAnswerTest extends HardCodeTest {

	@Override
	public void testNew(String site) throws InterruptedException, IOException {

		ArrayList<Answer> answerList = AnswerListReader.readAnswerList(new File("C:\\Users\\Steve-O\\Desktop\\answerlist.txt"));

		driver.get("https://hbclaims.teamnetsol.com/servlet/QuestEngine?TNSA_A=claim&TNSA_S=html&TNS_LI=false");
		while (isElementPresent(By.id("QUESTFORM"))) {
			for (Answer answer : answerList) {
				answerQuestions(answer);
			}
		}
	}

	private void answerQuestions( Answer answer ) {
		boolean elementFound = false;
		if (answer.getFindMethod().equals(FindMethod.ID)||answer.getFindMethod().equals(FindMethod.NAME)) {
			AnswerType answerType = answer.getAnswerType();
			if (isElementPresent(answer.getByStatement())) {
				//First we want to select the object
				if (answerType.equals(AnswerType.CHECKBOX) || answerType.equals(AnswerType.RADIO)) {
					driver.findElement(answer.getByStatement()).click();
				} else if (answerType.equals(AnswerType.SELECT)) {
					new Select(driver.findElement(answer.getByStatement())).selectByVisibleText(answer.getValue());
				} else if (answerType.equals(AnswerType.TEXT)) {
					driver.findElement(answer.getByStatement()).sendKeys(answer.getValue());
				} else { //answer.getAnswerType().equals(AnswerType.NONE)

				}
			}
			elementFound = true;
		} else if( answer.getFindMethod().equals(FindMethod.VALUE) ) {
		//Always going to have AnswerType.NONE
			if (isElementPresent(answer.getByStatement())) {
				if( driver.findElement(answer.getByStatement()).getAttribute("value").equals(answer.getName()) ) {
					elementFound = true;
				}
			}
		}
		
		if( elementFound && answer.hasSpecial() ) {
			
		}
	}
}
