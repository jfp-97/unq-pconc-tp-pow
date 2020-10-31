package monitors;


import threads.PowWorker;
import util.Config;

public class ThreadPool {
	private PowWorker[] workers;

	private PowWorker[] getWorkers() {
		return this.workers;
	}

	private void setWorkers(PowWorker[] workers) {
		this.workers = workers;
	}

	public void startSearching(Buffer buffer, ResultBuffer resultBuffer, Config config) {
		this.setWorkers(new PowWorker[config.getThreadAmount()]);

		for (int i = 0; i < config.getThreadAmount(); i++) {
			this.getWorkers()[i] = new PowWorker(buffer, resultBuffer, config);
			this.getWorkers()[i].start();
		}
	}

	public void stopSearching() {
		for (PowWorker worker : this.getWorkers()) {
			worker.stopWorking();
		}
	}
}
