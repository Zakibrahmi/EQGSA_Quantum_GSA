package org.um.feri.ears.problems.unconstrained.cec2005;

import java.util.ArrayList;
import java.util.Collections;

public class F14 extends CEC2005Base {

    static final public String DEFAULT_FILE_DATA = "E_ScafferF6_func_data.txt";
    static final public String DEFAULT_FILE_MX_PREFIX = "E_ScafferF6_M_D";
    static final public String DEFAULT_FILE_MX_SUFFIX = ".txt";

    private final double[][] m_matrix;

    // In order to avoid excessive memory allocation,
    // a fixed memory buffer is allocated for each function object.
    private double[] m_z;
    private double[] m_zM;

    public F14(int d) {
        super("Shifted Rotated Expanded Scaffer's F6 Function", d, 14);

        lowerLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, -100.0));
        upperLimit = new ArrayList<>(Collections.nCopies(numberOfDimensions, 100.0));

        m_o = new double[d];
        m_matrix = new double[d][d];

        m_z = new double[d];
        m_zM = new double[d];

        // Load the shifted global optimum
        loadRowVectorFromFile(DEFAULT_FILE_DATA, d, m_o);
        // Load the matrix
        loadMatrixFromFile(DEFAULT_FILE_MX_PREFIX + d + DEFAULT_FILE_MX_SUFFIX, d, d, m_matrix);
        decisionSpaceOptima[0] = m_o;
    }

    @Override
    public double eval(double[] x) {
        double result = 0.0;

        shift(m_z, x, m_o);
        rotate(m_zM, m_z, m_matrix);

        result = EScafferF6(m_zM);

        result += m_biases[funcNum - 1];

        return result;
    }

}
