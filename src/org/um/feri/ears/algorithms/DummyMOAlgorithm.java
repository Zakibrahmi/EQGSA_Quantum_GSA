package org.um.feri.ears.algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import org.um.feri.ears.problems.*;
import org.um.feri.ears.problems.StopCriterionException;
import org.um.feri.ears.problems.moo.ParetoSolution;

public class DummyMOAlgorithm<N extends Number, P extends Problem<NumberSolution<N>>, T extends Task<NumberSolution<N>,P>> extends MOAlgorithm<N, NumberSolution<N>, P> {

    HashMap<String, ParetoSolution<N>[]> results;
    HashMap<String, Integer> positions; //stores the position of the current result of the current problem

    public DummyMOAlgorithm(String name) {
        ai = new AlgorithmInfo(name, name, "");
        fillResults(name);
    }

    @Override
    public void resetToDefaultsBeforeNewRun() {

    }

    @Override
    public ParetoSolution<N> execute(Task task) throws StopCriterionException {
        String problemName = task.problem.getReferenceSetFileName();

        if (results.containsKey(problemName.toLowerCase())) {
            ParetoSolution<N> val = getNextValue(problemName.toLowerCase());
            return val;
        }

        return new ParetoSolution<N>();
    }

    private ParetoSolution<N> getNextValue(String problemName) {

        ParetoSolution<N>[] problemReults = results.get(problemName);
        int index = positions.get(problemName);
        positions.put(problemName, positions.get(problemName) + 1);
        return problemReults[index];
    }

    private void fillResults(String name) {

        results = new HashMap<String, ParetoSolution<N>[]>();
        positions = new HashMap<String, Integer>();

        File folder = new File("D:/Pareto/");
        File[] listOfFiles = folder.listFiles();

        String pathName = name + "_";
        String problemName, fileName;
        String[] value;

        for (File file : listOfFiles) {
            if (file.isFile()) {
                fileName = file.getName().toLowerCase();
                if (fileName.toLowerCase().contains(pathName.toLowerCase())) {
                    problemName = fileName.substring(fileName.indexOf(pathName) + pathName.length(), fileName.length() - 4);
                    ParetoSolution[] resultArray = new ParetoSolution[10000];
                    int index = 0;
                    try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
                        String line = br.readLine();

                        while (line != null) {
                            if (line.compareTo("#") == 0) {
                                line = br.readLine();
                                if (line != null) {
                                    ParetoSolution<N> ps = new ParetoSolution<N>();
                                    while (line != null && line.compareTo("#") != 0) {
                                        value = line.split(" ");
                                        NumberSolution<N> mos = new NumberSolution<N>(value.length);
                                        for (int i = 0; i < value.length; i++) {
                                            mos.setObjective(i, Double.parseDouble(value[i]));
                                        }
                                        ps.add(mos);
                                        line = br.readLine();
                                    }
                                    ps.setCapacity(ps.size());
                                    resultArray[index] = ps;
                                    index++;
                                }
                            }
                        }

                        results.put(problemName.toLowerCase(), resultArray);
                        positions.put(problemName.toLowerCase(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }

    @Override
    protected void init() throws StopCriterionException {

    }

    @Override
    protected void start() throws StopCriterionException {

    }
}
