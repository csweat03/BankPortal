package me.christian.bankportal.client.command;

import me.christian.bankportal.client.socket.CSocket;
import me.christian.bankportal.global.GlobalReferences;

import java.util.Arrays;

/**
 * @author Christian Sweat
 * @since 10:07 PM 7/28/22
 */
public record CCommand(String name, String... args) {

    public void execute(CSocket socket) {
        socket.sendMessage(String.format("%s " + GlobalReferences.COMMAND_PADDING_LEFT + "%s" + GlobalReferences.COMMAND_PADDING_RIGHT, name, Arrays.toString(args).replace("[", "").replace("]", "").replace(", ", ",,,")));
    }
}
