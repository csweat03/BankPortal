package me.christian.bankportal.server.handler.handlers;

import me.christian.bankportal.global.GlobalReferences;
import me.christian.bankportal.server.BankPortal;
import me.christian.bankportal.server.handler.Handler;
import me.christian.bankportal.server.socket.SSocket;

import java.net.ServerSocket;

/**
 * @author Christian Sweat
 * @since 7:41 PM 7/28/22
 */
public class SocketHandler extends Handler<SSocket> {
    private ServerSocket server;

    public void initialize() {
        try {
            server = new ServerSocket(GlobalReferences.CONNECTION_PORT);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        add(new SSocket(server, implementationList.size()));
    }

    public void update() {
        if (implementationList.get(implementationList.size() - 1).isActive()) {
            BankPortal.log("Adding new Socket connection.");
            add(new SSocket(server, implementationList.size()));
        }
        implementationList.removeAll(implementationList.stream().filter(SSocket::isDead).toList());
    }

    public ServerSocket getServer() {
        return server;
    }

}
