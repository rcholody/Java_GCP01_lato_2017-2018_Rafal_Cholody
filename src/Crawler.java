import java.io.IOException;
import java.net.URL;
import java.util.*;


public class  Crawler {

    //listenery do while
    private Set<Student> data = new HashSet<Student>();

    private List<CrawlerListener> addedStudentListeners = new LinkedList<>();

    private List<CrawlerListener> deleteStudentListeners = new LinkedList<>();
    private List<CrawlerListener> endIterListeners = new LinkedList<>();
    private List<CrawlerListener> beginIterListeners = new LinkedList<>();
    private List<CrawlerListener> nochangeListeners = new LinkedList<>();
    private int iterations = 0;
   public List<Student> extractStudents(OrderMode mode) {
        List<Student> result = new LinkedList<>(data);

        switch (mode) {
            case AGE:
                Collections.sort(result, new Comparator<Student>() {
                    @Override
                    public int compare(Student o1, Student o2) {
                        return o1.getAge() - o2.getAge();
                    }
                });
                return result;

            case FIRST_NAME:
                Collections.sort(result, new Comparator<Student>() {
                    @Override
                    public int compare(Student o1, Student o2) {
                        return o1.getFirstName().compareTo(o2.getFirstName());
                    }
                });
                return result;


            case LAST_NAME:
                Collections.sort(result, new Comparator<Student>() {
                    @Override
                    public int compare(Student o1, Student o2) {
                        return o1.getLastName().compareTo(o2.getLastName());
                    }
                });
                return result;

            case MARK:
                Collections.sort(result, new Comparator<Student>() {
                    @Override
                    public int compare(Student o1, Student o2) {
                        return Double.compare(o1.getMark(), o2.getMark());
                    }
                });
                default: return result;

        }
        // result = data.toArray();
        //posortowani studenci
    }
    public Crawler() {
    }

   double extractMark(ExtremumMode mode) {
       List<Student> tmp = extractStudents(OrderMode.MARK);

       if(mode.equals(ExtremumMode.MAX)){
           return tmp.get(tmp.size()-1).getMark();


       }
       else {
           return tmp.get(0).getMark();
       }
    }// maksymalna lub minimalna ocena

    int extractAge(ExtremumMode mode) {
        List<Student> tmp = extractStudents(OrderMode.AGE);

        if(mode.equals(ExtremumMode.MAX)){
            return tmp.get(tmp.size()-1).getAge();


        }
        else {
            return tmp.get(0).getAge();
        }

    }// maksymalnylub minimalnywiek

    public void addNochangeIterListener(CrawlerListener nochangeListener) {
        this.nochangeListeners.add(nochangeListener);
    }

    public void addDeletedStudentListener(CrawlerListener deletedListener) {
        this.deleteStudentListeners.add(deletedListener);
    }

    public void addEndIterListeners(CrawlerListener endIterListener) {
        this.endIterListeners.add(endIterListener);
    }

    public void run() throws IOException {
        while (true) {
            callListenersIterBegin(iterations);
            //listener usunietych //lister do while iteracja przed i po whilem //jeśli nic to też rzuć string (bez zmian)
            Set<Student> oldData = data;
            data = new HashSet<Student>() {{
                addAll(StudentsParser.parse(new URL("http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt")));
            }};


            Set<Student> added = getAdded(oldData, data);
            Set<Student> deleted = getRemoved(oldData, data);

            if (added.size() == 0 && deleted.size() == 0) {
                callListenersNochange(data);
            } else {
                callListenersAdded(added);
                callListenersDeleted(deleted);
            }


            System.out.println(oldData.toString());

            try {
                Thread.sleep(1000 * 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            callListenersIterEnd(iterations);
            iterations++;

        }
    }


    private void callListenersAdded(Set<Student> input) {
        for (Student s : input) {
            for (CrawlerListener cl : addedStudentListeners) {
                cl.actionPerformed(new CrawlerEvent(CrawlerEventType.ADD, s));
            }
        }

    }

    private void callListenersNochange(Set<Student> input) {
        for (Student s : input) {
            for (CrawlerListener cl : nochangeListeners) {
                cl.actionPerformed(new CrawlerEvent(CrawlerEventType.NO_CHANGE, s));
            }
        }
    }

    private void callListenersDeleted(Set<Student> input) {
        for (Student s : input) {
            for (CrawlerListener cl : deleteStudentListeners) {
                cl.actionPerformed(new CrawlerEvent(CrawlerEventType.DELETE, s));
            }
        }
    }

    private void callListenersIterEnd(int input) { //

        for (CrawlerListener cl : endIterListeners) {
            cl.actionPerformed(new CrawlerIterationEvent(CrawlerEventType.ITERATION_END, input));
        }


    }

    private void callListenersIterBegin(int input) { //

        for (CrawlerListener cl : beginIterListeners) {
            cl.actionPerformed(new CrawlerIterationEvent(CrawlerEventType.ITERATION_START, input));
        }


    }

    public void addAddedStudentsListener(CrawlerListener crawlerListener) {
        addedStudentListeners.add(crawlerListener);
    }

    public void addBeginIterListeners(CrawlerListener beginIterListener) {
        this.beginIterListeners.add(beginIterListener);
    }


    private Set<Student> getAdded(Set<Student> oldData, Set<Student> data) {
        Set<Student> result = new HashSet<Student>(data);
        //result.addAll(data);
        result.removeAll(oldData);

        return result;
    }

    private Set<Student> getRemoved(Set<Student> oldData, Set<Student> data) {
        Set<Student> result = new HashSet<Student>(oldData);
        //result.addAll(oldData);
        result.removeAll(data);

        return result;
    }


}
