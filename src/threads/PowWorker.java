package threads;

import buffer.Buffer;
import util.ByteArrayUtil;
import workUnit.WorkUnit;

public class PowWorker extends Thread {
	private Buffer buffer;
	private int nonceSize;
	private byte[] prefix;
	private int difficulty;

	public PowWorker(Buffer buffer, int nonceSize, byte[] prefix, int difficulty) {
		this.buffer = buffer;
		this.nonceSize = nonceSize;
		this.prefix = prefix;
		this.difficulty = difficulty;
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
				&& currentNum < workUnit.maxRange()) {

			currentNum++;
			currentNonce = ByteArrayUtil.nextByteArray(currentNonce);
			currentHash = ByteArrayUtil.hash(this.prefixConc(currentNonce));
			System.out.println("Checked " + ByteArrayUtil.byteArrayAsString(currentHash));
		}

		if (ByteArrayUtil.compliesDifficulty(currentHash, this.difficulty)) {
			System.out.println("Found " + ByteArrayUtil.byteArrayAsString(currentHash));
			System.out.println("For nonce " + ByteArrayUtil.byteArrayAsString(currentNonce));
		} else {
			System.out.println("no, che");
		}
	}
}
