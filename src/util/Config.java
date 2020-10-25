package util;

public class Config {
	private int nonceSize;
	private int threadAmount;
	private int difficulty;
	private int maxThreads;
	private byte[] prefix;

	public int getThreadAmount() {
		return threadAmount;
	}

	public void setThreadAmount(int threadAmount) {
		this.threadAmount = threadAmount;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public byte[] getPrefix() {
		return prefix;
	}

	public void setPrefix(byte[] prefix) {
		this.prefix = prefix;
	}

	public int getNonceSize() {
		return nonceSize;
	}

	public void setNonceSize(int nonceSize) {
		this.nonceSize = nonceSize;
	}

	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	public int getMaxThreads() {
		return this.maxThreads;
	}
}
