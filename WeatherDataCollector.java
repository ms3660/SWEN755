import java.io.*;
import java.util.Random;

public class WeatherDataCollector implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int FAILURE_PROBABILITY = 5; // 5% chance of failure
    private final Random random = new Random();
    private int dataPointsCollected = 0;
    private static final String CHECKPOINT_FILE = "weather_checkpoint.ser";

    public void collectData() {
        while (true) {
            try {
                if (random.nextInt(100) < FAILURE_PROBABILITY) {
                    throw new RuntimeException("Sensor malfunction!");
                }
                
                double temperature = 20 + random.nextDouble() * 15;
                double humidity = 30 + random.nextDouble() * 40;
                System.out.println("Weather Data: Temperature = " + 
                    String.format("%.2f", temperature) + "°C, Humidity = " + 
                    String.format("%.2f", humidity) + "%");
                
                dataPointsCollected++;
                
                if (dataPointsCollected % 5 == 0) {
                    checkpoint();
                }
                
                HeartbeatSender.sendHeartbeat("PRIMARY");
                
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("Critical error in Primary Collector: " + e.getMessage());
                System.exit(1); // Crash the process
            }
        }
    }

    private void checkpoint() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CHECKPOINT_FILE))) {
            out.writeObject(this);
            System.out.println("Checkpoint saved.");
        } catch (IOException e) {
            System.err.println("Error saving checkpoint: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        WeatherDataCollector collector = new WeatherDataCollector();
        collector.collectData();
    }
}