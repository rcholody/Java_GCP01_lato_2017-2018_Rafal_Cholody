package com.company.CLIENT;

import javafx.event.ActionEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements IClient
{
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    public String username, password;
    public Socket socket;

    public boolean isRunning = false;
    private Thread thread;

    private ClientEventLogin eventLogin;
    private ClientEventChat eventChat;

    public Client( String u, String p )
    {
        username = u;
        password = p;
    }

    public void setEventLogin( ClientEventLogin ev )
    {
        eventLogin = ev;
    }

    public void setEventChat( ClientEventChat ev )
    {
        eventChat = ev;
    }

    public void listen()
    {
        thread = new Thread( "Listen" )
        {
            public void run()
            {
                if( !isRunning )
                {
                    isRunning = true;
                    sendToAll( "<html><font color=\"green   \" size=\"2\"><b> has joined to conversation\n.</b></font><br/></html>" );
                    showStats();

                    while( isRunning )
                    {
                        try
                        {
                            String msg = inputStream.readUTF();
                            eventChat.messageReceived( msg );
                        }
                        catch( IOException e )
                        {
                            eventChat.disconnectedFromTheServer();
                            disconnect();
                            isRunning = false;
                        }
                    }
                }
            }
        };
        thread.setDaemon( true );
        thread.start();
    }

    public void sendToAll( String msg )
    {
        try
        {
            outputStream.writeInt( MessageType.MESSAGE );
            outputStream.writeUTF( msg );
        }
        catch( IOException e ) {e.printStackTrace();}
    }

    @Override
    public boolean connect( String ip, int port )
    {
        boolean result = false;
        try
        {
            socket = new Socket( ip, port );
            outputStream = new DataOutputStream( socket.getOutputStream() );
            inputStream = new DataInputStream( socket.getInputStream() );
            result = true;
        }
        catch( IOException e )
        {
            eventLogin.cannotConnectLogin();
            eventLogin.cannotConnectRegister();
            return false;
        }

        return result;
    }

    @Override
    public void disconnect()
    {
        if( !socket.isClosed() )
        {
            try
            {
                outputStream.writeInt( MessageType.LOGOUT );
                socket.close();
                outputStream.close();
                inputStream.close();
            }
            catch( IOException e ) {e.printStackTrace();}
        }
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void login( ActionEvent ev )
    {
        try
        {
            outputStream.writeInt( MessageType.LOGIN );
            outputStream.writeUTF( username );
            outputStream.writeUTF( password );
            if( inputStream.readBoolean() )
                eventLogin.successfulLogin( ev );
            else
                eventLogin.invalidLogin();
        }
        catch( IOException e ) {e.printStackTrace();}
    }

    public void register()
    {
        try
        {
            outputStream.writeInt( MessageType.REGISTER );
            outputStream.writeUTF( username );
            outputStream.writeUTF( password );
            if( inputStream.readBoolean() )
                eventLogin.newAccountCreated();
            else
                eventLogin.usernameUsed();
        }
        catch( IOException e ) {e.printStackTrace();}
    }

    public void showStats()
    {
        try{
            outputStream.writeInt(MessageType.STATISTICS);
        } catch (IOException e){e.printStackTrace();}
    }
}
