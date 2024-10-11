import java.io.*;

public class BackupWeatherDataCollector {
    private static final String CHECKPOINT_FILE = "weather_checkpoint.ser";
    private static final String ACTIVATION_FLAG = "activate_backup";

    public void run() {
        System.out.println("Backup Collector standing by...");
        while (!checkActivationFlag()) {
            try {
                HeartbeatSender.sendHeartbeat("BACKUP");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Backup Collector interrupted: " + e.getMessage());
            }
        }
        takeOver();
    }

    private boolean checkActivationFlag() {
        File flag = new File(ACTIVATION_FLAG);
        return flag.exists();
    }

    private void takeOver() {
        System.out.println("Backup Collector taking over...");
        WeatherDataCollector collector = loadCheckpoint();
        if (collector != null) {
            collector.collectData(); // Continue from the last checkpoint
        } else {
            new WeatherDataCollector().collectData(); // Start fresh if checkpoint loading fails
        }
    }

    private WeatherDataCollector loadCheckpoint() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(CHECKPOINT_FILE))) {
            System.out.println("Checkpoint loaded. Continuing data collection...");
            return (WeatherDataCollector) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading checkpoint: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        BackupWeatherDataCollector backup = new BackupWeatherDataCollector();
        backup.run();
    }
}