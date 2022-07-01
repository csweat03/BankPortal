package me.christian.bankportal;

import me.christian.bankportal.logic.User;
import me.christian.bankportal.utility.SQL;
import me.christian.bankportal.utility.Timer;

public class BankPortal {

    public static BankPortal INSTANCE = lazyAssign();

    public final SQL CONNECTION = new SQL();
    private boolean running = false;
    private float frameRate = 5.0F;
    private Timer gameTimer;

    public void run() {
        initialize();

        Thread main = new Thread(() -> {
            gameTimer = new Timer();

            while (running) {
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                render();
                if (gameTimer.hasTimeElapsed(1000.0F / frameRate)) {
                    update();
                }
            }
        });

        main.run();

        exit();
    }

    private void initialize() {
        lazyAssign(); // Allowing static instance to BankPortal class
        running = true;

        CONNECTION.showTables();
        CONNECTION.createTable("users", "uuid TEXT, userName TEXT, email TEXT, passwordHash TEXT");
        CONNECTION.showTables();
        CONNECTION.addNewUserToDatabase(new User("billbob", "billyboob@gmial.com", "sucker"));

        Runtime.getRuntime().addShutdownHook(new Thread(this::exit));
    }

    public void render() {

    }

    public void update() {
    }

    private void exit() {
        try {
            gracefullyClose();
        } catch (Exception exception) {
            exception.printStackTrace();
            forceClose();
        }
    }

    private void gracefullyClose() {
        running = false;
        System.out.println("grace");
    }

    private void forceClose() {
        System.out.println("force");
        System.exit(-1);
    }

    public void addUsers() {

    }

    public void updateFrameRate(float frameRate) {
        this.frameRate = frameRate;
    }

    private static BankPortal lazyAssign() {
        if (INSTANCE != null) return INSTANCE;

        return INSTANCE = new BankPortal();
    }

}
