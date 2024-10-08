import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class HeartbeatMonitor {
    private static final int PORT = 9876;
    private static final int TIMEOUT = 5000; // 5 seconds
    private static boolean primaryActive = true;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            System.out.println("Heartbeat Monitor started. Listening for heartbeats...");

            while (true) {
                try {
                    socket.setSoTimeout(TIMEOUT);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    processHeartbeat(message);
                } catch (IOException e) {
                    handleMissingHeartbeat();
                }
            }
        } catch (IOException e) {
            System.err.println("Error in Heartbeat Monitor: " + e.getMessage());
        }
    }

    private static void processHeartbeat(String message) {
        String[] parts = message.split("_");
        if (parts.length == 2) {
            String source = parts[1];
            System.out.println("Received heartbeat from: " + source);
            if (source.equals("PRIMARY")) {
                primaryActive = true;
            }
        }
    }

    private static void handleMissingHeartbeat() {
        if (primaryActive) {
            System.out.println("No heartbeat from Primary Collector. Initiating failover...");
            primaryActive = false;
            initiateFailover();
        } else {
            System.out.println("No heartbeat from Backup Collector.");
        }
    }

    private static void initiateFailover() {
        try {
            Process process = Runtime.getRuntime().exec("java BackupWeatherDataCollector");
            System.out.println("Backup Collector activated.");
        } catch (IOException e) {
            System.err.println("Failed to start Backup Collector: " + e.getMessage());
        }
    }
}