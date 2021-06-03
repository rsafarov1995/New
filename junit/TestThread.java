package junit;

class TestThread extends Thread {
    final private TestRunner runner;

    public TestThread(TestRunner runner) {
        super();
        this.runner = runner;
    }

    @Override
    public void run() {
        Analyzer analyzer = new Analyzer();
        boolean runnerInitialized;
        while (true) {
            runnerInitialized = runner.isInitialized();
            Class<?> testObject = runner.getTestObject();
            if (testObject == null) {
                if (runnerInitialized) {
                    runner.countDown();
                    return;
                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {}
                    continue;
                }
            }
            analyzer.analyze(testObject);
            runner.sendInfo(analyzer);
        }
    }

}
