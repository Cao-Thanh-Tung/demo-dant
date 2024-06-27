package org.algorithm.strategy.heuristic;

import org.algorithm.*;
import org.algorithm.objective.IObjective;
import org.algorithm.strategy.greedy.FirstFitOrderAssignStrategy;
import org.algorithm.strategy.IStrategy;
import org.algorithm.strategy.greedy.MinDistanceFitOrderAssignStrategy;

import java.util.Random;

public class ShuffleFirstFitStrategy extends HeuristicStrategy implements IStrategy {
    @Override
    public String getName() {
        return "SHUFFLE_FIRST_FIT_STRATEGY";
    }

    @Override
    public Solution createSolution(AlgorithmInput input, AlgorithmConfig config) {
        long startTime = System.currentTimeMillis();
        Vehicle[] vehicles = input.getVehicles();
        Order[] orders = input.getOrders();
        MinDistanceFitOrderAssignStrategy minDistanceFitOrderAssignStrategy = new MinDistanceFitOrderAssignStrategy();
        Solution solution = minDistanceFitOrderAssignStrategy.createSolution(new AlgorithmInput(vehicles, orders, input.getDistanceTimeMatrix()), config);
        IObjective[] objectives = config.getObjectives();
        for (int i = 0; i < config.getNumShuffle(); i++) {
            shuffleOrderArray(orders);
            Solution solution1 = minDistanceFitOrderAssignStrategy.createSolution(new AlgorithmInput(vehicles, orders, input.getDistanceTimeMatrix()), config);
            solution = getBetterSolution(solution, solution1, objectives);
        }
        return solution;
    }
    public static void shuffleOrderArray(Order[] array) {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Order a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }
}
