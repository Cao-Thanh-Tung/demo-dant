package org.algorithm.distancetimematrix;

import org.algorithm.Position;

public class DistanceTimeVincentyMatrix implements IDistanceTimeMatrix{
    private static final double R = 6371e3;


    @Override
    public double getDistance(Position from, Position to) {
        double x1 = Math.toRadians(from.getLat());
        double x2 = Math.toRadians(to.getLat());
        double dy = Math.toRadians(to.getLon() - from.getLon());
        return Math.acos(Math.sin(x1) * Math.sin(x2) + Math.cos(x1) * Math.cos(x2) * Math.cos(dy)) * R;
    }

    @Override
    public double getDuration(Position from, Position to) {
        return getDistance(from, to) /50 * 3600;
    }
}
