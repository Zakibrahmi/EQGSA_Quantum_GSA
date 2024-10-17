package org.um.feri.ears.problems.unconstrained.cec2014;

import org.um.feri.ears.problems.unconstrained.cec.Functions;

public class F19 extends CEC2014 {

    public F19(int d) {
        super("F19 Hybrid Function 3", d, 19);
    }

    @Override
    public double eval(double[] x) {
        return Functions.hf03(x, numberOfDimensions, OShift, M, SS, 1, 1) + funcNum * 100.0;
    }
}