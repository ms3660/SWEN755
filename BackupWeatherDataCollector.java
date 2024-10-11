import java.io.*;
import java.net.*;

public class BackupWeatherDataCollector {
    private boolean isCollecting = false;
    private static final String CHECKPOINT_FILE = "weather_checkpoint.ser";
    private static final int MONITOR_PORT = 9877; // Port for communication with HeartbeatMonitor

    public void start() {
        System.out.println("Backup collector standing by...");
        sendHeartbeats();
        waitForTakeoverSignal();
    }

    private void sendHeartbeats() {
        Thread heartbeatThread = new Thread(() -> {
            while (!isCollecting) {
                try {
                    Thread.sleep(1000);
                    HeartbeatSender.sendHeartbeat("BACKUP");
                } catch (InterruptedException e) {
                    System.err.println("Backup collector heartbeat interrupted: " + e.getMessage());
                }
            }
        });
        heartbeatThread.start();
    }

    private void waitForTakeoverSignal() {
        try (ServerSocket serverSocket = new ServerSocket(MONITOR_PORT)) {
            System.out.println("Waiting for takeover signal on port " + MONITOR_PORT);
            Socket socket = serverSocket.accept();
            System.out.println("Received takeover signal. Taking over...");
            takeOver();
        } catch (IOException e) {
            System.err.println("Error waiting for takeover signal: " + e.getMessage());
        }
    }

    public void takeOver() {
        System.out.println("Backup collector taking over!");
        isCollecting = true;
        loadCheckpoint();
        collectData();
    }

    private void loadCheckpoint() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(CHECKPOINT_FILE))) {
            WeatherDataCollector checkpoint = (WeatherDataCollector) in.readObject();
            System.out.println("Loaded checkpoint: " + checkpoint);
            // Use the checkpoint data to initialize the backup collector's state
            // You might need to add fields to store the checkpoint data
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading checkpoint: " + e.getMessage());
        }
    }

    public void collectData() {
        System.out.println("Backup collector started collecting data...");
        while (true) {
            try {
                Thread.sleep(1000); // Simulate time to collect data
                double temperature = 20 + Math.random() * 15; // Generate a random temperature value
                double humidity = 30 + Math.random() * 40; // Generate a random humidity value
                System.out.println("Backup Weather Data: Temperature = " + 
                                   String.format("%.2f", temperature) + "°C, Humidity = " + 
                                   String.format("%.2f", humidity) + "%");
            } catch (InterruptedException e) {
                System.err.println("Data collection interrupted: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        BackupWeatherDataCollector backup = new BackupWeatherDataCollector();
        backup.start();
    }
}