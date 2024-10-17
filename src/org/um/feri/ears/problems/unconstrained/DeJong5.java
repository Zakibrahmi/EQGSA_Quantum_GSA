package org.um.feri.ears.problems.unconstrained;

import org.um.feri.ears.problems.DoubleProblem;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.lang.Math.pow;

/*
https://www.sfu.ca/~ssurjano/dejong5.html
 */

public class DeJong5 extends DoubleProblem {

    private static final double[][] a = {
            {-32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32},
            {-32, -32, -32, -32, -32, -16, -16, -16, -16, -16, 0, 0, 0, 0, 0, 16, 16, 16, 16, 16, 32, 32, 32, 32, 32}
    };


    public DeJong5() {
        super("DeJong5", 2, 1, 1, 0);
        lowerLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, -65.536));
        upperLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, 65.536));

        Arrays.fill(decisionSpaceOptima[0], 1);
    }

    @Override
    public double eval(double[] x) {
        double sum = 0;
        for (int i = 0; i < 25; i++) {
            sum += 1.0 / (i + 1 + pow(x[0] - a[0][i], 6) + pow(x[1] - a[1][i], 6));
        }
        return 1.0 / (0.002 + sum);
    }
}