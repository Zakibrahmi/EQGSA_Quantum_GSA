package org.um.feri.ears.problems.unconstrained.cec2014;

import org.um.feri.ears.problems.unconstrained.cec.Functions;

public class F4 extends CEC2014 {
	
	public F4(int d) {
		super("F04 Rosenbrock Function", d,4);
	}

	@Override
	public double eval(double[] x) {
		return Functions.rosenbrock_func(x, numberOfDimensions, OShift, M, 1, 1) + funcNum * 100.0;
	}
}
