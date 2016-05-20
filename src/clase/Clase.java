package clase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import herramientaTesting.Opciones;
import metodo.Metodo;

public class Clase {
	String nombre;
	ArrayList<Metodo> metodos;
	private Long cantidadDeLineas;
	private Long cantidadLineasComentadas;
	private Long cantidadLineasEnBlanco;
	
	public Clase(String name) {
		metodos = new ArrayList<Metodo>();
		nombre = name;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void agregarMetodo(String name, String cuerpo) {
		metodos.add(new Metodo(name,cuerpo));
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
			System.out.println("Algo");
			linea = br.readLine();
			System.out.println(linea);
			while(linea != null) {
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
					Metodo metodoAnalizado = null;
					String [] methodSignature = linea.split("\\(");
					if(methodSignature.length > 1) {
						methodSignature = methodSignature[0].split(" ");
						String nombreMetodo = methodSignature[methodSignature.length-1];
						metodoAnalizado = new Metodo(nombreMetodo,linea);
						System.out.println("Encontre el metodo "+nombreMetodo);
					}
					if(metodoAnalizado != null) {
						br = metodoAnalizado.analizar(br);
					}
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
