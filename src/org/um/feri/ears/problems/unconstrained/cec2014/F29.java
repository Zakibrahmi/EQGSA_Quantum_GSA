package org.um.feri.ears.problems.unconstrained.cec2014;

import org.um.feri.ears.problems.unconstrained.cec.Functions;

public class F29 extends CEC2014 {

    public F29(int d) {
        super("F29 Composition Function 7", d, 29);
    }

    @Override
    public double eval(double[] x) {
        return Functions.cf07(x, numberOfDimensions, OShift, M, SS, 1) + funcNum * 100.0;
    }
}