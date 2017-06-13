package com.company.SERVER;

import com.company.CLIENT.MessageType;

import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class ClientThread extends Thread
{
    private String fileName = "config.properties";
    private Properties prop = new Properties();
    private Socket socket;
    private Server server;
    private DataInputStream dataInputStream = null;
    private DataOutputStream outputStream = null;
    private String user;
    public boolean isRunning = false;

    public ClientThread( Socket clientSocket, Server srv )
    {
        this.socket = clientSocket;
        server = srv;
        try
        {
            dataInputStream = new DataInputStream( socket.getInputStream() );
            outputStream = new DataOutputStream( socket.getOutputStream() );
        }
        catch( IOException e )
        {
            e.printStackTrace();
            return;
        }

        if( !( new File( fileName ).isFile() ) )
            createConfig();
    }

    public void run()
    {
        if ( !isRunning )
        {
            isRunning = true;
            String line;
            while( isRunning ) {
                try {
                    int msgtype = dataInputStream.readInt();


                    if (msgtype == MessageType.REGISTER) {
                        outputStream.writeBoolean(register());
                        outputStream.flush();
                        isRunning = false;

                    } else if (msgtype == MessageType.LOGIN) {
                        outputStream.writeBoolean(login());
                        outputStream.flush();

                    } else if (msgtype == MessageType.MESSAGE) {
                        line = dataInputStream.readUTF();
                        server.sayAll(user, line);
                    } else if (msgtype == MessageType.STATISTICS) {
                        showStats();
                    } else if (msgtype == MessageType.LOGOUT) {
                        try {
                            socket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        server.removeThread(this);

                        if (user != null) {
                            try {
                                server.sayAll(user, " left the conversation.");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }

                        isRunning = false;
                        break;
                    }
                }

                catch( IOException e )
                {
                    System.err.println( user + " disconnected." );

                    try {socket.close();}
                    catch( IOException e1 ) {e1.printStackTrace();}

                    server.removeThread( this );

                    try {server.sayAll( user," left the conversation." );}
                    catch( IOException e1 ) {e1.printStackTrace();}

                    isRunning = false;
                }
            }
        }
    }

    public void say( String msg ) throws IOException
    {
        outputStream.writeUTF( msg );
        outputStream.flush();
    }

    public void showStats() throws IOException
    {
        System.out.println(server.getThreadList().size() + " users online.");
    }

    public boolean login() throws IOException
    {
        boolean result = false;

        String username = dataInputStream.readUTF();
        String password = dataInputStream.readUTF();

        InputStream input = null;

        try
        {
            File file = new File( fileName );
            input = new FileInputStream( fileName );

            if( file.isFile() )
            {
                prop.load( input );

                if( prop.containsValue( username ) )
                {
                    String file_password = prop.getProperty( username + "password" );
                    String hashedPassword = hashMD5( password );

                    result = file_password.equals( hashedPassword );
                }
                else
                    result = false;
            }

        }
        catch( IOException ex ) {ex.printStackTrace();}
        finally
        {
            showStats();
            if( input != null )
            {
                try {input.close();}
                catch( IOException e ) {e.printStackTrace();}
            }
        }

        if ( result )
            user = username;

        return result;
    }

    public boolean register() throws IOException
    {
        boolean result = false;
        String newuser = dataInputStream.readUTF();
        String newpass = dataInputStream.readUTF();

        FileWriter output = null;

        try
        {
            output = new FileWriter( fileName, true );
            prop.load( new FileInputStream( fileName ) );

            if( !prop.contains( newuser ) )
            {
                prop.clear();
                String hashedPassword = hashMD5( newpass );

                prop.setProperty( newuser + "password", hashedPassword );
                prop.setProperty( newuser, newpass );


                prop.store( output, null );
                result = true;
            }
            else
                result = false;
        }
        catch( IOException io ) {io.printStackTrace();}
        finally
        {
            if( output != null )
            {
                try {output.close();}
                catch( IOException e ) {e.printStackTrace();}
            }
        }

        server.removeThread( this );

        return result;
    }

    private void createConfig()
    {
        OutputStream output = null;

        try
        {
            output = new FileOutputStream( fileName );

            String password = "stackoverflow";
            String hashedPassword = hashMD5( password );

            prop.setProperty( "root", "root" );
            prop.setProperty( "rootpassword", hashedPassword );
            prop.store( output, null );
        }
        catch ( IOException io ) {io.printStackTrace();}
        finally
        {
            if ( output != null )
            {
                try {output.close();}
                catch ( IOException e ) {e.printStackTrace();}
            }
        }
    }

    public String hashMD5( String passwordToHash )
    {
        String generatedPassword = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance( "MD5" );
            md.update( passwordToHash.getBytes() );
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for ( int i = 0; i < bytes.length; i++ )
                sb.append( Integer.toString( ( bytes[i] & 0xff ) + 0x100, 16 ).substring( 1 ) );

            generatedPassword = sb.toString();
        }
        catch ( NoSuchAlgorithmException e ) {e.printStackTrace();}

        return generatedPassword;
    }

    public void close()
    {
        try
        {
            isRunning = false;
            socket.close();
        }
        catch( IOException e ) {e.printStackTrace();}
    }
}
