package herramientaTesting;

import java.io.*;
import java.util.*;

import javax.swing.tree.DefaultMutableTreeNode;

public class Carpeta implements Analizable {
	//Atributos
	private ArrayList <Analizable> contenido;
	private Long cantidadDeLineas;
	private Long cantidadLineasComentadas;
	private Long cantidadLineasEnBlanco;
	private File carpeta;
	private DefaultMutableTreeNode nodo;
	
	//Constructor
	public Carpeta(File carpeta){
		contenido = new ArrayList<Analizable>();
		File[] listOfFiles = carpeta.listFiles();
		for (File file : listOfFiles) {
			if(file.isDirectory())
				contenido.add(new Carpeta (file));
			else
				contenido.add(new Archivo(file));
		}
		this.cantidadDeLineas=0L;
		this.cantidadLineasComentadas=0L;
		this.cantidadLineasEnBlanco=0L;
		this.carpeta=carpeta;
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
	
	public Long getCantidadLineasDeCodigo(){
		return this.cantidadDeLineas-this.cantidadLineasComentadas-this.cantidadLineasEnBlanco;
	}
	
	public void agregarContenido(Analizable contenido){
		this.contenido.add(contenido);
	}
	
	public File getFile(){
		return carpeta;
	}
	
	public ArrayList<Analizable> getContenido(){
		return contenido;
	}
	
	public String toString(){
		return carpeta.getName();
	}
	
	public DefaultMutableTreeNode colocarEnArbol(DefaultMutableTreeNode nodo){
		this.nodo = new DefaultMutableTreeNode(this);
		for (Analizable an : contenido) {
				an.colocarEnArbol(this.nodo);
		}
		nodo.add(this.nodo);
		return this.nodo;
	}
	
	public DefaultMutableTreeNode getNodo(){
		return this.nodo;
	}
	
	public void analizar(){
		for(Iterator<Analizable>cont=contenido.iterator();cont.hasNext();){
			Analizable a=cont.next();
			a.analizar();
			cantidadDeLineas+=a.getCantidadDeLineas();
			cantidadLineasComentadas+=a.getCantidadLineasComentadas();
			cantidadLineasEnBlanco+=a.getCantidadLineasEnBlanco();
		}
	}
}