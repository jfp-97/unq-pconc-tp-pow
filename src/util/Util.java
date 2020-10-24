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
			cumple &= hash[i] == 0;
		}

		return cumple;
	}

	public static byte[] longAByteArray() {
		return new byte[2];
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

	public static void main(String[] args) {
		// byte[] bytes = { 0, 0, -128, -128, 0 };
		byte[] bytes = "0".getBytes();

		System.out.println(4294967295l%256);
	}
}
