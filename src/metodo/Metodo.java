package metodo;

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
	
	public void analizar() {
		
	}
}