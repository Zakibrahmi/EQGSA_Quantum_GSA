package org.um.feri.ears.problems.unconstrained;

import org.um.feri.ears.problems.DoubleProblem;


import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.*;

/*
http://infinity77.net/global_optimization/test_functions_nd_Y.html#go_benchmark.YaoLiu09
 */
public class YaoLiu9 extends DoubleProblem {

    public YaoLiu9() {
        super("YaoLiu9", 2, 1, 1, 0);
        lowerLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, -5.12));
        upperLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, 5.12));
    }

    @Override
    public double eval(double[] x) {
        double fitness = 0;
        for (int i = 0; i < numberOfDimensions; i++) {
            fitness += pow(x[i], 2.0) - 10.0 * cos(2 * PI * x[i]) + 10;
        }
        return abs(fitness);
    }
}