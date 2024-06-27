package org.algorithm.objective;

import org.algorithm.Route;
import org.algorithm.Solution;
import org.algorithm.distancetimematrix.IDistanceTimeMatrix;

public class MinDistanceObjective implements IObjective{

    @Override
    public String getName() {
        return "MIN_DISTANCE_OBJECTIVE";
    }

    @Override
    public double evaluate(Solution solution) {
        double totalDistance = 0;
        Route[] routes = solution.routes;
        for (Route route : routes) {
            if (!route.isEmptyRoute()){
                totalDistance += route.totalDistance;
            }
        }
        return totalDistance;
    }

    @Override
    public int compare(double current, double candidate) {
        if (current < candidate) {
            return 1;
        }else if(current > candidate){
            return -1;
        }else{
            return 0;
        }
    }
}
