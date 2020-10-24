package unidadDeTrabajo;

public class UnidadDeTrabajo {

	private int id;
	private long combinacionesPosibles;
	private long combinacionesPorUnidad;
	private long combinacionesRestantes;

	public UnidadDeTrabajo(int id, int cantThreads, int tamanioNonce) {
		this.id = id;
		this.combinacionesPosibles = (long) Math.pow(256, tamanioNonce);
		this.combinacionesPorUnidad = (long) Math.floor(this.combinacionesPosibles / cantThreads);
		this.combinacionesRestantes = this.combinacionesPosibles % cantThreads;
	}

	private int getId() {
		return this.id;
	}

	private long getCombinacionesPosibles() {
		return this.combinacionesPosibles;
	}

	private long getCombinacionesPorUnidad() {
		return this.combinacionesPorUnidad;
	}

	private long getCombinacionesRestantes() {
		return this.combinacionesRestantes;
	}

	public long rangoMin() {
		return this.desplazamiento(this.getId()) + this.getId() * this.getCombinacionesPorUnidad();
	}

	public long rangoMax() {
		return this.desplazamiento(this.getId() + 1) + (this.getId() + 1) * this.getCombinacionesPorUnidad() - 1;
	}

	private long desplazamiento(int id) {
		return Math.min(id, this.getCombinacionesRestantes());
	}

	public static void main(String[] args) {
		int cantThreads = 10;
		int tamNonce = 4;
		UnidadDeTrabajo[] us = new UnidadDeTrabajo[cantThreads];

		for(int i = 0; i < cantThreads; i++) {
			us[i] = new UnidadDeTrabajo(i, cantThreads, tamNonce);
			System.out.println("U. d. t. " + i + ": [" + us[i].rangoMin() + ", " + us[i].rangoMax() + "]");
		}


	}
}
