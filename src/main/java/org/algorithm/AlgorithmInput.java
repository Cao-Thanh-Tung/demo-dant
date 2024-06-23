package org.algorithm;

import org.algorithm.distancetimematrix.IDistanceTimeMatrix;

public class AlgorithmInput {
    private Vehicle[] vehicles;
    private Order[] orders;
    private IDistanceTimeMatrix distanceTimeMatrix;
    public AlgorithmInput(Vehicle[] vehicles, Order[] orders, IDistanceTimeMatrix distanceTimeMatrix) {
        this.vehicles = vehicles;
        this.orders = orders;
        this.distanceTimeMatrix = distanceTimeMatrix;
    }
    public int getOrdersLength() {
        return orders.length;
    }
    public int getVehiclesLength() {
        return vehicles.length;
    }
    public Vehicle getVehicle(int index) {
        return vehicles[index];
    }
    public Order getOrder(int index) {
        return orders[index];
    }
    public Vehicle[] getVehicles() {
        return vehicles.clone();
    }
    public Order[] getOrders() {
        return orders.clone();
    }
    public IDistanceTimeMatrix getDistanceTimeMatrix() {
        return distanceTimeMatrix;
    }
    public void print() {
        System.out.println("AlgorithmInput{");
        System.out.println("vehicles=[");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
        System.out.println("]");
        System.out.println("orders=[");
        for (Order order : orders) {
            System.out.println(order);
        }
        System.out.println("]");
        System.out.println("}");
    }
}
