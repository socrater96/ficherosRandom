package practicaFicheros;

import java.io.IOException;
import java.util.Scanner;

public class PracticaFicheros {
	static int menu(Scanner in) throws IOException {
		int opcion=0;
		Teclado t= new Teclado();
		do {
			System.out.println("\tMENÚ");
			System.out.println("1. Altas");
			System.out.println("2. Bajas");
			System.out.println("3. Modificaciones");
			System.out.println("4. Consultas");
			System.out.println("5. Listados");
			System.out.println("6. Fin");
			opcion=t.leerInt();
		}while(opcion==Integer.MIN_VALUE);
		return opcion;
	}
	static void altas(Scanner in) throws IOException{
		
		System.out.println("Introduzca numero de persona");
		
	}
	public static void main(String []args) throws IOException {
		Scanner in = new Scanner(System.in);
		int opcion=0;
		do {
			opcion=menu(in);
			switch(opcion) {
			case 1:
				altas(in);
				break;
			case 2:
				
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				
			}
		}while(opcion!=6);
	}
}
