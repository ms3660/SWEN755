import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

public class WeatherDataCollector implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String CHECKPOINT_FILE = "weather_checkpoint.ser";
    private int dataPointsCollected = 0; // Tracks how many data points have been collected
    private boolean isRunning = true;

    public void collectData() {
        System.out.println("Primary Weather Data Collector started collecting data...");

        Random random = new Random();

        // Collect data in a loop, simulating random failure
        while (isRunning) {
            try {
                Thread.sleep(1000); // Simulate time taken to collect data

                // Simulate data collection
                double temperature = 20 + random.nextDouble() * 15;
                double humidity = 30 + random.nextDouble() * 40;

                System.out.println("Weather Data: Temperature = " + String.format("%.2f", temperature) + "°C, Humidity = " + String.format("%.2f", humidity) + "%");

                dataPointsCollected++;

                // Save checkpoint every 5 data points
                if (dataPointsCollected % 5 == 0) {
                    saveCheckpoint();
                }

                // Randomly fail with a small chance (simulate a crash)
                if (random.nextDouble() < 0.1) { // 10% chance of failure per iteration
                    System.out.println("Primary Collector has crashed randomly!");
                    isRunning = false;
                    return; // Exit the loop to simulate failure
                }

                // Send heartbeat
                HeartbeatSender.sendHeartbeat("PRIMARY");

            } catch (InterruptedException e) {
                System.err.println("Data collection interrupted: " + e.getMessage());
            }
        }
    }

    private void saveCheckpoint() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CHECKPOINT_FILE))) {
            out.writeObject(this); // Save the entire state of this object
            System.out.println("Checkpoint saved. Data points collected: " + dataPointsCollected);
        } catch (IOException e) {
            System.err.println("Error saving checkpoint: " + e.getMessage());
        }
    }

    public static WeatherDataCollector loadCheckpoint() {
        try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(new java.io.FileInputStream(CHECKPOINT_FILE))) {
            return (WeatherDataCollector) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading checkpoint: " + e.getMessage());
            return new WeatherDataCollector(); // Return new instance if checkpoint loading fails
        }
    }

    public static void main(String[] args) {
        WeatherDataCollector collector = new WeatherDataCollector();
        collector.collectData();
    }
}
