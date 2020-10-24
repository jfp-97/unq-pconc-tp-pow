package powWorker;

import buffer.Buffer;
import unidadDeTrabajo.UnidadDeTrabajo;

public class PowWorker extends Thread {
	private Buffer buffer;
	private String prefijo;
	private int dificultad;

	public PowWorker(Buffer buffer, String prefijo, int dificultad) {
		this.buffer = buffer;
		this.prefijo = prefijo;
		this.dificultad = dificultad;
	}

	@Override
	public void run() {
		UnidadDeTrabajo udt = buffer.leer();


	}
}
