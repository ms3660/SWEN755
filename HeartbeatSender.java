import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HeartbeatSender {

    private static final String MONITOR_IP = "127.0.0.1"; // IP address of the monitor
    private static final int MONITOR_PORT = 9876; // Port of the monitor

    public static void sendHeartbeat(String senderType) {
        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] buffer = senderType.getBytes();
            InetAddress address = InetAddress.getByName(MONITOR_IP);

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, MONITOR_PORT);
            socket.send(packet);
            System.out.println(senderType + " heartbeat sent.");
        } catch (Exception e) {
            System.err.println("Error sending heartbeat: " + e.getMessage());
        }
    }
}
