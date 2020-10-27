package monitors;

public class NonceResult {
	private byte[] nonceResult;
	private boolean isEmpty;
	private long writeTime;

	public NonceResult() {
		this.isEmpty = true;
	}

	private byte[] getNonceResult() {
		return this.nonceResult;
	}

	private void setNonceResult(byte[] nonceResult) {
		this.nonceResult = nonceResult;
	}

	private long getWriteTime() {
		return this.writeTime;
	}

	private void setWriteTime(long writeTime) {
		this.writeTime = writeTime;
	}

	public boolean isEmpty() {
		return this.isEmpty;
	}

	private boolean isFull() {
		return !(this.isEmpty());
	}

	private void setFull() {
		this.isEmpty = false;
	}

	public synchronized void write(byte[] nonce) {
		if (this.isFull()) return;

		this.setWriteTime(System.currentTimeMillis());
		this.setNonceResult(nonce);
		this.setFull();
		notifyAll();
	}

	public synchronized byte[] read() {
		while (this.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
		return this.getNonceResult();
	}

	public synchronized long writeTime() {
		while (this.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
		return this.getWriteTime();
	}
}
