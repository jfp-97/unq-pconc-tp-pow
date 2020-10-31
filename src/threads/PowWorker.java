package threads;

import monitors.Buffer;
import monitors.ResultBuffer;
import searchResult.FoundNonce;
import util.ByteArrayUtil;
import util.Config;
import workUnit.WorkUnit;

public class PowWorker extends Thread {
	private Buffer buffer;
	private ResultBuffer resultBuffer;
	private int nonceSize;
	private byte[] prefix;
	private int difficulty;
	private boolean hasToWork;

	public PowWorker(Buffer buffer, ResultBuffer resultBuffer, Config config) {
		this.buffer = buffer;
		this.resultBuffer = resultBuffer;
		this.nonceSize = config.getNonceSize();
		this.prefix = config.getPrefix();
		this.difficulty = config.getDifficulty();
		this.hasToWork = true;
	}

	private Buffer getBuffer() {
		return this.buffer;
	}

	private ResultBuffer getResultBuffer() {
		return this.resultBuffer;
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
			this.getResultBuffer().write(new FoundNonce(currentNonce));
		else if (currentNum == workUnit.maxRange())
			this.getResultBuffer().incFailures();
	}
}
