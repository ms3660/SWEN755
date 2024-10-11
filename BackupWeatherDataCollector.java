public class BackupWeatherDataCollector {

    public void run() {
        System.out.println("Backup collector standing by...");

        // Start a thread to continuously send heartbeats
        Thread heartbeatThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); // Send heartbeat every second
                    HeartbeatSender.sendHeartbeat("BACKUP");
                } catch (InterruptedException e) {
                    System.err.println("Backup collector heartbeat interrupted: " + e.getMessage());
                }
            }
        });

        // Start the heartbeat thread
        heartbeatThread.start();

        // Standby until failover is triggered
        while (true) {
            try {
                Thread.sleep(1000); // Waiting for failover
            } catch (InterruptedException e) {
                System.err.println("Backup collector interrupted during standby: " + e.getMessage());
            }
        }
    }

    public void takeOver() {
        System.out.println("Backup collector taking over!");

        // Load checkpoint from the last saved state
        WeatherDataCollector collector = WeatherDataCollector.loadCheckpoint();
        if (collector == null) {
            collector = new WeatherDataCollector(); // Start fresh if no checkpoint exists
        }

        // Continue data collection from the loaded state
        collector.collectData();
    }

    public static void main(String[] args) {
        BackupWeatherDataCollector backup = new BackupWeatherDataCollector();
        backup.run();
    }
}
