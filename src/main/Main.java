package main;

import java.util.Scanner;

import monitors.Buffer;
import monitors.ThreadPool;
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
		ThreadPool threadPool = new ThreadPool(buffer, config.getNonceSize(), config.getPrefix(), config.getDifficulty());
		WorkUnitProducer workUnitProducer = new WorkUnitProducer(buffer, config.getThreadAmount(), config.getNonceSize());

		workUnitProducer.start();
		threadPool.initializeThreads(config.getThreadAmount());
	}
}
