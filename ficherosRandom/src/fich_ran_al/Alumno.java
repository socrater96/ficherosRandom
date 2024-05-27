package fich_ran_al;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Alumno {
	private int numero;
	private String nombre;
	private int nota1;
	private int nota2;
	private boolean apto;
	
	Alumno(){}
	Alumno(int numero, String nombre, int nota1, int nota2, boolean apto) {
		super();
		this.numero = numero;
		this.nombre = nombre;
		this.nota1 = nota1;
		this.nota2 = nota2;
		this.apto = apto;
	}
	
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
	public int getNota1() {
		return nota1;
	}
	public void setNota1(int nota1) {
		this.nota1 = nota1;
	}
	public int getNota2() {
		return nota2;
	}
	public void setNota2(int nota2) {
		this.nota2 = nota2;
	}
	public boolean isApto() {
		return apto;
	}
	public void setApto(boolean apto) {
		this.apto = apto;
	}
	public void escribirFichero(RandomAccessFile raf) throws IOException {
		raf.writeInt(numero);
		raf.writeUTF(nombre);
		raf.writeInt(nota1);
		raf.writeInt(nota2);
		raf.writeBoolean(apto);
	}
	public void leerFichero(RandomAccessFile raf) throws IOException {
		this.numero = raf.readInt();
		this.nombre = raf.readUTF();
		this.nota1 = raf.readInt();
		this.nota2 = raf.readInt();
		this.apto = raf.readBoolean();
	}
}
