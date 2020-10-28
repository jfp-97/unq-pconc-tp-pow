package util;

public class Timer {
	private long startTime;
	private long stopTime;

	public Timer() {}

	private long getStartTime() {
		return this.startTime;
	}

	private void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	private long getStopTime() {
		return this.stopTime;
	}

	private void setStopTime(long stopTime) {
		this.stopTime = stopTime;
	}

	public void startRunning() {
		this.setStartTime(System.currentTimeMillis());
	}

	public void stopRunning() {
		this.setStopTime(System.currentTimeMillis());
	}

	public long elapsedTime() {
		return this.getStopTime() - this.getStartTime();
	}

	public void printTime() {
		System.out.println("Elapsed time: " + (this.elapsedTime()) + " ms");
	}
}
