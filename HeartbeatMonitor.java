import java.io.*;
import java.net.*;
import java.time.Instant;

public class HeartbeatMonitor {
    private static final int HEARTBEAT_PORT = 9876; // Port to listen for heartbeats
    private static final int BACKUP_PORT = 9877; // Port to send takeover signal to backup
    private static final int TIMEOUT = 5000; // 5-second timeout to detect primary failure
    private static long lastPrimaryHeartbeat = Instant.now().toEpochMilli();
    private static boolean primaryActive = true;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(HEARTBEAT_PORT)) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("Heartbeat Monitor started. Listening on port " + HEARTBEAT_PORT);
            
            while (true) {
                socket.setSoTimeout(TIMEOUT);
                try {
                    System.out.println("Waiting for heartbeat...");
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    processHeartbeat(message);
                } catch (IOException e) {
                    System.err.println("No heartbeat received within the timeout period.");
                    handleMissingHeartbeat();
                }
                
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
            lastPrimaryHeartbeat = Instant.now().toEpochMilli();
            primaryActive = true;
        } else if (message.equals("BACKUP")) {
            System.out.println("Backup Collector heartbeat received.");
        }
    }

    private static void handleMissingHeartbeat() {
        System.out.println("Heartbeat not received in time. Checking if primary is active...");
    }

    private static void triggerBackup() {
        try (Socket socket = new Socket("localhost", BACKUP_PORT)) {
            System.out.println("Sent takeover signal to backup collector.");
        } catch (IOException e) {
            System.err.println("Failed to trigger backup: " + e.getMessage());
        }
    }
}