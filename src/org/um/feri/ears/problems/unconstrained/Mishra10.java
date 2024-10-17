package org.um.feri.ears.problems.unconstrained;

import org.um.feri.ears.problems.DoubleProblem;


import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.pow;

/*
http://infinity77.net/global_optimization/test_functions_nd_M.html#go_benchmark.Mishra07
https://www.al-roomi.org/benchmarks/unconstrained/2-dimensions/152-mishra-s-function-no-10a-or-seqp-function-no-1
 */
public class Mishra10 extends DoubleProblem {

    public Mishra10() {
        super("Mishra10", 2, 2, 1, 0);
        lowerLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, -10.0));
        upperLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, 10.0));

        decisionSpaceOptima[0] = new double[]{0.0, 0.0};
        decisionSpaceOptima[1] = new double[]{2.0, 2.0};
    }

    @Override
    public double eval(double[] x) {
        return pow((x[0] + x[1]) - (x[0] * x[1]), 2);
    }
}