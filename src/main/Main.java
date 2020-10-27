package main;

import java.util.Scanner;

import monitors.Buffer;
import monitors.NonceResult;
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
		NonceResult nonceResult = new NonceResult();
		ThreadPool threadPool = new ThreadPool(
				buffer, config.getNonceSize(), config.getPrefix(), config.getDifficulty(), nonceResult);
		WorkUnitProducer workUnitProducer = new WorkUnitProducer(
				buffer, config.getThreadAmount(), config.getNonceSize());

		long startTime = System.currentTimeMillis();
		workUnitProducer.start();
		threadPool.initializeThreads(config.getThreadAmount());

		byte[] result = nonceResult.read();
		System.out.println("Time: " + (nonceResult.writeTime() - startTime) + " ms");
		System.out.println("Nonce: " + ByteArrayUtil.byteArrayAsString(result));
		System.out.println("Hashed nonce: " + ByteArrayUtil.byteArrayAsString(
				ByteArrayUtil.hash(result)));
	}
}
