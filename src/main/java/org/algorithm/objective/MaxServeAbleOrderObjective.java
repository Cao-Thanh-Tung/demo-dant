package org.algorithm.objective;

import org.algorithm.Route;
import org.algorithm.Solution;

public class MaxServeAbleOrderObjective implements IObjective{
    @Override
    public String getName() {
        return "MAX_SERVE_ABLE_ORDER_OBJECTIVE";
    }

    @Override
    public double evaluate(Solution solution) {
        Route[] routes = solution.routes;
        double totalServeAbleOrder = 0;
        for (Route route : routes) {
            if (!route.isEmptyRoute()){
                totalServeAbleOrder += route.orderXES.size();
            }
        }
        return totalServeAbleOrder;
    }

    @Override
    public int compare(double current, double candidate) {
        if (current == candidate) return 0;
        else if (current < candidate) {
            return -1;
        }else{
            return candidate > current ? 1 : 0;
        }
    }
}
