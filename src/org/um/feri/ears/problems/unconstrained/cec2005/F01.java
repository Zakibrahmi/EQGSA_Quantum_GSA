package org.um.feri.ears.problems.unconstrained.cec2005;

import java.util.ArrayList;
import java.util.Collections;

public class F01 extends CEC2005Base {

	// Fixed (class) parameters
	static final public String DEFAULT_FILE_DATA = "sphere_func_data.txt";

	// In order to avoid excessive memory allocation,
	// a fixed memory buffer is allocated for each function object.
	private double[] m_z;

	
	public F01(int d) {
		super("Shifted Sphere Function", d,1);

		lowerLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, -100.0));
		upperLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, 100.0));

		m_o = new double[d];
		m_z = new double[d];

		// Load the shifted global optimum
		loadRowVectorFromFile(DEFAULT_FILE_DATA, d, m_o);
		decisionSpaceOptima[0] = m_o;
	}

	@Override
	public double eval(double[] x) {
		
		double result = 0.0;

		shift(m_z, x, m_o);

		result = sphere(m_z);

		result += m_biases[funcNum -1];

		return result;
	}

}
