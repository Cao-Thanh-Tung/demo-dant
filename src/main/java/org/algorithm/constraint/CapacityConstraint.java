package org.algorithm.constraint;

import org.algorithm.ActionPoint;
import org.algorithm.Order;
import org.algorithm.Route;

import java.util.ArrayList;

public class CapacityConstraint implements IConstraint {
    @Override
    public String getName() {
        return "CAPACITY_CONSTRAINT";
    }

    @Override
    public boolean check(Order order, int pickIndex, int deliveryIndex, Route route) {
        ArrayList<ActionPoint> points = route.pointXES;
        points.add(pickIndex, order.getPickup());
        points.add(deliveryIndex, order.getDelivery());
        boolean isFeasible = true;
        for (int i = pickIndex-1; i < deliveryIndex; i++) {
            if (route.accumulateDemands.get(i) + order.getDemand() > route.vehicle.getCapacity()) {
                isFeasible = false;
                break;
            }
        }
        points.remove(deliveryIndex);
        points.remove(pickIndex);
        return isFeasible;
    }
}
