package com.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FirebugLogWatcher implements Runnable {

  private String logDirString;
  private File logDir, outputFile;
  private final long pollingInterval = 1000;
  private FileAlterationObserver observer;
  private FileAlterationMonitor monitor;
  private FileAlterationListener listener;
  private BufferedWriter bw;

  public FirebugLogWatcher( String logDirString ) {
    this.logDirString = logDirString;
    logDir = new File( logDirString );
  }
  
  public void run( ) {
    Date date = new Date() ;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
    outputFile = new File( "MissingResources+" + dateFormat.format(date) + ".log" );
    init( );
    try {
      initFileOutput( );
    } catch ( IOException e ) {
      try {
        e.printStackTrace();
        bw.close( );
      } catch ( IOException e1 ) {
        e1.printStackTrace();
      }
    }
  }
  private void init( ) {
    observer = new FileAlterationObserver( logDir );
    monitor = new FileAlterationMonitor( pollingInterval );
    listener = new FileAlterationListenerAdaptor( ) {

      // Is triggered when a file is created in the monitored folder
      @Override
      public void onFileCreate( File file ) {
        try {
          // "file" is the reference to the newly created file
          System.out.println( "File created: " + file.getCanonicalPath( ) );
          String missingResources = parseFile( file );
          writeToFile( missingResources );
          bw.flush( );
        } catch ( IOException e ) {
          e.printStackTrace( System.err );
        }
      }
    };
    observer.addListener(listener);
    monitor.addObserver(observer);
    try {
      monitor.start();
    } catch ( Exception e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  private void initFileOutput( ) throws IOException {
      bw = new BufferedWriter( new FileWriter( outputFile ) );
  }

  private String parseFile( File file ) {
    JSONReader reader = new JSONReader( );
    String content = reader.readFile( file );
    System.out.println( content );
    reader.parseFile( content );
    String missingResources = "File read: " + file.getName( ) + "\n";
    missingResources += reader.getMissingResources( );
    return missingResources;
  }
  
  private void writeToFile( String missingResources) throws IOException {
      bw.write( missingResources );
  }

}
