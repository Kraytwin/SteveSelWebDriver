package com.core;

import org.openqa.selenium.By;

public class Answer {

	private FindMethod method;
	private String name, value, specialValue;
	private AnswerType type;
	private Special special;
	private By by;
	private LocalisationItem item;
	
	public Answer( FindMethod method, String name, AnswerType answerType, String value, Special special, String specialValue ) {
		this.method = method;
		this.name = name;
		this.type = answerType;
		this.value = value;
		this.special = special;
		this.specialValue = specialValue;
		this.findBy();
		if( special.equals(Special.VERIFY) ) {
			item = new LocalisationItem(specialValue);
		}
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
		    by = By.name("TNSA_L");
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
	
	public LocalisationItem getLocalisationItem() {
		return item;
	}
	
	public String toString() {
		String answer = "|" + method.toString() + "|" + name + "|" + type.toString() + "|" + value;
		if( hasSpecial() && special.equals(Special.VERIFY) ) {
			answer += "|" + item.toString() + "|";
		} else if( hasSpecial() ) {
			answer += "|" + special.toString() + "|" + specialValue + "|";
		}
		return answer;
	}
}