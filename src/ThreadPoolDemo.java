import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        // Define range to be processed
        int startRange = 1;
        int endRange = 100000;
        int chunkSize = 10000;

        // Create a pool of 10 threads
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // List to hold Future objects for each task
        List<Future<Long>> futures = new ArrayList<>();

        // Divide the range into chunks and submit tasks
        for (int i = startRange; i <= endRange; i += chunkSize) {
            PrimeSumTask task = new PrimeSumTask(i, Math.min(i + chunkSize - 1, endRange));
            Future<Long> future = executorService.submit(task);
            futures.add(future);
        }

        // Collect results
        long totalSum = 0;
        try {
            for (Future<Long> future : futures) {
                totalSum += future.get(); // Waits for each task to complete and retrieves result
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown(); // Shutdown the thread pool
        }

        System.out.println("Total sum of prime numbers: " + totalSum);
    }
}

