package com.company;

public class EventCrawler {

    private EventCrawlerTypes type;
    private Student student;

    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public EventCrawler(EventCrawlerTypes type) {
        this.type = type;
    }
    public EventCrawlerTypes getType() {
        return type;
    }
}
