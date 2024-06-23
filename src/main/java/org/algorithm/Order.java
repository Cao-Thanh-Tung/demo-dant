package org.algorithm;

public class Order {
    static long _id = 0;

    private long id;
    private double demand;
    private ActionPoint pickup;
    private ActionPoint delivery;

    public Order(double demand, ActionPoint pickup, ActionPoint delivery) {
        this.id = _id; _id++;
        this.demand = demand;
        this.pickup = pickup;
        this.delivery = delivery;
    }

    public long getId() {
        return id;
    }

    public double getDemand() {
        return demand;
    }

    public ActionPoint getPickup() {
        return pickup;
    }

    public ActionPoint getDelivery() {
        return delivery;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", demand=" + demand +
                ", \n   pickup=" + pickup +
                ", \n   delivery=" + delivery +
                '}';
    }
}
