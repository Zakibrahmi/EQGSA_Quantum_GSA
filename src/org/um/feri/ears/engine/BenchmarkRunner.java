package org.um.feri.ears.engine;

import org.apache.commons.lang3.SystemUtils;
import org.um.feri.ears.algorithms.so.ff.FireflyAlgorithm;
import org.um.feri.ears.benchmark.RPUOed30Benchmark;
import org.um.feri.ears.problems.DoubleProblem;
import org.um.feri.ears.problems.NumberSolution;
import org.um.feri.ears.problems.StopCriterionException;
import org.um.feri.ears.problems.Task;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BenchmarkRunner {

	static Logger logger = Logger.getLogger(BenchmarkRunner.class.getName());
	
	public static void main(String args[]) {

		String destFolder = "D:\\Benchmark results\\test\\";
		String fileName;
		
		URL url = BenchmarkRunner.class.getResource(".");
		String logLocation = url.getPath();
		if(SystemUtils.IS_OS_WINDOWS)
			logLocation = logLocation.substring(1);
		
		FileHandler fileTxt;
		SimpleFormatter formatterTxt;
		logger.setLevel(Level.INFO);
		formatterTxt = new SimpleFormatter();
		
		try {
			fileTxt = new FileHandler(logLocation+"log_file.txt", true);
	        fileTxt.setFormatter(formatterTxt);
	        logger.addHandler(fileTxt);
	        logger.setUseParentHandlers(false); //disable logger's console output
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}
		
		//RandomWalkAlgorithm algorithm = new RandomWalkAlgorithm();
		FireflyAlgorithm algorithm = new FireflyAlgorithm();
		RPUOed30Benchmark benchmark = new RPUOed30Benchmark(); // Create benchmark
		benchmark.initAllProblems(); //manually initialize problems
		algorithm.addCustomInfo("submissionAuthor", "author");
		algorithm.addCustomInfo("submissionId", "id");
		
		logger.log(Level.INFO, "algorithm "+"algorithm");
		logger.log(Level.INFO, "submission author "+"author");
		logger.log(Level.INFO, "submission id "+"id");

		ArrayList<Task> tasks = benchmark.getAllTasks();
		int numberOfRuns = benchmark.getNumberOfRuns();
		logger.log(Level.INFO, "number of runs "+numberOfRuns);
		long totalDuration = System.nanoTime();
		
		for (Task<NumberSolution<Double>, DoubleProblem> t: tasks) {
			logger.log(Level.INFO, "starting task "+t.getProblemName());
			StringBuilder sb = new StringBuilder();
			sb.append(algorithm.getAlgorithmInfoCSV()+";"+t.getTaskInfoCSV());
			sb.append("\n");
			long taskDuration = System.nanoTime();
			for (int i = 0; i < numberOfRuns; i++) {
				long runDuration = System.nanoTime();
				logger.log(Level.INFO, "run "+ (i+1));
				NumberSolution result;
				try {
					result = algorithm.execute(t);
				} catch (StopCriterionException e) {
					e.printStackTrace();
					logger.log(Level.SEVERE, e.getMessage());
					return;
				}
				sb.append(result.getEval());
				if(i+1 < numberOfRuns)
					sb.append("\n");
				t.resetCounter();
				logger.log(Level.INFO, "run duration "+ TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - runDuration) + "ms");
			}
			logger.log(Level.INFO, "finished task "+t.getProblemName() +" in "+TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - taskDuration) + "ms");
			fileName = algorithm.getClass().getSimpleName().replace("_", " ")+"_"+t.getProblemName()+".txt";
			
			try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFolder+File.separator+fileName)))) {
				bw.write(sb.toString());
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		logger.log(Level.INFO, "total execution time "+ TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - totalDuration)+"ms");
	}
}
