package herramientaTesting;

public enum Lenguajes {
	//Valores
	JAVA(0,"Java"),
	C(1,"C"),
	CPP(2,"C++")
	;
	
	//Campos
	private int valor;
	private String nombre;
	
	//Constructor
	private Lenguajes(int val, String nom){
		valor=val;
		nombre=nom;
	}
	
	//Metodos
	public int getValor(){
		return valor;
	}
	
	public String getNombre(){
		return nombre;
	}
}
