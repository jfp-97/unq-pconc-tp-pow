package monitors;

import workUnit.WorkUnit;

public class Buffer {
	private int capacity;
	private WorkUnit[] workUnits;
	private int readPointer;
	private int writePointer;

	public Buffer(int capacity) {
		this.capacity = capacity;
		this.workUnits = new WorkUnit[capacity + 1];
		this.readPointer = 0;
		this.writePointer = 0;
	}

	private int getCapacity() { return this.capacity; }

	private int getReadPointer() {
		return this.readPointer;
	}

	private void setReadPointer(int readPointer) {
		this.readPointer = readPointer;
	}

	private int getWritePointer() {
		return this.writePointer;
	}

	private void setWritePointer(int writePointer) {
		this.writePointer = writePointer;
	}

	private WorkUnit[] getWorkUnits() {
		return this.workUnits;
	}

	private int next(int n) {
		return (n + 1) % (this.getCapacity() + 1);
	}

	private boolean isFull() {
		return this.next(this.getWritePointer()) == this.getReadPointer();
	}

	private boolean isEmpty() {
		return this.getReadPointer() == this.getWritePointer();
	}

	public synchronized WorkUnit read() {
		while (this.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		WorkUnit workUnit = this.getWorkUnits()[this.getReadPointer()];
		this.setReadPointer(this.next(this.getReadPointer()));
		notifyAll();
		return workUnit;
	}

	public synchronized void write(WorkUnit workUnit) {
		while (this.isFull()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		this.getWorkUnits()[this.getWritePointer()] = workUnit;
		this.setWritePointer(this.next(this.getWritePointer()));
		notifyAll();
	}
}
