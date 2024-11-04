import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        // Define range and chunk size
        int startRange = 1;
        int endRange = 10000000;  // Increased range for a heavier task
        int chunkSize = 5000;    // Smaller chunks to ensure thread reuse

        // Create a pool of 10 threads
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // List to hold Future objects for each task
        List<Future<Long>> futures = new ArrayList<>();

        // Start timing the execution
        long startTime = System.currentTimeMillis();

        // Submit multiple tasks to the thread pool
        for (int i = startRange; i <= endRange; i += chunkSize) {
            PrimeSumTask task = new PrimeSumTask(i, Math.min(i + chunkSize - 1, endRange));
            futures.add(executorService.submit(task));
        }

        // Collect results
        long totalSum = 0;
        try {
            for (Future<Long> future : futures) {
                totalSum += future.get();  // Wait for each task to complete
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();  // Shutdown the thread pool
        }

        // Stop timing and print the total execution time
        long endTime = System.currentTimeMillis();
        System.out.println("Total sum of prime numbers: " + totalSum);
        System.out.println("Time taken with 10 threads: " + (endTime - startTime) + " ms");
    }
}
