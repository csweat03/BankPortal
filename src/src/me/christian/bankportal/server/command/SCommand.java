package me.christian.bankportal.server.command;

import me.christian.bankportal.global.GlobalReferences;
import me.christian.bankportal.server.BankPortal;

import java.util.Arrays;

/**
 * @author Christian Sweat
 * @since 6:56 PM 7/28/22
 */
public class SCommand {

    private final String name;

    private final String header, footer;

    public SCommand(String name) {
        this.name = name;
        this.header = String.format("%s %s", name, GlobalReferences.COMMAND_PADDING_LEFT);
        this.footer = GlobalReferences.COMMAND_PADDING_RIGHT;
    }

    public void execute(int id, String message) {
        BankPortal.log("command: Client %s, %s, %s", id, getName(), Arrays.toString(getArguments(message)));
    }

    public boolean isQueryValid(String query) {
        return query.startsWith(header) && query.endsWith(footer);
    }

    public String getName() {
        return name;
    }

    public String[] getArguments(String query) {
        return query.replace(header, "").replace(footer, "").split(",,,");
    }
}
