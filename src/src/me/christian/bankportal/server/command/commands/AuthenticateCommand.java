package me.christian.bankportal.server.command.commands;

import me.christian.bankportal.server.BankPortal;
import me.christian.bankportal.server.command.SCommand;
import me.christian.bankportal.server.utility.User;

/**
 * @author Christian Sweat
 * @since 10:54 PM 7/28/22
 */
public class AuthenticateCommand extends SCommand {

    public AuthenticateCommand() {
        super("authenticate");
    }

    public void execute(int id, String message) {
        String[] cred = getArguments(message);
        User account;
        if ((account = BankPortal.INSTANCE.findUserProfile(cred[0], cred[1])) != null) {
            String msg = "Good afternoon, " + account.getFirstName() + "! Welcome to BankPortal";
            BankPortal.INSTANCE.sendMessage(id, msg);
        } else {
            BankPortal.INSTANCE.sendMessage(id, "exit client");
        }
        super.execute(id, message);
    }
}
