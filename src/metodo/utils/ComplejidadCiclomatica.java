package metodo.utils;
import java.util.HashMap;

import metodo.Metodo;
public class ComplejidadCiclomatica {
	private String [] predicados={"if","for","while","switch"};
	private HashMap<String, Integer> ocurrenciaPredicados= new HashMap<String, Integer>();
	private int cantidadPredicados;
	private int complejidadCiclomatica=0;
	public ComplejidadCiclomatica(Metodo metodo){
		cantidadPredicados=predicados.length;
		inicializarEnCero();
		for (int j = 0; j < metodo.getDefinicion().length; j++) {
		for(int i=0; i<cantidadPredicados; i++)
			if(metodo.getDefinicion()[j].contains(predicados[i]+"(") || metodo.getDefinicion()[j].contains(predicados[i]+" (")){
				ocurrenciaPredicados.put(predicados[i], ocurrenciaPredicados.get(predicados[i])+1);				
				ocurrenciaPredicados.put(predicados[i], ocurrenciaPredicados.get(predicados[i])+contarCondiciones(metodo.getDefinicion()[j], "&&"));
				ocurrenciaPredicados.put(predicados[i], ocurrenciaPredicados.get(predicados[i])+contarCondiciones(metodo.getDefinicion()[j], "||"));
				break;
			}
		}
		calcularOcurrencias();
	}
	private void calcularOcurrencias (){
		for (int i = 0; i < predicados.length; i++) {
			complejidadCiclomatica+=ocurrenciaPredicados.get(predicados[i]);
		}
	}
	private void inicializarEnCero(){
		for (int i = 0; i < cantidadPredicados; i++) {
			ocurrenciaPredicados.put(predicados[i],0);
		}
	}
	private int contarCondiciones(String linea, String condicion){
		int cantidadCondiciones=0;
		while (linea.indexOf(condicion) > -1) {
		      linea = linea.substring(linea.indexOf(condicion)+condicion.length(),linea.length());
		      cantidadCondiciones++; 
		}
		return cantidadCondiciones;
	}
	public int getComplejidadCiclomatica() {
		return complejidadCiclomatica==0?0:complejidadCiclomatica+1;
	}
}