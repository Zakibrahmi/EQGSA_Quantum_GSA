package org.um.feri.ears.problems.unconstrained;

import org.um.feri.ears.problems.DoubleProblem;


import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.*;

/*
http://benchmarkfcns.xyz/benchmarkfcns/xinsheyangn3fcn.html
http://infinity77.net/global_optimization/test_functions_nd_X.html#go_benchmark.XinSheYang03
 */
public class XinSheYang3 extends DoubleProblem {

    public XinSheYang3() {
        super("XinSheYang3", 2, 1, 1, 0);
        lowerLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, -20.0));
        upperLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, 20.0));
        objectiveSpaceOptima[0] = -1.0;
    }

    @Override
    public double eval(double[] x) {
        double beta = 15.0;
        int m = 5; //3
        double sum1 = 0, sum2 = 0, prod = 1.0;
        for (int i = 0; i < numberOfDimensions; i++) {
            sum1 += pow(x[i] / beta, 2 * m);
            sum2 += pow(x[i], 2);
            prod *= pow(cos(x[i]), 2);
        }
        return exp(-sum1) - (2 * exp(-sum2)) * prod;
    }
}