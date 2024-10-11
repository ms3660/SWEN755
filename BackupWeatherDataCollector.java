public class BackupWeatherDataCollector {

    private boolean isCollecting = false;

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
        while (!isCollecting) {
            try {
                Thread.sleep(1000); // Waiting for failover
            } catch (InterruptedException e) {
                System.err.println("Backup collector interrupted during standby: " + e.getMessage());
            }
        }
    }

    public void takeOver() {
        System.out.println("Backup collector taking over!");
        isCollecting = true;
        collectData();
    }

    public void collectData() {
        System.out.println("Backup collector started collecting data...");

        // Simulate data collection
        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(1000); // Simulate time to collect data
                double temperature = 20 + Math.random() * 15; // Generate a random temperature value
                double humidity = 30 + Math.random() * 40; // Generate a random humidity value

                System.out.println("Backup Weather Data: Temperature = " + String.format("%.2f", temperature) + "°C, Humidity = " + String.format("%.2f", humidity) + "%");

            } catch (InterruptedException e) {
                System.err.println("Data collection interrupted: " + e.getMessage());
            }
        }

        System.out.println("Backup collector has finished collecting data.");
    }

    public static void main(String[] args) {
        BackupWeatherDataCollector backup = new BackupWeatherDataCollector();
        backup.run();
    }
}
