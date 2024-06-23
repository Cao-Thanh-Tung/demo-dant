package org.algorithm.strategy.greedy;

import org.algorithm.*;
import org.algorithm.constraint.IConstraint;
import org.algorithm.distancetimematrix.IDistanceTimeMatrix;
import org.algorithm.strategy.IStrategy;

public class MinDistanceFitOrderAssignStrategy implements IStrategy{
    @Override
    public String getName() {
        return "MIN_DISTANCE_FIT_ORDER_ASSIGN_STRATEGY";
    }

    @Override
    public Solution createSolution(AlgorithmInput input, AlgorithmConfig config) {
        int ordersLength = input.getOrdersLength();
        boolean[] isOrderServed = new boolean[ordersLength];
        int vehiclesLength = input.getVehiclesLength();
        Solution solution = new Solution(input.getVehicles());
        int routeIndex;
        double minIncreasedDistance;
        double increasedDistance;
        int[] bestFeasiblePosition = new int[2];
        for (int i = 0; i < ordersLength; i++) {
            Order order = input.getOrder(i);
            routeIndex = -1;
            minIncreasedDistance = -1;
            for (int j = 0; j < vehiclesLength; j++) {
                Route route = solution.routes[j];
                int[] feasiblePosition = findFeasiblePosition(order, route, config.getConstraints());
                if (feasiblePosition != null) {
                    increasedDistance = calculateIncreasedDistance(route, order, feasiblePosition[0], feasiblePosition[1], input.getDistanceTimeMatrix());
                    if (minIncreasedDistance == -1 || increasedDistance < minIncreasedDistance){
                        minIncreasedDistance = increasedDistance;
                        routeIndex = j;
                        bestFeasiblePosition[0] = feasiblePosition[0];
                        bestFeasiblePosition[1] = feasiblePosition[1];
                    }
                    isOrderServed[i] = true;
                }
            }
            if (routeIndex != -1){
                solution.routes[routeIndex].addOrder(order, bestFeasiblePosition[0], bestFeasiblePosition[1], input.getDistanceTimeMatrix());
            }
        }
        for (int i = 0; i < ordersLength; i++) {
            if (!isOrderServed[i]) {
                solution.unServeAbleOrders.add(input.getOrder(i));
            }
        }
        solution.summary();
        return solution;
    }

    private int[] findFeasiblePosition(Order order, Route route, IConstraint[] constraints) {
        int routeLength = route.pointXES.size();
        for (int k = 1; k < routeLength; k++) {
            for (int l = k+1; l < routeLength+1; l++) {
                if (checkConstraints(constraints, order, k, l, route)) {
                    return new int[]{k, l};
                }
            }
        }
        return null;
    }

    private boolean checkConstraints(IConstraint[] constraints, Order order, int pickIndex, int deliveryIndex, Route route) {
        for (IConstraint constraint : constraints) {
            if (!constraint.check(order, pickIndex, deliveryIndex, route)){
                return false;
            }
        }
        return true;
    }

    private double calculateIncreasedDistance(Route route, Order order, int pickIndex, int deliveryIndex, IDistanceTimeMatrix distanceTimeMatrix){
        ActionPoint pickup = order.getPickup();
        ActionPoint delivery = order.getDelivery();
        double currentDistance = route.totalDistance;
        route.pointXES.add(pickIndex, pickup);
        route.pointXES.add(deliveryIndex, delivery);
        double predictDistance = route.accumulateDistances.get(pickIndex - 1);
        for (int i = pickIndex; i < route.pointXES.size(); i++) {
            ActionPoint point = route.pointXES.get(i);
            predictDistance += distanceTimeMatrix.getDistance(route.pointXES.get(i - 1).getPosition(), point.getPosition());
        }
        route.pointXES.remove(deliveryIndex);
        route.pointXES.remove(pickIndex);
        return predictDistance - currentDistance;
    }
}
