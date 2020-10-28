package threads;

import monitors.Buffer;
import monitors.ThreadPool;
import searchResult.FoundNonce;
import util.ByteArrayUtil;
import workUnit.WorkUnit;

public class PowWorker extends Thread {
	private Buffer buffer;
	private int nonceSize;
	private byte[] prefix;
	private int difficulty;
	private ThreadPool threadPool;
	private boolean hasToWork;

	public PowWorker(
			Buffer buffer, int nonceSize, byte[] prefix, int difficulty, ThreadPool threadPool) {
		this.buffer = buffer;
		this.nonceSize = nonceSize;
		this.prefix = prefix;
		this.difficulty = difficulty;
		this.threadPool = threadPool;
		this.hasToWork = true;
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

	private ThreadPool getThreadPool() {
		return this.threadPool;
	}

	private boolean getHasToWork() {
		return this.hasToWork;
	}

	private void setHasToWork(boolean hasToWork) {
		this.hasToWork = hasToWork;
	}

	private byte[] prefixConc(byte[] nonce) {
		return ByteArrayUtil.byteArrayConc(this.getPrefix(), nonce);
	}

	public void stopWorking() {
		this.setHasToWork(false);
	}

	@Override
	public void run() {
		WorkUnit workUnit = this.getBuffer().read();

		long currentNum = workUnit.minRange();
		byte[] currentNonce = ByteArrayUtil.longAsByteArray(currentNum, this.getNonceSize());
		byte[] currentHash = ByteArrayUtil.hash(this.prefixConc(currentNonce));

		while (!(ByteArrayUtil.compliesDifficulty(currentHash, this.getDifficulty()))
				&& currentNum < workUnit.maxRange()
				&& this.getHasToWork()) {

			currentNum++;
			currentNonce = ByteArrayUtil.nextByteArray(currentNonce);
			currentHash = ByteArrayUtil.hash(this.prefixConc(currentNonce));
		}

		if (ByteArrayUtil.compliesDifficulty(currentHash, this.getDifficulty()))
			this.getThreadPool().write(new FoundNonce(currentNonce));
		else if (currentNum == workUnit.maxRange())
			this.getThreadPool().incFailedThreads();
	}
}
