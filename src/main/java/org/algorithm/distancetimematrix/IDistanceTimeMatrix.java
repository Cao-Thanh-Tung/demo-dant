package org.algorithm.distancetimematrix;

import org.algorithm.Position;

public interface IDistanceTimeMatrix {
    double getDistance(Position from, Position to);
    double getDuration(Position from, Position to);
}
