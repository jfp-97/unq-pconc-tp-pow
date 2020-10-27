package main;

import java.util.Scanner;

import monitors.Buffer;
import monitors.ThreadPool;
import threads.WorkUnitProducer;
import util.ByteArrayUtil;
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
		ThreadPool threadPool = new ThreadPool(
				buffer, config.getNonceSize(), config.getPrefix(), config.getDifficulty());
		WorkUnitProducer workUnitProducer = new WorkUnitProducer(
				buffer, config.getThreadAmount(), config.getNonceSize());

		workUnitProducer.start();
		threadPool.searchNonce(config.getThreadAmount());

		byte[] result = threadPool.readNonce();
		System.out.println("Nonce: " + ByteArrayUtil.byteArrayAsString(result));
		System.out.println("Hashed nonce: " + ByteArrayUtil.byteArrayAsString(
				ByteArrayUtil.hash(result)));
		System.out.println("Time: " + (threadPool.timeElapsed()) + " ms");
	}
}
