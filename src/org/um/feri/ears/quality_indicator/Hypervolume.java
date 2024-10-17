//  Hypervolume.java
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
 * This class implements the hypervolume indicator. The code is the a Java version
 * of the original metric implementation by Eckart Zitzler.
 * Reference: E. Zitzler and L. Thiele
 * Multiobjective Evolutionary Algorithms: A Comparative Case Study
 * and the Strength Pareto Approach,
 * IEEE Transactions on Evolutionary Computation, vol. 3, no. 4,
 * pp. 257-271, 1999.
 */
public class Hypervolume<T extends Number> extends QualityIndicator<T> {

    /**
     * Constructor Creates a new instance of MultiDelta
     */
    public Hypervolume(int numObj, String problemName) {
        super(numObj, problemName, (ParetoSolution<T>) getReferenceSet(problemName));
        name = "Hypervolume";
    }

    public Hypervolume(int numObj, String problemName, double[][] referenceFront, double[] referencePoint) {
        super(numObj, problemName, referenceFront, referencePoint);
        name = "Hypervolume";
    }

    /*
     * returns true if 'point1' dominates 'points2' with respect to the
     * first 'noObjectives' objectives
     */
    boolean dominates(double[] point1, double[] point2, int noObjectives) {
        int i;
        int betterInAnyObjective;

        betterInAnyObjective = 0;
        for (i = 0; i < noObjectives && point1[i] >= point2[i]; i++)
            if (point1[i] > point2[i])
                betterInAnyObjective = 1;

        return ((i >= noObjectives) && (betterInAnyObjective > 0));
    }

    void swap(double[][] front, int i, int j) {
        double[] temp;

        temp = front[i];
        front[i] = front[j];
        front[j] = temp;
    }


    /* all nondominated points regarding the first 'noObjectives' dimensions
      are collected; the points referenced by 'front[0..noPoints-1]' are
      considered; 'front' is resorted, such that 'front[0..n-1]' contains
      the nondominated points; n is returned */
    int filterNondominatedSet(double[][] front, int noPoints, int noObjectives) {
        int i, j;
        int n;

        n = noPoints;
        i = 0;
        while (i < n) {
            j = i + 1;
            while (j < n) {
                if (dominates(front[i], front[j], noObjectives)) {
                    /* remove point 'j' */
                    n--;
                    swap(front, j, n);
                } else if (dominates(front[j], front[i], noObjectives)) {
                    /*
                     * remove point 'i'; ensure that the point copied to index
                     * 'i' is considered in the next outer loop (thus, decrement
                     * i)
                     */
                    n--;
                    swap(front, i, n);
                    i--;
                    break;
                } else
                    j++;
            }
            i++;
        }
        return n;
    }


    /*calculate next value regarding dimension 'objective'; consider points
     * referenced in 'front[0..noPoints-1]'
     */
    double surfaceUnchangedTo(double[][] front, int noPoints, int objective) {
        int i;
        double minValue, value;

        if (noPoints < 1)
            System.err.println("run-time error");

        minValue = front[0][objective];
        for (i = 1; i < noPoints; i++) {
            value = front[i][objective];
            if (value < minValue)
                minValue = value;
        }
        return minValue;
    }

    /*remove all points which have a value <= 'threshold' regarding the
     * dimension 'objective'; the points referenced by 'front[0..noPoints-1]'
     * are considered; 'front' is resorted, such that 'front[0..n-1]' contains
     * the remaining points; 'n' is returned
     */
    int reduceNondominatedSet(double[][] front, int noPoints, int objective,
                              double threshold) {
        int n;
        int i;

        n = noPoints;
        for (i = 0; i < n; i++)
            if (front[i][objective] <= threshold) {
                n--;
                swap(front, i, n);
            }

        return n;
    }

    public double calculateHypervolume(double[][] front, int noPoints, int noObjectives) {
        int n;
        double volume, distance;

        volume = 0;
        distance = 0;
        n = noPoints;
        while (n > 0) {
            int noNondominatedPoints;
            double tempVolume, tempDistance;

            noNondominatedPoints = filterNondominatedSet(front, n, noObjectives - 1);
            tempVolume = 0;
            if (noObjectives < 3) {
                if (noNondominatedPoints < 1)
                    System.err.println("run-time error");

                tempVolume = front[0][0];
            } else
                tempVolume = calculateHypervolume(front, noNondominatedPoints, noObjectives - 1);

            tempDistance = surfaceUnchangedTo(front, n, noObjectives - 1);
            volume += tempVolume * (tempDistance - distance);
            distance = tempDistance;
            n = reduceNondominatedSet(front, n, noObjectives - 1, distance);
        }
        return volume;
    }

    /* merge two fronts */
    double[][] mergeFronts(double[][] front1, int sizeFront1,
                           double[][] front2, int sizeFront2, int noObjectives) {
        int i, j;
        int noPoints;
        double[][] frontPtr;

        /* allocate memory */
        noPoints = sizeFront1 + sizeFront2;
        frontPtr = new double[noPoints][noObjectives];
        /* copy points */
        noPoints = 0;
        for (i = 0; i < sizeFront1; i++) {
            for (j = 0; j < noObjectives; j++)
                frontPtr[noPoints][j] = front1[i][j];
            noPoints++;
        }
        for (i = 0; i < sizeFront2; i++) {
            for (j = 0; j < noObjectives; j++)
                frontPtr[noPoints][j] = front2[i][j];
            noPoints++;
        }

        return frontPtr;
    }

    @Override
    public double evaluate(double[][] paretoFrontApproximation) {


        double[][] normalizedApproximation;

        double[][] invertedFront;
		
		/*maximumValue[0] = 1.18;
		maximumValue[1] = 1.18;
		maximumValue[2] = 1.18;*/

        //0.4217095828301254
        //0.6484027573510406

        normalizedApproximation = QualityIndicatorUtil.normalizeFront(paretoFrontApproximation, maximumValue, minimumValue);

        // Inverse the pareto front. This is needed because of the original
        // metric by Zitzler is for maximization problems
        invertedFront = QualityIndicatorUtil.invertedFront(normalizedApproximation);

        // The hypervolumen (control is passed to java version of Zitzler code)
        return this.calculateHypervolume(invertedFront, invertedFront.length, numberOfObjectives);
    }

    @Override
    public boolean isMin() {
        return false;
    }

    @Override
    public IndicatorType getIndicatorType() {
        return QualityIndicator.IndicatorType.UNARY;
    }

    @Override
    public boolean requiresReferenceSet() {
        return true; // for normalization
    }

    @Override
    public int compare(ParetoSolution<T> front1, ParetoSolution<T> front2, Double epsilon) {
        return 0;
    }
}
