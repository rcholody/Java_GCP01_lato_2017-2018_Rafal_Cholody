package Listeners;

import Student.Student;

@FunctionalInterface
public interface StudentListener {
    void handle(Student student);
}

