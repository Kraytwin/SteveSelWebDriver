package com.core;

import java.util.ArrayList;

public class ResourceList {

  private ArrayList<String> urlList;
  private ArrayList<String> httpStatusList;
  
  public ResourceList( ) {
    urlList = new ArrayList<String>( );
    httpStatusList = new ArrayList<String>( ); 
  }
  
  public boolean addResource( String url, String httpStatus ) {
    if( urlList.contains( url ) ) {
      //URL already exists in this resourceList so we don't want to add it in again
      return false;
    } else {
      urlList.add( url );
      httpStatusList.add( httpStatus );
      return true;
    }
  }

  /**
   * This will return all the urls for a given status
   */
  public ResourceList getURLsForStatus( String httpCode ) {
    ResourceList rl = new ResourceList( );
    for( int i = 0; i < httpStatusList.size( ); i++ ) {
      if( httpStatusList.get( i ).equals( httpCode ) ) {
        rl.addResource( urlList.get( i ), httpStatusList.get( i ) );
      }
    }
    return rl;
  }
  
  /**
   * This will return all the urls for a given status as a string list
   */
  public String getURLsForStatusToString( String httpCode ) {
    String urlString = "";
    for( int i = 0; i < httpStatusList.size( ); i++ ) {
      if( httpStatusList.get( i ).equals( httpCode ) ) {
        urlString += "URL: " + urlList.get( i ) + " Status: " + httpStatusList.get( i ) + "\n";
      }
    }
    if( urlString.equals( "" ) ) {
      urlString = "No missing resources.\n";
    }
    return urlString;
  }
  
  public String getResourcesToString(  ) {
    String urlString = "";
    for( int i = 0; i < httpStatusList.size( ); i++ ) {
      urlString += "URL: " + urlList.get( i ) + " Status: " + httpStatusList.get( i ) + "\n";
    }
    if( urlString.equals( "" ) ) {
      urlString = "No missing resources.\n";
    }
    return urlString;
  }
}
