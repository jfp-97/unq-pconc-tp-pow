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

	private long getStartTime() {
		return this.startTime;
	}

	private void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	private long getFinishTime() {
		return this.finishTime;
	}

	private void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	private byte[] getNonceResult() {
		return this.nonceResult;
	}

	private void setNonceResult(byte[] nonceResult) {
		this.nonceResult = nonceResult;
	}

	private PowWorker[] getPowWorkers() {
		return this.powWorkers;
	}

	private void setPowWorkers(PowWorker[] powWorkers) {
		this.powWorkers = powWorkers;
	}

	private Buffer getBuffer() {
		return this.buffer;
	}

	private int getNonceSize() {
		return this.nonceSize;
	}

	private byte[] getPrefix() {
		return this.prefix;
	}

	private int getDifficulty() {
		return this.difficulty;
	}

	private boolean getHasNonce() {
		return this.hasNonce;
	}

	private void setHasNonce(boolean hasNonce) {
		this.hasNonce = hasNonce;
	}

	public void searchNonce(int threadAmount) {
		this.setStartTime(System.currentTimeMillis());
		this.setPowWorkers(new PowWorker[threadAmount]);

		for (int i = 0; i < threadAmount; i++) {
			this.getPowWorkers()[i] = new PowWorker(
					this.getBuffer(), this.getNonceSize(), this.getPrefix(), this.getDifficulty(), this);
			this.getPowWorkers()[i].start();
		}
	}

	public synchronized void writeNonce(byte[] nonceResult) {
		if (this.getHasNonce()) return;

		this.setHasNonce(true);

		for (PowWorker powWorker : this.getPowWorkers()) {
			powWorker.stopWorking();
		}

		this.setNonceResult(nonceResult);
		notifyAll();
		this.setFinishTime(System.currentTimeMillis());
	}

	public synchronized byte[] readNonce() {
		while (!(this.getHasNonce())) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
		return this.getNonceResult();
	}

	public long timeElapsed() {
		return this.getFinishTime() - this.getStartTime();
	}
}
