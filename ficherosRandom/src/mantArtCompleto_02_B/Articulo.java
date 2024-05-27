package mantArtCompleto_02_B;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Articulo {
	private int codigo;
	private String denominacion;
	private double stockAct;
	private double stockMin;
	private double stockMax;
	private float precio;
	private char aviso;
	final int LONGDENOMINACION = 20;
	Articulo(int codigo,String denominacion,double stockAct, double stockMin, double stockMax, float precio,char aviso){
		this.codigo = codigo;
		this.denominacion = denominacion;
		this.stockAct = stockAct;
		this.stockMin = stockMin;
		this.stockMax = stockMax;
		this.precio = precio;
		this.aviso=aviso; 
	}
	Articulo(){}
	int tamano(){
		return (4+20+2+8+8+8+4+2);
	}
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
	public double getStockAct() {
		return stockAct;
	}
	public void setStockAct(double stockAct) {
		this.stockAct = stockAct;
	}
	public double getStockMin() {
		return stockMin;
	}
	public void setStockMin(double stockMin) {
		this.stockMin = stockMin;
	}
	public double getStockMax() {
		return stockMax;
	}
	public void setStockMax(double stockMax) {
		this.stockMax = stockMax;
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
	public void setAviso(char aviso) {
		this.aviso = aviso;
	}
	public int getLONGDENOMINACION() {
		return LONGDENOMINACION;
	}
	String construirDenominacion(){
		String aux;
		int relleno;
		denominacion.trim();
		relleno = LONGDENOMINACION - denominacion.length();
		aux = denominacion + blancos(relleno);
		return aux;
	}
	String blancos(int numblancos){
		char[] blancos = new char[numblancos];
		for(int i = 0 ;i<numblancos; i++)
			blancos[i] = ' ';
		String sblancos = new String(blancos);
		return sblancos;
	}
	void grabarEnArchivo(RandomAccessFile f){
		String aux;
		try{
			f.writeInt(codigo);
			aux = construirDenominacion();
//			System.out.println("Longitud del Denominación.................................: "+aux.length());
			f.writeUTF(aux);
			f.writeDouble(stockAct);
			f.writeDouble(stockMin);
			f.writeDouble(stockMax);
			f.writeFloat(precio);
			
			aviso = conocerAviso();
			
			f.writeChar(aviso);
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	char conocerAviso(){
		if(stockAct<=stockMin)
		{
			aviso='B';
		}else{
			if(stockAct>=stockMax){
				aviso='A';
			}else{
				aviso='N';
			}
		}
		return aviso;
	}
/**************************************************************************************/
	boolean leerDeArchivo(RandomAccessFile f){
		boolean finArchivo = false;
		try{
			codigo=f.readInt();
			denominacion=f.readUTF();
			stockAct = f.readDouble();
			stockMin = f.readDouble();
			stockMax = f.readDouble();
			precio = f.readFloat();
			aviso = f.readChar();
		}catch(EOFException eofe){
			finArchivo=true;
		}catch(IOException ioe){
			System.out.println("Error: "+ioe);
		}
		return (finArchivo);
		}
	void mostrarDatos(int t){
		switch(t){
		case 0:
			System.out.println(codigo+"\t"+denominacion+"\t"+stockAct+"\t\t"+stockMin+"\t\t"+stockMax+"\t"+precio+"\t "+aviso);
			break;
		case 1:
			System.out.println("1.- Denominación...........: "+denominacion);
			System.out.println("2.- Stock Actual...........: "+stockAct);
			System.out.println("3.- Stock Mínimo...........: "+stockMin);
			System.out.println("4.- Stock Máximo...........: "+stockMax);
			System.out.println("5.- Precio.................: "+precio);
			System.out.println("Aviso......................: "+aviso);
			break;
		case 2:
			System.out.println("Denominación...............: "+denominacion);
			System.out.println("Stock Actual...............: "+stockAct);
			System.out.println("Stock Mínimo...............: "+stockMin);
			System.out.println("Stock Máximo...............: "+stockMax);
			System.out.println("Precio.....................: "+precio);
			System.out.println("Aviso......................: "+aviso);
			break;
		}
	}
}
