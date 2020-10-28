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

	private byte[] prefixConc(byte[] nonce) {
		return ByteArrayUtil.byteArrayConc(this.prefix, nonce);
	}

	public void stopWorking() {
		this.hasToWork = false;
	}

	@Override
	public void run() {
		WorkUnit workUnit = this.buffer.read();

		long currentNum = workUnit.minRange();
		byte[] currentNonce = ByteArrayUtil.longAsByteArray(currentNum, nonceSize);
		byte[] currentHash = ByteArrayUtil.hash(this.prefixConc(currentNonce));

		while (!(ByteArrayUtil.compliesDifficulty(currentHash, this.difficulty))
				&& currentNum < workUnit.maxRange()
				&& this.hasToWork) {

			currentNum++;
			currentNonce = ByteArrayUtil.nextByteArray(currentNonce);
			currentHash = ByteArrayUtil.hash(this.prefixConc(currentNonce));
		}

		if (ByteArrayUtil.compliesDifficulty(currentHash, this.difficulty)) {
			this.threadPool.write(
					new FoundNonce(currentNonce));
		} else if (currentNum == workUnit.maxRange()) {
			this.threadPool.incFailedThreads();
		}
	}
}
