package Logs;

import Student.Student;

public class ConsoleLogger implements Logger{

    @Override
    public void log(String status, Student student) {
        if(student == null) System.out.print(status);
        else System.out.print(status + " : " + student.toString() + "\n");
    }
}
