import java.util.Random;

public class WeatherDataCollector {
    private static final int FAILURE_PROBABILITY = 5; // 5% chance of failure
    private final Random random = new Random();

    public void collectData() {
        while (true) {
            try {
                if (random.nextInt(100) < FAILURE_PROBABILITY) {
                    throw new RuntimeException("Sensor malfunction!");
                }
                
                // Simulate data collection
                double temperature = 20 + random.nextDouble() * 15;
                double humidity = 30 + random.nextDouble() * 40;
                
                System.out.println("Weather Data: Temperature = " + String.format("%.2f", temperature) + "°C, Humidity = " + String.format("%.2f", humidity) + "%");
                
                // Send heartbeat
                HeartbeatSender.sendHeartbeat();
                
                Thread.sleep(1000); // Wait for 1 second before next reading
            } catch (Exception e) {
                System.err.println("Critical error: " + e.getMessage());
                System.exit(1); // Crash the process
            }
        }
    }

    public static void main(String[] args) {
        WeatherDataCollector collector = new WeatherDataCollector();
        collector.collectData();
    }
}