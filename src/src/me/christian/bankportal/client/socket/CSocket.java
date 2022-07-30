package me.christian.bankportal.client.socket;

import me.christian.bankportal.global.GlobalReferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian Sweat
 * @since 10:16 PM 7/27/22
 */
public class CSocket {

    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    private final List<String> sendQueue = new ArrayList<>();

    private boolean active = false;

    public void run() {
        try {
            _run();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void _run() throws IOException {
        try {
            clientSocket = new Socket(GlobalReferences.SOCKET_URL, GlobalReferences.CONNECTION_PORT);
        } catch (Exception exception) {
            terminate();
        }
        writer = new PrintWriter(clientSocket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        active = true;

        Thread receiver = new Thread(() -> {
            try {
                String message;
                while ((message = reader.readLine()) != null && !message.equals("exit client")) {
                    System.out.println(": " + message);
                }
                terminate();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        receiver.start();

        Thread sender = new Thread(() -> {
                while (active) {
                    if (sendQueue.size() > 0) {
                        writer.println(sendQueue.get(0));
                        writer.flush();
                        sendQueue.remove(sendQueue.size() - 1);
                    }
                }
                terminate();
        });
        sender.start();
    }

    private void terminate() {
        try {
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
            if (clientSocket != null)
                clientSocket.close();
            active = false;
            System.exit(-1);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        sendQueue.add(message);
    }

}
