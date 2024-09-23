import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class HeartbeatMonitor {
    private static final int PORT = 9876;
    private static final int TIMEOUT = 5000; // 5 seconds

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
                    System.out.println("Received heartbeat: " + message);
                } catch (IOException e) {
                    System.err.println("No heartbeat received. Weather Data Collector may have crashed!");
                }
            }
        } catch (IOException e) {
            System.err.println("Error in Heartbeat Monitor: " + e.getMessage());
        }
    }
}