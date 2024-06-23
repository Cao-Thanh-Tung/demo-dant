package com.example.demo.DTO;

import org.algorithm.Order;
import org.algorithm.Vehicle;

public class Input {
    public Vehicle[] vehicles;
    public Order[] orders;
    public String distanceType;
    public int numShuffle;
    public String[] constraints;
    public String[] objectives;
    public String strategy;

    public Input(Vehicle[] vehicles, Order[] orders, String distanceType, String strategy, int numShuffle, String[] constraints, String[] objectives) {
        this.vehicles = vehicles;
        this.orders = orders;
        this.distanceType = distanceType;
        this.strategy = strategy;
        this.numShuffle = numShuffle;
        this.constraints = constraints;
        this.objectives = objectives;
    }
}
