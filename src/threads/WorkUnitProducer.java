package threads;

import monitors.Buffer;
import util.Config;
import workUnit.WorkUnit;

public class WorkUnitProducer extends Thread {
	private Buffer buffer;
	private int threadAmount;
	private int nonceSize;

	public WorkUnitProducer(Buffer buffer, Config config) {
		this.buffer = buffer;
		this.threadAmount = config.getThreadAmount();
		this.nonceSize = config.getNonceSize();
	}

	private Buffer getBuffer() {
		return this.buffer;
	}

	private int getThreadAmount() {
		return this.threadAmount;
	}

	public int getNonceSize() {
		return this.nonceSize;
	}

	@Override
	public void run() {
		for (int i = 0; i < this.getThreadAmount(); i++) {
			this.getBuffer().write(new WorkUnit(i, this.getThreadAmount(), this.getNonceSize()));
		}
	}
}
