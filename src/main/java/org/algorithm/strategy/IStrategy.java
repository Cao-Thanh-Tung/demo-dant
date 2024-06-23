package org.algorithm.strategy;

import org.algorithm.AlgorithmConfig;
import org.algorithm.AlgorithmInput;
import org.algorithm.Solution;

public interface IStrategy {
    String getName();
    Solution createSolution(AlgorithmInput input, AlgorithmConfig config);
}
