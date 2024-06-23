package org.algorithm.strategy.heuristic;

import org.algorithm.AlgorithmConfig;
import org.algorithm.AlgorithmInput;
import org.algorithm.Solution;
import org.algorithm.objective.IObjective;
import org.algorithm.strategy.IStrategy;

public abstract class HeuristicStrategy {
    public static Solution getBetterSolution(Solution solution1, Solution solution2, IObjective[] objectives){
        for (IObjective objective : objectives) {
            int compare = objective.compare(objective.evaluate(solution1), objective.evaluate(solution2));
            if (compare == 1){
                return solution1;
            }else if(compare == -1){
                return solution2;
            }
        }
        return solution1;
    }
}
