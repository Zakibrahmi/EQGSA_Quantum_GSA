package org.um.feri.ears.problems.unconstrained.cec2014;

import org.um.feri.ears.problems.unconstrained.cec.Functions;

public class F2 extends CEC2014 {

    public F2(int d) {
        super("F02 Bent Cigar Function", d, 2);
    }

    @Override
    public double eval(double[] x) {
        return Functions.bent_cigar_func(x, numberOfDimensions, OShift, M, 1, 1) + funcNum * 100.0;
    }
}
