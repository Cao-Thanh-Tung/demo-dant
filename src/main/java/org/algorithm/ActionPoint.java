package org.algorithm;

public class ActionPoint {
    private static long _id = 0;
    private long id;
    private String action;
    private Position position;
    private double earliest;
    private double latest;
    private double serviceTime;
    private double waitingTime;

    public ActionPoint(String action, Position position, double earliest, double latest, double serviceTime, double waitingTime) {
        this.id = _id; _id++;
        this.action = action;
        this.position = position;
        this.earliest = earliest;
        this.latest = latest;
        this.serviceTime = serviceTime;
        this.waitingTime = waitingTime;
    }

    public long getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public Position getPosition() {
        return position;
    }

    public double getEarliest() {
        return earliest;
    }

    public double getLatest() {
        return latest;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    @Override
    public String toString() {
        return "ActionPoint{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", position=" + position +
                ", earliest=" + earliest +
                ", latest=" + latest +
                ", serviceTime=" + serviceTime +
                ", waitingTime=" + waitingTime +
                '}';
    }
}
