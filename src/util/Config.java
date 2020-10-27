package util;

import java.util.Scanner;

public class Config {
	private int nonceSize;
	private int threadAmount;
	private int difficulty;
	private int maxThreads;
	private byte[] prefix;

	public int getThreadAmount() {
		return this.threadAmount;
	}

	public void setThreadAmount(int threadAmount) {
		this.threadAmount = threadAmount;
	}

	public int getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public byte[] getPrefix() {
		return this.prefix;
	}

	public void setPrefix(byte[] prefix) {
		this.prefix = prefix;
	}

	public int getNonceSize() {
		return this.nonceSize;
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

		System.out.println("Enter amount of threads (between 1 and " + this.getMaxThreads() + ")");
		int input = Integer.parseInt(scanner.nextLine());

		while (input < 1
				|| input > this.getMaxThreads()) {

			System.out.println("The specified amount of threads is invalid");
			System.out.println("Enter amount of threads (between 1 and " + this.getMaxThreads() + ")");
			input = Integer.parseInt(scanner.nextLine());
		}
		this.setThreadAmount(input);
	}

	public void inputDifficulty(Scanner scanner) {
		System.out.println("Enter difficulty");
		this.setDifficulty(Integer.parseInt(scanner.nextLine()));
	}

	public void inputPrefix(Scanner scanner) {
		System.out.println("Enter prefix");
		this.setPrefix(scanner.nextLine().getBytes());
	}
}
