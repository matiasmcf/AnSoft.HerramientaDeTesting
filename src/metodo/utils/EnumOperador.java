package metodo.utils;

public enum EnumOperador {
	//Operadores aritmeticos
	SUMA("+"),
	RESTA("-"),
	MULTIPLICACION("*"),
	DIVISION("/"),
	IGUAL("="),
	//Operadores logicos
	IGUAL_A("=="),
	DISTINTO("!="),
	
	MENOR("<"),
	MENOR_IGUAL("<="),
	
	MAYOR(">"),
	MAYOR_IGUAL(">="),
	
	NEGADO("!="),
	XOR("^"),
	OR("||"),
	AND("&&"),
	FOR("for"),
	WHILE("while"),
	CASE("case"),
	SYSO("System.out.println(");
	
	
	private String descripcion;
	private EnumOperador(String textoOperador ){
		this.descripcion= textoOperador;
	}
	
	public String getDescripcion(){
		return descripcion;
	}
	
}
