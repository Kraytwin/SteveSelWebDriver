package core;

import java.io.IOException;


public class SiteHandler {
  private String site;
  private String siteStartedText, siteStoppedText, claimSubmittedText;
  private StreamGobbler errorGobbler, outputGobbler;
  private Process p;

  public SiteHandler( String siteIn ) {
    site = siteIn;
    siteStartedText = "jvm 1    | INFO: Server startup in ";
    siteStoppedText = "wrapper  | <-- Wrapper Stopped";
    claimSubmittedText = "";
  }

  public void start( ) throws IOException, InterruptedException {
    System.out.println( "Site " + site + " is starting." );
    p = Runtime.getRuntime( ).exec( "sudo /opt/" + site + "tomcat/bin/becs_tomcat console" );
    errorGobbler = new StreamGobbler( p.getErrorStream( ), "ERROR", siteStartedText, siteStoppedText,
        claimSubmittedText, false );
    outputGobbler = new StreamGobbler( p.getInputStream( ), "OUTPUT", siteStartedText, siteStoppedText,
        claimSubmittedText, true );

    errorGobbler.start( );
    outputGobbler.start( );
    
    Thread.sleep( 15000 );
    System.out.println( "Site " + site + " is running." );
  }

  public void stop( ) throws IOException, InterruptedException {
    Runtime.getRuntime( ).exec( "sudo /opt/" + site + "tomcat/bin/becs_tomcat stop" );
    System.out.println( "Site " + site + " is stopping." );

    Thread.sleep( 15000 );
    p.destroy();
  }
}
