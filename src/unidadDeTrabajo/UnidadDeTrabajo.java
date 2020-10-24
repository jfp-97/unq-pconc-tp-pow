package unidadDeTrabajo;

public class UnidadDeTrabajo {

	private int id;
	private int threadsTotales;
	private int tamanioNonce;

	public UnidadDeTrabajo(int id, int threadsTotales, int tamanioNonce) {
		this.id = id;
		this.threadsTotales = threadsTotales;
		this.tamanioNonce = tamanioNonce;
	}

	public long rangoMin() {
		return (long) Math.floor(
			(this.desplazamiento() + this.id * (Math.pow(256, this.tamanioNonce)) / this.threadsTotales) - 1
		);
	}

	public long rangoMax() {
		return (long) Math.floor(
			(this.desplazamiento() + (this.id + 1) * (Math.pow(256, this.tamanioNonce)) / this.threadsTotales) - 1
		);
	}

	private long desplazamiento() {
		return this.id > (256 ^ this.tamanioNonce) % this.threadsTotales
			? 0
			: this.id + 1;
	}

	public static void main(String[] args) {
		int cantThreads = 8;
		int tamNonce = 4;
		UnidadDeTrabajo[] us = new UnidadDeTrabajo[cantThreads];

		for(int i = 0; i < cantThreads; i++) {
			us[i] = new UnidadDeTrabajo(i, cantThreads, tamNonce);
			System.out.println("Udt " + i + ": [" + us[i].rangoMin() + ", " + us[i].rangoMax() + "]");
		}


	}
}
