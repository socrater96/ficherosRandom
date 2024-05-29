package practicaFicheros;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class PracticaFicheros {
	static final int tamanho=4+(20+2)+4;
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
			opcion=t.leerInt("Seleccione una opción: ");
		}while(opcion<1 || opcion>6);
		return opcion;
	}
	static void altas(Scanner in) throws IOException{
		Persona persona = new Persona();
		Persona personaNula=new Persona(0," ".repeat(20),0);
		Teclado t = new Teclado();
		boolean seguir=false;
		RandomAccessFile raf = new RandomAccessFile("practicaFiheros.dat","rw");
		int posicion=0;
		do {
			try {
				persona.setNumero(t.leerInt("Número de persona: "));
				while(raf.length()<persona.getNumero()*tamanho) {
					raf.writeInt(personaNula.getNumero());
					raf.writeUTF(personaNula.getNombre());
					raf.writeInt(personaNula.getEdad());
				}
				if(raf.readInt()!=0) {
					System.out.println("Posición ya ocupada");
				}
				else {
					persona.setNombre(t.leerString("Nombre: "));
					persona.setEdad(t.leerInt("Edad: "));
					raf.seek(persona.getNumero()*tamanho);
					while(raf.length()<persona.getNumero()*tamanho) {
						raf.seek(posicion);
						if(raf.readInt()!=0) {
							raf.seek(posicion);
							raf.writeInt(personaNula.getNumero());
							raf.writeUTF(personaNula.getNombre());
							raf.writeInt(personaNula.getEdad());
						}
						posicion+=tamanho;
					}
					raf.writeInt(persona.getNumero());
					raf.writeUTF(persona.getNombre());
					raf.writeInt(persona.getEdad());
				}
			}catch (EOFException ioe) {
            }catch (IOException e) {
            }
		}while(!seguir);
		raf.close();
		
	}
	static void modificaciones(Scanner in) {
		Persona persona = new Persona();
		Teclado t = new Teclado();
		System.out.println("Codigo de persona a cambiar");
		
	}
	public static void main(String []args) throws IOException {
		RandomAccessFile raf = new RandomAccessFile("practicaFicheros.dat","rw");
		raf.close();
		Scanner in = new Scanner(System.in);
		int opcion=0;
		do {
			opcion=menu(in);
			switch(opcion) {
			case 1:
				altas(in);
				break;
			case 2:
//				bajas(in);
				break;
			case 3:
				modificaciones(in);
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				System.out.println("FIN DE PROGRAMA");
			}
		}while(opcion!=6);
	}
}
