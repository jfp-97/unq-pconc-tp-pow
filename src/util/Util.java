package util;

public class Util {

	public static String byteArrayAString(byte[] bytes) {
		String arreglo = "{ ";

		for (int i = 0; i < bytes.length; i++) {
			arreglo += bytes[i] + ", ";
		}

		return arreglo.substring(0, arreglo.length() - 2) + " }";
	}

	public static boolean cumpleDificultad(byte[] hash, int dificultad) {
		boolean cumple = true;

		for (int i = 0; i < dificultad; i++) {
			cumple &= hash[i] == 00;
		}

		return cumple;
	}

	// Precondición: (nro - 1) ^ (1/tamanio) < 256
	// De no cumplirse la precondición, se genera overflow
	public static byte[] longAByteArray(long nro, int tamanio) {
		byte[] bytes = new byte[tamanio];
		long nroActual = nro;

		for (int i = 0; i < tamanio; i++) {
			bytes[tamanio - i - 1] = (byte) (nroActual % 256 - 128);
			nroActual = (long) Math.floor(nroActual / 256);
		}

		return bytes;
	}

	public static byte[] siguienteByteArray(byte[] bytes) {
		byte[] siguiente = bytes;
		int i = bytes.length - 1;
		siguiente[i]++;
		while (siguiente[i] == -128 && i > 0) {
			i--;
			siguiente[i]++;
		}

		return siguiente;
	}

	public static byte[] concatenarByteArrays(byte[] bs1, byte[] bs2) {
		byte[] concat  = new byte[bs1.length + bs2.length];

		System.arraycopy(bs1, 0, concat, 0, bs1.length);
        System.arraycopy(bs2, 0, concat, bs1.length, bs2.length);

		return concat;
	}

	public static void main(String[] args) {
		byte[] bytes = { 10, 0, 0, -128, -128, 0, 97 };
		byte[] bytes2 = { 2, 3, 62, -22, 4 };

		System.out.println(
			Util.byteArrayAString(
				Util.siguienteByteArray(Util.longAByteArray(4294967294l, 4))
			)
		);
	}
}
