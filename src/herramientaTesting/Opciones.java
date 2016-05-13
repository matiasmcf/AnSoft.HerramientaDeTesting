package herramientaTesting;

public class Opciones {
	//Atributos
	private boolean blancosEnMultilinea;
	private Lenguajes lenguaje;
	
	//Constructor
	/**
	 * Constructor de la clase
	 * @param l : lenguaje seleccionado para el analisis del codigo
	 * @param v : excluir lineas en blanco de los comentarios multilinea
	 */
	public Opciones(Lenguajes l, boolean v){
		lenguaje=l;
		blancosEnMultilinea=v;
	}
	
	//Get-Set
	/**
	 * true: cuenta las lineas en blanco dentro de los comentarios multilinea como "lineas en blanco" en lugar de como parte del comentario.
	 * false: cuenta las lineas en blanco dentro de los comentarios multilinea como comentarios.
	 */
	public void setBlancosMultilinea(boolean v){
		blancosEnMultilinea=v;
	}
	
	/**
	 * @return Valor seleccionado por el usuario en el menu. Devuelve true si las lineas en blanco dentro de comentarios multilinea NO deben contarse como parte de los mismos
	 */
	public boolean getBlancosMultilinea(){
		return blancosEnMultilinea;
	}
	
	/**
	 * Establece el lenguaje seleccionado para el analisis del codigo
	 */
	public void setLenguaje(Lenguajes l){
		lenguaje=l;
	}
	
	/**
	 * @return Lenguaje seleccionado por el usuario en el menu
	 */
	public Lenguajes getLenguaje(){
		return lenguaje;
	}
	
	/**
	 * @return Extension del lenguaje seleccionado
	 */
	public String getExtension(){
		return lenguaje.getExtension();
	}
}
