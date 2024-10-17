package org.um.feri.ears.problems.unconstrained.cec2014;

import org.um.feri.ears.problems.unconstrained.cec.Functions;

public class F7 extends CEC2014 {

    public F7(int d) {
        super("F07 Griewank Function", d, 7);
    }

    @Override
    public double eval(double[] x) {
        return Functions.griewank_func(x, numberOfDimensions, OShift, M, 1, 1) + funcNum * 100.0;
    }
}
