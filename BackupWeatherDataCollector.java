import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.*;

public class BackupWeatherDataCollector {
    private volatile boolean isCollecting = false;
    private static final String CHECKPOINT_FILE = "weather_checkpoint.ser";
    private static final int MONITOR_PORT = 9877;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void start() {
        System.out.println("Backup collector standing by...");
        sendHeartbeats();
        waitForTakeoverSignal();
    }

  private void sendHeartbeats() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                String heartbeatType = isCollecting ? "BACKUP_ACTIVE" : "BACKUP_STANDBY";
                HeartbeatSender.sendHeartbeat(heartbeatType);
            } catch (Exception e) {
                System.err.println("Error sending heartbeat: " + e.getMessage());
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
    private void waitForTakeoverSignal() {
        try (ServerSocket serverSocket = new ServerSocket(MONITOR_PORT)) {
            System.out.println("Waiting for takeover signal on port " + MONITOR_PORT);
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Received takeover signal. Taking over...");
                    takeOver();
                    break;
                } catch (IOException e) {
                    System.err.println("Error accepting takeover signal: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error setting up takeover signal listener: " + e.getMessage());
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
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading checkpoint: " + e.getMessage());
        }
    }

    public void collectData() {
        System.out.println("Backup collector started collecting data...");
        Random random = new Random();
        while (isCollecting) {
            try {
                Thread.sleep(1000);
                double temperature = 20 + random.nextDouble() * 15;
                double humidity = 30 + random.nextDouble() * 40;
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