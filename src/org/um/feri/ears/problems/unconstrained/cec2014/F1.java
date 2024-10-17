package org.um.feri.ears.problems.unconstrained.cec2014;

import org.um.feri.ears.problems.unconstrained.cec.Functions;

public class F1 extends CEC2014 {

    public F1(int d) {
        super("F01 Ellips Function", d, 1);
    }

    @Override
    public double eval(double[] x) {
        return Functions.ellips_func(x, numberOfDimensions, OShift, M, 1, 1) + funcNum * 100.0;
    }
}
