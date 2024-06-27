package org.algorithm;

import org.algorithm.constraint.IConstraint;
import org.algorithm.objective.IObjective;

public class AlgorithmConfig {
    private int numShuffle;
    private IConstraint[] constraints;
    private IObjective[] objectives;

    public int getNumShuffle() {
        return numShuffle;
    }

    public void setNumShuffle(int numShuffle) {
        this.numShuffle = numShuffle;
    }

    public IConstraint[] getConstraints() {
        return constraints;
    }

    public void setConstraints(IConstraint[] constraints) {
        this.constraints = constraints;
    }

    public IObjective[] getObjectives() {
        return objectives;
    }

    public void setObjectives(IObjective[] objectives) {
        this.objectives = objectives;
    }
}
