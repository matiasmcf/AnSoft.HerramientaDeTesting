package clase;

import java.util.ArrayList;

import metodo.Metodo;

public class Clase {
	String nombre;
	ArrayList<Metodo> metodos;
	
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
}
