package org.um.feri.ears.quality_indicator.wfg;

import java.util.Comparator;

import org.um.feri.ears.problems.NumberSolution;
import org.um.feri.ears.problems.moo.ParetoSolution;
import org.um.feri.ears.quality_indicator.QualityIndicatorUtil;
import org.um.feri.ears.quality_indicator.QualityIndicator;
import org.um.feri.ears.util.comparator.SolutionPointComparator;

/**
 * Created by ajnebro on 2/2/15.
 */
public class WfgHypervolume<T extends Number> extends QualityIndicator<T>{
	
	private double offset = 0.0;
	static final int OPT = 2;
	ParetoSolution<T>[] fs;
	boolean maximizing;
	private int currentDeep;
	private int currentDimension;
	private Comparator<NumberSolution<T>> pointComparator = new SolutionPointComparator();

	public WfgHypervolume(int numObj, String fileName) {
		super(numObj, fileName, getReferenceSet(fileName));
		name = "WFG Hypervolume";
		maximizing = false;
	}

	public WfgHypervolume(int numObj, String problemName, double[][] referenceFront, double[] referencePoint) {
		super(numObj, problemName, referenceFront, referencePoint);
		name = "WFG Hypervolume";
	}

	@Override
	public double evaluate(ParetoSolution<T> paretoFrontApproximation) {

		currentDeep = 0;
		currentDimension = numberOfObjectives;
		double hv = 0;

		ParetoSolution<T> copy = new ParetoSolution<T>(paretoFrontApproximation);

		QualityIndicatorUtil.normalizeFront(copy, maximumValue, minimumValue);
		QualityIndicatorUtil.invertedFront(copy);
		
		int maxd = copy.size() - (OPT / 2 + 1);
		fs = new ParetoSolution[maxd];
		for (int i = 0; i < maxd; i++) {
			fs[i] = new ParetoSolution<T>(copy.size(), numberOfObjectives);
		}
		
		if (paretoFrontApproximation.size() == 0) {
			hv = 0.0;
		} else {
			if(referencePoint == null) {
				updateReferencePoint(copy);
			}
			hv = getHV(new ParetoSolution<T>(copy));
		}
		return hv;
	}

	@Override
	public double evaluate(double[][] paretoFrontApproximation) {
		System.err.println("WFG Hypervolume not implemented");
		return 0;
	}

	/**
	 * Updates the reference point
	 */
	private void updateReferencePoint(ParetoSolution<T> front) {
		double[] maxObjectives = QualityIndicatorUtil.getMaximumValues(front.getObjectivesAsMatrix(), numberOfObjectives);
		referencePoint = new double[numberOfObjectives];
		for (int i = 0; i < numberOfObjectives; i++) {
			referencePoint[i] = maxObjectives[i] + offset;
		}
	}

	public double get2DHV(ParetoSolution<T> front) {
		double hv = 0.0;

		hv = Math.abs((front.get(0).getObjective(0) - referencePoint[0]) *
				(front.get(0).getObjective(1) - referencePoint[1])) ;

		int v = front.size() ;
		for (int i = 1; i < front.size(); i++) {
			hv += Math.abs((front.get(i).getObjective(0) - referencePoint[0]) *
					(front.get(i).getObjective(1) - front.get(i - 1).getObjective(1)));

		}

		return hv;
	}

	public double getInclusiveHV(NumberSolution<T> point) {
		double volume = 1;
		for (int i = 0; i < currentDimension; i++) {
			volume *= Math.abs(point.getObjective(i) - referencePoint[i]);
		}

		return volume;
	}

	public double getExclusiveHV(ParetoSolution<T> front, int point) {
		double volume;

		volume = getInclusiveHV(front.get(point));
		if (front.size() > point + 1) {
			makeDominatedBit(front, point);
			double v = getHV(fs[currentDeep - 1]);
			volume -= v;
			currentDeep--;
		}

		return volume;
	}

	public double getHV(ParetoSolution<T> front) {
		double volume ;
		front.sort(pointComparator);

		if (currentDimension == 2) {
			volume = get2DHV(front);
		} else {
			volume = 0.0;

			currentDimension--;
			int numberOfPoints = front.size() ;
			for (int i = numberOfPoints - 1; i >= 0; i--) {
				volume += Math.abs(front.get(i).getObjective(currentDimension) -
						referencePoint[currentDimension]) *
						this.getExclusiveHV(front, i);
			}
			currentDimension++;
		}

		return volume;
	}


	public void makeDominatedBit(ParetoSolution<T> front, int p) {
		int z = front.size() - 1 - p;

		for (int i = 0; i < z; i++) {
			for (int j = 0; j < currentDimension; j++) {
				NumberSolution<T> point1 = front.get(p) ;
				NumberSolution<T> point2 = front.get(p + 1 + i) ;
				double worseValue = worse(point1.getObjective(j), point2.getObjective(j), false) ;
				int cd = currentDeep ;
				NumberSolution<T> point3 = fs[currentDeep].get(i) ;
				point3.setObjective(j, worseValue);
			}
		}

		NumberSolution<T> t;
		fs[currentDeep].setCapacity(1);

		for (int i = 1; i < z; i++) {
			int j = 0;
			boolean keep = true;
			while (j < fs[currentDeep].getCapacity() && keep) {
				switch (dominates2way(fs[currentDeep].get(i), fs[currentDeep].get(j))) {
				case -1:
					t = fs[currentDeep].get(j);
					fs[currentDeep].setCapacity(fs[currentDeep].getCapacity()-1);
					fs[currentDeep].set(j, fs[currentDeep].get(fs[currentDeep].getCapacity()));
					fs[currentDeep].set(fs[currentDeep].getCapacity(), t);
					break;
				case 0:
					j++;
					break;
				default:
					keep = false;
					break;
				}
			}
			if (keep) {
				t = fs[currentDeep].get(fs[currentDeep].getCapacity());
				fs[currentDeep].set(fs[currentDeep].getCapacity(), fs[currentDeep].get(i));
				fs[currentDeep].set(i, t);
				fs[currentDeep].setCapacity(fs[currentDeep].getCapacity()+1);
			}
		}

		currentDeep++;
	}

	public int getLessContributorHV(ParetoSolution<T> solutionList) {

		int index = 0;
		double contribution = Double.POSITIVE_INFINITY;

		for (int i = 0; i < solutionList.size(); i++) {
			double[] v = new double[solutionList.get(i).getNumberOfObjectives()];
			for (int j = 0; j < v.length; j++) {
				v[j] = solutionList.get(i).getObjective(j);
			}

			double aux = this.getExclusiveHV(solutionList, i);
			if ((aux) < contribution) {
				index = i;
				contribution = aux;
			}

			//HypervolumeContributionAttribute<Solution<?>> hvc = new HypervolumeContributionAttribute<Solution<?>>() ;
			//hvc.setAttribute(solutionList.get(i), aux);
			//solutionList.get(i).setCrowdingDistance(aux);
		}

		return index;
	}

	private double worse(double x, double y, boolean maximizing) {
		double result;
		if (maximizing) {
			if (x > y) {
				result = y;
			} else {
				result = x;
			}
		} else {
			if (x > y) {
				result = x;
			} else {
				result = y;
			}
		}
		return result;
	}

	int dominates2way(NumberSolution<T> p, NumberSolution<T> q) {
		// returns -1 if p dominates q, 1 if q dominates p, 2 if p == q, 0 otherwise
		// ASSUMING MINIMIZATION

		// domination could be checked in either order

		for (int i = currentDimension - 1; i >= 0; i--) {
			if (p.getObjective(i) < q.getObjective(i)) {
				for (int j = i - 1; j >= 0; j--) {
					if (q.getObjective(j) < p.getObjective(j)) {
						return 0;
					}
				}
				return -1;
			} else if (q.getObjective(i) < p.getObjective(i)) {
				for (int j = i - 1; j >= 0; j--) {
					if (p.getObjective(j) < q.getObjective(j)) {
						return 0;
					}
				}
				return 1;
			}
		}
		return 2;
	}

	@Override
	public IndicatorType getIndicatorType() {
		return IndicatorType.UNARY;
	}

	@Override
	public boolean isMin() {
		return false;
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
