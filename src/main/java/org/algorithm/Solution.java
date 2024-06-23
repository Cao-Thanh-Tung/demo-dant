package org.algorithm;

import java.util.ArrayList;

public class Solution {
    public int numVehicleUsed;
    public double totalDistance; //km
    public int totalServeAbleOrder;
    public double totalDuration; //s
    public double totalDemand;
    public double earliestTime; //s
    public double latestTime; //s
    public Route[] routes;
    public ArrayList<Order> unServeAbleOrders;

    public Solution(Vehicle[] vehicles) {
        this.numVehicleUsed = 0;
        this.totalDistance = 0.0;
        this.totalServeAbleOrder = 0;
        this.totalDuration = 0.0;
        this.totalDemand = 0.0;
        this.earliestTime = 0.0;
        this.latestTime = 0.0;
        this.routes = new Route[vehicles.length];
        for (int i = 0; i < vehicles.length; i++) {
            this.routes[i] = new Route(vehicles[i]);
        }
        this.unServeAbleOrders = new ArrayList<>();
    }
    public void summary(){
        earliestTime = Double.MAX_VALUE;
        latestTime = Double.MIN_VALUE;
        totalServeAbleOrder = 0;
        numVehicleUsed = 0;
        totalDistance = 0;
        totalDuration = 0;
        totalDemand = 0;
        for (int i = 0; i < routes.length; i++) {
            if (!routes[i].isEmptyRoute()) {
                numVehicleUsed++;
                totalDistance += routes[i].totalDistance;
                totalDuration += routes[i].totalDuration;
                totalDemand += routes[i].totalWeight;
                totalServeAbleOrder += routes[i].orderXES.size();
                earliestTime = Math.min(earliestTime, routes[i].arrivalTimes.get(1));
                latestTime = Math.max(latestTime, routes[i].leaveTimes.get(routes[i].leaveTimes.size() - 1));
            }
        }
    }

    public void printSolution() {
        System.out.println("Solution------------------------------------");
        System.out.println("numVehicleUsed: " + this.numVehicleUsed);
        System.out.println("totalDistance: " + this.totalDistance);
        System.out.println("totalDuration: " + this.totalDuration);
        System.out.println("totalServeAbleOrder: " + this.totalServeAbleOrder);
        System.out.println("earliestTime: " + this.earliestTime);
        System.out.println("latestTime: " + this.latestTime);
        System.out.println("routes: ");
        for (Route route : this.routes) {
            if (!route.isEmptyRoute()) route.printRoute();
        }

    }
}
