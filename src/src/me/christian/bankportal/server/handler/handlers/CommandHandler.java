package me.christian.bankportal.server.handler.handlers;

import me.christian.bankportal.server.BankPortal;
import me.christian.bankportal.server.command.SCommand;
import me.christian.bankportal.server.command.commands.AuthenticateCommand;
import me.christian.bankportal.server.handler.Handler;
import me.christian.bankportal.server.utility.User;

/**
 * @author Christian Sweat
 * @since 7:31 PM 7/28/22
 */
public class CommandHandler extends Handler<SCommand> {
    public void initialize() {
        add(new AuthenticateCommand());
    }
    public void update() {}
}
