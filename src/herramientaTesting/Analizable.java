package herramientaTesting;

import java.io.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public interface Analizable {
	public void analizar();
	public Long getCantidadDeLineas();
	public Long getCantidadLineasComentadas();
	public Long getCantidadLineasEnBlanco();
	public Long getCantidadLineasDeCodigo();
	public File getFile();
	public DefaultMutableTreeNode getNodo();
	public DefaultMutableTreeNode colocarEnArbol(DefaultMutableTreeNode nodo);
}