package ficherosRandom;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.io.EOFException;

public class FicherosRandom {
	static final int tam = 4 + 25 + 2 + 4 + 4 + 1;
	static int menu(Scanner in) {
		int opcion=0;
		do {
			System.out.println("\tMENÚ");
			System.out.println("1.Alta");
			System.out.println("2.Listado");
			System.out.println("3.Consulta");
			System.out.println("4.Baja");
			System.out.println("5.Fin");
			System.out.println("Seleccione opción: (1-5)");
			try {
				opcion=Integer.parseInt(in.nextLine());
			}catch(NumberFormatException nfe) {
				System.out.println("Valor no numérico");
			}
		}while(opcion<1||opcion>5);
		return opcion;
	}
	static void alta(Scanner in) throws IOException {
		RandomAccessFile file = new RandomAccessFile("archivo.dat","rw");
		Alumno alumno = new Alumno();
		do {
			System.out.println("Número de alumno: ");
		}while(!alumno.setNumero(in.nextLine()));
		do {
			System.out.println("Nombre: ");
		}while(!alumno.setNombre(in.nextLine()));
		do{
			System.out.println("Nota 1: ");
		}while(!alumno.setNota1(in.nextLine()));
		do {
			System.out.println("Nota 2: ");
		}while(!alumno.setNota2(in.nextLine()));
		do {
			System.out.println("Nota 3: ");
		}while(!alumno.setNota3(in.nextLine()));
		file.write(alumno.getNumero());
		file.writeUTF(alumno.getNombre());
		file.writeDouble(alumno.getNota1());
		file.writeDouble(alumno.getNota2());
		file.writeDouble(alumno.getNota3());
		System.out.print(alumno);
		
	}
	static void listado() throws IOException, EOFException{
		RandomAccessFile file = new RandomAccessFile("archivo.dat","r");
		int i=0;
		while (file.length() > i) {
			Alumno alumno = new Alumno(file.readInt(),file.readUTF(),file.readDouble(),file.readDouble(),file.readDouble());
			System.out.println(alumno);
			i+=tam;
		}
	}
	
	static void consulta(Scanner in) {
		
	}
	static void baja(Scanner in) {
		
	}
	public static void main(String []args)throws IOException, EOFException {
		RandomAccessFile file = new RandomAccessFile("archivo.dat", "rw");
		file.close();
		Scanner in = new Scanner(System.in);
		int opcion=0;
		do {
			opcion=menu(in);
			switch(opcion) {
				case 1:
					alta(in);
					break;
				case 2:
					listado();
					break;
				case 3:
					consulta(in);
					break;
				case 4:
					baja(in);
					break;
				case 5:
					System.out.println("Fin de programa");
			}
		}while(opcion!=5);
	}
}
