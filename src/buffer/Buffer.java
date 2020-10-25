package buffer;

import workUnit.WorkUnit;

public class Buffer {
	private int capacity;
	private int readPointer;
	private int nonceSize;
	private WorkUnit[] workUnits = new WorkUnit[1];

	public Buffer(int capacity, WorkUnit workUnit) {
		this.capacity = capacity;
		this.readPointer = 0;
		this.workUnits[0] = workUnit;
	}

	public synchronized WorkUnit read() {

		return workUnits[this.readPointer];
	}
}
