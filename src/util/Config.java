package util;

import java.util.Scanner;

public class Config {
	private int nonceSize;
	private int threadAmount;
	private int difficulty;
	private int maxThreads;
	private byte[] prefix;

	public int getThreadAmount() {
		return threadAmount;
	}

	public void setThreadAmount(int threadAmount) {
		this.threadAmount = threadAmount;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public byte[] getPrefix() {
		return prefix;
	}

	public void setPrefix(byte[] prefix) {
		this.prefix = prefix;
	}

	public int getNonceSize() {
		return nonceSize;
	}

	public void setNonceSize(int nonceSize) {
		this.nonceSize = nonceSize;
	}

	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	public int getMaxThreads() {
		return this.maxThreads;
	}

	public void inputThreadAmount(Scanner scanner) {

		System.out.println("Ingrese la cantidad de threads a utilizar (entre 1 y 16 inclusive)");
		int input = Integer.parseInt(scanner.nextLine());

		while (input < 1
				|| input > this.getMaxThreads()) {

			System.out.println("La cantidad de threads ingresada es inválida");
			System.out.println("Ingrese la cantidad de threads a utilizar (entre 1 y 16 inclusive)");
			input = Integer.parseInt(scanner.nextLine());
		}
		this.setThreadAmount(input);
	}

	public void inputDifficulty(Scanner scanner) {
		System.out.println("Ingrese la dificultad objetivo");
		this.setDifficulty(Integer.parseInt(scanner.nextLine()));
	}

	public void inputPrefix(Scanner scanner) {
		System.out.println("Ingrese el prefijo");
		this.setPrefix(scanner.nextLine().getBytes());
	}
}
