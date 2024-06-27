package org.algorithm.objective;

import org.algorithm.Route;
import org.algorithm.Solution;

public class MinDurationObjective implements IObjective{
    @Override
    public String getName() {
        return "MIN_DURATION_OBJECTIVE";
    }

    @Override
    public double evaluate(Solution solution) {
        double totalDuration = 0;
        Route[] routes = solution.routes;
        for (Route route : routes) {
            if (!route.isEmptyRoute()){
                totalDuration += route.totalDuration;
            }
        }
        return totalDuration;
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
