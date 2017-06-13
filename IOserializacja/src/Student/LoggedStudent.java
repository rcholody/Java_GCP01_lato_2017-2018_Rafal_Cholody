package Student;

import Enums.Status;

import java.io.Serializable;

public class LoggedStudent extends Student implements Serializable{
    private long time;
    private Status status;

    public LoggedStudent(){
        super();
        time=0;
        status=null;
    }
    public LoggedStudent(Student student,long time, String status){
        super(student.getMark(),student.getFirstName(),student.getLastName(),student.getAge());
        this.time=time;
        if(status.equals("ADDED")) this.status=Status.ADDED;
        else if(status.equals("REMOVED")) this.status=Status.REMOVED;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status.equals("ADDED")) this.status=Status.ADDED;
        else if(status.equals("REMOVED")) this.status=Status.REMOVED;
    }
}
