package org.algorithm.distancetimematrix;

import org.algorithm.Position;

public class DistanceTimeHaversineMatrix implements IDistanceTimeMatrix{
    @Override
    public double getDistance(Position from, Position to) {
        double d = Math.pow(Math.sin((to.getLat() - from.getLat()) / 2), 2) +
                Math.cos(from.getLat()) * Math.cos(to.getLat()) * Math.pow(Math.sin((to.getLon() - from.getLon()) / 2), 2);
        return 2 * 6371 * Math.asin(Math.sqrt(d));
    }

    @Override
    public double getDuration(Position from, Position to) {
        return getDistance(from, to) /50 * 3600;
    }
}
