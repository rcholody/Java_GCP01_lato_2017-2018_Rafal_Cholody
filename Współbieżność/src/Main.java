import Crawler.CrawlerException;
import Logger.ConsoleLogger;
import Logger.MailLogger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Logger.*;
import Crawler.*;

import Monitor.*;

public class Main {
    public static void main(String []args){
        try {
            LinkedList<String> list = new LinkedList<>();
            list.add(new String("http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt"));
            Monitor monitor = new Monitor(list);

            (new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        monitor.startThreads();
                    } catch (MonitorException e) {
                        e.printStackTrace();
                    }
                }
            })).start();
            try{
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            monitor.cancel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
