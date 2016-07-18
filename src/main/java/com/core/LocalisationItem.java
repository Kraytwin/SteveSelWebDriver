package com.core;

public class LocalisationItem {

	private boolean found = false;
	private boolean matches = false;
	private String name;
	private String value;
	
	public LocalisationItem( String localisationItemName, String localisationItemValue ) {
		this.name=localisationItemName;
		this.value = localisationItemValue;
	}
	
	public LocalisationItem( String unparsedLocalisationItem ) {
		String[] values = unparsedLocalisationItem.split("~");
		this.name = values[0];
		this.value = values[1];
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return name + "|" + value + "|" + itemMatches();
	}
	
	public void setMatches() {
		matches = true;
	}
	
	public void setFound() {
		found = true;
	}
	
	private String itemMatches() {
		if( matches ) {
			return "Item Matches";
		} else if ( found ) {
			return "Item Does Not Match";
		} else {
			return "Item Not Found";
		}
	}
}
