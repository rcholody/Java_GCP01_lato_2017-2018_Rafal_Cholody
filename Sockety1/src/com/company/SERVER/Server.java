package com.company.SERVER;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server implements IServer
{
    private static final int PORT = 5000;                               // localhost + port: 5000
    private List<ClientThread> threadList = new ArrayList<>();          // lista wątków dla serwera, jeden wątek - jeden klient
    private static Server server;
    private boolean isRunnig = false;
    private ServerSocket finalServerSocket;

    public static void main( String args[] )
    {
        server = new Server();
        server.start();
    }

    public List<ClientThread> getThreadList() {
        return threadList;
    }

    @Override
    public void start()
    {
        ServerSocket serverSocket = null;
        final Socket[] socket = {null};

        try {serverSocket = new ServerSocket( PORT );}
        catch( IOException e ) {e.printStackTrace();}

        finalServerSocket = serverSocket;
        new Thread( "Nowi_klienci" )
        {
            public void run()
            {
                if( !isRunnig )
                {
                    isRunnig = true;
                    while( isRunnig )
                    {
                        try {socket[0] = finalServerSocket.accept();}
                        catch( IOException e ) {break;}

                        ClientThread et = new ClientThread( socket[0], server );
                        et.setDaemon( true );
                        et.start();

                        threadList.add( et );
                    }
                }
            }
        }.start();
    }

    @Override
    public void stop()
    {
        new Thread( "Close" )
        {
            public void run()
            {
                isRunnig = false;

                for( ClientThread el: threadList )
                    el.close();

                try {finalServerSocket.close();}
                catch( IOException e ) {e.printStackTrace();}
            }
        }.start();
    }

    public void sayAll( String user, String msg ) throws IOException
    {
        SimpleDateFormat dateformat = new SimpleDateFormat( "HH:mm:ss" );
        for( ClientThread el: threadList )
            el.say( "<html><font size=\"3\">[" + dateformat.format( new Date() ) + "] " + user + "</font></html>" + msg );
    }

    public void removeThread( ClientThread et )
    {
        threadList.remove( et );
    }

    public void showStats() throws IOException
    {
        System.out.println(threadList.size() + " users online.");
    }
}
