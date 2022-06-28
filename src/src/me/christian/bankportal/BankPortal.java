package me.christian.bankportal;

import me.christian.bankportal.utility.Timer;

public class BankPortal {

    public static BankPortal INSTANCE = lazyAssign();

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

        Runtime.getRuntime().addShutdownHook(new Thread(this::exit));
    }

    private void update() {
        System.out.println("update");
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

    public void updateFrameRate(float frameRate) {
        this.frameRate = frameRate;
    }

    private static BankPortal lazyAssign() {
        if (INSTANCE != null) return INSTANCE;

        return INSTANCE = new BankPortal();
    }

}
