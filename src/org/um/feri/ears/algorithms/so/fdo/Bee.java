package org.um.feri.ears.algorithms.so.fdo;

import org.um.feri.ears.problems.NumberSolution;

import java.util.ArrayList;

public class Bee extends NumberSolution<Double> {
    private ArrayList<Double> lastPace = new ArrayList<>();

    public Bee(NumberSolution<Double> solution) {
        super(solution);
    }

    public ArrayList<Double> getLastPace() {
        return lastPace;
    }

    public void setLastPace(ArrayList<Double> lastPace) {
        this.lastPace = lastPace;
    }
}
