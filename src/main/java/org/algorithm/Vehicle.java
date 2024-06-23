package org.algorithm;

public class Vehicle {
    static long _id = 0;
    long id;
    double capacity;
    Position park;
    double earliest;
    double latest;

    public Vehicle(double capacity, Position park, double earliest, double latest) {
        this.id = _id; _id++;
        this.capacity = capacity;
        this.park = park;
        this.earliest = earliest;
        this.latest = latest;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", park=" + park +
                ", earliest=" + earliest +
                ", latest=" + latest +
                '}';
    }

    public long getId() {
        return id;
    }

    public double getCapacity() {
        return capacity;
    }

    public Position getPark() {
        return park;
    }

    public double getEarliest() {
        return earliest;
    }

    public double getLatest() {
        return latest;
    }
}
