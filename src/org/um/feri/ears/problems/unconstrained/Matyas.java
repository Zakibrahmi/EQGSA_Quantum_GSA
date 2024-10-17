package org.um.feri.ears.problems.unconstrained;

import org.um.feri.ears.problems.DoubleProblem;


import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.pow;

/*
https://www.sfu.ca/~ssurjano/matya.html
http://benchmarkfcns.xyz/benchmarkfcns/matyasfcn.html
 */

public class Matyas extends DoubleProblem {

    public Matyas() {
        super("Matyas", 2, 1, 1, 0);
        lowerLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, -10.0));
        upperLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, 10.0));
    }

    @Override
    public double eval(double[] x) {
        return 0.26 * (pow(x[0], 2) + pow(x[1], 2)) - 0.48 * x[0] * x[1];
    }
}
