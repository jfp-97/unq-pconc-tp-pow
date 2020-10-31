package main;

import java.util.Scanner;

import monitors.Buffer;
import monitors.ResultBuffer;
import monitors.ThreadPool;
import searchResult.SearchResult;
import threads.WorkUnitProducer;
import util.Config;
import util.Timer;

public class Main {

	public static void main(String[] args) {
		Config config = new Config();
		config.setNonceSize(4);

		Scanner scanner = new Scanner(System.in);
		config.inputThreadAmount(scanner);
		config.inputDifficulty(scanner);
		config.inputPrefix(scanner);
		scanner.close();

		Buffer buffer = new Buffer(2);
		ResultBuffer resultBuffer = new ResultBuffer(config.getThreadAmount());

		Timer timer = new Timer();

		WorkUnitProducer workUnitProducer = new WorkUnitProducer(buffer, config);
		ThreadPool threadPool = new ThreadPool();

		timer.startRunning();
		workUnitProducer.start();
		threadPool.startSearching(buffer, resultBuffer, config);

		SearchResult searchResult = resultBuffer.read();
		threadPool.stopSearching();
		timer.stopRunning();

		searchResult.printResult();
		timer.printTime();
	}
}
