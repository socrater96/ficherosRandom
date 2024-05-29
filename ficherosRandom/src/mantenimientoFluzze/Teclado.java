package mantenimientoFluzze;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Teclado {
	Scanner e = new Scanner(System.in);
	
	int leerInt() {
		int numero;
		do {
			try {
				numero = e.nextInt();
				e.nextLine();
			}catch(InputMismatchException ime) {
				e.nextLine();
				numero=Integer.MIN_VALUE;
				System.out.println("Formato numérico incorrecto, porfavor intentelo de nuevo.");
			}
		}while(numero == Integer.MIN_VALUE);
		
		return numero;
		}
	
    String leerString() {
        String nombre;
        do {
            nombre = e.nextLine();
        } while (nombre.length() > 20 || !nombre.matches("[a-zA-Z\\s]+$"));

        // Si el nombre < 20 carateres, añade espacios.
        nombre = String.format("%-20s", nombre);

        return nombre;
    }
    
	Double leerDouble() {
		Double numero;
		do {
			try {
				numero = e.nextDouble();
				e.nextLine();
			}catch(InputMismatchException ime) {
				e.nextLine();
				numero=Double.MIN_VALUE;
				System.out.println("Formato numérico incorrecto, porfavor intentelo de nuevo.");
			}
		}while(numero == Double.MIN_VALUE);
		return numero;
		}

	Float leerFloat() {
		Float numero;
		do {
			try {
				numero = e.nextFloat();
				e.nextLine();
			}catch(InputMismatchException ime) {
				e.nextLine();
				numero=Float.MIN_VALUE;
				System.out.println("Formato numérico incorrecto, porfavor intentelo de nuevo.");
			}
		}while(numero == Float.MIN_VALUE);
		return numero;
		}
	char leerChar(){
		String letra = "";
		do {
			letra = e.nextLine().toUpperCase();
			if (letra.length() != 1)
				System.out.println("Formato de caracter incorrecto, porfavor intentelo de nuevo.");
		}while(letra.length()!=1);
		return letra.charAt(0);
	}
	public void pulsarEnter() {
		e.nextLine();
	}
	void cerrarScanner() {
		e.close();
	}
	
	}
