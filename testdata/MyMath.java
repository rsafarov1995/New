package testdata;

public class MyMath {

    public int fibonacci (int n) {
        if (n == 0 ||n == 1) {
            return 1;
        } else {
            return fibonacci(n -1 ) + fibonacci(n - 2);
        }
    }

    public int factorial (int n) {
        if (n == 0) {
            return 1;
        } else {
            return factorial(n-1)*n;
        }
    }

    public int factorialException(int n) throws Exception {
        if (n < 0) {
            throw new Exception("Number less than zero.");
        } else if (n == 0) {
            return 1;
        } else {
            return factorialException(n - 1) * n;
        }
    }
}
