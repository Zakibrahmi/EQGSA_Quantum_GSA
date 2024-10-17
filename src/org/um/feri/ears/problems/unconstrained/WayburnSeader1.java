package org.um.feri.ears.problems.unconstrained;

import org.um.feri.ears.problems.DoubleProblem;


import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.pow;

/*
http://infinity77.net/global_optimization/test_functions_nd_W.html#go_benchmark.WayburnSeader01
https://www.al-roomi.org/benchmarks/unconstrained/2-dimensions/277-wayburn-seader-s-function-no-01
 */
public class WayburnSeader1 extends DoubleProblem {

    public WayburnSeader1() {
        super("WayburnSeader1", 2, 2, 1, 0);
        lowerLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, -5.0));
        upperLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, 5.0));

        decisionSpaceOptima[0] = new double[]{1.0, 2.0};
        decisionSpaceOptima[1] = new double[]{1.596804153876933, 0.806391692246134};
    }

    @Override
    public double eval(double[] x) {
        return pow(pow(x[0], 6) + pow(x[1], 4) - 17, 2) + pow(2 * x[0] + x[1] - 4, 2);
    }
}