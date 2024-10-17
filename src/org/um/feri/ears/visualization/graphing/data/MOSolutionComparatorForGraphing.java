package org.um.feri.ears.visualization.graphing.data;

import org.um.feri.ears.problems.NumberSolution;

import java.util.Comparator;



@SuppressWarnings("rawtypes")
public class MOSolutionComparatorForGraphing implements Comparator<NumberSolution>
{
	int objectiveIndexes[] = null;
	
	public MOSolutionComparatorForGraphing()
	{}
	
	public MOSolutionComparatorForGraphing(int axisX, int axisY)
	{
		objectiveIndexes = new int[]{axisX, axisY};
	}
	
	public MOSolutionComparatorForGraphing(int[] objectiveIndexes)
	{
		this.objectiveIndexes = objectiveIndexes;
	}
	
	
	
	public int compare(NumberSolution obj1, NumberSolution obj2)
	{

		double[] o1 = obj1.getObjectives();
		double[] o2 = obj2.getObjectives();
		if (objectiveIndexes==null)
		{
			for (int i=0; i<o1.length; i++)
			{
				if (o1[i]<o2[i])
					return -1;
				else if (o1[i]>o2[i])
					return 1;
			}
		}
		else
		{
			for (int i=0; i<objectiveIndexes.length; i++)
			{
				if (o1[objectiveIndexes[i]]<o2[objectiveIndexes[i]])
					return -1;
				else if (o1[objectiveIndexes[i]]>o2[objectiveIndexes[i]])
					return 1;
			}
		}
		
		return 0;

	}
}
