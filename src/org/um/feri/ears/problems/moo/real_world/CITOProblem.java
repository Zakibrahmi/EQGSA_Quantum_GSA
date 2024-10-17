package org.um.feri.ears.problems.moo.real_world;

import java.util.ArrayList;
import java.util.List;

import org.um.feri.ears.problems.CombinatorialProblem;
import org.um.feri.ears.problems.NumberSolution;

public class CITOProblem extends CombinatorialProblem {

	private int numberOfUnits;
	private ArrayList<Integer> aspects;
	private int[][] constraintMatrix;
	private int[][] dependencyMatrix;
	private int[][] attributeCouplingMatrix;
	private int[][] methodCouplingMatrix;
	private int[][] methodReturnTypeMatrix;
	private int[][] methodParamTypeMatrix;

	public enum Problems
	{
		OA_AJ_HOT_DRAW,
		OA_AJ_HSQLDB,
		OA_HEALTH_WATCHER,
		OA_TOLL_SYSTEMS,
		OO_BCEL,
		OO_J_BOSS,
		OO_J_HOT_DRAW,
		OO_MY_BATIS;
	}
	
	public CITOProblem(String softwareName) {
		this(new CITOReader(softwareName));
	}

	public CITOProblem(CITOReader problemReader) {
		super("CITO_"+problemReader.getSoftwareName(), problemReader.getNumberOfUnits(), 1, 2, 0);
		this.numberOfUnits = problemReader.getNumberOfUnits();
		this.aspects = problemReader.getAspects();
		this.constraintMatrix = problemReader.getConstraintMatrix();
		this.dependencyMatrix = problemReader.getDependencyMatrix();
		
		this.attributeCouplingMatrix = problemReader.getAttributeCouplingMatrix();
		this.methodCouplingMatrix = problemReader.getMethodCouplingMatrix();
		this.methodParamTypeMatrix = problemReader.getMethodParamTypeMatrix();
		this.methodReturnTypeMatrix = problemReader.getMethodReturnTypeMatrix();

		referenceSetFileName = "CITO_"+problemReader.getSoftwareName();

		upperLimit = new ArrayList<>(numberOfDimensions);
		lowerLimit = new ArrayList<>(numberOfDimensions);

		for (int i = 0; i < numberOfDimensions; i++) {
			lowerLimit.add(0);
			upperLimit.add(problemReader.getNumberOfUnits() - 1);
		}
	}

	@Override
	public void evaluate(NumberSolution<Integer> solution) {
		double[] obj = evaluate(solution.getVariables());
		solution.setObjectives(obj);
	}

	public double[] evaluate(Integer[] ds){
		treatConstraintsAndRepairSolution(ds, constraintMatrix);

		double fitness0 = 0.0;
		double fitness1 = 0.0;

		//goes through the vector of the solution
		for (int i = 0; i < numberOfUnits; i++) {

			//get the id of the class
			int x = ds[i];

			//go to the dependency matrix columns
			for (int k = 0; k < numberOfUnits; k++) {

				//verify if there exists a dependence between x and k
				if (dependencyMatrix[x][k] == 1) {
					boolean verificador = false;

					//checks whether the class already exists
					for (int j = 0; j <= i; j++) {
						int y = ds[j];
						if (y == k) {
							verificador = true;
							break;
						}
					}

					//add the values to fitness if the class has not yet been tested
					if (!verificador) {
						fitness0 += attributeCouplingMatrix[x][k];
						fitness1 += methodCouplingMatrix[x][k];
					}
				}
			}
		}
		double[] obj = new double[numberOfObjectives];
		obj[0] = fitness0;
		obj[1] = fitness1;
		
		return obj;
	}
	
	public double[] evaluate(List<Integer> ds) {
		
		return this.evaluate(ds.toArray(new Integer[0]));
	}

	public int getPermutationLength() {
		return numberOfUnits;
	}

	public Integer[] treatConstraintsAndRepairSolution(Integer[] ds, int[][] constraints) {
		int[] array = new int[ds.length];
		int size = array.length;

		for (int i = 0; i < array.length; i++) {
			int variable = ds[i];
			array[i] = variable;
		}

		ArrayList<Integer> subVector = new ArrayList<>();

		for (int indexSolution = 0; indexSolution < size; indexSolution++) {
			//takes the class id to find the restrictions
			int contraintClassId = array[indexSolution];
			boolean addInSubVector = true;
			//It goes through all the classes to check restriction with the current class
			for (int indexConstraint = 0; indexConstraint < constraints[contraintClassId].length; indexConstraint++) {
				//check if there is a restriction
				if (constraints[contraintClassId][indexConstraint] == 1) {
					//verifies that the required class has appeared before
					int x = subVector.indexOf(indexConstraint);
					if (x == -1) {
						array = this.putEnd(array, indexSolution);
						addInSubVector = false;
						indexSolution--;
						break;
					}
				}
			}
			//add element in the subvector
			if (addInSubVector) {
				subVector.add(array[indexSolution]);
			}
		}

		for (int i = 0; i < array.length; i++) {
			int variable = array[i];
			ds[i] = variable;
		}
		return ds;
	}

	public int[] putEnd(int[] haystack, int index) {
		int temp = haystack[index];

		for (int i = index; i < haystack.length - 1; i++) {
			haystack[i] = haystack[i + 1];
		}

		haystack[haystack.length - 1] = temp;

		return haystack;
	}

}
