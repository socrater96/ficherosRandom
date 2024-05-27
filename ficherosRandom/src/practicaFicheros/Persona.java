package practicaFicheros;
import java.io.IOException;
public class Persona {
	private int numero;
	private String nombre;
	private int edad;
	final int LNOMBRE=20;
	Persona(int numero, String nombre, int edad){
		this.setNumero(numero);
		this.setNombre(nombre);
		this.setEdad(edad);
	}
	Persona(){}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	
}
