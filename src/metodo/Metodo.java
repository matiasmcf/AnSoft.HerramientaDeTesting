package metodo;

import herramientaTesting.Opciones;

import java.io.BufferedReader;
import java.io.IOException;

public class Metodo {
	private String nombre;
	private String cuerpo;
	private Long cantidadDeLineas=0L;
	private Long cantidadLineasComentadas=0L;
	private Long cantidadLineasEnBlanco=0L;
	private int complejidadCiclomatica = 1;
	private int fanIn;
	private int fanOut;
	private int halsteadLongitud;
	private double halsteadVolumen;
	private int cantidadLlavesAbiertas;
	private int cantidadLlavesCerradas;
	private int cantidadLlaves;
	
	public Metodo(String name, String body){
		nombre = name;
		cuerpo = body;
		cantidadDeLineas++;
		if(body.trim().endsWith("{"))
			cantidadLlavesAbiertas = 1;
	}
	
	public Long getCantidadDeLineas() {
		return cantidadDeLineas;
	}
	public Long getCantidadLineasComentadas() {
		return cantidadLineasComentadas;
	}
	public Long getCantidadLineasEnBlanco() {
		return cantidadLineasEnBlanco;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getCuerpo() {
		return cuerpo;
	}
	
	public Long getCantidadLineasDeCodigo() {
		return this.cantidadDeLineas-this.cantidadLineasComentadas-this.cantidadLineasEnBlanco;
	}
	
	public Double getPorcentajeLineasComentadas() {
		return ((double)cantidadLineasComentadas/(double)(100*(cantidadDeLineas-cantidadLineasEnBlanco)));
	}
	
	public int getComplejidadCiclomatica(){
		return complejidadCiclomatica;
	}
	
	public String toString(){
		return nombre;
	}
	
	public BufferedReader analizar(BufferedReader br, Opciones opciones) {	
//		this.cantidadDeLineas=0L;
//		this.cantidadLineasComentadas=0L;
//		this.cantidadLineasEnBlanco=0L;
		//Validacion de tipo de archivo
		/*if(!archivo.getName().endsWith(opciones.getExtension())){
			return;
		}*/
		//
		//FileReader fr = null;
		//BufferedReader br = null;
		try {
			String linea, aux;
			String[] condiciones;
			//fr = new FileReader(archivo);
			//br = new BufferedReader(fr);
			linea = br.readLine();
			while(linea != null /*&& !linea.endsWith("}")*/) {
				aux = linea;
				linea = linea.trim();
				if(linea.startsWith("/*")) {
					while(linea!=null && !linea.endsWith("*/")) {
						linea = linea.trim();
						if(opciones.getBlancosMultilinea()){
							if(linea.isEmpty()) {
								cantidadLineasEnBlanco++;
							}
							else {
								this.cantidadLineasComentadas++;
							}
						}
						else
							this.cantidadLineasComentadas++;
						this.cantidadDeLineas++;
						linea=br.readLine();
					}
					if(linea != null) {
						this.cantidadLineasComentadas++;
						this.cantidadDeLineas++;
					}
				}
				else if(linea.startsWith("//")) {
					cantidadLineasComentadas++;
					this.cantidadDeLineas++;
				}
				else if(linea.isEmpty()) {
					cantidadLineasEnBlanco++;
					this.cantidadDeLineas++;
				}
				else /* Encontro una linea de codigo */{
					this.cantidadDeLineas++;
					if(linea.matches(".*if\\s*\\(.*$") || linea.matches(".*while\\s*\\(.*$") || linea.startsWith("for") ){
						condiciones = linea.split("&&|\\|\\|");
						complejidadCiclomatica += condiciones.length;
					}
					if(linea.matches("case\\s.*:$") || linea.matches(".*catch\\s*\\(.*$")){
						complejidadCiclomatica++;
					}
					if(linea.endsWith("{") || linea.startsWith("{"))
						cantidadLlavesAbiertas++;
					if(linea.endsWith("}") || linea.startsWith("}"))
						cantidadLlavesCerradas++;
					cuerpo=cuerpo.concat("\n");
					System.out.println(aux);
					cuerpo=cuerpo.concat(aux);
					//
				}
				if(cantidadLlavesAbiertas==cantidadLlavesCerradas) 
					break;
				linea = br.readLine();
			}
			System.out.println("LLAVES ABIERTAS: "+cantidadLlavesAbiertas);
			System.out.println("LLAVES CERRADAS" + cantidadLlavesCerradas);
			return br;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		/*finally {
			if(fr!=null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}*/
	}
}