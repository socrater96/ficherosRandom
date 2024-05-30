package mantenimientoFluzze;


import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Mantenimiento_Completo {
	static final Teclado t = new Teclado();
    static final String FILEROUTE = "";
    static final int tam = 4 + (20+2) + 8 + 8 + 8 + 4 + 2;
	static final Articulo arvac = new Articulo(0," ".repeat(20),0,0,0,0);
    
    boolean comprobarPosicion(long a, RandomAccessFile raf) throws IOException {
    	try {
    		raf.seek(a);
    		if(raf.readInt()!= 0) {
    			System.out.println("Posición ocupada");
    			return false;
    		}
    	}catch(EOFException eofe) {}
    	return true;
    }
	void altas() throws IOException {
	    Articulo ar = new Articulo();
	    String ruta = "mantenimiento.dat";
	    Long posicion;
	    char resp = 'S';
	    RandomAccessFile raf = new RandomAccessFile(FILEROUTE + ruta, "rw");
	   while(true) {
		   do {
			   System.out.print("Código del artículo? ");
			   ar.setCodigo(t.leerInt());
		    }while(ar.getCodigo()<0);
		   posicion = (long) ar.getCodigo() * tam;
		   if (comprobarPosicion(posicion,raf)) {
           System.out.print("Nombre del artículo?");
           ar.setDenominacion(t.leerString());
           do {
	           do {
		           System.out.print("Cantidad de Stock Mínimo?");
		           ar.setStock_Minimo(t.leerDouble());
		           if (ar.getStock_Minimo()<0)
		        	   System.out.println("No puede introducir una cantidad negativa de stock.");
	           }while(ar.getStock_Minimo()<0);
	           
	           do {
		           System.out.print("Cantidad de Stock Actual?");
		           ar.setStock_Actual(t.leerDouble());
		           if (ar.getStock_Actual()<0)
		        	   System.out.println("No puede introducir una cantidad negativa de stock.");
	           }while(ar.getStock_Actual() < 0);
	           
	           do {
	        	   System.out.print("Cantidad de Stock Máximo?");
	        	   ar.setStock_Maximo(t.leerDouble());
	        	   if (ar.getStock_Maximo()<=0)
		        	   System.out.println("No puede introducir una cantidad negativa de stock.");
	           }while(ar.getStock_Maximo() <= 0);
	           if (ar.getStock_Maximo() < ar.getStock_Minimo())
	        	   System.out.println("No puede introducir una cantidad de Stock_Minimo mayor al Stock_Máximo.");
           }while(ar.getStock_Maximo() < ar.getStock_Minimo());
           do {
        	   System.out.print("Precio del artículo?");
        	   ar.setPrecio(t.leerFloat());
           }while(ar.getPrecio()<= 0);
           do {
               System.out.println("Confirmar alta? (s/N)");
               resp = t.leerChar();
               }while(resp != 'S' && resp != 'N');
    		           if(resp =='S') {
				           if((posicion) > raf.length()) {
				        	   raf.seek(raf.length());
				        	   while(raf.length()< (ar.getCodigo() * tam)) {
				        		   arvac.grabarFichero(raf);
				        	   }
					          }
				          
		           raf.seek(posicion);
		           ar.grabarFichero(raf);
		           }
			   }
		   do {
	           System.out.println("Otro registro? (s/N)");
	           resp = t.leerChar();
	           }while(resp != 'S' && resp != 'N');
		   	if(resp!='S')
		   		break;
		   }
	   
	    
	    raf.close();
	}

    void bajas() throws IOException {
    	Articulo ar = new Articulo();
    	String ruta = "mantenimiento.dat";
   	    RandomAccessFile raf = new RandomAccessFile(FILEROUTE + ruta, "rw");
    	char resp;
    	do {
    	System.out.print("Para realizar una baja debes introducir el codigo del artículo a dar de baja.\n Codigo?");
    	do {
    		ar.setCodigo(t.leerInt());
    	}while(ar.getCodigo()<=0);
    	if(raf.length()<(ar.getCodigo()*tam)) {
    		System.out.println("No hay un objeto registrado con ese codigo.");
    	}else {
    		raf.seek(ar.getCodigo()*tam);
    		ar.leerFichero(raf);
    		if(ar.getCodigo() == 0	) {
    			System.out.println("No hay un objeto registrado con ese codigo.");
    		}else {
    			System.out.println("Desea borrar el artículo...:");
    			System.out.println("Denominación:........"+ar.getDenominacion());
    			System.out.println("Codigo......:........"+ar.getCodigo());
    			System.out.println("Stock Actual:........"+ar.getStock_Actual());
    			System.out.println("Precio:.............."+ar.getPrecio());
 
    			
    			do {
    				System.out.println("Dar de baja?(S/n)");
    				resp = t.leerChar();
    			}while(resp != 'S' && resp != 'N');
    			
    			
    			if(resp == 'S') {
    				do {
        				System.out.println("Esta seguro que quiere dar de baja el artículo?(s/n)");
        				resp = t.leerChar();
        			}while(resp != 'S' && resp != 'N');
    			}
    			
    			
    			if(resp == 'S') {
    				raf.seek(ar.getCodigo()*tam);
    				arvac.grabarFichero(raf);
    			}
    				
    			
    			
    			
    		}
    	}
    	
    	System.out.println("Continuar dando artículos de baja?(s/n)");
    	do {
    	resp = t.leerChar();
    	}while(resp != 'N' && resp != 'S');
    	}while(resp == 'S');
    }
    
    void consultas() throws IOException{
    	Articulo ar = new Articulo();
    	String ruta = "mantenimiento.dat";
   	    RandomAccessFile raf = new RandomAccessFile(FILEROUTE + ruta, "r");
    	int op;
    	String denominacion = "";
    	int codigo = Integer.MIN_VALUE;
    	float precio = Float.MIN_VALUE;
    	char aviso ='w';
    	char resp = 'w';
    	do {
	    	System.out.println("MENÚ DE CONSULTAS");
	    	System.out.println("1.-Denominación");
	    	System.out.println("2.-Codigo");
	    	System.out.println("3.-Precio");
	    	System.out.println("4.-Aviso");
	    	System.out.println("5.-Cancelar");
	    	do {
				System.out.println("Opcion?");
				op = t.leerInt();
				}while(op < 1 || op > 5);
	    	switch(op) {
	    	case 1:
		    		System.out.print("Denominación a consultar?   ");
		    		denominacion = t.leerString();		
		    		break;
		    	case 2:
		 		   do {
					   System.out.print("Código a consultar? ");
					   codigo = t.leerInt();
				   }while(codigo<0);
		 		   break;
		    	case 3:
		    	     do {
		          	   System.out.print("Precio del artículo?");
		          	   ar.setPrecio(t.leerFloat());
		             }while(ar.getPrecio()<= 0);
		    		break;
		    	case 4:
		    		do {
		    			System.out.println("Consultar aviso? (A/N/B)");
		    		aviso = t.leerChar();
		    		}while(aviso != 'A' && aviso != 'N' && aviso !='B');
		    		break;
		    	case 5:
		    		System.out.println("No se va a realizar ninguna consulta.");
		    		break;
		    	}
		    	try {
					while(true) {
						ar.leerFichero(raf);
						if(ar.getDenominacion().equals(denominacion)|| codigo == ar.getCodigo() || aviso == ar.getAviso() || precio == ar.getPrecio() ) {
							System.out.println("Codigo..............:"+ar.getCodigo());
		    				System.out.println("Denominación........:"+ar.getDenominacion());
							System.out.println("Stock_Actual........:"+ar.getStock_Actual());
							System.out.println("Precio..............:"+ar.getPrecio());
							System.out.println("Aviso...............:"+ar.getAviso());
							System.out.println("Stock_Maximo........:"+ar.getStock_Maximo());
							System.out.println("Stock_Mínimo........:"+ar.getStock_Minimo());
						}
					}
			}catch(EOFException eofe) {
				System.out.println("Final de fichero");
			}
	       do {
	           System.out.println("Confirmar alta? (s/N)");
	           resp = t.leerChar();
	           }while(resp != 'S' && resp != 'N');
    	}while(resp == 'N');
    	raf.close();
    }
    
	void modificaciones() throws IOException{
    	Articulo ar = new Articulo();
    	String ruta = "mantenimiento.dat";
   	    RandomAccessFile raf = new RandomAccessFile(FILEROUTE + ruta, "rw");
   	    int op;
     	System.out.println("Para realizar una modificación debes introducir el codigo del artículo a dar de baja. Codigo?");
     		do {
     		ar.setCodigo(t.leerInt());
     	}while(ar.getCodigo()<=0);
		if(raf.length() < (ar.getCodigo() * tam)) {
			System.out.println("No hay un registro con ese codigo");
		}else {
			raf.seek(ar.getCodigo()*tam);
			ar.leerFichero(raf);
			raf.seek(ar.getCodigo()*tam);
			if(ar.getCodigo() == 0) {
				System.out.println("No hay un registro con ese codigo");
			}else {
				System.out.println("Que desea modificar?");
				System.out.println("1.-Denominación.");
				System.out.println("2.-Stock Mínimo.");
				System.out.println("3.-Stock Actual.");
				System.out.println("4.-Stock Máximo.");
				System.out.println("5.-Precio.");				
				System.out.println("6.-No hacer nada.");
				
				
				do {
				System.out.println("Opcion?");
				op = t.leerInt();
				}while(op < 1 || op > 6);
				
				switch(op) {
				case 1:
					System.out.println("Denominación?");
					ar.setDenominacion(t.leerString());
					break;
				case 2:
					System.out.println("Stock Mínimo?");
					do {
					ar.setStock_Minimo(t.leerDouble());
					}while(ar.getStock_Minimo() <= 0);
					ar.setAviso();
					break;
				case 3:
					System.out.println("Stock Actual?");
					do {
					ar.setStock_Actual(t.leerDouble());
					}while(ar.getStock_Actual() <= 0);
					ar.setAviso();
					break;
				case 4:
					System.out.println("Stock Maximo?");
					do {
					ar.setStock_Maximo(t.leerDouble());
					}while(ar.getStock_Maximo() <= 0);
					ar.setAviso();
					break;
				case 5:
					System.out.println("Precio?");
					do {
					ar.setPrecio(t.leerFloat());
					}while(ar.getPrecio() <= 0);
					break;
				case 6:
					System.out.println("No se está modificando nada.");
				}
				ar.grabarFichero(raf);
				
			}
		}
		
	    raf.close();
	 }
	
	void listado() throws IOException {
	    Articulo ar = new Articulo();
	    String ruta = "mantenimiento.dat";
	    RandomAccessFile raf = new RandomAccessFile(FILEROUTE + ruta, "r");
	    int i = tam;
	    Float precio_inf = (float) 0;
	    Float precio_sup = (float) 0;
	    int c = 1;
	    int NUM_REGISTROS = 6;
	    int cont = NUM_REGISTROS + 1;
	    int pagina = 1;
	    int cant_stma = 0;
	    int tot_max=0;
	    int tot_min = 0;
	    int cant_stmi = 0;
	    String cabecera = "CODIGO	DENOMINACIÓN        	STOCK MÍNIMO	STOCK ACTUAL	STOCK MÁXIMO	PRECIO	AVISO pag:"+pagina+"\n "+"=".repeat(100);
	    String pie_pagina = "Pulsa <INTRO> para continuar...";


        if(raf.length()==0)
        	System.out.println("No hay registros en el fichero");
        // LISTADO GENERAL
        while(raf.length() > i) {
        		System.out.println(cabecera);
        	while(c<cont && raf.length()>i) {
            	raf.seek(i);
        		ar.leerFichero(raf);
        		if(ar.getCodigo() != 0) {
        			System.out.println(ar.getCodigo()+"\t"+ar.getDenominacion()+"\t"+ar.getStock_Minimo()+"\t\t"+ar.getStock_Actual()+"\t\t"+ar.getStock_Maximo()+"\t\t"+ar.getPrecio()+"\t"+ar.getAviso());
                	c++; 
                	if(ar.getAviso() == 'B')
                		cant_stmi++;
                	if(ar.getAviso() == 'A')
                		cant_stma++;
        		}
            	i+=tam;
        	}
    		System.out.println("TOTALES:\t STOCK MINIMO: "+ cant_stmi +"\t STOCK MÁXIMO: "+ cant_stma);
        		System.out.println(pie_pagina);
        		tot_max+=cant_stma;
        		tot_min+=cant_stmi;
 			   cabecera = "CODIGO	DENOMINACIÓN        	STOCK MÍNIMO	STOCK_ACTUAL	STOCK MÁXIMO	PRECIO	AVISO pag:"+pagina+++"\n "+"=".repeat(100);
 			   t.pulsarEnter();
 			   cant_stmi = 0;
 			   cant_stma = 0;
        	c = 1;
        }
        
    	System.out.println("\n\n\nFINAL DE LISTADO GENERAL\n\n\n");
    	
    	//LISTADO DE PEDIDOS
    	
    	
    	raf.seek(0);
    	i = tam;
    	c= 1;
    	pagina = 1;
    	int precio_total_pag = 0;
    	int precio_total = 0;
    	cabecera = "CODIGO	DENOMINACIÓN  UNIDADES PEDIDAS	PRECIO pag:"+pagina+"\n "+"=".repeat(100);
        if(raf.length()==0)
        	System.out.println("No hay registros en el fichero");
        while(raf.length() > i) {
        		System.out.println(cabecera);
        	while(c<cont && raf.length()>i) {
            	raf.seek(i);
        		ar.leerFichero(raf);
        		if(ar.getAviso() == 'B') {
        			System.out.println(ar.getCodigo()+"\t"+ar.getDenominacion()+"\t"+(ar.getStock_Maximo()-ar.getStock_Actual())+"\t"+(ar.getPrecio()*(ar.getStock_Maximo()-ar.getStock_Actual())));
                	c++;       	
                	precio_total_pag += (ar.getPrecio()*(ar.getStock_Maximo()-ar.getStock_Actual()));
        		}
        		
            	i+=tam;
        	}
        		System.out.println("Precio total de página = " + precio_total_pag);
        		precio_total += precio_total_pag;
        		precio_total_pag = 0;
      			t.pulsarEnter();
        		System.out.println(pie_pagina);
        		pagina++;
   			  
 			   cabecera = "CODIGO	DENOMINACIÓN  UNIDADES PEDIDAS	PRECIO	pag:"+pagina+"\n "+"=".repeat(100);
   			  

        	
        	c = 1;

        }
        System.out.println("Precio total de " + precio_total);
    	System.out.println("\n\n\nFINAL DE LISTADO PEDIDOS\n\n\n");
    	
    	
    	//LISTADO ENTRE LIMITES DE PRECIO
    	raf.seek(0);
    	i = tam;
    	c= 1;
    	pagina = 1;
    	System.out.print("Precio inferior?");
		do {
		precio_inf = t.leerFloat();
		}while(precio_inf <= 0);
		
	  	System.out.print("Precio superior?");
			do {
			precio_sup = t.leerFloat();
			}while(precio_sup <= precio_inf);
    	
        while(raf.length() > i) {
    		System.out.println(cabecera);
    	while(c<cont && raf.length()>i) {
        	raf.seek(i);
    		ar.leerFichero(raf);
    		if(precio_inf < ar.getPrecio() && precio_inf > ar.getPrecio()) {
    			System.out.println(ar.getCodigo()+"\t"+ar.getDenominacion()+"\t"+ar.getStock_Minimo()+"\t\t"+ar.getStock_Actual()+"\t\t"+ar.getStock_Maximo()+"\t\t"+ar.getPrecio()+"\t"+ar.getAviso());
            	c++;       			
    		}
        	i+=tam;
    	}
    		System.out.println(pie_pagina);
    		pagina++;
			   cabecera = "CODIGO	DENOMINACIÓN        	STOCK MÍNIMO	STOCK_ACTUAL	STOCK MÁXIMO	PRECIO	AVISO pag:"+pagina+"\n "+"=".repeat(100);
			   t.pulsarEnter();
    	
    	c = 1;

    }
    	
    	
    	
    	
    	
    	
    	
        raf.close();
    }

	public static void main(String[] args) throws IOException {
		char op;
		String opciones = "ABCMLF";
		Mantenimiento_Completo mc = new Mantenimiento_Completo();    	
		String ruta = "mantenimiento.dat";
    	RandomAccessFile raf = new RandomAccessFile(FILEROUTE+ruta,"rw");
    	raf.close();
		do {
			System.out.println("\t\tMENÚ PRINCIPAL\nA.-Altas\nB.-Bajas\nM.-Modificaciones\nC.-Consultas\nL.-Listados\nF.-Fin");
			do {
			System.out.print("Opción?");
			op = t.leerChar();
			if(opciones.indexOf(op)== -1)
				System.out.println("La opción introducida no está en la lista.");
			}while(opciones.indexOf(op)== -1);
		
			switch (op){
			case 'A':
				System.out.println("\n...accediendo a la sección de altas...\n");
				mc.altas();
				break;
			case 'B':
				System.out.println("\n...accediendo a la sección de bajas...\n");
				mc.bajas();
				break;
			case 'C':	
				System.out.println("\n...accediendo a la sección de consultas...\n");
				mc.consultas();
				break;
			case 'M':
				System.out.println("\n...accediendo a la sección de modificaciones...\n");
				mc.modificaciones();
				break;
			case 'L':
				System.out.println("\n...accediendo a la sección de listados...\n");
				mc.listado();
				break;
			case 'F':
				System.out.println("Fin de programa.");
				break;
			}
		
		}while(op != 'F');
		
		t.cerrarScanner();
	}
}
