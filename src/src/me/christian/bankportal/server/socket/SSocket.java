package me.christian.bankportal.server.socket;

import me.christian.bankportal.server.BankPortal;
import me.christian.bankportal.server.command.SCommand;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Christian Sweat
 * @since 9:09 PM 7/27/22
 */
public class SSocket {

    private java.net.ServerSocket serverSocket;
    private Socket clientSocket;

    private BufferedReader reader;
    private PrintWriter writer;

    private final List<String> messageQueue = new ArrayList<>();
    private boolean active = false;
    private boolean dead = false;

    private final int id;

    public SSocket(ServerSocket serverSocket, int id) {
        this.id = id;
        run(serverSocket);
    }

    public void run(ServerSocket serverSocket) {
        try {
            _run(serverSocket);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void _run(ServerSocket serverSocket) throws IOException {
        String name = "Client " + id + ": ";
        clientSocket = serverSocket.accept();

        BankPortal.log(name + "Connected!");
        active = true;

        writer = new PrintWriter(clientSocket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        Thread receiver = new Thread(() -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    String finalMessage = message;
                    SCommand cmd = BankPortal.INSTANCE.getCommandHandler().get().stream().filter(command -> command.isQueryValid(finalMessage)).findFirst().orElse(null);

                    if (cmd == null) {
                        System.out.println(name + message);
                    } else {
                        cmd.execute(id, message);
                    }
                }
                active = false;
                BankPortal.log(name + "Disconnected!");
                reader.close();
                clientSocket.close();
                dead = true;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        receiver.start();

        Thread sender = new Thread(() -> {
            while (active) {
                if (messageQueue.size() > 0) {
                    writer.println(messageQueue.get(0));
                    writer.flush();
                    messageQueue.remove(messageQueue.size() - 1);
                }
            }
            writer.close();
        });
        sender.start();
    }

    public void sendMessage(String message) {
        messageQueue.add(message);
    }

    public java.net.ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public List<String> getMessageQueue() {
        return messageQueue;
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isDead() {
        return dead;
    }
}
