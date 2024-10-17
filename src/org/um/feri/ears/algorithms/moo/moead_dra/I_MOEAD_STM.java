package org.um.feri.ears.algorithms.moo.moead_dra;

import org.um.feri.ears.operators.CrossoverOperator;
import org.um.feri.ears.operators.MutationOperator;
import org.um.feri.ears.operators.PMXCrossover;
import org.um.feri.ears.operators.PermutationSwapMutation;
import org.um.feri.ears.problems.CombinatorialProblem;
import org.um.feri.ears.problems.NumberProblem;
import org.um.feri.ears.problems.NumberSolution;
import org.um.feri.ears.problems.Task;

public class I_MOEAD_STM extends MOEAD_DRA<Integer, NumberProblem<Integer>> {
	
	public I_MOEAD_STM() {
		this(new PMXCrossover(), new PermutationSwapMutation(0.2), 100);
	}
	
	public I_MOEAD_STM(int populationSize) {
		this(new PMXCrossover(), new PermutationSwapMutation(0.2), populationSize);
	}

	public I_MOEAD_STM(CrossoverOperator<NumberProblem<Integer>, NumberSolution<Integer>> crossover, MutationOperator<NumberProblem<Integer>, NumberSolution<Integer>> mutation, int populationSize) {
		super(crossover, mutation, populationSize);
	}
	
}