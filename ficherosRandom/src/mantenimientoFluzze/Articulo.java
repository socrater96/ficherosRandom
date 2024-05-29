package mantenimientoFluzze;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Articulo {
	private int codigo;
	private String denominacion; // longitud de 20 caracteres ( 20+2)
	private double Stock_Minimo;
	private double Stock_Actual;
	private double Stock_Maximo;
	private float precio;
	private char aviso;
	
	
	Articulo(int codigo, String denominacion, int stock_Minimo, int stock_Actual, int stock_Maximo, float precio) {
		this.codigo = codigo;
		this.denominacion = denominacion;
		this.Stock_Minimo = stock_Minimo;
		this.Stock_Actual = stock_Actual;
		this.Stock_Maximo = stock_Maximo;
		this.precio = precio;
		setAviso();
	}
	
	Articulo(){}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public double getStock_Minimo() {
		return Stock_Minimo;
	}

	public void setStock_Minimo(double stock_Minimo) {
		Stock_Minimo = stock_Minimo;
	}

	public double getStock_Actual() {
		return Stock_Actual;
	}

	public void setStock_Actual(double stock_Actual) {
		Stock_Actual = stock_Actual;
	}

	public double getStock_Maximo() {
		return Stock_Maximo;
	}

	public void setStock_Maximo(double stock_Maximo) {
		Stock_Maximo = stock_Maximo;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public char getAviso() {
		return aviso;
	}
	
	public void setAviso() {
		if(Stock_Maximo< Stock_Actual) {
			aviso= 'A';
		}
		if(Stock_Minimo> Stock_Actual) {
			aviso= 'B';
		}
		if(Stock_Maximo > Stock_Actual && Stock_Actual > Stock_Minimo) {
			aviso= 'N';
		}
	}
	
	public void grabarFichero(RandomAccessFile raf) throws IOException {
		raf.writeInt(codigo);
		raf.writeUTF(denominacion);
		raf.writeDouble(Stock_Minimo);
		raf.writeDouble(Stock_Actual);
		raf.writeDouble(Stock_Maximo);
		raf.writeFloat(precio);
		setAviso();
		raf.writeChar(aviso);
	}
	
	public void leerFichero(RandomAccessFile raf) throws IOException{
		codigo = raf.readInt();
		denominacion = raf.readUTF();
		Stock_Minimo = raf.readDouble();
		Stock_Actual = raf.readDouble();
		Stock_Maximo = raf.readDouble();
		precio = raf.readFloat();
		aviso= raf.readChar();

		
	}
	
	
}
