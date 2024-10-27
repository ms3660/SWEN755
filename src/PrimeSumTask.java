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
    public Long call() {
        long sum = 0;
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                sum += i;
            }
        }
        System.out.println("Processed range: " + start + " to " + end + ", Sum: " + sum);
        return sum;
    }
}

