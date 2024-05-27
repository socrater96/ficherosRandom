package ficherosRandom;

import java.util.InputMismatchException;

public class Alumno {
	private int numero;
	private String nombre;
	private double nota1;
	private double nota2;
	private double nota3;
	
	public Alumno(int numero, String nombre, double nota1, double nota2, double nota3) {
		this.numero=numero;
		this.nombre=nombre;
		this.nota1=nota1;
		this.nota2=nota2;
		this.nota3=nota3;
	}
	public Alumno() {}
	
	public int getNumero() {
		return numero;
	}
	public boolean setNumero(String numeroIn) {
		int numero=0;
		try {
			numero=Integer.parseInt(numeroIn);
		}catch(NumberFormatException e) {
			System.out.println("Valor no numérico");
			return false;
		}
		this.numero = numero;
		return true;
	}
	public String getNombre() {
		return nombre;
	}
	public boolean setNombre(String nombre) {
		if(nombre.length()<=25) {
			nombre.trim();
			nombre=nombre +" ".repeat(25-nombre.length());
			this.nombre = nombre;
			return true;
		}
		else 
			return false;
	}
	public double getNota1() {
		return nota1;
	}
	public boolean setNota1(String notaIn) {
		double nota=0;
		try {
			nota=Double.parseDouble(notaIn);
			if(nota<1||nota>10)
				return false;
		}catch(NumberFormatException e) {
			System.out.println("Valor no numérico");
			return false;
		}
		this.nota1=nota;
		return true;
	}
	public double getNota2() {
		return nota2;
	}
	public boolean setNota2(String notaIn) {
		double nota=0;
		try {
			nota=Double.parseDouble(notaIn);
			if(nota<1||nota>10)
				return false;
		}catch(NumberFormatException e) {
			System.out.println("Valor no numérico");
			return false;
		}
		this.nota2=nota;
		return true;
	}
	public double getNota3() {
		return nota3;
	}
	public boolean setNota3(String notaIn) {
		double nota=0;
		try {
			nota=Double.parseDouble(notaIn);
			if(nota<1||nota>10)
				return false;
		}catch(NumberFormatException e) {
			System.out.println("Valor no numérico");
			return false;
		}
		this.nota3=nota;
		return true;
	}	
	public String toString() {
		return numero+"\t"+nombre+"\t"+"\t"+nota1+"\t"+nota2+"\t"+nota3;
	}
}
