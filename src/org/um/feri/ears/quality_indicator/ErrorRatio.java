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

import org.um.feri.ears.problems.NumberSolution;
import org.um.feri.ears.problems.moo.ParetoSolution;

/**
 * This class implements the Error ratio indicator.
 * It is defined as the proportion of nontrue Pareto points.
 * Lower values of ER refer to smaller proportions of nontrue
 * Pareto points in the approximation and represent better nondominated sets.
 * <p>
 * Reference: D. A. Van Veldhuizen, "Multiobjective evolutionary algorithms:
 * Classifications, analyses, and new innovations," Ph.D. dissertation,
 * Air Force Inst. Technol., Wright-Patterson AFB, OH, 1999.
 */
public class ErrorRatio<T extends Number> extends QualityIndicator<T> {

    private final double epsilon;

    public ErrorRatio(int num_obj, String file_name) {
        this(0.0, num_obj, file_name);
    }

    public ErrorRatio(double epsilon, int numObj, String file_name) {
        super(numObj, file_name, (ParetoSolution<T>) getReferenceSet(file_name));
        name = "Error Ratio";
        this.epsilon = epsilon;
    }

    @Override
    public double evaluate(double[][] paretoFrontApproximation) {


        int nonTruePoint = 0;
		/* Euclidean distance with epsilon
		 * boolean isOnFront;
		double distance;
		for (int i = 0; i < referenceSet.length; i++)
		{
			distance = utils_.distanceToClosestPoint(normalizedApproximation[i], normalizedReference);
			if(distance > 0.0001)
				nonTruePoint++;
		}*/

        boolean thePointIsInTheParetoFront, found;

        for (int i = 0; i < paretoFrontApproximation.length; i++) {
            double[] currentPoint = paretoFrontApproximation[i];
            thePointIsInTheParetoFront = false;
            for (int j = 0; j < referenceSet.length; j++) {
                double[] currentParetoFrontPoint = referenceSet[j];
                found = true;
                for (int k = 0; k < numberOfObjectives; k++) {
                    if (Math.abs(currentPoint[k] - currentParetoFrontPoint[k]) > epsilon) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    thePointIsInTheParetoFront = true;
                    break;
                }
            }
            if (!thePointIsInTheParetoFront) {
                nonTruePoint++;
            }
        }

        double ER = (double) nonTruePoint / (double) paretoFrontApproximation.length;
        return ER;
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
