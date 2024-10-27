import java.util.concurrent.Callable;

public class PrimeSumTask implements Callable<Long> {
    private final int start;
    private final int end;

    public PrimeSumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    // Check if a number is prime
    private boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    @Override
    public Long call() throws InterruptedException {
        long sum = 0;

        // Simulate a time-consuming task by calculating prime numbers
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                sum += i;
            }
        }

        // Simulate processing delay
        Thread.sleep(100);

        // Print the thread name to demonstrate reuse
        System.out.printf("Thread %s processed range: %d to %d, Sum: %d%n",
                Thread.currentThread().getName(), start, end, sum);

        return sum;
    }
}
