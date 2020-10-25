package main;

import java.util.Scanner;

import buffer.Buffer;
import threads.PowWorker;
import util.Config;
import workUnit.WorkUnit;

public class Main {

	public static void main(String[] args) {
		Config config = new Config();
		config.setNonceSize(4);
		config.setMaxThreads(1);

		Scanner scanner = new Scanner(System.in);
		config.inputThreadAmount(scanner);
		config.inputDifficulty(scanner);
		config.inputPrefix(scanner);
		scanner.close();

		WorkUnit workUnit = new WorkUnit(0, config.getThreadAmount(), 4);
		Buffer buffer = new Buffer(2, workUnit);
		PowWorker powWorker = new PowWorker(buffer, config.getNonceSize(), config.getPrefix(), config.getDifficulty());
		powWorker.start();
	}
}
