package main;

import java.util.Scanner;

import monitors.Buffer;
import monitors.ThreadPool;
import searchResult.SearchResult;
import threads.WorkUnitProducer;
import util.Config;
import util.Timer;

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
		ThreadPool threadPool = new ThreadPool(
				buffer, config.getThreadAmount(), config.getNonceSize(), config.getPrefix(), config.getDifficulty());
		WorkUnitProducer workUnitProducer = new WorkUnitProducer(
				buffer, config.getThreadAmount(), config.getNonceSize());
		Timer timer = new Timer();

		timer.startRunning();
		workUnitProducer.start();
		threadPool.searchNonce();

		SearchResult searchResult = threadPool.read();
		timer.stopRunning();

		searchResult.printResult();
		timer.printTime();
	}
}
