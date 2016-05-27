package metodo.utils;

public enum EnumOperador {
	//Operadores aritmeticos
	SUMA("+"),
	RESTA("-"),
	MULTIPLICACION("*"),
	DIVISION("/"),
	IGUAL("="),
	INCREMENTO("++"),
	DECREMENTO("--"),
	//Operadores logicos
	IGUAL_A("=="),
	DISTINTO("!="),
	
	MENOR("<"),
	MENOR_IGUAL("<="),
	
	MAYOR(">"),
	MAYOR_IGUAL(">="),
	
	NEGADO("!"),
	XOR("^"),
	OR("||"),
	AND("&&");
	
	private String descripcion;
	private EnumOperador(String textoOperador ){
		this.descripcion= textoOperador;
	}
	
	public String getDescripcion(){
		return descripcion;
	}
	
}
