package metodo;

import java.io.BufferedReader;
import java.io.IOException;
import herramientaTesting.Opciones;
import metodo.utils.Halstead;

public class Metodo {
	private String nombre;
	private String cuerpo;
	private Long cantidadDeLineas = 0L;
	private Long cantidadLineasComentadas = 0L;
	private Long cantidadLineasEnBlanco = 0L;
	private int complejidadCiclomatica = 1;
	private int fanIn;
	private int fanOut;
	private int cantidadLlavesAbiertas;
	private int cantidadLlavesCerradas;
	private Halstead halstead;

	public Metodo(String name, String body) {
		nombre = name;
		cuerpo = body;
		cantidadDeLineas++;
		if (body.trim().endsWith("{"))
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
	
	public void setHalstead(Halstead halstead) {
		this.halstead = halstead;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public Long getCantidadLineasDeCodigo() {
		return this.cantidadDeLineas - this.cantidadLineasComentadas - this.cantidadLineasEnBlanco;
	}

	public Double getPorcentajeLineasComentadas() {
		return ((double) cantidadLineasComentadas / (double) (100 * (cantidadDeLineas - cantidadLineasEnBlanco)));
	}

	public int getfanIn() {
		return fanIn;
	}
	
	public int getFanOut() {
		return fanOut;
	}
	
	public void setFanIn(int fanIn) {
		this.fanIn = fanIn;
	}
	
	public int getComplejidadCiclomatica() {
		return complejidadCiclomatica;
	}
	
	public void setComplejidadCiclomatica(int complejidadCiclomatica) {
		this.complejidadCiclomatica = complejidadCiclomatica;
	}
	
	public String[] getDefinicion() {
		return cuerpo.split("\n");
	}
	public int getCantidadLineas(){
		return cuerpo.split("\n").length;
	}
	public String toString() {
		return nombre;
	}
	
	public Halstead getHalstead(){
		return halstead;
	}
	
	public int getHalsteadLongitud() {
		return halstead.getN();
	}
	
	public double getHalsteadVolumen(){
		return halstead.getVolumen();
	}
	
	public BufferedReader analizar(BufferedReader br, Opciones opciones) {
		// Validacion de tipo de archivo
		/*
		 * if(!archivo.getName().endsWith(opciones.getExtension())){ return; }
		 */
		//
		// FileReader fr = null;
		// BufferedReader br = null;
		try {
			String linea, aux;
			linea = br.readLine();
			while (linea != null /* && !linea.endsWith("}") */) {
				aux = linea;
				linea = linea.trim();
				if (linea.startsWith("/*")) {
					while (linea != null && !linea.endsWith("*/")) {
						linea = linea.trim();
						if (opciones.getBlancosMultilinea()) {
							if (linea.isEmpty()) {
								cantidadLineasEnBlanco++;
							} else {
								this.cantidadLineasComentadas++;
							}
						} else
							this.cantidadLineasComentadas++;
						this.cantidadDeLineas++;
						linea = br.readLine();
					}
					if (linea != null) {
						this.cantidadLineasComentadas++;
						this.cantidadDeLineas++;
					}
				} else if (linea.startsWith("//")) {
					cantidadLineasComentadas++;
					this.cantidadDeLineas++;
				} else if (linea.isEmpty()) {
					cantidadLineasEnBlanco++;
					this.cantidadDeLineas++;
				} else /* Encontro una linea de codigo */ {
					this.cantidadDeLineas++;
					if (linea.matches(".*\\..*\\(.*\\);$")) {
						fanOut++;
					}
					if (linea.endsWith("{") || linea.startsWith("{"))
						cantidadLlavesAbiertas++;
					if (linea.endsWith("}") || linea.startsWith("}"))
						cantidadLlavesCerradas++;
					cuerpo = cuerpo.concat("\n");
					cuerpo = cuerpo.concat(aux);
					//
				}
				if (cantidadLlavesAbiertas == cantidadLlavesCerradas)
					break;
				linea = br.readLine();
			}
			return br;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		/*
		 * finally { if(fr!=null) { try { fr.close(); } catch (IOException e) {
		 * e.printStackTrace(); } } }
		 */
	}
	// TODO: aca llamo cada vez que hay una linea
}