package herramientaTesting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import clase.Clase;

public class Archivo implements Analizable {
	//Atributos
	private Long cantidadDeLineas;
	private Long cantidadLineasComentadas;
	private Long cantidadLineasEnBlanco;
	private File archivo;
	private DefaultMutableTreeNode nodo;
	
	//Constructor
	public Archivo(File archivo) {
		this.cantidadDeLineas=0L;
		this.cantidadLineasComentadas=0L;
		this.cantidadLineasEnBlanco=0L;
		this.archivo=archivo;
	}

	//Metodos
	public Long getCantidadDeLineas() {
		return cantidadDeLineas;
	}


	public Long getCantidadLineasComentadas() {
		return cantidadLineasComentadas;
	}
	
	public Double getPorcentajeLineasComentadas() {
		return ((double)cantidadLineasComentadas/(double)(100*(cantidadDeLineas-cantidadLineasEnBlanco)));
	}

	public Long getCantidadLineasEnBlanco() {
		return cantidadLineasEnBlanco;
	}
	
	public Long getCantidadLineasDeCodigo() {
		return this.cantidadDeLineas-this.cantidadLineasComentadas-this.cantidadLineasEnBlanco;
	}
	
	public File getFile(){
		return archivo;
	}
	
	public boolean isDirectory() {
		return archivo.isDirectory();
	}
	
	public String toString() {
		return archivo.getName();
	}
	
	public DefaultMutableTreeNode colocarEnArbol(DefaultMutableTreeNode nodo){
		this.nodo=new DefaultMutableTreeNode(this);
		nodo.add(this.nodo);
		return this.nodo;
	}
	
	public DefaultMutableTreeNode getNodo(){
		return this.nodo;
	}
	
	public void analizar(Opciones opciones) {
		this.cantidadDeLineas=0L;
		this.cantidadLineasComentadas=0L;
		this.cantidadLineasEnBlanco=0L;
		//Validacion de tipo de archivo
		if(!archivo.getName().endsWith(opciones.getExtension())){
			return;
		}
		//
		FileReader fr = null;
		BufferedReader br = null;
		try {
			String linea;
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			linea = br.readLine();
			while(linea != null) {
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
					String [] classSignature = linea.split(" ");
					Clase claseAnalizada = null;
					for(int i = 0; i < classSignature.length; i++) {
						if(classSignature[i].equals("class")) {
							claseAnalizada = new Clase(classSignature[i+1]);
							System.out.println("Encontre una clase llamada "+classSignature[i+1]);
							break;
						}
					}
					if(claseAnalizada != null) {
						//aca analizo los metodos
						br = claseAnalizada.analizar(br);
					}
				}
				linea = br.readLine();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(fr!=null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}