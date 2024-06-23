package org.algorithm.strategy.greedy;

import org.algorithm.*;
import org.algorithm.constraint.IConstraint;
import org.algorithm.strategy.IStrategy;

public class RoundRobinOrderAssignStrategy implements IStrategy {
    @Override
    public String getName() {
        return "ROUND_ROBIN_ORDER_ASSIGN_STRATEGY";
    }

    @Override
    public Solution createSolution(AlgorithmInput input, AlgorithmConfig config) {
        int ordersLength = input.getOrdersLength();
        boolean[] isOrderServed = new boolean[ordersLength];
        int vehiclesLength = input.getVehiclesLength();
        Solution solution = new Solution(input.getVehicles());
        int index = 0;
        for (int i = 0; i < ordersLength; i++) {
            Order order = input.getOrder(i);
            for (int j = 0; j < vehiclesLength; j++) {
                Route route = solution.routes[index];
                int[] feasiblePosition = findFeasiblePosition(order, route, config.getConstraints());
                if (feasiblePosition != null) {
                    route.addOrder(order, feasiblePosition[0], feasiblePosition[1], input.getDistanceTimeMatrix());
                    isOrderServed[i] = true;
                    index = (index + 1)%vehiclesLength;
                    break;
                }
                index = (index + 1)%vehiclesLength;
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
}
