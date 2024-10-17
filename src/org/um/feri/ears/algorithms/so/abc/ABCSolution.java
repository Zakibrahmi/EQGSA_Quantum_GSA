package org.um.feri.ears.algorithms.so.abc;

import org.um.feri.ears.problems.NumberSolution;

public class ABCSolution extends NumberSolution<Double> {

    public int trials = 0;
    private double prob;

    public double getProb() {
        return prob;
    }

    public void setProb(double prob) {
        this.prob = prob;
    }

    public ABCSolution(NumberSolution<Double> s) {
        super(s);
    }

    public ABCSolution(ABCSolution s) {
        super(s);
        this.prob = s.prob;
    }

    public double getABCEval() {
        if (getEval() >= 0)
            return 1.0 / (1.0 + getEval());

        return 1.0 + Math.abs(getEval());
    }
}
