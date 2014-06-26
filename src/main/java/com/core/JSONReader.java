package com.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class JSONReader {

  private ResourceList failedResources;

  public JSONReader( ) {

    failedResources = new ResourceList( );
  }

  public String readFile( File jsonFile ) {
    String content = "";
    try {
      Scanner sc = new Scanner( jsonFile );
      content = sc.useDelimiter( "\\Z" ).next( );
      sc.close( );
    } catch ( FileNotFoundException e ) {
      e.printStackTrace( );
    }
    return content;
  }

  public void parseFile( String jsonString ) {
    ObjectMapper mapper = new ObjectMapper();
    Map jsonData;
    try {
      jsonData = mapper.readValue( jsonString, Map.class);
      jsonData = ( Map ) jsonData.get("log");
      //System.out.println( jsonData.keySet( ).toString( ) );
      if ( jsonData.containsKey( "entries" ) ) {
        List entriesList = ( List ) jsonData.get( "entries" );
        System.out.println( "List size is: " + entriesList.size( ) );
        parseEntries( entriesList );
      }
    } catch ( JsonParseException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch ( JsonMappingException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch ( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void parseEntries( List entriesList ) {
    if ( entriesList.size( ) > 0 ) {
      for ( Object entry : entriesList ) {
        Map entryMap = ( Map ) entry;
        // We want to check the response first as any other statuses but 404 and 204 can be ignored
        String status = parseResponse( entryMap );
        String url;
        //System.out.println(" The current status is: " + status );
        if ( status.equals( "404" ) || status.equals( "204" ) ) {
          url = parseRequest( entryMap );
          failedResources.addResource( url, status );
        }
      }
    }
  }

  private String parseResponse( Map entryMap ) {
    String status = "";
    if ( entryMap.containsKey( "response" ) ) {
      Map responseMap = ( Map ) entryMap.get( "response" );
      if ( responseMap.containsKey( "status" ) ) {
        status = responseMap.get( "status" ).toString( );
      }
    }
    return status;
  }

  private String parseRequest( Map entryMap ) {
    String url = "";
    if ( entryMap.containsKey( "request" ) ) {
      Map requestMap = ( Map ) entryMap.get( "request" );
      if ( requestMap.containsKey( "url" ) ) {
        url = ( String ) requestMap.get( "url" );
      }
    }
    return url;
  }

  public String getMissingResources( ) {
    return failedResources.getResourcesToString( );
  }

}
