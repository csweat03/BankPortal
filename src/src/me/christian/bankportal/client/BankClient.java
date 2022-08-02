package me.christian.bankportal.client;

import me.christian.bankportal.client.command.CCommand;
import me.christian.bankportal.client.socket.CSocket;

import java.util.Scanner;
import java.util.UUID;

public class BankClient {

    private final CSocket socket = new CSocket();

    public void run() {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> System.err.println("Your connection has been terminated.")));

            Scanner scanner = new Scanner(System.in);
            socket.run();

            System.out.print("Please enter your username or email: ");
            String user = scanner.next();
            System.out.print("Enter the password associated with this account: ");
            String pass = scanner.next();
            String uuid = UUID.randomUUID().toString().replace("-", "");

            new CCommand("authenticate", user, pass, uuid).execute(socket);

            while (scanner.hasNextLine()) {
                socket.sendMessage(scanner.nextLine());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
