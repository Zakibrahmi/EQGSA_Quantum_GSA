package org.um.feri.ears.problems.unconstrained;

import org.um.feri.ears.problems.DoubleProblem;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.lang.Math.pow;

/*
http://infinity77.net/global_optimization/test_functions_nd_M.html#go_benchmark.Mishra01
 */
public class Mishra1 extends DoubleProblem {

    public Mishra1() {
        super("Mishra1", 2, 1, 1, 0);
        lowerLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, 0.0));
        upperLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, 1.0));

        Arrays.fill(decisionSpaceOptima[0], 1.0);
        objectiveSpaceOptima[0] = 2.0;
    }

    @Override
    public double eval(double[] x) {
        double sum = 0;
        for (int i = 0; i < numberOfDimensions - 1; i++) {
            sum += x[i];
        }
        sum = numberOfDimensions - sum;
        return pow(1 + sum, sum);
    }
}