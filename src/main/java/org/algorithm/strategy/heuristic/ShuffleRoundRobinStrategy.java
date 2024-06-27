package org.algorithm.strategy.heuristic;

import org.algorithm.*;
import org.algorithm.objective.IObjective;
import org.algorithm.strategy.IStrategy;
import org.algorithm.strategy.greedy.RoundRobinOrderAssignStrategy;

import java.util.Random;

public class ShuffleRoundRobinStrategy extends HeuristicStrategy implements IStrategy {
    @Override
    public String getName() {
        return "SHUFFLE_ROUND_ROBIN_STRATEGY";
    }

    @Override
    public Solution createSolution(AlgorithmInput input, AlgorithmConfig config) {
        long startTime = System.currentTimeMillis();
        Vehicle[]vehicles  = input.getVehicles();
        Order[] orders = input.getOrders();
        RoundRobinOrderAssignStrategy roundRobinOrderAssignStrategy = new RoundRobinOrderAssignStrategy();
        Solution solution = roundRobinOrderAssignStrategy.createSolution(new AlgorithmInput(vehicles, orders, input.getDistanceTimeMatrix()), config);
        IObjective[] objectives = config.getObjectives();
        for (int i = 0; i <config.getNumShuffle(); i++) {
            shuffleOrderArray(orders);
            shuffleVehicleArray(vehicles);
            Solution solution1 = roundRobinOrderAssignStrategy.createSolution(new AlgorithmInput(vehicles, orders, input.getDistanceTimeMatrix()), config);
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

    private void shuffleVehicleArray(Vehicle[] array) {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Vehicle a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }
}
