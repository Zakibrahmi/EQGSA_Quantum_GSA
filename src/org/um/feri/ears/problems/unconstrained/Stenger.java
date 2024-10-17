package org.um.feri.ears.problems.unconstrained;

import org.um.feri.ears.problems.DoubleProblem;


import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.pow;

/*
https://www.al-roomi.org/benchmarks/unconstrained/2-dimensions/103-stenger-s-function
 */
public class Stenger extends DoubleProblem {

    public Stenger() {
        super("Stenger", 2, 2, 1, 0);
        lowerLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, -1.0));
        upperLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, 4.0));

        decisionSpaceOptima[0] = new double[]{0.0, 0.0};
        decisionSpaceOptima[1] = new double[]{1.695415196279268, 0.718608171943623};
    }

    @Override
    public double eval(double[] x) {
        return pow(pow(x[0], 2) - 4 * x[1], 2) + pow(pow(x[1], 2) - 2 * x[0] + 4 * x[1], 2);
    }
}