package org.algorithm.constraint;

import org.algorithm.Order;
import org.algorithm.Route;

public interface IConstraint {
    String getName();
    boolean check(Order order, int pickIndex, int deliveryIndex, Route route);
}
