package com.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AnswerListReader {

	private static ArrayList<Answer> answerList;
	
	public static ArrayList<Answer> readAnswerList( File file ) {
	    String line;
	    try {
	      BufferedReader in = new BufferedReader( new FileReader( file ) );
	      while ( ( line = in.readLine( ) ) != null ) {
	    	Answer answer = parseAnswer(line);
	    	if( answer != null ) {
	          answerList.add( answer );
	    	}
	      }
	      in.close( );
	    } catch ( IOException e ) {
	      System.out.println( "Could not read " + " because of an IOException " );
	      e.printStackTrace( );
	    }
	    return answerList;
	  }
	
	private static Answer parseAnswer(String line) {
		Answer answer = null;
		String[] answerParts = line.split("::::");
		if( answerParts.length == 4 ) {
			answer = new Answer( FindMethod.valueOf(answerParts[0]),answerParts[1], AnswerType.valueOf(answerParts[2]), answerParts[3]);
		} else if ( answerParts.length == 6 ) {
			answer = new Answer( FindMethod.valueOf(answerParts[0]),answerParts[1], AnswerType.valueOf(answerParts[2]), answerParts[3], Special.valueOf(answerParts[4]), answerParts[5]);
		}
		
		return answer;
	}
	
	public ArrayList<Answer> getAnswerList(  ) {
		return answerList;
	}
	
}
