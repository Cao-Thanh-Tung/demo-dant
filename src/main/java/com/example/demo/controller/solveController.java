package com.example.demo.controller;

import com.example.demo.DTO.Input;
import org.algorithm.AlgorithmConfig;
import org.algorithm.AlgorithmInput;
import org.algorithm.Solution;
import org.algorithm.constraint.CapacityConstraint;
import org.algorithm.constraint.IConstraint;
import org.algorithm.constraint.TimeWindowConstraint;
import org.algorithm.distancetimematrix.DistanceTimeHaversineMatrix;
import org.algorithm.distancetimematrix.DistanceTimeOsmMatrix;
import org.algorithm.distancetimematrix.DistanceTimeVincentyMatrix;
import org.algorithm.distancetimematrix.IDistanceTimeMatrix;
import org.algorithm.objective.IObjective;
import org.algorithm.objective.MaxServeAbleOrderObjective;
import org.algorithm.objective.MinDistanceObjective;
import org.algorithm.objective.MinDurationObjective;
import org.algorithm.strategy.IStrategy;
import org.algorithm.strategy.greedy.FirstFitOrderAssignStrategy;
import org.algorithm.strategy.greedy.MinDistanceFitOrderAssignStrategy;
import org.algorithm.strategy.greedy.RoundRobinOrderAssignStrategy;
import org.algorithm.strategy.heuristic.ShuffleFirstFitStrategy;
import org.algorithm.strategy.heuristic.ShuffleMinDistanceFitStrategy;
import org.algorithm.strategy.heuristic.ShuffleRoundRobinStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class solveController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/solve")
    public ResponseEntity<Solution> solve(@RequestBody Input input) {
        String[] constraintNames = input.constraints;
        String[] objectiveNames = input.objectives;
        String distanceType = input.distanceType;
        IDistanceTimeMatrix distanceTimeMatrix;
        if (distanceType.equals("OSM")) {
            distanceTimeMatrix = new DistanceTimeOsmMatrix();
        } else if(distanceType.equals("HAVERSINE")) {
            distanceTimeMatrix = new DistanceTimeHaversineMatrix();
        }else if(distanceType.equals("VINCENTY")) {
            distanceTimeMatrix = new DistanceTimeVincentyMatrix();
        }else {
            distanceTimeMatrix = null;
        }

        IConstraint[] constraints = new IConstraint[constraintNames.length];
        for (int i = 0; i < constraintNames.length; i++){
            if (constraintNames[i].equals("TIME_WINDOW_CONSTRAINT")) {
                constraints[i] = new TimeWindowConstraint(distanceTimeMatrix);
            } else if (constraintNames[i].equals("CAPACITY_CONSTRAINT")) {
                constraints[i] = new CapacityConstraint();
            }
        }

        IObjective[] objectives = new IObjective[objectiveNames.length];
        for (int i = 0; i < objectiveNames.length; i++) {
            if (objectiveNames[i].equals("MAX_SERVE_ABLE_ORDER_OBJECTIVE")) {
                objectives[i] = new MaxServeAbleOrderObjective();
            } else if (objectiveNames[i].equals("MIN_DISTANCE_OBJECTIVE")) {
                objectives[i] = new MinDistanceObjective();
            } else if (objectiveNames[i].equals("MIN_DURATION_OBJECTIVE")) {
                objectives[i] = new MinDurationObjective();
            }
        }

        IStrategy strategy = getiStrategy(input);

        AlgorithmInput algorithmInput = new AlgorithmInput(input.vehicles, input.orders, distanceTimeMatrix);
        AlgorithmConfig algorithmConfig = new AlgorithmConfig();
        algorithmConfig.setNumShuffle(input.numShuffle);
        algorithmConfig.setConstraints(constraints);
        algorithmConfig.setObjectives(objectives);

        Solution solution = strategy.createSolution(algorithmInput, algorithmConfig);
//        solution.printSolution();

        return ResponseEntity.ok(solution);
    }

    private static IStrategy getiStrategy(Input input) {
        IStrategy strategy;
        if (input.strategy.equals("FIRST_FIT_ORDER_ASSIGN_STRATEGY")) {
            strategy = new FirstFitOrderAssignStrategy();
        } else if (input.strategy.equals("MIN_DISTANCE_FIT_ORDER_ASSIGN_STRATEGY")) {
            strategy = new MinDistanceFitOrderAssignStrategy();
        } else if (input.strategy.equals("ROUND_ROBIN_ORDER_ASSIGN_STRATEGY")) {
            strategy = new RoundRobinOrderAssignStrategy();
        } else if (input.strategy.equals("SHUFFLE_FIRST_FIT_STRATEGY")) {
            strategy = new ShuffleFirstFitStrategy();
        } else if(input.strategy.equals("SHUFFLE_MIN_DISTANCE_FIT_STRATEGY")) {
            strategy = new ShuffleMinDistanceFitStrategy();
        }else if(input.strategy.equals("SHUFFLE_ROUND_ROBIN_STRATEGY")) {
            strategy = new ShuffleRoundRobinStrategy();
        }else{
            strategy = null;
        }
        return strategy;
    }
}
