package main;

import buffer.Buffer;
import threads.PowWorker;
import util.Config;
import workUnit.WorkUnit;

public class Main {

	public static void main(String[] args) {
		Config config = new Config();
		config.setNonceSize(4);
		config.setMaxThreads(1);

		config.inputThreadAmount();
		config.inputDifficulty();
		config.inputPrefix();

		WorkUnit workUnit = new WorkUnit(0, config.getThreadAmount(), 4);
		Buffer buffer = new Buffer(2, workUnit);
		PowWorker powWorker = new PowWorker(buffer, config.getNonceSize(), config.getPrefix(), config.getDifficulty());
		powWorker.start();
	}
}
