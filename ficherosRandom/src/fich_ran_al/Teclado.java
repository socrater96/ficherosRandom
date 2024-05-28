package fich_ran_al;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Teclado {
	public static final Scanner e = new Scanner(System.in);
	
	public int leerInt(String mensaje) {
		int numero = Integer.MIN_VALUE;
		do {
			try {
				System.out.print(mensaje);
				numero = Integer.parseInt(e.nextLine());
			}catch(NumberFormatException nfe) {
				numero = Integer.MIN_VALUE;
			}
			
		}while (numero == Integer.MIN_VALUE);
		return numero;
	}
	public double leerDouble(String mensaje) {
		double numero;
		do {
			try {
				System.out.print(mensaje);
				numero = e.nextDouble();
				e.nextLine();
			}catch(InputMismatchException ime) {
				numero = Double.MIN_VALUE;
			}
		}while (numero == Double.MIN_VALUE);
		return numero;
	}
	
	public String leerString(String mensaje) {
		String texto;
		do {
			System.out.print(mensaje);
			texto = e.nextLine();
		}while(texto.length()>25 || texto.isBlank());
		return texto + " ".repeat(25-texto.length());
	}
	public char leerChar(String mensaje) {
		String texto;
		do {
			System.out.print(mensaje);
			texto = e.nextLine().toUpperCase();
		}while(texto.length()!=1 || texto.isBlank());
		return texto.charAt(0);
	}
	public void pulsarEnter() {
		e.nextLine();
	}
	public void cerrarScanner() {
		e.close();
	}
}