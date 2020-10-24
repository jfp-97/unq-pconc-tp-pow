package powWorker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import unidadDeTrabajo.UnidadDeTrabajo;
import util.Util;

public class PowWorker extends Thread {
	private UnidadDeTrabajo buffer;
	private int tamanioNonce;
	private byte[] prefijo;
	private int dificultad;

	public PowWorker(UnidadDeTrabajo buffer, int tamanioNonce, String prefijo, int dificultad) {
		this.buffer = buffer;
		this.tamanioNonce = tamanioNonce;
		this.prefijo = prefijo.getBytes();
		this.dificultad = dificultad;
	}

	@Override
	public void run() {
		UnidadDeTrabajo udt = this.buffer;

		long numeroActual = udt.rangoMin();
		byte[] nonceActual = Util.longAByteArray(udt.rangoMin(), tamanioNonce);

		byte[] bytesAHashear = Util.concatenarByteArrays(this.prefijo, nonceActual);
		byte[] hashActual = null;
		try {
			hashActual = MessageDigest.getInstance("SHA-256").digest(bytesAHashear);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		while (!(Util.cumpleDificultad(hashActual, this.dificultad)) && numeroActual < udt.rangoMax()) {
			numeroActual++;
			nonceActual = Util.siguienteByteArray(nonceActual);

			bytesAHashear = Util.concatenarByteArrays(this.prefijo, nonceActual);
			try {
				hashActual = MessageDigest.getInstance("SHA-256").digest(bytesAHashear);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			System.out.println("Checked " + Util.byteArrayAString(hashActual));
		}

		if (Util.cumpleDificultad(hashActual, this.dificultad)) {
			System.out.println("Found " + Util.byteArrayAString(hashActual));
			System.out.println("For nonce " + Util.byteArrayAString(nonceActual));
		} else {
			System.out.println("no, che");
		}
	}
}
