package org.algorithm.constraint;

import org.algorithm.ActionPoint;
import org.algorithm.Order;
import org.algorithm.Route;
import org.algorithm.distancetimematrix.IDistanceTimeMatrix;

import java.util.ArrayList;

public class TimeWindowConstraint implements IConstraint {
    private IDistanceTimeMatrix distanceTimeMatrix;
    public TimeWindowConstraint(IDistanceTimeMatrix distanceTimeMatrix) {
        this.distanceTimeMatrix = distanceTimeMatrix;
    }

    @Override
    public String getName() {
        return "TIME_WINDOW_CONSTRAINT";
    }

    @Override
    public boolean check(Order order, int pickIndex, int deliveryIndex, Route route) {
        ArrayList<ActionPoint> points = route.pointXES;
        points.add(pickIndex, order.getPickup());
        points.add(deliveryIndex, order.getDelivery());
        boolean isFeasible = true;
        double curArrivalTime;
        double curLeaveTime = route.leaveTimes.get(pickIndex - 1);
        for (int i = pickIndex; i < points.size(); i++) {
            ActionPoint point = points.get(i);
            curArrivalTime = curLeaveTime + distanceTimeMatrix.getDuration(points.get(i - 1).getPosition(), point.getPosition());
            curLeaveTime = Math.max(curArrivalTime, point.getEarliest()) + point.getServiceTime() + point.getWaitingTime();
            if (curLeaveTime > point.getLatest()) {
                isFeasible = false;
                break;
            }
        }
        points.remove(deliveryIndex);
        points.remove(pickIndex);
        return isFeasible;
    }
}
