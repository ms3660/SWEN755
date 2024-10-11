import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.Instant;

public class HeartbeatMonitor {

    private static final int PORT = 9876; // Port to listen for heartbeats
    private static final int TIMEOUT = 5000; // 5-second timeout to detect primary failure
    private static long lastPrimaryHeartbeat = Instant.now().toEpochMilli();
    private static boolean primaryActive = true;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            System.out.println("Heartbeat Monitor started. Listening on port " + PORT);

            // Monitor loop
            while (true) {
                // Set a timeout for waiting on receiving heartbeats
                socket.setSoTimeout(TIMEOUT);
                try {
                    // Waiting for heartbeat
                    System.out.println("Waiting for heartbeat...");
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    processHeartbeat(message);
                } catch (IOException e) {
                    // Timeout has occurred (no heartbeat received within TIMEOUT)
                    System.err.println("No heartbeat received within the timeout period.");
                    handleMissingHeartbeat();
                }

                // Check if primary has failed by comparing the current time with the last received heartbeat time
                long currentTime = Instant.now().toEpochMilli();
                if (primaryActive && currentTime - lastPrimaryHeartbeat > TIMEOUT) {
                    System.out.println("Primary collector has failed! Triggering backup...");
                    primaryActive = false;
                    triggerBackup();
                }
            }
        } catch (IOException e) {
            System.err.println("Error with Heartbeat Monitor: " + e.getMessage());
        }
    }

    private static void processHeartbeat(String message) {
        if (message.equals("PRIMARY")) {
            System.out.println("Primary Collector heartbeat received.");
            lastPrimaryHeartbeat = Instant.now().toEpochMilli(); // Update last received time
            primaryActive = true; // Mark primary as active
        } else if (message.equals("BACKUP")) {
            System.out.println("Backup Collector heartbeat received.");
        }
    }

    private static void handleMissingHeartbeat() {
        System.out.println("Heartbeat not received in time. Checking if primary is active...");
        // No action needed here yet, since the check happens in the main loop after each receive attempt.
    }

    private static void triggerBackup() {
        System.out.println("Failover initiated. Backup Collector will take over.");
        BackupWeatherDataCollector backup = new BackupWeatherDataCollector();
        backup.takeOver();
    }
}
