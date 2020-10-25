package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ByteArrayUtil {

	public static String byteArrayAsString(byte[] bs) {
		String printable = "{ ";

		for (int i = 0; i < bs.length; i++) {
			printable += bs[i] + ", ";
		}

		return printable.substring(0, printable.length() - 2) + " }";
	}

	public static boolean compliesDifficulty(byte[] bs, int difficulty) {
		boolean complies = true;

		for (int i = 0; i < difficulty; i++) {
			complies &= bs[i] == 00;
		}

		return complies;
	}

	// Precondición: (nro - 1) ^ (1/tamanio) < 256
	// De no cumplirse la precondición, se genera overflow
	public static byte[] longAsByteArray(long n, int size) {
		byte[] bs = new byte[size];
		long current = n;

		for (int i = 0; i < size; i++) {
			bs[size - i - 1] = (byte) (current % 256 - 128);
			current = (long) Math.floor(current / 256);
		}

		return bs;
	}

	public static byte[] nextByteArray(byte[] bs) {
		byte[] next = bs;
		int i = bs.length - 1;
		next[i]++;
		while (next[i] == -128 && i > 0) {
			i--;
			next[i]++;
		}

		return next;
	}

	public static byte[] byteArrayConc(byte[] bs1, byte[] bs2) {
		byte[] conc  = new byte[bs1.length + bs2.length];

		System.arraycopy(bs1, 0, conc, 0, bs1.length);
        System.arraycopy(bs2, 0, conc, bs1.length, bs2.length);

		return conc;
	}

	public static byte[] hash(byte[] bs) {
		try {
			return MessageDigest.getInstance("SHA-256").digest(bs);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return bs;
		}
	}
}
