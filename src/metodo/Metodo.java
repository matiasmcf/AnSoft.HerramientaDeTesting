package metodo;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import herramientaTesting.Opciones;
import metodo.utils.ElementoListaOperadoresCantidad;
import metodo.utils.EnumOperador;

public class Metodo {
	private String nombre;
	private String cuerpo;
	private Long cantidadDeLineas = 0L;
	private Long cantidadLineasComentadas = 0L;
	private Long cantidadLineasEnBlanco = 0L;
	private int complejidadCiclomatica = 1;
	private int fanIn;
	private int fanOut;
	private int halsteadLongitud;
	private double halsteadVolumen;
	private int cantidadLlavesAbiertas;
	private int cantidadLlavesCerradas;
	private int cantidadLlaves;
	//
	private int operadoresDistintos;
	private int operandosDistintos;
	private int operadoresTotales;
	private int operandosTotales;

	private List<EnumOperador> listaDeOperadores;
	private List<ElementoListaOperadoresCantidad> listaOperadoresYRepeticiones;
	//
	private static String[] palabras = { "abstract", "continue", "for", "new", "switch", "assert", "default", "goto",
			"package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements",
			"protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return",
			"transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void",
			"class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while" };
	
	private static String[] simbolos={"+","++","-","--","*",".",";","/","%","!",">","<",">=","<=","==","=",":","~","(",")","{","}","[","]"};

	public Metodo(String name, String body) {
		nombre = name;
		cuerpo = body;
		cantidadDeLineas++;
		if (body.trim().endsWith("{"))
			cantidadLlavesAbiertas = 1;
		// INICIALIZO LAS DOS LISTAS
		inicializarListaEnums();
		inicializarListaOperadoresYCantidades();
	}

	private void inicializarListaEnums() {
		this.listaDeOperadores = new ArrayList<EnumOperador>();
		// Operadores aritmeticos
		this.listaDeOperadores.add(EnumOperador.MENOR_IGUAL);
		this.listaDeOperadores.add(EnumOperador.MAYOR_IGUAL);
		this.listaDeOperadores.add(EnumOperador.DISTINTO);
		this.listaDeOperadores.add(EnumOperador.IGUAL_A);
		this.listaDeOperadores.add(EnumOperador.INCREMENTO);
		this.listaDeOperadores.add(EnumOperador.DECREMENTO);
		
		
		this.listaDeOperadores.add(EnumOperador.SUMA);
		this.listaDeOperadores.add(EnumOperador.RESTA);
		this.listaDeOperadores.add(EnumOperador.MULTIPLICACION);
		this.listaDeOperadores.add(EnumOperador.DIVISION);
		this.listaDeOperadores.add(EnumOperador.IGUAL);
		
		// Operadores logicos
		this.listaDeOperadores.add(EnumOperador.MENOR);
		this.listaDeOperadores.add(EnumOperador.MAYOR);
		this.listaDeOperadores.add(EnumOperador.NEGADO);
		this.listaDeOperadores.add(EnumOperador.XOR);
		this.listaDeOperadores.add(EnumOperador.OR);
		this.listaDeOperadores.add(EnumOperador.AND);
		this.listaDeOperadores.add(EnumOperador.FOR);
		this.listaDeOperadores.add(EnumOperador.WHILE);
		this.listaDeOperadores.add(EnumOperador.CASE);
		this.listaDeOperadores.add(EnumOperador.SYSO);
		this.listaDeOperadores.add(EnumOperador.IF);
		this.listaDeOperadores.add(EnumOperador.PUNTO_Y_COMA);
		this.listaDeOperadores.add(EnumOperador.RETURN);
	}

	private void inicializarListaOperadoresYCantidades() {
		this.listaOperadoresYRepeticiones = new ArrayList<ElementoListaOperadoresCantidad>();
		for (EnumOperador enumAGuardarEnLista : this.listaDeOperadores) {
			this.listaOperadoresYRepeticiones.add(new ElementoListaOperadoresCantidad(enumAGuardarEnLista));
		}
	}

	public Long getCantidadDeLineas() {
		return cantidadDeLineas;
	}

	public Long getCantidadLineasComentadas() {
		return cantidadLineasComentadas;
	}

	public Long getCantidadLineasEnBlanco() {
		return cantidadLineasEnBlanco;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public Long getCantidadLineasDeCodigo() {
		return this.cantidadDeLineas - this.cantidadLineasComentadas - this.cantidadLineasEnBlanco;
	}

	public Double getPorcentajeLineasComentadas() {
		return ((double) cantidadLineasComentadas / (double) (100 * (cantidadDeLineas - cantidadLineasEnBlanco)));
	}

	public int getfanIn() {
		return fanIn;
	}
	
	public int getFanOut() {
		return fanOut;
	}
	
	public int getComplejidadCiclomatica() {
		return complejidadCiclomatica;
	}

	public String toString() {
		return nombre;
	}

	public BufferedReader analizar(BufferedReader br, Opciones opciones) {
		// this.cantidadDeLineas=0L;
		// this.cantidadLineasComentadas=0L;
		// this.cantidadLineasEnBlanco=0L;
		// Validacion de tipo de archivo
		/*
		 * if(!archivo.getName().endsWith(opciones.getExtension())){ return; }
		 */
		//
		// FileReader fr = null;
		// BufferedReader br = null;
		try {
			String linea, aux;
			String[] condiciones;
			// fr = new FileReader(archivo);
			// br = new BufferedReader(fr);
			linea = br.readLine();
			while (linea != null /* && !linea.endsWith("}") */) {
				aux = linea;
				linea = linea.trim();
				if (linea.startsWith("/*")) {
					while (linea != null && !linea.endsWith("*/")) {
						linea = linea.trim();
						if (opciones.getBlancosMultilinea()) {
							if (linea.isEmpty()) {
								cantidadLineasEnBlanco++;
							} else {
								this.cantidadLineasComentadas++;
							}
						} else
							this.cantidadLineasComentadas++;
						this.cantidadDeLineas++;
						linea = br.readLine();
					}
					if (linea != null) {
						this.cantidadLineasComentadas++;
						this.cantidadDeLineas++;
					}
				} else if (linea.startsWith("//")) {
					cantidadLineasComentadas++;
					this.cantidadDeLineas++;
				} else if (linea.isEmpty()) {
					cantidadLineasEnBlanco++;
					this.cantidadDeLineas++;
				} else /* Encontro una linea de codigo */ {
					this.cantidadDeLineas++;
					this.verificarOperadores(linea);
					if (linea.matches(".*if\\s*\\(.*$") || linea.matches(".*while\\s*\\(.*$")
							|| linea.startsWith("for")) {
						condiciones = linea.split("&&|\\|\\|");
						complejidadCiclomatica += condiciones.length;
					}
					if (linea.matches("case\\s.*:$") || linea.matches(".*catch\\s*\\(.*$")) {
						complejidadCiclomatica++;
					}
					if (linea.matches(".*\\..*\\(.*\\);$")) {
						fanOut++;
					}
					if (linea.endsWith("{") || linea.startsWith("{"))
						cantidadLlavesAbiertas++;
					if (linea.endsWith("}") || linea.startsWith("}"))
						cantidadLlavesCerradas++;
					cuerpo = cuerpo.concat("\n");
					System.out.println(aux);
					cuerpo = cuerpo.concat(aux);
					//
				}
				if (cantidadLlavesAbiertas == cantidadLlavesCerradas)
					break;
				linea = br.readLine();
			}
			emparejarListaOperadoresCantidad();
			System.out.println("LLAVES ABIERTAS: " + cantidadLlavesAbiertas);
			System.out.println("LLAVES CERRADAS" + cantidadLlavesCerradas);
			return br;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		/*
		 * finally { if(fr!=null) { try { fr.close(); } catch (IOException e) {
		 * e.printStackTrace(); } } }
		 */
	}
	// TODO: aca llamo cada vez que hay una linea
	private void verificarOperadores(final String linea) {
		int i = 0;
		for(EnumOperador operadorActual : this.listaDeOperadores){
			this.listaOperadoresYRepeticiones.get(i).addRepeticiones(this.analizarLineaConOperadores(operadorActual, linea));
			i++;
		}

	}

	private Long analizarLineaConOperadores(EnumOperador operadorActual, String linea) {
		Long contador = 0L;
		while (linea.indexOf(operadorActual.getDescripcion()) > -1) {
			linea = linea.substring(
					linea.indexOf(operadorActual.getDescripcion()) + operadorActual.getDescripcion().length(),
					linea.length());
			contador++;
		}
		return contador;
	}
	
	
	private void emparejarListaOperadoresCantidad() {
		Long cantidadDeMenorIgual 	= listaOperadoresYRepeticiones.get(0).getCantidad();
		Long cantidadDeMayorIgual 	= listaOperadoresYRepeticiones.get(1).getCantidad();
		Long cantidadDeDistintos  	= listaOperadoresYRepeticiones.get(2).getCantidad();
		Long cantidadDeIgualA		= listaOperadoresYRepeticiones.get(3).getCantidad();
		Long cantidadDeIncrementos 	= listaOperadoresYRepeticiones.get(4).getCantidad();
		Long cantidadDeDecrementos 	= listaOperadoresYRepeticiones.get(5).getCantidad();
		for(ElementoListaOperadoresCantidad operadorYCantidad : listaOperadoresYRepeticiones){
			switch (operadorYCantidad.getEnumOperador()) {
			case SUMA:
					//cada incremento tiene dos mas tengo que restarlo y por eso multiplico por -1
					operadorYCantidad.addRepeticiones((cantidadDeIncrementos*2)*(-1));
				break;
			case RESTA:
					operadorYCantidad.addRepeticiones((cantidadDeDecrementos*2)*(-1));
				break;
			case IGUAL:
					operadorYCantidad.addRepeticiones( (cantidadDeIgualA * 2 + cantidadDeDistintos + cantidadDeMayorIgual+ cantidadDeMenorIgual) * (-1) );
				break;
			case NEGADO:
					operadorYCantidad.addRepeticiones((cantidadDeDistintos) * (-1) );
				break;
			default:
				break;
			}
		}
	}
	
	public void calcularCantOperandos(){
		operandosTotales=0;
		for(ElementoListaOperadoresCantidad e : listaOperadoresYRepeticiones){
			if(e.getCantidad()>0){
			switch(e.getEnumOperador()){
			case AND:
				break;
			case CASE: operandosTotales+=e.getCantidad();
				break;
			case DECREMENTO: operadoresTotales+=e.getCantidad();
			System.out.println("a3");
				break;
			case DISTINTO: operandosTotales+=2*e.getCantidad();
			System.out.println("a4");
				break;
			case DIVISION: operandosTotales+=2*e.getCantidad();
			System.out.println("a5");
				break;
			case FOR:
				break;
			case IF:
				break;
			case IGUAL: operandosTotales+=2*e.getCantidad();
			System.out.println("a6");
				break;
			case IGUAL_A: operandosTotales+=2*e.getCantidad();
			System.out.println("a7");
				break;
			case INCREMENTO: operandosTotales+=e.getCantidad();
			System.out.println("a8");
				break;
			case MAYOR: operandosTotales+=2*e.getCantidad();
			System.out.println("a9");
				break;
			case MAYOR_IGUAL: operandosTotales+=2*e.getCantidad();
			System.out.println("a10");
				break;
			case MENOR: operandosTotales+=2*e.getCantidad();
			System.out.println("a11");
				break;
			case MENOR_IGUAL: operandosTotales+=2*e.getCantidad();
			System.out.println("a12");
				break;
			case MULTIPLICACION: operandosTotales+=2*e.getCantidad();
			System.out.println("a13");
				break;
			case NEGADO: operandosTotales+=e.getCantidad();
			System.out.println("a14");
				break;
			case OR:
				break;
			case PUNTO_Y_COMA:
				break;
			case RESTA: operandosTotales+=2*e.getCantidad();
			System.out.println("a15");
				break;
			case SUMA: operandosTotales+=2*e.getCantidad();
			System.out.println("a16");
				break;
			case SYSO: operandosTotales+=e.getCantidad();
			System.out.println("a17");
				break;
			case WHILE:
				break;
			case XOR:
				break;
			case RETURN: 
				break;
			default:
				break;
			}
		}
		}
	}
	
	public void contarOperandos(){
		String []codigo=cuerpo.split("\n");
		for(int i=0;i<codigo.length;i++){
			
		}
	}

	public int getHalsteadLongitud() {
		if (halsteadLongitud == 0) {
			setHalsteadLongitud();
		}
		return halsteadLongitud;
	}

	private void setHalsteadLongitud() {
		this.halsteadLongitud = 0;
		for (ElementoListaOperadoresCantidad elemento : listaOperadoresYRepeticiones) {
			this.halsteadLongitud += elemento.getCantidad();
			System.out.println(elemento.getEnumOperador().getDescripcion()+"\t"+elemento.getCantidad());
		}
		calcularCantOperandos();
		System.out.println("CANT OPERANDOS: "+operandosTotales);
		halsteadLongitud+=operandosTotales;
	}
	
	private void setHalsteadVolumen() {
		int contador = 0;
		for (ElementoListaOperadoresCantidad elemento : listaOperadoresYRepeticiones) {
			if(elemento.getCantidad()!=0)
				contador += elemento.getCantidad();
		}
		halsteadVolumen=halsteadLongitud*(Math.log(contador)/Math.log(2));
	}
	
	
	public double getHalsteadVolumen() {
		if (halsteadVolumen == 0) {
			setHalsteadVolumen();
		}
		return halsteadVolumen;
	}
}