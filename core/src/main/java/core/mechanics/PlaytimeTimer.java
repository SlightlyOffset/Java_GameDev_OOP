package core.mechanics;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

//Thread Time
public class PlaytimeTimer implements Runnable {
    private final AtomicInteger seconds = new AtomicInteger(0);
    private volatile boolean running = true;
    private volatile boolean paused = true;

    private Thread thread;

    public void start() {
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this, "Playtime-Timer");
            thread.start();
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                sleep(1000);
                if (!paused) {
                    seconds.incrementAndGet();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public void pause() {
        paused = true;
    }
    public void resume() {
        paused = false;
    }
    public void reset() {
        seconds.set(0); //reset time for next level
    }
    public void stop() {
        running = false;
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
            try {
                thread.join(2000); // Wait up to 2 seconds for thread to finish
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public String getFormattedTime() {
        int total = seconds.get();
        int min = total / 60;
        int sec = total % 60;
        return String.format("%02d:%02d", min, sec);
    }
}
