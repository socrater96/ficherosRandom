package fich_ran_al;
import java.io.RandomAccessFile;
import java.io.EOFException;
//import java.io.FileNotFoundException;
import java.io.IOException;

public class Fich_Ran_Al {
    static final String FILEROUTE = "";
    static final int tam = 4 + 25 + 2 + 4 + 4 + 1;
    static final Alumno alvac = new Alumno(0," ".repeat(25),0,0,false);
    static Teclado t = new Teclado();
    
    void altas() throws IOException {
        String ruta = FILEROUTE + "fichran.dat";
        RandomAccessFile raf = new RandomAccessFile(ruta, "rw");
        Alumno al = new Alumno();
        int posicion;
        while (true) {
            String nombre = t.leerString("Nombre? ");
            if (nombre.equals("**" + " ".repeat(23))) {
                break; // Termina si se ingresa "**" seguido de espacios
            }
            do {
            	posicion = t.leerInt("Posicion? ");
            }while(posicion < 0);
            al.setNumero(posicion); // Asigna el número de alumno como la posición
            al.setNombre(nombre);
            al.setNota1(t.leerInt("Nota1? "));
            al.setNota2(t.leerInt("Nota2? "));
            if ((al.getNota1() + al.getNota2()) / 2 >= 5) {
                al.setApto(true);
            } 
            else {
                al.setApto(false);
            }
            long posicionArchivo = (long) posicion * tam; // Calcula la posición en el archivo
            if (posicionArchivo < raf.length()) {
                // Si la posición ya existe en el archivo
                raf.seek(posicionArchivo);
                if (raf.readInt() == 0) {
                    // Si la posición está vacía, se puede escribir
                    raf.seek(posicionArchivo);
                    al.escribirFichero(raf);
                } else {
                    System.out.println("Posición ocupada, no se pudo agregar el registro.");
                }
            } else {
                // Si la posición está más allá del final del archivo, llenar con alvac
                while (raf.length() < posicionArchivo) {
                    alvac.escribirFichero(raf);
                }
                raf.seek(posicionArchivo);
                al.escribirFichero(raf);
            }
        }
        raf.close();
    }

    void bajas() throws IOException{
        String ruta = FILEROUTE + "fichran.dat";
        RandomAccessFile raf = new RandomAccessFile(ruta, "rw");
        int posicion = Integer.MIN_VALUE;
        posicion = t.leerInt("Posición a borrar? ");
        if((posicion * tam) > raf.length()) {
        	System.out.println("No hay esa entrada en el registro");
        }else {
        	raf.seek(posicion*tam);
        	if(raf.readInt()== 0) {
        		System.out.println("El registro está vacio");
        	}else {
        		System.out.println("Desea borrar a :");
        		System.out.println("Nombre....." + raf.readUTF());
        		System.out.println("Nota1....." + raf.readInt());
        		System.out.println("Nota2....." + raf.readInt());
        		System.out.println("Apto....." + raf.readBoolean());
        		System.out.print("Desea borrar? ");
        		char op = t.leerChar("S/N");
        		if (op == 'S'){
                	raf.seek(posicion*tam);
                	alvac.escribirFichero(raf);
        		}
        	}
        		
        }
        raf.close();
    }

    void visualizar() throws IOException {
        String ruta = FILEROUTE + "fichran.dat";
        RandomAccessFile raf = new RandomAccessFile(ruta, "r");
        int NUM_REGISTROS=4;
        int cont = NUM_REGISTROS + 1;
        int c = 1;
        String cabecera;
        String pie_pagina;
        Alumno al = new Alumno();
        int pagina = 1;
        cabecera="\t\tVisualización\tpag:"+pagina+"\nNumero\t" + "Nombre"+" ".repeat(19)+ "\tNota1\t" + "Nota2\t" + "Apto\n"+"=".repeat(60);
        pie_pagina="Pulse <INTRO> para continuar....";
        int i = tam;
        while(raf.length()>raf.getFilePointer()) {
        		System.out.println(cabecera);
        	while(c<cont && raf.length()>raf.getFilePointer()) {
            	raf.seek(i);
        		al.leerFichero(raf);
        		if(al.getNumero() != 0) {
        			System.out.println(al.getNumero()+"\t"+al.getNombre()+"\t"+al.getNota1()+"\t"+al.getNota2()+"\t"+al.isApto());
                	c++;       			
        		}
            	i+=tam;
        	}
        		System.out.println(pie_pagina);
        		pagina++;
        		cabecera="\t\tVisualización\tpag:"+pagina+"\nNumero\t" + "Nombre"+" ".repeat(19)+ "\tNota1\t" + "Nota2\t" + "Apto\n"+"=".repeat(60);
        		t.pulsarEnter();
        	
        	c = 1;

        }
        
    	System.out.println("\n\n\n");
        raf.close();
    }

    
    void consultas() throws IOException {
    	 String ruta = FILEROUTE + "fichran.dat";
         RandomAccessFile raf = new RandomAccessFile(ruta, "r");

    	char resp;

    	do {
        	int pos=0;
    	System.out.println("N.-Numero.");
    	System.out.println("X.-Nombre,");
    	System.out.println("A.-Apto");
    	System.out.println("S.-Salir");
    	resp = t.leerChar("Opción?");
    	switch (resp) {
    	case 'N':
    		  do {
              	pos = t.leerInt("Posicion? ");
              }while(pos < 0);
    		try {
    			raf.seek(pos*tam);
    			if (raf.readInt()!= 0) {
    				System.out.println("Numero....."+pos);
            		System.out.println("Nombre....." + raf.readUTF());
            		System.out.println("Nota1....." + raf.readInt());
            		System.out.println("Nota2....." + raf.readInt());
            		System.out.println("Apto....." + raf.readBoolean());
    			}
    		}catch(EOFException eofe) {
    			System.out.println("Posición no registrada.");
    		}
    		break;
    	case 'X':
    		String nombre;
    		try {
    			nombre = t.leerString("Nombre a consultar?");
    			while(true) {
    				raf.seek(pos*tam);
    				raf.readInt();
    				if(raf.readUTF().equals(nombre)) {
    					raf.seek(pos*tam);
    					System.out.println("Numero....."+raf.readInt());
                		System.out.println("Nombre....." + raf.readUTF());
                		System.out.println("Nota1....." + raf.readInt());
                		System.out.println("Nota2....." + raf.readInt());
                		System.out.println("Apto....." + raf.readBoolean());
    				}
    				pos++;
    			}
    		}catch(EOFException eofe) {
    			System.out.println("FINAL DE REGISTRO");
    			
    		}
    		break;
    	case 'A':
    		char comp;
    		boolean ap = false;
    		comp = t.leerChar("A para apto, N para no apto?");
    		if (comp == 'A')
    			ap = !ap;
    		try {
    			while(true) {
    				raf.seek(pos*tam);
    				raf.readInt();
    				raf.readUTF();
    				raf.readInt();
    				raf.readInt();
    				
    				if(raf.readBoolean() == ap) {
    					raf.seek(pos*tam);
    					System.out.println("Numero....."+raf.readInt());
                		System.out.println("Nombre....." + raf.readUTF());
                		System.out.println("Nota1....." + raf.readInt());
                		System.out.println("Nota2....." + raf.readInt());
                		System.out.println("Apto....." + raf.readBoolean());
    				}
    				pos++;
    			}
    		}catch(EOFException eofe) {
    			System.out.println("FINAL DE REGISTRO");	
    		}
    		break;
    	case 'S':
    		break;
    	}	
    	}while(resp != 'S');
    	raf.close();
    }
    
    void modificar() throws IOException {
        String ruta = FILEROUTE + "fichran.dat";
        RandomAccessFile raf = new RandomAccessFile(ruta, "rw");
        Alumno al = new Alumno();
        char resp;
        int pos;
        int opcion = 0;
        do {
	        do {
	          	pos = t.leerInt("Posicion a modificar? ");
	          }while(pos < 0);
	        raf.seek(tam*pos);
	        al.setNumero(raf.readInt());
	        al.setNombre(raf.readUTF());
	        al.setNota1(raf.readInt());
	        al.setNota2(raf.readInt());
	        
	        System.out.println("Numero......"+al.getNumero());
    		System.out.println("1.Nombre....." + al.getNombre());
    		System.out.println("2.Nota1....." + al.getNota1());
    		System.out.println("3.Nota2....." + al.getNota2());
    		do {
    		opcion = t.leerInt("Campo a modificar? (1/2/3)");
    		}while(opcion < 1 || opcion > 3);
    		switch (opcion) {
    		case 1:
    			al.setNombre(t.leerString("Nombre modificado?"));
    			break;
    		case 2:
    			al.setNota1(t.leerInt("Nota modificada"));
    		break;
    		case 3:
    			al.setNota2(t.leerInt("Nota modificada"));
    		break;
    		}
	        
	        raf.seek(pos*tam);
	        resp = t.leerChar("Confirmar modificación??(s/n)");
	        if(resp == 'S')
	        	al.escribirFichero(raf);
	        resp = t.leerChar("Desea modificar otro registro?(s/n)");
	        }while(resp != 'N');
        
        
        raf.close();
    }

    public static void main(String[] args) throws IOException {
        Fich_Ran_Al fra = new Fich_Ran_Al();
        String ruta = FILEROUTE + "fichran.dat";
        RandomAccessFile raf = new RandomAccessFile(ruta, "rw");
        raf.close();
        while (true) {
        	System.out.println("1.-Altas");
        	System.out.println("2.-Bajas");
        	System.out.println("3.-Consultas");
        	System.out.println("4.-Visualizar");
        	System.out.println("5.-Modificaciones");
        	System.out.println("6.-Fin");
        	int w = t.leerInt("Opcion del menu(1-6).");
        	switch (w) {
        	case 1:
        		fra.altas();
        		break;
        	case 2:
        		fra.bajas();
        		break;
        	case 3:
        		fra.consultas();
        		break;
        	case 4:
        		fra.visualizar();
        		break;
        	case 5:
        		fra.modificar();
        		break;
        	case 6:
        		System.out.println("Fin de Programa.");
        		break;
        	}
        	if (w == 6)
        		break;
        }
    }
}
