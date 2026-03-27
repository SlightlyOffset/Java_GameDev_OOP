package core.mechanics;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A timer that runs on a separate thread to track the player's time spent in a level.
 * It can be started, paused, resumed, reset, and stopped. The time is tracked in seconds.
 */
public class PlaytimeTimer implements Runnable {
    private AtomicInteger seconds = new AtomicInteger(0);
    private volatile boolean running = true;
    private volatile boolean paused = true;

    private Thread thread;

    /**
     * Starts the timer thread if it is not already running.
     */
    public void start() {
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this, "Playtime-Timer");
            thread.start();
        }
    }

    /**
     * The main execution logic for the timer thread.
     * It increments the second counter every second, provided the timer is not paused.
     */
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                if (!paused) {
                    seconds.incrementAndGet();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Pauses the timer, preventing the second counter from incrementing.
     */
    public void pause() {
        paused = true;
    }

    /**
     * Resumes the timer, allowing the second counter to increment again.
     */
    public void resume() {
        paused = false;
    }

    /**
     * Resets the timer's second counter back to zero.
     */
    public void reset() {
        seconds.set(0); //reset time for next level
    }

    /**
     * Stops the timer thread permanently.
     */
    public void stop() {
        running = false;
        if (thread != null) {
            thread.interrupt();
        }
    }

    /**
     * Returns the elapsed time formatted as a "MM:SS" string.
     * @return A formatted string representing the minutes and seconds.
     */
    public String getFormattedTime() {
        int total = seconds.get();
        int min = total / 60;
        int sec = total % 60;
        return String.format("%02d:%02d", min, sec);
    }
}
