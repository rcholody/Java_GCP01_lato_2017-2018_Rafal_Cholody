package Monitor;

public class MonitorException extends Exception{
    private String msg;

    public MonitorException(String msg){
        super(msg);
    }

    @Override
    public String toString() { return msg; }
}
