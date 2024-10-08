import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HeartbeatSender {
    private static final String HEARTBEAT_MESSAGE = "HEARTBEAT";
    private static final int PORT = 9876;

    public static void sendHeartbeat(String source) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getLocalHost();
            String message = HEARTBEAT_MESSAGE + "_" + source;
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("Error sending heartbeat: " + e.getMessage());
        }
    }
}