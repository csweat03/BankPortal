package me.christian.bankportal.client.command;

import me.christian.bankportal.client.socket.CSocket;
import me.christian.bankportal.server.utility.JSON;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * @author Christian Sweat
 * @since 10:07 PM 7/28/22
 */
public record CCommand(String name, String... args) {

    public void execute(CSocket socket) {
        JSONObject command = JSON.References.Obj("formatting").getJSONObject("command");
        socket.sendMessage(String.format("%s " + command.get("left_padding") + "%s" + command.get("right_padding"), name, Arrays.toString(args).replace("[", "").replace("]", "").replace(", ", ",,,")));
    }
}
