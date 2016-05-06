package herramientaTesting;

import java.io.*;

import javax.swing.tree.DefaultMutableTreeNode;

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

	public Long getCantidadLineasEnBlanco() {
		return cantidadLineasEnBlanco;
	}
	
	public Long getCantidadLineasDeCodigo() {
		return this.cantidadDeLineas-this.cantidadLineasComentadas-this.cantidadLineasEnBlanco;
	}
	
	public File getFile(){
		return archivo;
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
	
	public void analizar() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			String linea;
			fr = new FileReader(archivo);
			br= new BufferedReader(fr);
			linea = br.readLine();
			while(linea != null) {
				linea = linea.trim();
				if(linea.startsWith("/*")) {
					while(linea!=null && !linea.endsWith("*/")) {
						linea = linea.trim();
						if(linea.isEmpty()) {
							cantidadLineasEnBlanco++;
						}
						else {
							this.cantidadLineasComentadas++;
						}
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
				else
					this.cantidadDeLineas++;
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