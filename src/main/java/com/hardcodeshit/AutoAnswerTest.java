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

public class AutoAnswerTest extends HardCodeTest {

  ArrayList<Answer> answerList = AnswerListReader.readAnswerList( new File( "/Users/stephenfallis/Desktop/BECSDefaultsWork/answerlist.txt" ) );
  private ArrayList<Answer> verifyAnswerList = new ArrayList<Answer>( );

  @Test
  public void testNew( String site ) throws InterruptedException, IOException {
    sc = new Screenshot( "Headless", "", "", "", driver );

    driver.get( "http://10.0.0.32/admin" );
    driver.findElement( By.id( "j_username" ) ).clear( );
    driver.findElement( By.id( "j_username" ) ).sendKeys( "user2" );
    driver.findElement( By.id( "j_password" ) ).clear( );
    driver.findElement( By.id( "j_password" ) ).sendKeys( "Pa55w0rd!" );
    driver.findElement( By.cssSelector( "input.q_button" ) ).click( );
    driver.findElement( By.linkText( "Go back to calculator home" ) ).click( );
    driver.findElement( By.id( "becsStartCalculator" ) ).click( );
    while ( isElementPresent( By.id( "QUESTFORM" ) ) ) {
      for ( Answer answer : answerList ) {
        answerQuestions( answer );
      }
      if ( isElementPresent( By.id( "btn_next" ) ) ) {
        driver.findElement( By.id( "btn_next" ) ).click( );
        System.out.println( "click" );
      }
    }
    this.findVerifys( answerList );

  }

  private void answerQuestions( Answer answer ) {
    boolean elementFound = false;
    if ( answer.getFindMethod( ).equals( FindMethod.ID ) || answer.getFindMethod( ).equals( FindMethod.NAME ) ) {
      AnswerType answerType = answer.getAnswerType( );
      if ( doesElementExist( answer.getByStatement( ) ) ) {
        elementFound = true;
        // First we want to select the object
        switch ( answerType ) {
          case CHECKBOX:
          case RADIO:
            driver.findElement( answer.getByStatement( ) ).click( );
            break;
          case SELECT:
            new Select( driver.findElement( answer.getByStatement( ) ) ).selectByVisibleText( answer.getValue( ) );
            break;
          case TEXT:
            driver.findElement( answer.getByStatement( ) ).sendKeys( answer.getValue( ) );
          default: // NONE
            break;
        }
      }
    } else if ( answer.getFindMethod( ).equals( FindMethod.VALUE ) ) {
      // Always going to have AnswerType.NONE
      if ( doesElementExist( answer.getByStatement( ) ) ) {

        if ( driver.findElement( answer.getByStatement( ) ).getAttribute( "value" ).equals( answer.getName( ) ) ) {
          System.out.println( answer.getName( ) + " was found" );
          elementFound = true;
        }
      }
    }

    if ( elementFound && answer.hasSpecial( ) ) {
      switch ( answer.getSpecialCommand( ) ) {
        case SCREENSHOT:
          sc.captureScreenshot(answer.getSpecialValue());
          break;
        case ASSERT:
          // DO JUNIT testing
          break;
        case VERIFY:
          String textToCheck;
          // Need to do it this horrible way as we were finding hidden labels. In general we will only
          // want to search inside the questionnaire section anyway
          if ( answer.getFindMethod( ).equals( FindMethod.VALUE ) ) {
            textToCheck = driver.findElement( By.id( "questionnaire" ) ).getText( );
          } else {
            textToCheck = driver.findElement( answer.getByStatement( ) ).getText( );
          }
          System.out.println( textToCheck );
          answer.getLocalisationItem( ).setFound( );
          if ( textToCheck.contains( answer.getLocalisationItem( ).getValue( ) ) ) {
            answer.getLocalisationItem( ).setMatches( );
          }
          break;
      }
    }
  }

  @After
  public void findVerifys( ArrayList<Answer> answerList ) {
    for ( Answer answer : answerList ) {
      if ( answer.hasSpecial( ) && answer.getSpecialCommand( ).equals( Special.VERIFY ) ) {
        verifyAnswerList.add( answer );
      }
    }
    System.out.println( "|_{background:black;color:white}.Search Value|_{background:black;color:white}.Question/Node|_{background:black;color:white}.Question Type|_{background:black;color:white}.Answer|_{background:black;color:white}.Verify|_{background:black;color:white}.Notes|_{background:black;color:white}.Searched For|_{background:black;color:white}.Found?|_{background:black;color:white}.Confirmed Manually to be expected" );
    for ( Answer answer : verifyAnswerList ) {
      System.out.println( answer.toString( ) );
    }
  }
}
