package com.dnd.digimax.core.cluster.network;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Lightweight UDP transport layer for cluster communication.
 *
 * Used by:
 * - HeartbeatManager
 * - SyncManager
 * - MasterElectionService
 *
 * Design goals:
 * - Non-blocking I/O
 * - Minimal GC pressure
 * - Kiosk-safe (no crash propagation)
 */
public class UdpClient {

    private static final String TAG = "UdpClient";

    private final ExecutorService ioExecutor = Executors.newSingleThreadExecutor();

    private DatagramSocket socket;

    private volatile boolean running = false;

    // =========================
    // LIFECYCLE
    // =========================

    public synchronized void start(int port) {
        if (running) return;

        try {
            socket = new DatagramSocket(port);
            socket.setReuseAddress(true);
            running = true;

            Log.i(TAG, "UDP Client started on port: " + port);

        } catch (Exception e) {
            Log.e(TAG, "Failed to start UDP client", e);
            running = false;
        }
    }

    public synchronized void stop() {
        running = false;

        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error stopping UDP client", e);
        }

        ioExecutor.shutdownNow();
        Log.i(TAG, "UDP Client stopped");
    }

    public boolean isRunning() {
        return running;
    }

    // =========================
    // SEND
    // =========================

    public void send(String message, String host, int port) {
        if (!running) return;

        ioExecutor.execute(() -> {
            try {
                byte[] data = message.getBytes(StandardCharsets.UTF_8);

                InetAddress address = InetAddress.getByName(host);

                DatagramPacket packet =
                        new DatagramPacket(data, data.length, address, port);

                socket.send(packet);

                Log.d(TAG, "UDP sent -> " + host + ":" + port + " | " + message);

            } catch (IOException e) {
                Log.e(TAG, "UDP send failed", e);
            }
        });
    }

    // =========================
    // RECEIVE (blocking loop)
    // =========================

    public void listen(OnPacketListener listener) {

        ioExecutor.execute(() -> {

            byte[] buffer = new byte[2048];

            while (running && socket != null && !socket.isClosed()) {

                try {
                    DatagramPacket packet =
                            new DatagramPacket(buffer, buffer.length);

                    socket.receive(packet);

                    String msg = new String(
                            packet.getData(),
                            0,
                            packet.getLength(),
                            StandardCharsets.UTF_8
                    );

                    String sender = packet.getAddress().getHostAddress();

                    if (listener != null) {
                        listener.onMessage(sender, packet.getPort(), msg);
                    }

                } catch (IOException e) {
                    if (running) {
                        Log.e(TAG, "UDP receive error", e);
                    }
                }
            }
        });
    }

    // =========================
    // CALLBACK
    // =========================

    public interface OnPacketListener {
        void onMessage(String fromIp, int port, String message);
    }
}