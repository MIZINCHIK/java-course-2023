package edu.hw6.task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

public class PortScanner {
    @SuppressWarnings("MultipleStringLiterals")
    private static final Map<Integer, String> KNOWN_PORTS = Map.ofEntries(
        entry(80, "HyperText Transfer Protocol"), entry(21, "File Transfer Protocol"),
        entry(25, "Simple Mail Transfer Protocol"), entry(22, "Secure Shell"),
        entry(443, "HyperText Transfer Protocol Secure"), entry(53, "Domain Name System"),
        entry(3306, "MySQL Database"), entry(5432, "PostgreSQL Database"),
        entry(3389, "Remote Desktop Protocol"), entry(27017, "MongoDB Database"),
        entry(1521, "Oracle Database"), entry(5353, "Multicast Domain Name System"),
        entry(23, "Telnet"), entry(110, "Post Office Protocol version 3"),
        entry(143, "Internet Message Access Protocol"), entry(67, "Dynamic Host Configuration Protocol"),
        entry(68, "Dynamic Host Configuration Protocol"), entry(8080, "HTTP proxy server"),
        entry(1080, "SOCKS proxy server"), entry(3128, "HTTPS proxy server"),
        entry(2049, "Network File System"), entry(6379, "Redis key-value store")
    );
    private static final int WELL_KNOWN_PORTS_LIMIT = 49152;
    private static final String PROTOCOL = "Protocol";
    private static final String PORT = "Port";
    private static final String SERVICE = "Service";
    private static final String TCP = "TCP";
    private static final String UDP = "UDP";

    private PortScanner() {
        throw new IllegalStateException();
    }

    public static String scanPorts() {
        List<Port> busyPorts = new ArrayList<>();
        for (int i = 0; i < WELL_KNOWN_PORTS_LIMIT; i++) {
            try (ServerSocket socket = new ServerSocket(i)) {
                busyPorts.add(new Port(i, TCP, KNOWN_PORTS.getOrDefault(i, "")));
            } catch (IOException ignored) {
            }
            try (DatagramSocket socket = new DatagramSocket(i)) {
                busyPorts.add(new Port(i, UDP, KNOWN_PORTS.getOrDefault(i, "")));
            } catch (SocketException ignored) {
            }
        }
        return formatPorts(busyPorts);
    }

    private static String formatPorts(List<Port> busyPorts) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-15s %-15s %-15s%n", PROTOCOL, PORT, SERVICE));
        for (Port port : busyPorts) {
            builder.append(String.format("%-15s %-15s %-30s%n", port.protocol(), port.number(), port.message()));
        }
        return builder.toString();
    }
}
