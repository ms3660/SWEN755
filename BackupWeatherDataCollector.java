public class BackupWeatherDataCollector {
    private static final String CHECKPOINT_FILE = "weather_checkpoint.ser";

    public void run() {
        System.out.println("Backup Collector standing by...");
        while (true) {
            try {
                Thread.sleep(1000); // Check every second
                HeartbeatSender.sendHeartbeat("BACKUP");
            } catch (InterruptedException e) {
                System.err.println("Backup Collector interrupted: " + e.getMessage());
            }
        }
    }

    public void takeOver() {
        System.out.println("Backup Collector taking over...");
        WeatherDataCollector collector = WeatherDataCollector.loadCheckpoint();
        collector.collectData(); // Continue from the last checkpoint
    }

    public static void main(String[] args) {
        BackupWeatherDataCollector backup = new BackupWeatherDataCollector();
        backup.run();
    }
}