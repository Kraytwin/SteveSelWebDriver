package com.core;

public class LocalisationItem {

	private boolean found = false;
	private boolean searchedFor = false;
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
		return name + "|" + value + "|" + itemFound();
	}
	
	public void setFound() {
		found = true;
	}
	
	public void setSearchedFor() {
		searchedFor = true;
	}
	
	private String itemFound() {
		if( found ) {
			return "Item Found";
		} else if ( searchedFor ) {
			return "Item not found";
		} else {
			return "Item not checked for";
		}
	}
}
