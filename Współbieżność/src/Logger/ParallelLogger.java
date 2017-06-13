package Logger;

import Monitor.MonitorLogger;
import Student.Student;

import java.util.LinkedList;

public class ParallelLogger extends Thread implements Logger {

    private LinkedList<MonitorLogger> list = new LinkedList<MonitorLogger>();
    private Logger[] loggers;
    private boolean isStop = false;

    public ParallelLogger(Logger[] loggers){
        this.loggers = loggers;
    }

    public synchronized void cancel(){
        this.isStop = true;
    }

    public void run(){
        isStop = false;
        while(!isStop){
            for(MonitorLogger monitorLogger : (LinkedList<MonitorLogger>)list.clone()){
                list.remove(monitorLogger);
                for(Logger l : loggers){
                    l.log(monitorLogger.getStatus(), monitorLogger.getStudent());
                }
            }
        }
    }

    @Override
    public void log(String status, Student student) {
        list.add(new MonitorLogger(status,student));
    }
}
