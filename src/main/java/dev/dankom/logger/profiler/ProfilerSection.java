package dev.dankom.logger.profiler;

import java.util.Date;

public class ProfilerSection {
    private final String name;
    private Date startTime;
    private Date endTime;
    private boolean crashed;

    public ProfilerSection(String name) {
        this.name = name;
    }

    public void start() {
        this.startTime = new Date();
    }

    public void stop() {
        this.endTime = new Date();
    }

    public boolean isStarted() {
        return startTime != null;
    }

    public boolean isStopped() {
        return endTime != null;
    }

    public boolean isRunning() {
        return isStarted() && !isStopped();
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return startTime;
    }

    public Date getTimePassed() {
        return new Date(getEndTime().getTime() - getStartTime().getTime());
    }

    public String getName() {
        return name;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void crash() {
        this.crashed = true;
        stop();
    }

    @Override
    public String toString() {
        return "ProfileSection{" +
                "name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", crashed=" + crashed +
                '}';
    }
}
