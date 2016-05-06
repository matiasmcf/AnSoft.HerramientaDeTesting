package herramientaTesting;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;

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
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static GUI frame;
	private JLabel lblLines;	
	private JLabel lblBlanks;	
	private JLabel lblComment;
	private JLabel lblFile;
	private JLabel lblTotalLines;
	private Stack <Analizable> pila;
	private JList<String> listCarpetas;
	private JList<String> listContenido;
	private DefaultListModel<String> listModelCarpetas;
	private DefaultListModel<String> listModelContenido;
	private ArrayList<Analizable>listaContenido;
	private File archivo;
	private Analizable carpetaBase;
	//private String extension;
	
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
		
		pila = new Stack<Analizable>();
		listaContenido = new ArrayList<Analizable>();
		
		JMenuItem mntmAnalizarArchivo = new JMenuItem("Analizar archivo...");
		mntmAnalizarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = fc.showOpenDialog(frame);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            archivo = fc.getSelectedFile();
		            analizarPath(archivo);
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
		mnLenguaje.add(mntmC);
		
		JMenuItem mntmC_1 = new JMenuItem("C++");
		mnLenguaje.add(mntmC_1);
		
		JMenuItem mntmJava = new JMenuItem("Java");
		mnLenguaje.add(mntmJava);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLneasDeCdigo = new JLabel("L\u00EDneas de c\u00F3digo");
		lblLneasDeCdigo.setBounds(284, 150, 115, 14);
		contentPane.add(lblLneasDeCdigo);
		
		JLabel lblLneasEnBlanco = new JLabel("L\u00EDneas en blanco");
		lblLneasEnBlanco.setBounds(284, 175, 115, 14);
		contentPane.add(lblLneasEnBlanco);
		
		JLabel lblComentariosDeLnea = new JLabel("Comentarios");
		lblComentariosDeLnea.setBounds(284, 200, 179, 14);
		contentPane.add(lblComentariosDeLnea);
		
		JLabel lblArchivo = new JLabel("Archivo:");
		lblArchivo.setBounds(10, 11, 46, 14);
		contentPane.add(lblArchivo);
		
		JLabel lblLneasTotales = new JLabel("L\u00EDneas totales");
		lblLneasTotales.setBounds(284, 125, 115, 14);
		contentPane.add(lblLneasTotales);
		
		lblTotalLines = new JLabel("");
		lblTotalLines.setBackground(Color.GRAY);
		lblTotalLines.setBounds(398, 125, 121, 14);
		contentPane.add(lblTotalLines);
		
		lblFile = new JLabel("");
		lblFile.setBounds(57, 11, 462, 14);
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
		
		listModelCarpetas = new DefaultListModel<String>();
		listCarpetas= new JList<String>(listModelCarpetas);
		listCarpetas.setEnabled(false);
		listCarpetas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listCarpetas.setBounds(30, 77, 164, 23);
		contentPane.add(listCarpetas);
		
		listModelContenido = new DefaultListModel<String>();
		listContenido = new JList<String>(listModelContenido);
		listContenido.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
				String aux = listContenido.getSelectedValue();
				Analizable auxAn=carpetaBase;;
				for(Analizable an:listaContenido){
					if(an.getFile().getName().equals(aux))
						{
						auxAn=an;
							break;
						}
				}
				
		        lblLines.setText(auxAn.getCantidadLineasDeCodigo().toString());
		    	lblBlanks.setText(String.valueOf(auxAn.getCantidadLineasEnBlanco()));
		    	lblComment.setText(String.valueOf(auxAn.getCantidadLineasComentadas()));
		    	lblTotalLines.setText(String.valueOf(auxAn.getCantidadDeLineas()));
			}
		});
		listContenido.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listContenido.setBounds(30, 124, 164, 119);
		contentPane.add(listContenido);
		
		JButton btnAbrirCarpeta = new JButton("Abrir");
		btnAbrirCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!listaContenido.isEmpty()&&listContenido.getSelectedIndex()!=-1){
					String aux = listContenido.getSelectedValue();
					for(Analizable an:listaContenido){
						if(an.getFile().isDirectory()&&an.getFile().getName().equals(aux))
							{
								pila.push(carpetaBase);
								carpetaBase=an;
								break;
							}
					}
					actualizarListas(carpetaBase);
				}
			}
		});
		btnAbrirCarpeta.setBounds(205, 146, 69, 23);
		contentPane.add(btnAbrirCarpeta);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!pila.isEmpty()){
					carpetaBase=pila.pop();	
				}
				actualizarListas(carpetaBase);
			}
		});
		btnAtras.setBounds(205, 191, 69, 23);
		contentPane.add(btnAtras);
		
		JLabel lblCarpetaActual = new JLabel("Carpeta/Archivo:");
		lblCarpetaActual.setBounds(30, 51, 164, 23);
		contentPane.add(lblCarpetaActual);
		
		JLabel lblContenido = new JLabel("Contenido");
		lblContenido.setBounds(30, 99, 164, 23);
		contentPane.add(lblContenido);

		//JSeparator separator_3 = new JSeparator();
		//separator_3.setBounds(10, 203, 206, 14);
		//contentPane.add(separator_3);
	}
	
	private void analizarPath(File file) {
		//Verificar si es una carpeta o un archivo
        if(file.isDirectory()){
        	carpetaBase = new Carpeta(file);
        	listModelCarpetas.addElement(carpetaBase.getFile().getName());
        	listaContenido.clear();
        	for(Analizable an:((Carpeta)carpetaBase).getContenido()){
				listModelContenido.addElement(an.getFile().getName());
				listaContenido.add(an);
			}
        }
        else{
        	carpetaBase = new Archivo(file);
        	listaContenido.clear();
        	listModelCarpetas.addElement(carpetaBase.getFile().getName());
        }
        carpetaBase.analizar();
        lblFile.setText(file.getAbsolutePath());
        lblLines.setText(carpetaBase.getCantidadLineasDeCodigo().toString());
    	lblBlanks.setText(String.valueOf(carpetaBase.getCantidadLineasEnBlanco()));
    	lblComment.setText(String.valueOf(carpetaBase.getCantidadLineasComentadas()));
    	lblTotalLines.setText(String.valueOf(carpetaBase.getCantidadDeLineas()));
	}
	
	private void actualizarListas(Analizable a){
		listaContenido.clear();
		listModelContenido.clear();
		listModelCarpetas.clear();
		if(carpetaBase.getFile().isDirectory()){
			listModelCarpetas.addElement(carpetaBase.getFile().getName());
        	listaContenido.clear();
        	for(Analizable an:((Carpeta)carpetaBase).getContenido()){
				listModelContenido.addElement(an.getFile().getName());
				listaContenido.add(an);
			}
		}
		 else{
	        	listaContenido.clear();
	        	listModelCarpetas.addElement(carpetaBase.getFile().getName());
	        }
        lblLines.setText(carpetaBase.getCantidadLineasDeCodigo().toString());
    	lblBlanks.setText(String.valueOf(carpetaBase.getCantidadLineasEnBlanco()));
    	lblComment.setText(String.valueOf(carpetaBase.getCantidadLineasComentadas()));
    	lblTotalLines.setText(String.valueOf(carpetaBase.getCantidadDeLineas()));
	}
	
	private void confirmarSalir() {
		int option = JOptionPane.showConfirmDialog(frame, "Esta seguro que quiere salir?",
													"Cerrando aplicacion", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			frame.dispose();
		}
	}
}