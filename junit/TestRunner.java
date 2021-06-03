package junit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TestRunner {
    private String[] arguments;
    private ConcurrentLinkedQueue<Class<?>> testClasses = new ConcurrentLinkedQueue<>();
    private ArrayList<String> testPassed = new ArrayList<>();
    private HashMap<String, String> testFailed = new HashMap<>();
    private HashMap<String, String> classFailed = new HashMap<>();
    private CountDownLatch countDownLatch;
    private boolean initialized = false;

    public TestRunner(String[] args) {
        arguments = args;
    }

    public void start() throws Exception {
        if (arguments.length < 2) {
            throw new Exception("Too few arguments.");
        }

        int threadsNumber = Integer.parseInt(arguments[0]);
        if (threadsNumber < 1) {
            throw new Exception("Threads number should be more than 1.");
        }

        countDownLatch = new CountDownLatch(threadsNumber);

        for (int i = 0; i < threadsNumber; i++ ) {
            new TestThread(this).start();
        }

        for (int i = 1; i < arguments.length; i++) {
            testClasses.add(Class.forName(arguments[i]));
        }
        initialized = true;

        countDownLatch.await(10, TimeUnit.MINUTES);

        printOutput();
    }

    public Class<?> getTestObject() {
        return testClasses.poll();
    }

    public synchronized void sendInfo(Analyzer analyzer) {
        testPassed.addAll(analyzer.getTestPassed());
        testFailed.putAll(analyzer.getTestFailed());
        classFailed.putAll(analyzer.getClassFailed());
    }

    public void countDown() {
        countDownLatch.countDown();
    }

    public boolean isInitialized() {
        return initialized;
    }

    private void printOutput() {
        if(!testPassed.isEmpty()) {
            System.out.println("The following tests are passed:");
            for (String str : testPassed) {
                System.out.println(str);
            }
        }

        System.out.println();
        if (!testFailed.isEmpty()) {
            System.out.println("The following test are failed with errors:");
            for (String key : testFailed.keySet()) {
                System.out.println(key + ": " + testFailed.get(key));
            }
        }

        System.out.println();
        if (!classFailed.isEmpty()) {
            System.out.println("The instance of the following classes wasn't created because of the exception:");
            for (String key : classFailed.keySet()) {
                System.out.println(key + ": " + classFailed.get(key));
            }
        }
    }
}
