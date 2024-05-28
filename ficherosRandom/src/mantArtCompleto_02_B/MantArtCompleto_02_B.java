package mantArtCompleto_02_B;
import java.io.IOException;
import java.io.RandomAccessFile;
public class MantArtCompleto_02_B {
	String menu(int tm) throws IOException{
		Teclado t = new Teclado();
		String op=null;
		switch (tm){
		case 0:
			System.out.println("\n\tMENU\n\t====\n");
			System.out.println("A.- Altas.");
			System.out.println("B.- Bajas.");
			System.out.println("M.- Modificaciones.");
			System.out.println("C.- Consultas. ");
			System.out.println("L.- Listados.");
			System.out.println("F.- Fin.");
			System.out.print("\n\n\tTeclee opción? ");
			op = t.leerString();
			break;
		case 1:
			int opl = 0;
			do{
				System.out.println("\n\tMENU LISTADOS\n\t=============\n");
				System.out.println("1.- General.");
				System.out.println("2.- Entre Límites.");
				System.out.println("3.- Pedidos.");
				System.out.println("4.- Volver al menú.");
				do{
					System.out.print("\n\n\tTeclee opción? ");
					opl = t.leerInt();
				}while(opl == Integer.MIN_VALUE || opl < 1 || opl > 4 );
				switch(opl){
					case 1: 
						listadoGeneral();
						break;
					case 2:
						listadoEntreLimites();
						break;
					case 3:
						listadoPedidos();
						break;
					}
			}while (opl!=4);
		}
		return op;
	}
	void altas()throws IOException{
		Articulo av = new Articulo(0," ",0,0,0,0,' ');
		Teclado t = new Teclado();
		int codigo;
		String denominacion;
		double stockAct,stockMin,stockMax;
		char confirmar = ' ';
		float precio;
		RandomAccessFile fich = new RandomAccessFile("articulosM.dat","rw");
		System.out.println("\n\tALTAS\n\t=====\n");
		do{
			av = new Articulo(0," ",0,0,0,0,' ');
			do{
				System.out.print("Código..........: ");
				codigo = t.leerInt();
			}while(codigo == Integer.MIN_VALUE);
			fich.seek(codigo * av.tamano());
			av.leerDeArchivo(fich);
			if(av.getCodigo() != 0) {
				System.out.println("\n\tEl registro ya existe.....\n");
				
			}
			else{
				do {
					System.out.print("Denominación....: ");
					denominacion = t.leerString();
				}while(denominacion.length() >20);
				do{
					System.out.print("Stock Actual....: ");
					stockAct = t.leerDouble();
				}while(stockAct == Double.MIN_VALUE);
				do{
					do{
						System.out.print("Stock mínimo....: ");
						stockMin = t.leerDouble();
					}while(stockMin == Double.MIN_VALUE);
					do{
						System.out.print("Stock máximo....: ");
						stockMax = t.leerDouble();
					}while(stockMax == Double.MIN_VALUE);
				}while(stockMax<stockMin);
				do{
					System.out.print("Precio..........: ");
					precio = t.leerFloat();
				}while(precio == Float.MIN_VALUE);
				do {
					System.out.print("\n\tConfirmar alta(s/n)? " );
					confirmar = Character.toLowerCase(t.leerChar());
				}while(confirmar != 's' && confirmar != 'n');
				if(confirmar == 's') {
					Articulo a = new Articulo(codigo,denominacion,stockAct,stockMin,stockMax,precio,' ');
					if (codigo * a.tamano()>fich.length())
						fich.seek(fich.length());
					while(codigo*a.tamano()>fich.length())
						av.grabarEnArchivo(fich);
					fich.seek(codigo * a.tamano());
					a.grabarEnArchivo(fich);
				}
			}
			do {
				System.out.print("\n\tOtra alta(s/n)? " );
				confirmar = Character.toLowerCase(t.leerChar());
			}while(confirmar != 's' && confirmar != 'n');
		}while(confirmar == 's');
		fich.close();
	}
	void fin(){
		System.out.println("\n\n\n\tFINAL DEL PROGRAMA\n\t==================\n");
	}
void bajas()throws IOException{
	Articulo a = new Articulo(0," ",0,0,0,0,' ');
	Teclado t = new Teclado();
	int codBus=0;
	char confirmar;
	System.out.println("\n\tBAJAS\n\t=====\n");
	RandomAccessFile fich = new RandomAccessFile("articulosM.dat","rw");
	do {
		do{
			System.out.print("\nTeclee el código del articulo? ");
			codBus = t.leerInt();
		}while(codBus == Integer.MIN_VALUE);
		fich.seek(codBus*a.tamano());
		a.leerDeArchivo(fich);
		if(a.getCodigo() != 0){
			a.mostrarDatos(2);
			do{
				System.out.print("\n\tConfirma borrar el registro (s/n)? ");
				confirmar = Character.toLowerCase(t.leerChar());
			}while(confirmar!='s' && confirmar!='n');
			if (confirmar=='s'){
				a=new Articulo(0,"",0,0,0,0,' ');
				fich.seek(codBus * a.tamano());
				a.grabarEnArchivo(fich);
				System.out.println("\n\tRegistro borrado correctamente\n");
			}
		}
		else{
			System.out.println("\nEl artículo con código: "+codBus+" no existe.\n");
		}
		do{
			System.out.print("\n\tDesea borrar otro registro (s/n)? ");
			confirmar = Character.toLowerCase(t.leerChar());
		}while(confirmar!='s' && confirmar!='n');
	}while(confirmar == 's');
	t.leerSalto();
	fich.close();
}
void modificaciones() throws IOException{
	Articulo a = new Articulo(0," ",0,0,0,0,' ');
	Teclado t = new Teclado();
	char otro;
	int codBus = 0,cm=0;
	String denominacionN;
	double stockActN, stockMinN, stockMaxN;
	float precioN;
	RandomAccessFile fich = new RandomAccessFile("articulosM.dat","rw");
	do{
		System.out.println("\n\tMODIFICACIONES\n\t==============\n");
		do{
			System.out.print("Teclee código de artículo.....: ");
			codBus=t.leerInt();
		}while(codBus == Integer.MIN_VALUE);
		fich.seek(codBus * a.tamano());
		a.leerDeArchivo(fich);
		if (a.getCodigo()==0)
			System.out.println("\n\tEl artículo con código: "+codBus+" no existe.\n");
		else{
			denominacionN = a.getDenominacion();
			stockActN = a.getStockAct();
			stockMinN = a.getStockMin();
			stockMaxN = a.getStockMax();
			precioN = a.getPrecio();
			a.mostrarDatos(1);
			do{
				do{
					System.out.print("\n\tTeclee campo a modificar (1-5)?");
					cm=t.leerInt();
				}while(cm==Integer.MIN_VALUE || cm<1 || cm>5);
				switch(cm){
				case 1:
					do {
						System.out.print("Teclee nueva denominación.....: ");
						denominacionN = t.leerString();
					}while(denominacionN.length() > 20);
					break;
				case 2:
					do {
						System.out.print("Teclee nuevo stock actual.....: ");
						stockActN = t.leerDouble();
					}while (stockActN == Double.MIN_VALUE);
					break;
				case 3:
					do{
						System.out.print("Teclee nuevo stock Mínimo.....: ");
						stockMinN = t.leerDouble();
					}while (stockMinN == Integer.MIN_VALUE || stockMinN>a.getStockMax());
					break;
				case 4:
					do{
						System.out.print("Teclee nuevo stock Máximo.....: ");
						stockMaxN = t.leerDouble();
					}while(stockMaxN == Integer.MIN_VALUE || stockMaxN < a.getStockMin());
					break;
				default:
					do{
						System.out.print("Teclee nuevo precio...........: ");
						precioN = t.leerFloat();
					}while(precioN == Integer.MIN_VALUE);
				}
				do{
					System.out.print("\n\tOtro campo a modificar (s/n)? ");
					otro = Character.toLowerCase(t.leerChar());
				}while (otro != 's' && otro != 'n');
			}while (otro =='s');
			do{
				System.out.print("\n\tConfirmar las modificaciones (s/n)? ");
				otro = Character.toLowerCase(t.leerChar());
			}while (otro != 's' && otro != 'n');
			if (otro =='s'){
				a = new Articulo(codBus,denominacionN,stockActN,stockMinN,stockMaxN,precioN,' ');
				fich.seek(codBus*a.tamano());
				a.grabarEnArchivo(fich);
			}
			
			}
		do{
			System.out.print("\n\tModificar otro artículo (s/n).....: ");
			otro = Character.toLowerCase(t.leerChar());
		}while(otro!='s' && otro!='n');
		}while(otro=='s');
	fich.close();
}
void consultas() throws IOException{
	Articulo a = new Articulo(0," ",0,0,0,0,' ');
	Teclado t = new Teclado();
	int codBus;
	char confirmar = ' ';
	System.out.println("\n\tCONSULTAS\n\t=========\n");
	RandomAccessFile fich = new RandomAccessFile("articulosM.dat","r");
	do {
		do{
			System.out.print("Teclee el código del artículo? ");
			codBus = t.leerInt();
		}while(codBus == Integer.MIN_VALUE);
		fich.seek(codBus * a.tamano());
		a.leerDeArchivo(fich);
		if(a.getCodigo() != 0)
			a.mostrarDatos(2);
		else
			System.out.println("\nNo existe ningún artículo con el código: "+codBus+" en el fichero.\n");
		do{
			System.out.print("\n\tRealizar otra consulta (s/n)? ");
			confirmar = Character.toLowerCase(t.leerChar());
		}while (confirmar != 's' && confirmar != 'n');
	}while(confirmar == 's');
	fich.close();
	System.out.println("\n\tPulse <Intro> para continuar....");
	t.leerSalto();
}
//***************************************************************************************************
/*	void listadoGeneral()throws IOException{
		Teclado t = new Teclado();
		boolean fin = false;
		final int LINEAS = 4;
		int cp=0,cl=LINEAS+1;
		Articulo a = new Articulo(0," ",0,0,0,0,' ');
		RandomAccessFile fich = new RandomAccessFile("C:\\Users\\Emilio P F\\Ejercicios_Java\\articulosM.dat","r");
//		RandomAccessFile fich = new RandomAccessFile("f:\\Ejercicios_Java\\articulosM.dat","r");
		
		fin = a.leerDeArchivo(fich);
		do{
			fin = a.leerDeArchivo(fich);
			if (fin)
				cl=LINEAS;
			if(cl>=LINEAS){
				if(cl==LINEAS){
					System.out.println("\n\tPulse <Intro> para continuar....");
					t.leerSalto();
				}
				if(!fin){
					System.out.println("\n\n\tLISTADO GENERAL\t\tPag.: "+ ++cp+"\n\t===============\n");
					System.out.println("código\tDenominación\tStock Actual\tStock Mínimo\tStock Máximo\tPrecio\tAviso");
					System.out.println("-------------------------------------------------------------------------------------");
					cl=0;
				}
				
			}
			if(!fin && a.codigo != 0){
				a.mostrarDatos(0);
				cl++;
			}
		}while(!fin );
		fich.close();
	}	
	*/
/******************************************** Otra forma ***************************************/
void listadoGeneral()throws IOException{
	Articulo a = new Articulo(0," ",0,0,0,0,' ');
	Teclado t = new Teclado();
	boolean fin = false;
	final int LINEAS = 6;
	int cp=0,cl=LINEAS+1;
	int totAPag = 0,totNPag = 0, totBPag = 0,totAGen = 0, totNGen = 0, totBGen = 0;
	RandomAccessFile fich = new RandomAccessFile("articulosM.dat","r");
	do{
		while(!fin && cl < LINEAS){
			if(a.getCodigo()!=0){
//				System.out.println("Posición de puntero.........: "+fich.getFilePointer());
				a.mostrarDatos(0);
				switch(a.getAviso()) {
				case 'A':
					totAPag++;
					break;
				case 'N':
					totNPag++;
					break;
					default:
						totBPag++;
				}
				cl++;
			}
			fin = a.leerDeArchivo(fich);
		}
		if (cl==LINEAS || fin){
			System.out.println("\nTotal página en stock Máximo -> "+totAPag);
			System.out.println("Total página en stock Normal -> "+totNPag);
			System.out.println("Total página en stock Mínimo -> "+totBPag);
			totAGen += totAPag;
			totNGen += totNPag;
			totBGen += totBPag;
			totAPag = 0;
			totNPag = 0;
			totBPag = 0;
			System.out.println("\n\tPulse <Intro> para continuar....");
			t.leerSalto();
			}
		if(!fin){
			System.out.println("\n\n\t\t\tLISTADO GENERAL\t\t\tPag.: "+ ++cp+"\n\t\t\t===============\n");
			System.out.println("código\tDenominación\tStock Actual\tStock Mínimo\tStock Máximo\tPrecio\tAviso");
			System.out.println("-------------------------------------------------------------------------------------");
			cl=0;
		}
	}while(!fin);
	fich.close();
	System.out.println("\nTotal General en stock Máximo -> "+totAGen);
	System.out.println("Total General en stock Normal -> "+totNGen);
	System.out.println("Total General en stock Mínimo -> "+totBGen);
	System.out.println("\n\n\t\tFIN DEL LISTADO \n");
	
	System.out.println("\n\tPulse <Intro> para continuar....");
	t.leerSalto();
}
void listadoEntreLimites() throws IOException{
	Articulo a = new Articulo(0,"",0,0,0,0,' ');
	Teclado t = new Teclado();
	boolean fin = false;
	int li=0,ls=0;
	final int LINEAS = 4;
	int cp=0,cl=LINEAS+1;
	do{
		do{
			System.out.print("\nTeclee límite inferior.....: ");
			li = t.leerInt();
			}while(li == Integer.MIN_VALUE);
		do{
			System.out.print("Teclee límite superior.......: ");
			ls = t.leerInt();
			}while(ls == Integer.MIN_VALUE);
	}while(li>ls);
	RandomAccessFile fich = new RandomAccessFile("articulosM.dat","r");
	fich.seek(li*a.tamano());
	do{
		fin = a.leerDeArchivo(fich);
	}while(a.getCodigo()==0 && !fin);
	do{
		while(!fin && cl < LINEAS){
				a.mostrarDatos(0);
				cl++;
			do{
				fin = a.leerDeArchivo(fich);
			}while(a.getCodigo()==0 && !fin);
				if (a.getCodigo() > ls){
				fin=true;
			}
		}
		if (cl==LINEAS){
			System.out.println("\n\tPulse <Intro> para continuar....");
			t.leerSalto();
			}
		if(!fin){
			System.out.println("\n\n\tLISTADO ENTRE LÍMITES\t\tPag.: "+ ++cp +"\n\t=====================\n");
			System.out.println("código\tDenominación\tStock Actual\tStock Mínimo\tStock Máximo\tPrecio\tAviso");
			System.out.println("-------------------------------------------------------------------------------------");
			cl=0;
			}
	}while(!fin);
		fich.close();
		System.out.println("\n\n\t\tFIN DEL LISTADO \n");
		System.out.println("\n\tPulse <Intro> para continuar....");
		t.leerSalto();	
}
void listadoPedidos() throws IOException{
	Articulo a = new Articulo(0," ",0,0,0,0,' ');
	Teclado t = new Teclado();
	boolean fin = false;
	double unidadesPedidas = 0;
	float precioPedido=0,totalPagina = 0, sumaSigue = 0;
	final int LINEAS = 4;
	int cp=0,cl=LINEAS+1;
	RandomAccessFile fich = new RandomAccessFile("articulosM.dat","r");
	do{
		fin = a.leerDeArchivo(fich);
	}while(a.getCodigo()==0 && !fin);
	do{
		while(!fin && cl < LINEAS){
			if(a.getAviso() == 'B'){
				unidadesPedidas = a.getStockMax() - a.getStockAct();
				precioPedido = (float) unidadesPedidas * a.getPrecio();
				lineasDetalle(a.getCodigo(),a.getDenominacion(),unidadesPedidas,a.getPrecio(),precioPedido);
				totalPagina += precioPedido;
				cl++;
			}
			do{
				fin = a.leerDeArchivo(fich);
			}while(a.getCodigo()==0 && !fin);
		}
		if (cl==LINEAS){
			sumaSigue += totalPagina;
			System.out.println("\n\t\t\tTotal página........................: "+totalPagina);
			System.out.println("\t\t\tSuma y sigue........................: "+sumaSigue);
			totalPagina = 0;
			System.out.println("\n\tPulse <Intro> para continuar....");
			t.leerSalto();
			}
		if(!fin){
			System.out.println("\n\n\t\t\tLISTADO PEDIDOS\t\t\t\tlPag.: "+ ++cp+"\n\t\t\t===============\n");
			System.out.println("código\tDenominación\tUnidades Pedidas\tPrecio Unidad\tValor Pedido");
			System.out.println("---------------------------------------------------------------------------");
			cl=0;
		}
	}while(!fin);
		sumaSigue += totalPagina;
		System.out.println("\n\t\t\tTotal página........................: "+totalPagina);
		System.out.println("\t\t\tSuma y sigue........................: "+sumaSigue);
		fich.close();
		System.out.println("\n\n\t\tFIN DEL LISTADO \n");
		System.out.println("\n\tPulse <Intro> para continuar....");
		t.leerSalto();
}
static void lineasDetalle(int codigo,String denominacion,double unidadesPedidas,float precio,float precioPedido){
	System.out.println(codigo+"\t"+denominacion+"\t"+unidadesPedidas+"\t\t"+precio+"\t\t"+precioPedido);
}
//***************************************************************************************************	
	
	public static void main(String[] args)throws IOException{
		MantArtCompleto_02_B mac = new MantArtCompleto_02_B();
		String opcion = null;
		boolean fin=false;
		RandomAccessFile fich = new RandomAccessFile("articulosM.dat","rw");
		fich.close();
		while (!fin){
			opcion = mac.menu(0);
			if(opcion.equalsIgnoreCase("A")) mac.altas();
			if(opcion.equalsIgnoreCase("B")) mac.bajas();
			if(opcion.equalsIgnoreCase("M")) mac.modificaciones();
			if(opcion.equalsIgnoreCase("C")) mac.consultas();
			if(opcion.equalsIgnoreCase("L")) mac.menu(1);
			if(opcion.equalsIgnoreCase("F")){
				mac.fin();
				fin=true;
			}
		}
	}
}
