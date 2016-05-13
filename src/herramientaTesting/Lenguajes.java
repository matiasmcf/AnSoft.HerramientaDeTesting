package herramientaTesting;

public enum Lenguajes {
	//Valores
	JAVA(0,"Java",".java"),
	C(1,"C",".c"),
	CPP(2,"C++",".cpp")
	;
	
	//Campos
	private int valor;
	private String nombre;
	private String extension;
	
	//Constructor
	private Lenguajes(int val,String nomb, String ext){
		valor=val;
		extension=ext;
		nombre=nomb;
	}
	
	//Metodos
	public int getValor(){
		return valor;
	}
	
	public String getExtension(){
		return extension;
	}
	
	public String getNombre(){
		return nombre;
	}
}
