package Student;

import java.util.Comparator;

public class Student {
    private double mark;
    private String firstName;
    private String secondName;
    private int age;

    public double getMark() {
        return mark;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getSecondName() {
        return secondName;
    }
    public int getAge() {
        return age;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public Student(double mark, String fn, String ln, int age){
        this.mark = mark;
        this.firstName = fn;
        this.secondName = ln;
        this.age = age;
    }
    public Student(){};

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        if (Double.compare(student.mark, mark) != 0) return false;
        if (age != student.age) return false;
        if (firstName != null ? !firstName.equals(student.firstName) : student.firstName != null) return false;
        return secondName != null ? secondName.equals(student.secondName) : student.secondName == null;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;

        result = prime * result + age;
        result = prime * result + (firstName != null ? firstName.hashCode() : 0);
        result = prime * result + (secondName != null ? secondName.hashCode() : 0);

        temp = Double.doubleToLongBits(mark);
        result = prime * result + (int) (temp ^ (temp >>> 32));

        return result;
    }

    @Override
    public String toString(){ return mark + " " + firstName + " " + secondName + " " + age;}

    public static class Comparators{
        public static final Comparator<Student> AGE = (Student s1, Student s2) -> Integer.compare(s1.age, s2.age);
        public static final Comparator<Student> MARK = (Student s1, Student s2) -> Double.compare(s1.mark, s2.mark);
        public static final Comparator<Student> FIRSTNAME = (Student s1, Student s2) -> s1.firstName.compareTo(s2.firstName);
        public static final Comparator<Student> SECONDNAME = (Student s1, Student s2) -> s1.secondName.compareTo(s2.secondName);
    }
}
