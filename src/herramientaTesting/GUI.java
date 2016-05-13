package herramientaTesting;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.SystemColor;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static GUI frame;
	private JLabel lblLines;	
	private JLabel lblBlanks;	
	private JLabel lblComment;
	private JLabel lblFile;
	private JLabel lblTotalLines;
	private File archivo;
	private Analizable carpetaBase;
	private JTree arbolDirectorios;
	private JScrollPane scrollPane;
	private JTextField textFieldActual;
	//
	private Opciones opciones;
	private boolean permitirAnalizar;
	private JTextField textFieldLenguaje;
	
	//Application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Constructor
	public GUI() {
		setResizable(false);
		setTitle("Herramienta de Testing (Grupo 11)");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				confirmarSalir();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 554, 374);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldLenguaje = new JTextField();
		textFieldLenguaje.setEditable(false);
		textFieldLenguaje.setBounds(350, 36, 169, 27);
		contentPane.add(textFieldLenguaje);
		textFieldLenguaje.setColumns(10);
		
		opciones = new Opciones(Lenguajes.JAVA,false);
		permitirAnalizar=false;
		textFieldLenguaje.setText(opciones.getLenguaje().getNombre());
		
		JMenuItem mntmAnalizarArchivo = new JMenuItem("Analizar archivo...");
		mntmAnalizarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = fc.showOpenDialog(frame);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            archivo = fc.getSelectedFile();
		            analizarPath(archivo);
		            permitirAnalizar=true;
		        }
			}
		});
		mnArchivo.add(mntmAnalizarArchivo);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmarSalir();
			}
		});
		mnArchivo.add(mntmSalir);
		
		JMenu mnLenguaje = new JMenu("Lenguaje");
		menuBar.add(mnLenguaje);
		
		JMenuItem mntmC = new JMenuItem("C");
		mntmC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones.setLenguaje(Lenguajes.C);
				textFieldLenguaje.setText(opciones.getLenguaje().getNombre());
				actualizarAnalisis();
			}
		});
		mnLenguaje.add(mntmC);
		
		JMenuItem mntmCPP = new JMenuItem("C++");
		mntmCPP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				opciones.setLenguaje(Lenguajes.CPP);
				textFieldLenguaje.setText(opciones.getLenguaje().getNombre());
				actualizarAnalisis();
			}
		});
		mnLenguaje.add(mntmCPP);
		
		JMenuItem mntmJava = new JMenuItem("Java");
		mntmJava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones.setLenguaje(Lenguajes.JAVA);
				textFieldLenguaje.setText(opciones.getLenguaje().getNombre());
				actualizarAnalisis();
			}
		});
		mnLenguaje.add(mntmJava);
		
		JMenu mnComentarios = new JMenu("Comentarios");
		menuBar.add(mnComentarios);
		
		JMenu mnMultilinea = new JMenu("Multilinea");
		mnComentarios.add(mnMultilinea);
		
		JMenuItem mntmContarBlancosComoComentarios = new JMenuItem("Contar blancos como comentarios");
		mntmContarBlancosComoComentarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones.setBlancosMultilinea(false);
				actualizarAnalisis();
			}
		});
		mnMultilinea.add(mntmContarBlancosComoComentarios);
		
		JMenuItem mntmContarBlancosAparte = new JMenuItem("Contar blancos como lineas en blanco");
		mntmContarBlancosAparte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones.setBlancosMultilinea(true);
				actualizarAnalisis();
			}
		});
		mnMultilinea.add(mntmContarBlancosAparte);
		
		JLabel lblLneasDeCdigo = new JLabel("L\u00EDneas de c\u00F3digo");
		lblLneasDeCdigo.setBounds(284, 150, 115, 14);
		contentPane.add(lblLneasDeCdigo);
		
		JLabel lblLneasEnBlanco = new JLabel("L\u00EDneas en blanco");
		lblLneasEnBlanco.setBounds(284, 175, 115, 14);
		contentPane.add(lblLneasEnBlanco);
		
		JLabel lblComentariosDeLnea = new JLabel("Comentarios");
		lblComentariosDeLnea.setBounds(284, 200, 179, 14);
		contentPane.add(lblComentariosDeLnea);
		
		JLabel lblArchivo = new JLabel("Archivo/Carpeta:");
		lblArchivo.setBounds(10, 11, 111, 14);
		contentPane.add(lblArchivo);
		
		JLabel lblLneasTotales = new JLabel("L\u00EDneas totales");
		lblLneasTotales.setBounds(284, 125, 115, 14);
		contentPane.add(lblLneasTotales);
		
		lblTotalLines = new JLabel("");
		lblTotalLines.setBackground(Color.GRAY);
		lblTotalLines.setBounds(398, 125, 121, 14);
		contentPane.add(lblTotalLines);
		
		lblFile = new JLabel("");
		lblFile.setBounds(131, 11, 388, 14);
		contentPane.add(lblFile);
		
		lblLines = new JLabel("");
		lblLines.setBounds(398, 150, 121, 14);
		contentPane.add(lblLines);
		
		lblBlanks = new JLabel("");
		lblBlanks.setBounds(398, 175, 121, 14);
		contentPane.add(lblBlanks);
		
		lblComment = new JLabel("");
		lblComment.setBounds(398, 200, 121, 14);
		contentPane.add(lblComment);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(284, 163, 115, 14);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(284, 188, 115, 14);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(284, 213, 115, 14);
		contentPane.add(separator_2);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(284, 138, 115, 14);
		contentPane.add(separator_4);
		
		JLabel lblActual = new JLabel("Actual:");
		lblActual.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblActual.setBounds(284, 88, 46, 27);
		contentPane.add(lblActual);
		
		textFieldActual = new JTextField();
		textFieldActual.setEditable(false);
		textFieldActual.setBackground(SystemColor.control);
		textFieldActual.setBounds(350, 88, 169, 26);
		contentPane.add(textFieldActual);
		textFieldActual.setColumns(10);
		
		JLabel lblLenguaje = new JLabel("Lenguaje:");
		lblLenguaje.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLenguaje.setBounds(284, 36, 60, 27);
		contentPane.add(lblLenguaje);
	}	
	private void analizarPath(File file) {
		//Verificar si es una carpeta o un archivo
		if(scrollPane!=null)
			contentPane.remove(scrollPane);
		if(arbolDirectorios!=null)
			scrollPane.remove(arbolDirectorios);
        if(file.isDirectory()){
        	carpetaBase = new Carpeta(file);
        }
        else{
        	carpetaBase = new Archivo(file);
        }
        arbolDirectorios = new JTree(carpetaBase.colocarEnArbol(new DefaultMutableTreeNode()));
        arbolDirectorios.setSelectionRow(0); //Selecciona la raiz del arbol por defecto
        scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 48, 179, 265);
		contentPane.add(scrollPane);
        scrollPane.add(arbolDirectorios);
        scrollPane.setViewportView(arbolDirectorios);
        carpetaBase.analizar(opciones);
        actualizarLineas(carpetaBase);
        textFieldActual.setText(carpetaBase.getFile().getName());
        lblFile.setText(file.getAbsolutePath());
        arbolDirectorios.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
            	DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) arbolDirectorios.getLastSelectedPathComponent();
            	Analizable an = (Analizable) selectedNode.getUserObject();
            	actualizarLineas(an);
            	textFieldActual.setText(an.getFile().getName());
            }
        });
        arbolDirectorios.addTreeExpansionListener(new TreeExpansionListener() {
			public void treeCollapsed(TreeExpansionEvent e) {
				arbolDirectorios.setSelectionPath(e.getPath());
			}
			public void treeExpanded(TreeExpansionEvent e) {
			}
		});
	}
	
	private void actualizarLineas(Analizable a){
        lblLines.setText(String.valueOf(a.getCantidadLineasDeCodigo()));
    	lblBlanks.setText(String.valueOf(a.getCantidadLineasEnBlanco()));
    	lblComment.setText(String.valueOf(a.getCantidadLineasComentadas()));
    	lblTotalLines.setText(String.valueOf(a.getCantidadDeLineas()));
	}
	
	private void confirmarSalir() {
		int option = JOptionPane.showConfirmDialog(frame, "Esta seguro que quiere salir?",
													"Cerrando aplicacion", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			frame.dispose();
		}
	}
	
	private void actualizarAnalisis(){
		if(!permitirAnalizar)
			return;
		carpetaBase.analizar(opciones);
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) arbolDirectorios.getLastSelectedPathComponent();
    	Analizable an = (Analizable) selectedNode.getUserObject();
    	actualizarLineas(an);
	}
}