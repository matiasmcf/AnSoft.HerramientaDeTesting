package metodo;

import java.io.BufferedReader;
import java.io.IOException;

public class Metodo {
	private String nombre;
	private String cuerpo;
	private Long cantidadDeLineas;
	private Long cantidadLineasComentadas;
	private Long cantidadLineasEnBlanco;
	private int complejidadCiclomatica;
	private int fanIn;
	private int fanOut;
	private int halsteadLongitud;
	private double halsteadVolumen;
	
	public Metodo(String name, String body) {
		nombre = name;
		cuerpo = body;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getCuerpo() {
		return cuerpo;
	}
	
	public Double getPorcentajeLineasComentadas() {
		return ((double)cantidadLineasComentadas/(double)(100*(cantidadDeLineas-cantidadLineasEnBlanco)));
	}
	
	public BufferedReader analizar(BufferedReader br) {
		this.cantidadDeLineas=0L;
		this.cantidadLineasComentadas=0L;
		this.cantidadLineasEnBlanco=0L;
		//Validacion de tipo de archivo
		/*if(!archivo.getName().endsWith(opciones.getExtension())){
			return;
		}*/
		//
		//FileReader fr = null;
		//BufferedReader br = null;
		try {
			String linea;
			//fr = new FileReader(archivo);
			//br = new BufferedReader(fr);
			linea = br.readLine();
			while(linea != null && !linea.endsWith("}")) {
				linea = linea.trim();
				if(linea.startsWith("/*")) {
					while(linea!=null && !linea.endsWith("*/")) {
						linea = linea.trim();
						//if(opciones.getBlancosMultilinea()){
							if(linea.isEmpty()) {
								cantidadLineasEnBlanco++;
							}
							else {
								this.cantidadLineasComentadas++;
							}
						//}
						//else
							//this.cantidadLineasComentadas++;
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
					//
				}
				linea = br.readLine();
			}
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