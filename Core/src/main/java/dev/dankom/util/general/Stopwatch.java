package dev.dankom.util.general;

import java.util.Date;

public class Stopwatch {

    private long startTime;
    private long endTime;
    private boolean isEnded;
    private boolean isStarted;

    public void start() {
        this.startTime = new Date().getTime();
        isStarted = true;
        isEnded = false;
    }

    public void stop() {
        this.endTime = new Date().getTime();
        isEnded = true;
        isStarted = false;
    }

    public long getStartTime() {
        return startTime;
    }

    public Date getStartDate() {
        return new Date(getStartTime());
    }

    public long getEndTime() throws Exception {
        if (!isEnded()) {
            throw new Exception("Attempted to get end time before timer ended!");
        }
        return endTime;
    }

    public Date getEndDate() throws Exception {
        return new Date(getEndTime());
    }

    public boolean isEnded() {
        return isEnded;
    }

    public boolean isStarted() {
        return isStarted;
    }
}
