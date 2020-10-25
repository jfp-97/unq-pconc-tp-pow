package main;

import java.util.Scanner;

import buffer.Buffer;
import threads.PowWorker;
import threads.WorkUnitProducer;
import util.Config;

public class Main {

	public static void main(String[] args) {
		Config config = new Config();
		config.setNonceSize(4);
		config.setMaxThreads(16);

		Scanner scanner = new Scanner(System.in);
		config.inputThreadAmount(scanner);
		config.inputDifficulty(scanner);
		config.inputPrefix(scanner);
		scanner.close();

		Buffer buffer = new Buffer(2);
		WorkUnitProducer workUnitProducer = new WorkUnitProducer(buffer, config.getThreadAmount(), config.getNonceSize());

		PowWorker powWorker = new PowWorker(buffer, config.getNonceSize(), config.getPrefix(), config.getDifficulty());

		workUnitProducer.start();
		powWorker.start();
	}
}
