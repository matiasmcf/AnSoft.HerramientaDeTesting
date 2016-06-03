package herramientaTesting;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import clase.Clase;
import metodo.Metodo;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static GUI frame;
	private JLabel lblLineasDeCodigo;	
	private JLabel lblLineasEnBlanco;	
	private JLabel lblPorcentajeComentarios;
	private JLabel lblFile;
	private JLabel lblLineasTotales;
	private File archivo;
	private Analizable carpetaBase;
	private JTree arbolDirectorios;
	private JScrollPane scrollPaneArbol;
	//
	private Opciones opciones;
	private boolean permitirAnalizar;
	//Labels de informes
	private JLabel labelActual;
	private JLabel lblTxtlenguaje;
	private JLabel labelHalsteadLong;
	private JLabel labelHalsteadVol;
	private JLabel labelFanOut;
	private JLabel labelFanIn;
	private JLabel labelComplejidad;
	//Listas
	private JList<Metodo> listaMetodos;
	private JList<Clase> listaClases;
	private DefaultListModel<Clase> listModelClases;
	private DefaultListModel<Metodo> listModelMetodos;
	private JTextArea textAreaCodigo;
	private JLabel labelOperandosTotales;
	private JLabel labelOperadoresTotales;
	private JLabel labelOperandosUnicos;
	private JLabel labelOperadoresUnicos;
	private JLabel lblCantidadOperandosTotales;
	private JLabel lblOperadoresTotales;
	private JLabel lblOperandosUnicos;
	private JLabel lblOperadoresUnicos;
	
	private JLabel warningComplejidad;
	
	private int severidadMinimaComplejidad = 10;
	
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
		setBounds(100, 100, 978, 555);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		opciones = new Opciones(Lenguajes.JAVA,true);
		permitirAnalizar=false;
		
		JScrollPane scrollPaneCodigo = new JScrollPane();
		scrollPaneCodigo.setBounds(10, 296, 582, 198);
		contentPane.add(scrollPaneCodigo);
		
		textAreaCodigo = new JTextArea();
		textAreaCodigo.setTabSize(4);
		textAreaCodigo.setEditable(false);
		textAreaCodigo.setBounds(10, 298, 584, 196);
		//contentPane.add(textAreaCodigo);
		//scrollPaneCodigo.add(textAreaCodigo);
		scrollPaneCodigo.setViewportView(textAreaCodigo);
		
		lblTxtlenguaje = new JLabel("Java");
		lblTxtlenguaje.setBounds(820, 62, 121, 14);
		contentPane.add(lblTxtlenguaje);
		
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
		
		JMenu mnConfiguracion = new JMenu("Configuracion");
		menuBar.add(mnConfiguracion);
		
		JMenu mnLenguaje = new JMenu("Lenguaje");
		mnConfiguracion.add(mnLenguaje);
		
//		JMenuItem mntmC = new JMenuItem("C");
//		mntmC.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				opciones.setLenguaje(Lenguajes.C);
//				textFieldLenguaje.setText(opciones.getLenguaje().getNombre());
//				actualizarAnalisis();
//			}
//		});
//		mnLenguaje.add(mntmC);
//		
//		JMenuItem mntmCPP = new JMenuItem("C++");
//		mntmCPP.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				opciones.setLenguaje(Lenguajes.CPP);
//				textFieldLenguaje.setText(opciones.getLenguaje().getNombre());
//				actualizarAnalisis();
//			}
//		});
//		mnLenguaje.add(mntmCPP);
		
		JMenuItem mntmJava = new JMenuItem("Java");
		mntmJava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opciones.setLenguaje(Lenguajes.JAVA);
				lblTxtlenguaje.setText(opciones.getLenguaje().getNombre());
				actualizarAnalisis();
			}
		});
		mnLenguaje.add(mntmJava);
		
		JMenu mnComentarios = new JMenu("Comentarios");
		mnConfiguracion.add(mnComentarios);
		
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
		
		JMenu mnWarnings = new JMenu("Warnings");
		mnConfiguracion.add(mnWarnings);
		
		JMenuItem mntmSeveridadComplejidad = new JMenuItem("Severidad minima de complejidad");
		mntmSeveridadComplejidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = (String)JOptionPane.showInputDialog(
	                    frame,
	                    "Ingrese un valor entero positivo que representara el limite con el que se mostrara un warning\n",
	                    "Severidad minima para complejidad",
	                    JOptionPane.PLAIN_MESSAGE);
				System.out.println(s);
				try {
					int valorIngresado = Integer.valueOf(s);
					if(valorIngresado > 0) {
						severidadMinimaComplejidad = valorIngresado;
					}
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(frame,
						    "El valor debe ser un numero entero.",
						    "Error al ingresar severidad",
						    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		mnWarnings.add(mntmSeveridadComplejidad);
		
		JLabel lblLneasDeCdigo = new JLabel("L\u00EDneas de c\u00F3digo");
		lblLneasDeCdigo.setBounds(638, 136, 172, 14);
		contentPane.add(lblLneasDeCdigo);
		
		JLabel lblLneasEnBlanco = new JLabel("Lineas en blanco");
		lblLneasEnBlanco.setBounds(638, 161, 172, 14);
		contentPane.add(lblLneasEnBlanco);
		
		JLabel lblComentariosDeLnea = new JLabel("Lineas de Comentarios");
		lblComentariosDeLnea.setBounds(638, 186, 172, 14);
		contentPane.add(lblComentariosDeLnea);
		
		JLabel lblArchivo = new JLabel("Archivo/Carpeta:");
		lblArchivo.setBounds(10, 11, 111, 14);
		contentPane.add(lblArchivo);
		
		JLabel lblLneasTotales = new JLabel("Lineas totales");
		lblLneasTotales.setBounds(638, 111, 172, 14);
		contentPane.add(lblLneasTotales);
		
		lblLineasTotales = new JLabel("");
		lblLineasTotales.setBackground(Color.GRAY);
		lblLineasTotales.setBounds(820, 111, 121, 14);
		contentPane.add(lblLineasTotales);
		
		lblFile = new JLabel("");
		lblFile.setBounds(131, 11, 388, 14);
		contentPane.add(lblFile);
		
		lblLineasDeCodigo = new JLabel("");
		lblLineasDeCodigo.setBounds(820, 136, 121, 14);
		contentPane.add(lblLineasDeCodigo);
		
		lblLineasEnBlanco = new JLabel("");
		lblLineasEnBlanco.setBounds(820, 161, 121, 14);
		contentPane.add(lblLineasEnBlanco);
		
		lblPorcentajeComentarios = new JLabel("");
		lblPorcentajeComentarios.setBounds(820, 186, 121, 14);
		contentPane.add(lblPorcentajeComentarios);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(638, 149, 172, 14);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(638, 174, 172, 14);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(638, 199, 172, 8);
		contentPane.add(separator_2);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(638, 124, 172, 14);
		contentPane.add(separator_4);
		
		JLabel lblActual = new JLabel("Analizando");
		lblActual.setFont(UIManager.getFont("Button.font"));
		lblActual.setBounds(638, 86, 172, 14);
		contentPane.add(lblActual);
		
		JLabel lblLenguaje = new JLabel("Lenguaje");
		lblLenguaje.setFont(UIManager.getFont("Button.font"));
		lblLenguaje.setBounds(638, 62, 172, 14);
		contentPane.add(lblLenguaje);
		
		JScrollPane scrollPaneMetodos = new JScrollPane();
		scrollPaneMetodos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneMetodos.setBounds(452, 62, 140, 196);
		contentPane.add(scrollPaneMetodos);
		
		listModelMetodos = new DefaultListModel<Metodo>();
		listaMetodos = new JList<Metodo>(listModelMetodos);
		listaMetodos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()==false)
					actualizarVistaCodigo(listaMetodos.getSelectedValue());
			}
		});
		listaMetodos.setBackground(SystemColor.control);
		listaMetodos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//scrollPaneMetodos.add(listaMetodos);
		scrollPaneMetodos.setViewportView(listaMetodos);
		
		JLabel lblMetodos = new JLabel("Metodos:");
		lblMetodos.setForeground(Color.BLUE);
		lblMetodos.setBounds(452, 37, 67, 14);
		contentPane.add(lblMetodos);
		
		JScrollPane scrollPaneClases = new JScrollPane();
		scrollPaneClases.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneClases.setBounds(249, 62, 140, 196);
		contentPane.add(scrollPaneClases);
		
		listModelClases = new DefaultListModel<Clase>();
		listaClases = new JList<Clase>(listModelClases);
		listaClases.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaClases.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()==false)
					actualizarListaMetodos(listaClases.getSelectedValue());
			}
		});
		listaClases.setBackground(SystemColor.control);
		listaMetodos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneClases.add(listaClases);
		scrollPaneClases.setRowHeaderView(listaClases);
		
		JLabel lblClases = new JLabel("Clases:");
		lblClases.setForeground(Color.BLUE);
		lblClases.setBounds(249, 37, 46, 14);
		contentPane.add(lblClases);
		
		JLabel lblCodigo = new JLabel("Codigo:");
		lblCodigo.setForeground(Color.BLUE);
		lblCodigo.setBounds(11, 273, 46, 14);
		contentPane.add(lblCodigo);
		
		JLabel lblAnalisis = new JLabel("Analisis:");
		lblAnalisis.setForeground(Color.BLUE);
		lblAnalisis.setBounds(638, 37, 115, 14);
		contentPane.add(lblAnalisis);
		
		JLabel labelExplorador = new JLabel("Explorador:");
		labelExplorador.setForeground(Color.BLUE);
		labelExplorador.setBounds(10, 37, 93, 14);
		contentPane.add(labelExplorador);
		
		labelActual = new JLabel("");
		labelActual.setBounds(820, 86, 121, 14);
		contentPane.add(labelActual);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(638, 99, 172, 14);
		contentPane.add(separator_3);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(638, 75, 172, 14);
		contentPane.add(separator_5);
		
		JLabel lblComplejidadCiclomatica = new JLabel("Complejidad ciclomatica");
		lblComplejidadCiclomatica.setBounds(638, 211, 172, 14);
		contentPane.add(lblComplejidadCiclomatica);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(638, 224, 172, 14);
		contentPane.add(separator_6);
		
		JLabel lblFanIn = new JLabel("Fan In");
		lblFanIn.setBounds(638, 236, 172, 14);
		contentPane.add(lblFanIn);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(638, 249, 172, 14);
		contentPane.add(separator_7);
		
		JLabel lblFanOut = new JLabel("Fan Out");
		lblFanOut.setBounds(638, 260, 172, 14);
		contentPane.add(lblFanOut);
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setBounds(638, 273, 172, 14);
		contentPane.add(separator_8);
		
		JLabel lblHalsteadLongitud = new JLabel("Halstead longitud");
		lblHalsteadLongitud.setBounds(638, 284, 172, 14);
		contentPane.add(lblHalsteadLongitud);
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setBounds(638, 297, 172, 14);
		contentPane.add(separator_9);
		
		JLabel lblHalsteadVolumen = new JLabel("Halstead volumen");
		lblHalsteadVolumen.setBounds(638, 309, 172, 14);
		contentPane.add(lblHalsteadVolumen);
		
		JSeparator separator_10 = new JSeparator();
		separator_10.setBounds(638, 322, 172, 14);
		contentPane.add(separator_10);
		
		labelComplejidad = new JLabel("");
		labelComplejidad.setBounds(820, 211, 121, 14);
		contentPane.add(labelComplejidad);
		
		labelFanIn = new JLabel("");
		labelFanIn.setBounds(820, 236, 121, 14);
		contentPane.add(labelFanIn);
		
		labelFanOut = new JLabel("");
		labelFanOut.setBounds(820, 260, 121, 14);
		contentPane.add(labelFanOut);
		
		labelHalsteadLong = new JLabel("");
		labelHalsteadLong.setBounds(820, 284, 121, 14);
		contentPane.add(labelHalsteadLong);
		
		labelHalsteadVol = new JLabel("");
		labelHalsteadVol.setBounds(820, 309, 121, 14);
		contentPane.add(labelHalsteadVol);
		
		scrollPaneArbol = new JScrollPane();
		scrollPaneArbol.setBounds(10, 62, 200, 196);
		contentPane.add(scrollPaneArbol);
		
		lblOperadoresUnicos = new JLabel("Cantidad Operadores Unicos");
		lblOperadoresUnicos.setBounds(638, 334, 172, 14);
		contentPane.add(lblOperadoresUnicos);
		
		lblOperandosUnicos = new JLabel("Cantidad Operandos Unicos");
		lblOperandosUnicos.setBounds(638, 359, 172, 14);
		contentPane.add(lblOperandosUnicos);
		
		lblOperadoresTotales = new JLabel("Cantidad Operadores Totales");
		lblOperadoresTotales.setBounds(638, 384, 172, 14);
		contentPane.add(lblOperadoresTotales);
		
		lblCantidadOperandosTotales = new JLabel("Cantidad Operandos Totales");
		lblCantidadOperandosTotales.setBounds(638, 409, 172, 14);
		contentPane.add(lblCantidadOperandosTotales);
		
		JSeparator separator_11 = new JSeparator();
		separator_11.setBounds(638, 347, 172, 14);
		contentPane.add(separator_11);
		
		JSeparator separator_12 = new JSeparator();
		separator_12.setBounds(638, 372, 172, 14);
		contentPane.add(separator_12);
		
		JSeparator separator_13 = new JSeparator();
		separator_13.setBounds(638, 397, 172, 14);
		contentPane.add(separator_13);
		
		JSeparator separator_14 = new JSeparator();
		separator_14.setBounds(638, 422, 172, 14);
		contentPane.add(separator_14);
		
		labelOperadoresUnicos = new JLabel("");
		labelOperadoresUnicos.setBounds(820, 334, 46, 14);
		contentPane.add(labelOperadoresUnicos);
		
		labelOperandosUnicos = new JLabel("");
		labelOperandosUnicos.setBounds(820, 359, 46, 14);
		contentPane.add(labelOperandosUnicos);
		
		labelOperadoresTotales = new JLabel("");
		labelOperadoresTotales.setBounds(820, 384, 46, 14);
		contentPane.add(labelOperadoresTotales);
		
		labelOperandosTotales = new JLabel("");
		labelOperandosTotales.setBounds(820, 409, 46, 14);
		contentPane.add(labelOperandosTotales);
		
		warningComplejidad = new JLabel("");
		warningComplejidad.setBounds(785, 202, 22, 22);
		contentPane.add(warningComplejidad);
	}	
	private void analizarPath(File file) {
		//Verificar si es una carpeta o un archivo
		if(scrollPaneArbol!=null)
			contentPane.remove(scrollPaneArbol);
		if(arbolDirectorios!=null)
			scrollPaneArbol.remove(arbolDirectorios);
        if(file.isDirectory()){
        	carpetaBase = new Carpeta(file);
        }
        else{
        	carpetaBase = new Archivo(file);
        }
        arbolDirectorios = new JTree(carpetaBase.colocarEnArbol(new DefaultMutableTreeNode()));
        arbolDirectorios.setSelectionRow(0); //Selecciona la raiz del arbol por defecto
        scrollPaneArbol = new JScrollPane();
        scrollPaneArbol.setBounds(10, 62, 200, 196);
		contentPane.add(scrollPaneArbol);
		scrollPaneArbol.add(arbolDirectorios);
		scrollPaneArbol.setViewportView(arbolDirectorios);
        carpetaBase.analizar(opciones);
        actualizarLineas(carpetaBase);
        labelActual.setText(carpetaBase.getFile().getName());
        lblFile.setText(file.getAbsolutePath());
        arbolDirectorios.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
            	DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) arbolDirectorios.getLastSelectedPathComponent();
            	Analizable an = (Analizable) selectedNode.getUserObject();
            	actualizarLineas(an);
            	labelActual.setText(an.getFile().getName());
            	if(!(an.isDirectory()))
            		actualizarListaClases((Archivo)an);
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
        lblLineasDeCodigo.setText(String.valueOf(a.getCantidadLineasDeCodigo()));
    	lblLineasEnBlanco.setText(String.valueOf(a.getCantidadLineasEnBlanco()));
    	lblPorcentajeComentarios.setText(String.valueOf(a.getCantidadLineasComentadas()));
    	lblLineasTotales.setText(String.valueOf(a.getCantidadDeLineas()));
    	labelComplejidad.setText("");
    	labelFanIn.setText("");
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
	
	private void actualizarListaClases(Archivo a){
		System.out.println("ACTUALIZANDO LISTA DE CLASES");
		listModelClases.clear();
		if(a.getClase()==null)
			System.out.println("CLASE NULA");
		else
			System.out.println("CLASE: "+a.getClase().getNombre());
		listModelClases.addElement(a.getClase());
//		for(int i=0;i<nodo.getChildCount();i++){
//			//if(!((Analizable)nodo.getUserObject()).isDirectory())
//				listModelClases.addElement((DefaultMutableTreeNode)nodo.getChildAt(i));
//		}
	}
	
	private void actualizarListaMetodos(Clase c){
		listModelMetodos.clear();
		if(c==null){
			System.out.println("asd");
		}
		else		
			for(Metodo m : c.getMetodos()){
				listModelMetodos.addElement(m);
			}
	}
	
	private void actualizarVistaCodigo(Metodo m){
		if(m!=null){
			lblLineasDeCodigo.setText(String.valueOf(m.getCantidadLineasDeCodigo()));
	    	lblLineasEnBlanco.setText(String.valueOf(m.getCantidadLineasEnBlanco()));
	    	lblPorcentajeComentarios.setText(String.valueOf(m.getCantidadLineasComentadas()));
	    	lblLineasTotales.setText(String.valueOf(m.getCantidadDeLineas()));
	    	labelComplejidad.setText(String.valueOf(m.getComplejidadCiclomatica()));
	    	labelFanIn.setText(String.valueOf(m.getfanIn()));
	    	labelFanOut.setText(String.valueOf(m.getFanOut()));
	    	labelHalsteadLong.setText(String.valueOf(m.getHalsteadLongitud()));;
	    	labelHalsteadVol.setText(String.format("%.2f",m.getHalsteadVolumen()));
	    	labelOperadoresUnicos.setText(String.valueOf(m.getHalstead().get_n1()));
	    	labelOperandosUnicos.setText(String.valueOf(m.getHalstead().get_n2()));
	    	labelOperadoresTotales.setText(String.valueOf(m.getHalstead().getN1()));
	    	labelOperandosTotales.setText(String.valueOf(m.getHalstead().getN2()));
	    	textAreaCodigo.setText("");
			textAreaCodigo.setText(m.getCuerpo());
			if(m.getComplejidadCiclomatica() > severidadMinimaComplejidad) {
				ImageIcon ico = new ImageIcon(".\\media\\img\\warning_icon.png");
				warningComplejidad.setIcon(ico);
			}
			else {
				warningComplejidad.setIcon(null);
			}
			warningComplejidad.repaint();
		}
	}
}