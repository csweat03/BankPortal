package me.christian.bankportal.server.command;

import me.christian.bankportal.server.BankPortal;
import me.christian.bankportal.server.utility.JSON;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * @author Christian Sweat
 * @since 6:56 PM 7/28/22
 */
public class SCommand {

    private final String name;

    private final String left, right;

    public SCommand(String name) {
        this.name = name;
        JSONObject command = JSON.References.Obj("formatting").getJSONObject("command");
        this.left = String.format("%s %s", name, command.getString("left_padding"));
        this.right = command.getString("right_padding");
    }

    public void execute(int id, String message) {
        BankPortal.log("command: Client %s, %s, %s", id, getName(), Arrays.toString(getArguments(message)));
    }

    public boolean isQueryValid(String query) {
        return query.startsWith(left) && query.endsWith(right);
    }

    public String getName() {
        return name;
    }

    public String[] getArguments(String query) {
        return query.replace(left, "").replace(right, "").split(",,,");
    }
}
