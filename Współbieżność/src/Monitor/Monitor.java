package Monitor;

import Crawler.Crawler;
import Crawler.CrawlerException;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import Logger.*;
import Listeners.*;
import javafx.application.Platform;

public class Monitor extends Thread{
    private LinkedList<String> linksList = new LinkedList<String>();
    private LinkedList<Crawler> crawlerList = new LinkedList<Crawler>();
    private Logger[] loggers = new Logger[]{
            new ConsoleLogger(),
            new MailLogger()
    };

    private List<IterationListener> iterationStartedListeners = new ArrayList<>();
    public void addIterationStartedListener(IterationListener iterationListener){ iterationStartedListeners.add(iterationListener);}
    public void removeIterationStartedListener(IterationListener listener){ iterationStartedListeners.remove(listener);}

    private List<IterationListener> iterationComplitedListeners = new ArrayList<>();
    public void addIterationComplitedListener(IterationListener listener){
        iterationComplitedListeners.add(listener);
    }
    public void removeIterationComplitedListener(IterationListener listener){iterationComplitedListeners.remove(listener);}

    private List<StudentListener> studentaddedListeners = new ArrayList<>();
    public void addStudentaddedListener(StudentListener listener){
        studentaddedListeners.add(listener);
    }
    public void removeStudentaddedListeners(StudentListener listener){
        studentaddedListeners.remove(listener);
    }

    private List<StudentListener> studentRemovedListener = new ArrayList<>();
    public void addStudentremovedListener(StudentListener listener){
        studentRemovedListener.add(listener);
    }
    public void removeStudentremovedListeners(StudentListener listener){
        studentaddedListeners.remove(listener);
    }

    private int maxThreads = 10;
    private ParallelLogger parallelLogger = new ParallelLogger(loggers);
    private boolean isStop = false;

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int x){
        this.maxThreads = x;
    }

    public Monitor(LinkedList<String> urls) {
        isStop = false;
        this.linksList = urls;
    }

    public void addLink(String link){
        linksList.add(link);
    }

    @Override
    public void run() {
        while(!isStop) {
            for (Crawler crawler : crawlerList) {
            }
        }
    }

    public void startThreads() throws MonitorException{
        if (maxThreads < linksList.size()) throw new MonitorException("Komunikat bledu: zbyt duzo watkow!\n");
        for(String link : linksList) {
            Crawler crawler = new Crawler(link);
            try {
                crawler.addIterationStartedListener(itr -> System.out.println("ITERATION START"));
                crawler.addIterationComplitedListener(itr -> System.out.println("ITERATION END"));

                crawler.addStudentaddedListener(Student -> {
                            for (Logger el : loggers) el.log("ADDED ", Student);
                        }
                );

                crawler.addStudentremovedListener(Student -> {
                            for (Logger el : loggers) el.log("REMOVED ", Student);
                        }
                );
                crawler.run();
                crawlerList.add(crawler);
            } catch (CrawlerException e) {
                e.printStackTrace();
                e.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        parallelLogger.start();
        this.start();
    }

    public synchronized void cancel() throws InterruptedException {
        this.isStop = true;
        for(Crawler c : crawlerList){
            c.postCancel();
            this.join();
        }
        parallelLogger.cancel();
        parallelLogger.join();
    }
}
