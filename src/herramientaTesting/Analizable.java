package herramientaTesting;

import java.io.*;

import javax.swing.tree.DefaultMutableTreeNode;

public interface Analizable {
	/**
	 * Analiza el codigo contenido en el archivo/carpeta seleccionado, teniendo en cuenta las opciones seleccionadas.
	 * @param opciones : configuracion seleccionada por el usuario
	 */
	public void analizar(Opciones opciones);
	public Long getCantidadDeLineas();
	public Long getCantidadLineasComentadas();
	public Long getCantidadLineasEnBlanco();
	public Long getCantidadLineasDeCodigo();
	public File getFile();
	public DefaultMutableTreeNode getNodo();
	/**
	 * Coloca los archivos/carpetas del objeto Analizable como un subarbol del nodo enviado.
	 * @param nodo
	 * @return Nodo representando al archivo/carpeta actual
	 */
	public DefaultMutableTreeNode colocarEnArbol(DefaultMutableTreeNode nodo);
	public boolean isDirectory();
}