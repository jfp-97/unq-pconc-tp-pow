package monitors;


import threads.PowWorker;

public class ThreadPool {
	private Buffer buffer;
	private int nonceSize;
	private byte[] prefix;
	private int difficulty;
	private PowWorker[] powWorkers;
	private boolean hasNonce;
	private long startTime;
	private long finishTime;
	private byte[] nonceResult;

	public ThreadPool(Buffer buffer, int nonceSize, byte[] prefix, int difficulty) {
		this.buffer = buffer;
		this.nonceSize = nonceSize;
		this.prefix = prefix;
		this.difficulty = difficulty;
		this.hasNonce = false;
	}

	private void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	private void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	private void setNonceResult(byte[] nonceResult) {
		this.nonceResult = nonceResult;
	}

	public void searchNonce(int threadAmount) {
		this.setStartTime(System.currentTimeMillis());
		this.powWorkers = new PowWorker[threadAmount];
		for (int i = 0; i < threadAmount; i++) {
			this.powWorkers[i] = new PowWorker(
					this.buffer, this.nonceSize, this.prefix, this.difficulty, this);
			this.powWorkers[i].start();
		}
	}

	public synchronized void writeNonce(byte[] nonceResult) {
		if (this.hasNonce) return;

		this.hasNonce = true;

		for (PowWorker powWorker : this.powWorkers) {
			powWorker.stopWorking();
		}

		this.setNonceResult(nonceResult);
		notifyAll();
		this.setFinishTime(System.currentTimeMillis());
	}

	public synchronized byte[] readNonce() {
		while (!(this.hasNonce)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
		return this.nonceResult;
	}

	public long timeElapsed() {
		return this.finishTime - this.startTime;
	}
}
