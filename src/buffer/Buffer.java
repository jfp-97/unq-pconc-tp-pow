package buffer;

import unidadDeTrabajo.UnidadDeTrabajo;

public class Buffer {
	private int capacidad;
	private int readPointer;
	private int tamanioNonce;
	private UnidadDeTrabajo[] unidadesDeTrabajo;

	public Buffer(int capacidad) {
		this.capacidad = capacidad;
		this.readPointer = 0;
	}

	public synchronized UnidadDeTrabajo leer() {

		return unidadesDeTrabajo[this.readPointer];
	}
}
