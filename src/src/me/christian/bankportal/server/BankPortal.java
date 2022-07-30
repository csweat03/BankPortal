package me.christian.bankportal.server;

import me.christian.bankportal.global.GlobalReferences;
import me.christian.bankportal.server.utility.Timer;
import me.christian.bankportal.server.handler.handlers.CommandHandler;
import me.christian.bankportal.server.handler.handlers.SocketHandler;
import me.christian.bankportal.server.socket.SSocket;
import me.christian.bankportal.server.utility.Cryptography;
import me.christian.bankportal.server.utility.References;
import me.christian.bankportal.server.utility.SQL;
import me.christian.bankportal.server.utility.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian Sweat
 */
public class BankPortal {

    public static BankPortal INSTANCE = lazyAssign();

    private final SQL SQL = new SQL();

    private final CommandHandler CommandHandler = new CommandHandler();
    private final SocketHandler SocketHandler = new SocketHandler();

    private boolean running = false;
    private final boolean debug = true;
    private float frameRate = 100F;
    private Timer appTime;

    protected final List<User> users = new ArrayList<>();

    public void run() {
        initialize();

        Thread main = new Thread(() -> {
            appTime = new Timer();

            while (running) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                render();
                if (appTime.hasTimeElapsed(1000.0F / frameRate)) {
                    update();
                }
            }
        });

        main.start();

        exit();
    }

    /**
     * Initialize function to perform all basic App configuration.
     * Sets up a static reference to the {@link BankPortal} class.
     * Creates MySQL default table to the defined database in {@link GlobalReferences}.
     * Adds all users found in database to ArrayList defined as users.
     * Adds a runtime hook on new Thread to exit program if failure or closed.
     */
    protected void initialize() {
        lazyAssign();
        running = true;

        try {
            SQL.updateDatabase("create table " + References.DATABASE_TABLE_USERS_NAME + " ( uuid TEXT, userName TEXT, passwordHash TEXT, firstName TEXT, lastName TEXT, birthday TEXT, phone TEXT, email TEXT );");
            //String userName, char[] password, String firstName, String lastName, String birthday, String phone, String email
            //SQL.addNewUserToDatabase(new User("admin", "password".toCharArray(), "Admin", "Account", "01/01/2000", "123-456-7890", "admin@bankportal.com"));
            users.addAll(SQL.addUsersFromDatabase());

            CommandHandler.initialize();
            SocketHandler.initialize();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(this::exit));
    }

    public void render() {

    }

    public void update() {
        SocketHandler.update();
    }

    protected void exit() {
        try {
            SocketHandler.getServer().close();
            running = false;
        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * A function to return a User class of the qualified user, given the data provided from the client request.
     *
     * @param usernameOrEmail Takes the supplied username or email from the client.
     * @param password Takes the raw password from the client, hashes it, and runs it against the stored password hash.
     * @return Verified User object if test comes back true, else returns a null user.
     */
    public User findUserProfile(String usernameOrEmail, String password) {
        User user = users.stream().filter(u -> u.getUserName().equals(usernameOrEmail) || u.getEmail().equals(usernameOrEmail)).findFirst().orElse(null);

        if (user == null) return null;

        if (!Cryptography.encrypt(password.toCharArray()).equals(user.getPasswordHash())) return null;

        return user;
    }

    public void sendMessage(int id, String message) {
        SSocket socket = SocketHandler.get().stream().filter(sckt -> sckt.getId() == id).findFirst().orElse(null);

        if (socket == null) {
            log("broadcast: %s", message);
            SocketHandler.get().forEach(sckt -> sckt.sendMessage(message));
        } else {
            log("singlecast: %s, %s", id, message);
            socket.sendMessage(message);
        }
    }

    public void updateFrameRate(float frameRate) {
        log("updateFrameRate: %s => %s", this.frameRate, frameRate);
        this.frameRate = frameRate;
    }

    public static void log(String message, Object... parameters) {
        if (INSTANCE.debug) System.err.println("DEBUG: " + String.format(message, parameters));
    }

    public CommandHandler getCommandHandler() {
        return CommandHandler;
    }

    public SocketHandler getSocketHandler() {
        return SocketHandler;
    }

    protected static BankPortal lazyAssign() {
        if (INSTANCE != null) return INSTANCE;

        return INSTANCE = new BankPortal();
    }

}
