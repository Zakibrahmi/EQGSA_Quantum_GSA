package org.um.feri.ears.problems.unconstrained;

import org.um.feri.ears.problems.DoubleProblem;


import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.*;

/*
http://benchmarkfcns.xyz/benchmarkfcns/ackleyn4fcn.html
 */
public class Ackley4 extends DoubleProblem {

    public Ackley4() {
        super("Ackley4", 2, 2, 1, 0);
        lowerLimit = new ArrayList<Double>(Collections.nCopies(numberOfDimensions, -35.0));
        upperLimit = new ArrayList<Double>(Collections.nCopies(numberOfDimensions, 35.0));
        name = "Ackley4"; // also known as Modified Ackley Function

        decisionSpaceOptima[0] = new double[]{-1.51, -0.755};
        decisionSpaceOptima[1] = new double[]{1.51, -0.755};
        objectiveSpaceOptima[0] = -4.590101633799122;
    }

    @Override
    public double eval(double[] x) {
        double fitness = 0;
        for (int i = 0; i < numberOfDimensions - 1; i++) {
            fitness += exp(-0.2) * sqrt(pow(x[i], 2) + pow(x[i + 1], 2))
                    + 3.0 * (cos(2 * x[i]) + sin(2 * x[i + 1]));
        }
        return fitness;
    }
}