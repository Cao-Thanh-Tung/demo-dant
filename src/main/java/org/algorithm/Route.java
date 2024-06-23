package org.algorithm;
import org.algorithm.distancetimematrix.IDistanceTimeMatrix;
import java.util.ArrayList;
public class Route {

    public Vehicle vehicle;
    public double totalDistance;
    public double totalDuration;
    public double totalWeight;
    public int totalStopPoints;
    public ArrayList<ActionPoint> pointXES;
    public ArrayList<Order> orderXES;
    public ArrayList<Double> arrivalTimes;
    public ArrayList<Double> leaveTimes;
    public ArrayList<Double> accumulateDemands;
    public ArrayList<Double> accumulateDistances;
    public Route(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.totalDistance = 0.0;
        this.totalDuration = 0.0;
        this.totalWeight = 0.0;
        this.totalStopPoints = 0;
        this.pointXES = new ArrayList<ActionPoint>();
        pointXES.add(new ActionPoint("park", vehicle.park, vehicle.earliest, vehicle.latest, 0, 0));
        pointXES.add(new ActionPoint("park", vehicle.park, vehicle.earliest, vehicle.latest, 0, 0));
        this.orderXES = new ArrayList<Order>();
        this.arrivalTimes = new ArrayList<Double>(); this.arrivalTimes.add(vehicle.earliest); this.arrivalTimes.add(vehicle.earliest);
        this.leaveTimes = new ArrayList<Double>(); this.leaveTimes.add(vehicle.earliest); this.leaveTimes.add(vehicle.earliest);
        this.accumulateDemands = new ArrayList<Double>(); this.accumulateDemands.add(0.0); this.accumulateDemands.add(0.0);
        this.accumulateDistances = new ArrayList<Double>(); this.accumulateDistances.add(0.0); this.accumulateDistances.add(0.0);
    }
    public boolean isEmptyRoute() {
        return orderXES.isEmpty();
    }
    public void addOrder(Order order, int pickIndex, int deliveryIndex, IDistanceTimeMatrix distanceTimeMatrix) {
        ActionPoint pickup = order.getPickup();
        ActionPoint delivery = order.getDelivery();
        orderXES.add(order);
        pointXES.add(pickIndex, pickup);
        arrivalTimes.add(pickIndex, 0.0);
        leaveTimes.add(pickIndex, 0.0);
        accumulateDemands.add(pickIndex, 0.0);
        accumulateDistances.add(pickIndex, 0.0);
        pointXES.add(deliveryIndex, delivery);
        arrivalTimes.add(deliveryIndex, 0.0);
        leaveTimes.add(deliveryIndex, 0.0);
        accumulateDemands.add(deliveryIndex, 0.0);
        accumulateDistances.add(deliveryIndex, 0.0);
        totalStopPoints += 2;
        for (int i = pickIndex; i < pointXES.size(); i++) {
            ActionPoint point = pointXES.get(i);
            arrivalTimes.set(i,leaveTimes.get(i - 1) + distanceTimeMatrix.getDuration(pointXES.get(i - 1).getPosition(), point.getPosition()));
            leaveTimes.set(i,Math.max(arrivalTimes.get(i),point.getEarliest()) + point.getServiceTime() + point.getWaitingTime());
            accumulateDistances.set(i,accumulateDistances.get(i - 1) + distanceTimeMatrix.getDistance(pointXES.get(i - 1).getPosition(), point.getPosition()));
        }
        accumulateDemands.set(pickIndex, accumulateDemands.get(pickIndex - 1) + order.getDemand());
        for (int i = pickIndex+1; i < deliveryIndex; i++) {
            accumulateDemands.set(i, accumulateDemands.get(i) + order.getDemand());
        }
        accumulateDemands.set(deliveryIndex, accumulateDemands.get(deliveryIndex - 1) - order.getDemand());

        totalDistance = accumulateDistances.get(accumulateDistances.size() - 1);
        totalDuration = leaveTimes.get(leaveTimes.size() - 1) - vehicle.earliest;
        totalWeight = 0;
        for (Order orderX : orderXES) {
            totalWeight += orderX.getDemand();
        }
    }

    public void printRoute(){
        System.out.println("Route------------------------------------");
        System.out.println("totalDistance: " + this.totalDistance);
        System.out.println("totalDuration: " + this.totalDuration);
        System.out.println("totalWeight: " + this.totalWeight);
        System.out.println("totalStopPoints: " + this.totalStopPoints);
        System.out.println("pointXES: ");
        for (int i=0; i<pointXES.size(); i++) {
            System.out.println("ArrivalTime: "+ arrivalTimes.get(i));
            System.out.println(pointXES.get(i));
            System.out.println("LeaveTime: "+ leaveTimes.get(i));
        }
    }
}
