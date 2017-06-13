package Crawler;

import Student.*;

@FunctionalInterface
public interface StudentListener {
    void handle(Student student);
}
