package threads;

import monitors.Buffer;
import monitors.NonceResult;
import util.ByteArrayUtil;
import workUnit.WorkUnit;

public class PowWorker extends Thread {
	private Buffer buffer;
	private int nonceSize;
	private byte[] prefix;
	private int difficulty;
	private NonceResult nonceResult;

	public PowWorker(Buffer buffer, int nonceSize, byte[] prefix, int difficulty, NonceResult nonceResult) {
		this.buffer = buffer;
		this.nonceSize = nonceSize;
		this.prefix = prefix;
		this.difficulty = difficulty;
		this.nonceResult = nonceResult;
	}

	private byte[] prefixConc(byte[] nonce) {
		return ByteArrayUtil.byteArrayConc(this.prefix, nonce);
	}

	@Override
	public void run() {
		WorkUnit workUnit = this.buffer.read();

		long currentNum = workUnit.minRange();
		byte[] currentNonce = ByteArrayUtil.longAsByteArray(currentNum, nonceSize);
		byte[] currentHash = ByteArrayUtil.hash(this.prefixConc(currentNonce));

		while (!(ByteArrayUtil.compliesDifficulty(currentHash, this.difficulty))
				&& currentNum < workUnit.maxRange()
				&& this.nonceResult.isEmpty()) {

			currentNum++;
			currentNonce = ByteArrayUtil.nextByteArray(currentNonce);
			currentHash = ByteArrayUtil.hash(this.prefixConc(currentNonce));
		}

		if (ByteArrayUtil.compliesDifficulty(currentHash, this.difficulty)) {
			this.nonceResult.write(currentNonce);
		} else {
		}
	}
}
