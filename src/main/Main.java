package main;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int cantThreads;

		do {
			System.out.println("Ingrese la cantidad de threads a utilizar (entre 1 y 16 inclusive)");
			cantThreads = Integer.parseInt(input.nextLine());
			if (cantThreads < 0 || cantThreads > 16)
				System.out.println("La cantidad de threads ingresada es inválida");
		} while (cantThreads < 0 || cantThreads > 16);

		System.out.println("Ingrese la dificultad objetivo");
		int dificultad = Integer.parseInt(input.nextLine());

		System.out.println("Ingrese el prefijo");
		String prefijo = input.nextLine();

		input.close();


	}
}
