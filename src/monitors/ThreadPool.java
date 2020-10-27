package monitors;


import threads.PowWorker;

public class ThreadPool {
	private Buffer buffer;
	private int nonceSize;
	private byte[] prefix;
	private int difficulty;
	private PowWorker[] powWorkers;
	private NonceResult nonceResult;

	public ThreadPool(Buffer buffer, int nonceSize, byte[] prefix, int difficulty, NonceResult nonceResult) {
		this.buffer = buffer;
		this.nonceSize = nonceSize;
		this.prefix = prefix;
		this.difficulty = difficulty;
		this.nonceResult = nonceResult;
	}

	public void initializeThreads(int threadAmount) {
		this.powWorkers = new PowWorker[threadAmount];
		for (int i = 0; i < threadAmount; i++) {
			this.powWorkers[i] = new PowWorker(this.buffer, this.nonceSize, this.prefix, this.difficulty, this.nonceResult);
			this.powWorkers[i].start();
		}
	}
}
