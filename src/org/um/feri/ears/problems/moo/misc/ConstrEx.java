//  ConstrEx.java
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
package org.um.feri.ears.problems.moo.misc;

import java.util.ArrayList;

import org.um.feri.ears.problems.DoubleProblem;
import org.um.feri.ears.problems.NumberSolution;
import org.um.feri.ears.problems.moo.functions.ConstrEx_F1;
import org.um.feri.ears.problems.moo.functions.ConstrEx_F2;
import org.um.feri.ears.util.Util;

public class ConstrEx extends DoubleProblem {

    public ConstrEx() {

        super("Constr_Ex", 2, 1, 2, 2);

        referenceSetFileName = "ConstrEx";

        upperLimit = new ArrayList<>(numberOfDimensions);
        lowerLimit = new ArrayList<>(numberOfDimensions);

        lowerLimit.add(0.1);
        upperLimit.add(1.0);
        lowerLimit.add(0.0);
        upperLimit.add(5.0);

        addObjective(new ConstrEx_F1());
        addObjective(new ConstrEx_F2());
    }

    @Override
    public void evaluate(NumberSolution<Double> solution) {

        double[] x = Util.toDoubleArray(solution.getVariables());


        double[] obj = new double[objectives.size()];
        for (int i = 0; i < obj.length; i++) {
            obj[i] = objectives.get(i).eval(x);
        }
        solution.setObjectives(obj);
    }

    public double[] evaluate(Double[] ds) {
        double[] x = new double[numberOfDimensions];
        for (int i = 0; i < numberOfDimensions; i++)
            x[i] = ds[i];

        double[] obj = new double[objectives.size()];
        for (int i = 0; i < obj.length; i++) {
            obj[i] = objectives.get(i).eval(x);
        }

        return obj;
    }

    @Override
    public double[] calculateConstrains(NumberSolution<Double> solution) {
        double[] constraints = new double[numberOfConstraints];

        double[] x = Util.toDoubleArray(solution.getVariables());

        constraints[0] = (x[1] + 9 * x[0] - 6.0);
        constraints[1] = (-x[1] + 9 * x[0] - 1.0);

        return constraints;
    }
}
