package main;

import java.util.Scanner;

import buffer.Buffer;
import threads.PowWorker;
import util.Config;
import workUnit.WorkUnit;

public class Main {

	public static void main(String[] args) {
		Config config = new Config();
		config.setNonceSize(4);
		config.setMaxThreads(1);

		Scanner scanner = new Scanner(System.in);

		System.out.println("Ingrese la cantidad de threads a utilizar (entre 1 y 16 inclusive)");
		int threadInput = Integer.parseInt(scanner.nextLine());

		while (threadInput < 1
				|| threadInput > config.getMaxThreads()) {

			System.out.println("La cantidad de threads ingresada es inválida");
			System.out.println("Ingrese la cantidad de threads a utilizar (entre 1 y 16 inclusive)");
			threadInput = Integer.parseInt(scanner.nextLine());
		}
		config.setThreadAmount(threadInput);

		System.out.println("Ingrese la dificultad objetivo");
		config.setDifficulty(Integer.parseInt(scanner.nextLine()));

		System.out.println("Ingrese el prefijo");
		config.setPrefix(scanner.nextLine().getBytes());

		scanner.close();

		WorkUnit workUnit = new WorkUnit(0, config.getThreadAmount(), 4);
		Buffer buffer = new Buffer(2, workUnit);
		PowWorker powWorker = new PowWorker(buffer, config.getNonceSize(), config.getPrefix(), config.getDifficulty());
		powWorker.start();
	}
}
