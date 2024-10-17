//  Epsilon.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.um.feri.ears.quality_indicator;

import org.um.feri.ears.problems.moo.ParetoSolution;

/**
 * This class implements the unary epsilon additive indicator as proposed in
 * E. Zitzler, E. Thiele, L. Laummanns, M., Fonseca, C., and Grunert da Fonseca.
 * V (2003): Performance Assesment of Multiobjective Optimizers: An Analysis and
 * Review. The code is the a Java version of the original metric implementation
 * by Eckart Zitzler.
 * It can be used also as a command line program just by typing
 * $java jmetal.qualityIndicator.Epsilon <solutionFrontFile> <trueFrontFile> <numberOfObjectives>
 */
public class Epsilon<T extends Number> extends QualityIndicator<T> {

    int[] obj; /* obj_[i] = 0 means objective i is to be minimized */
    // method_ = 0 means apply additive epsilon and method_ = 1 means multiplicative epsilon. This code always apply additive epsilon
    int method;


    public Epsilon(int num_obj, String file_name) {
        super(num_obj, file_name, (ParetoSolution<T>) getReferenceSet(file_name));
        name = "Epsilon";
    }

    @Override
    public double evaluate(double[][] paretoFrontApproximation) {

        //TODO add +1 to all normalized values if additive method [1,2]
        double[][] normalizedFront = QualityIndicatorUtil.normalizeFront(paretoFrontApproximation, maximumValue, minimumValue);

        int i, j, k;
        double eps, epsJ = 0.0, epsK = 0.0, epsTemp;


        set_params();

        if (method == 0)
            eps = Double.MIN_VALUE;
        else
            eps = 0;

        for (i = 0; i < normalizedReference.length; i++) {
            for (j = 0; j < normalizedFront.length; j++) {
                for (k = 0; k < numberOfObjectives; k++) {
                    if (method == 0) {
                        if (obj[k] == 0)
                            epsTemp = normalizedFront[j][k] - normalizedReference[i][k];
                            // epsTemp = b[j * dim_ + k] - a[i * dim_ + k];
                        else
                            epsTemp = normalizedReference[i][k] - normalizedFront[j][k];
                        // epsTemp = a[i * dim_ + k] - b[j * dim_ + k];
                    } else {
                        if ((normalizedReference[i][k] < 0 && normalizedFront[j][k] > 0)
                                || (normalizedReference[i][k] > 0 && normalizedFront[j][k] < 0)
                                || (normalizedReference[i][k] == 0 || normalizedFront[j][k] == 0)) {
                            // if ( (a[i * dim_ + k] < 0 && b[j * dim_ + k] > 0)
                            // ||
                            // (a[i * dim_ + k] > 0 && b[j * dim_ + k] < 0) ||
                            // (a[i * dim_ + k] == 0 || b[j * dim_ + k] == 0)) {
                            System.err.println("error in data file");
                            System.exit(0);
                        }
                        if (obj[k] == 0)
                            epsTemp = normalizedFront[j][k] / normalizedReference[i][k];
                            // epsTemp = b[j * dim_ + k] / a[i * dim_ + k];
                        else
                            epsTemp = normalizedReference[i][k] / normalizedFront[j][k];
                        // epsTemp = a[i * dim_ + k] / b[j * dim_ + k];
                    }
                    if (k == 0)
                        epsK = epsTemp;
                    else if (epsK < epsTemp)
                        epsK = epsTemp;
                }
                if (j == 0)
                    epsJ = epsK;
                else if (epsJ > epsK)
                    epsJ = epsK;
            }
            if (i == 0)
                eps = epsJ;
            else if (eps < epsJ)
                eps = epsJ;
        }
        return eps;
    }

    /**
     * Established the params by default
     */
    void set_params() {
        int i;
        obj = new int[numberOfObjectives];
        for (i = 0; i < numberOfObjectives; i++) {
            obj[i] = 0;
        }
        method = 0;
    }

    @Override
    public boolean isMin() {
        return true;
    }

    @Override
    public IndicatorType getIndicatorType() {
        return QualityIndicator.IndicatorType.UNARY;
    }

    @Override
    public boolean requiresReferenceSet() {
        return true;
    }

    @Override
    public int compare(ParetoSolution<T> front1, ParetoSolution<T> front2, Double epsilon) {
        return 0;
    }
}
