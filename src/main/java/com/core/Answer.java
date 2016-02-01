package com.core;

import org.openqa.selenium.By;

public class Answer {

	private FindMethod method;
	private String name, value, specialValue;
	private AnswerType type;
	private Special special;
	private By by;
	
	public Answer( FindMethod method, String name, AnswerType answerType, String value, Special special, String specialValue ) {
		this.method = method;
		this.name = name;
		this.type = answerType;
		this.value = value;
		this.special = special;
		this.specialValue = specialValue;
		this.findBy();
	}
	
	public Answer(FindMethod method, String name, AnswerType answerType, String value) {
		this.method = method;
		this.name = name;
		this.type = answerType;
		this.value = value;
		this.findBy();
	}
	
	public FindMethod getFindMethod() {
		return method;
	}
	
	public String getName() {
		return name;
	}
	
	public By getByStatement() {
		return by;
	}
	
	private void findBy() {
		if( this.method.equals(FindMethod.ID) ) {
			by = By.id(name);
		} else if ( this.method.equals(FindMethod.NAME) ) {
			by = By.name(name);
		} else if ( this.method.equals(FindMethod.VALUE) ) {
		    by = By.xpath("//input[@id='TNSA_L']");
		}
	}
	
	public AnswerType getAnswerType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean hasSpecial( ) {
		if(special != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Special getSpecialCommand() {
		return special;
	}
	
	public String getSpecialValue() {
		return specialValue;
	}
}