package Crawler;

@FunctionalInterface
public interface IterationListener {
    void handle(int iteration);
}