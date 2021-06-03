package testdata;

import junit.After;
import junit.Assert;
import junit.Before;
import junit.Test;

public class FactorialTest {
    private MyMath instance;

    @Before
    public void beforeEach() {
        instance = new MyMath();
    }

    @Test
    public void factorialTest() {
        int x = instance.factorial(10);
        Assert.equals(x, 3628800);
    }

    @Test(expected = Exception.class)
    public void testFactorialException() throws Exception {
        instance.factorialException(-42);
    }

    @After
    public void afterEach() {
        Assert.isFalse(true);
    }
}