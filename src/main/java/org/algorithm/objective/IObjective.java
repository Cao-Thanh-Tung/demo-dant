package org.algorithm.objective;

import org.algorithm.Solution;

public interface IObjective {
    String getName();
    double evaluate(Solution solution);
    int compare(double current, double candidate);
}
