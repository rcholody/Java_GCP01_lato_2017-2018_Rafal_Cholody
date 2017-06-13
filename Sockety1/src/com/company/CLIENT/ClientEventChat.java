package com.company.CLIENT;

public interface ClientEventChat
{
    void messageReceived( String msg );
    void disconnectedFromTheServer();
}
