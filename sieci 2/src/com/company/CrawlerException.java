package com.company;

public class CrawlerException extends Exception{
    private String msg;

    public CrawlerException(String msg) {
        super(msg);
    }

    @Override
    public String toString(){
        return msg;
    }
}
