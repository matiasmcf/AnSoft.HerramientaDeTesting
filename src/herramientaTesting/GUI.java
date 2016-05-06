package herramientaTesting;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.LineNumberInputStream;
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
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.AbstractListModel;
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
		            analizarCodigo(archivo);
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
		lblLneasDeCdigo.setBounds(10, 211, 115, 14);
		contentPane.add(lblLneasDeCdigo);
		
		JLabel lblLneasEnBlanco = new JLabel("L\u00EDneas en blanco");
		lblLneasEnBlanco.setBounds(10, 236, 115, 14);
		contentPane.add(lblLneasEnBlanco);
		
		JLabel lblComentariosDeLnea = new JLabel("Comentarios");
		lblComentariosDeLnea.setBounds(10, 261, 179, 14);
		contentPane.add(lblComentariosDeLnea);
		
		JLabel lblArchivo = new JLabel("Archivo:");
		lblArchivo.setBounds(10, 11, 46, 14);
		contentPane.add(lblArchivo);
		
		JLabel lblLneasTotales = new JLabel("L\u00EDneas totales");
		lblLneasTotales.setBounds(10, 186, 115, 14);
		contentPane.add(lblLneasTotales);
		
		lblTotalLines = new JLabel("");
		lblTotalLines.setBackground(Color.GRAY);
		lblTotalLines.setBounds(124, 186, 121, 14);
		contentPane.add(lblTotalLines);
		
		lblFile = new JLabel("");
		lblFile.setBounds(57, 11, 462, 14);
		contentPane.add(lblFile);
		
		lblLines = new JLabel("");
		lblLines.setBounds(124, 211, 121, 14);
		contentPane.add(lblLines);
		
		lblBlanks = new JLabel("");
		lblBlanks.setBounds(124, 236, 121, 14);
		contentPane.add(lblBlanks);
		
		lblComment = new JLabel("");
		lblComment.setBounds(124, 261, 121, 14);
		contentPane.add(lblComment);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 224, 115, 14);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 249, 115, 14);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 274, 115, 14);
		contentPane.add(separator_2);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(10, 199, 115, 14);
		contentPane.add(separator_4);
		
		listModelCarpetas = new DefaultListModel<String>();
		listCarpetas= new JList<String>(listModelCarpetas);
		listCarpetas.setEnabled(false);
		listCarpetas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listCarpetas.setBounds(20, 36, 187, 119);
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
		listContenido.setBounds(296, 36, 164, 119);
		contentPane.add(listContenido);
		
		JButton btnAbrirCarpeta = new JButton("Abrir");
		btnAbrirCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!listaContenido.isEmpty()&&listContenido.getSelectedIndex()!=-1){
					String aux = listContenido.getSelectedValue();
					pila.push(carpetaBase);
					for(Analizable an:listaContenido){
						if(an.getFile().getName().equals(aux))
							{
								carpetaBase=an;
								break;
							}
					}
					listaContenido.clear();
					listModelContenido.clear();
					listModelCarpetas.clear();
					actualizarListas(carpetaBase);
				}
			}
		});
		btnAbrirCarpeta.setBounds(217, 36, 69, 23);
		contentPane.add(btnAbrirCarpeta);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!pila.isEmpty()){
					listaContenido.clear();
					listModelContenido.clear();
					listModelCarpetas.clear();
					carpetaBase=pila.pop();
					actualizarListas(carpetaBase);
				}
			}
		});
		btnAtras.setBounds(217, 70, 69, 23);
		contentPane.add(btnAtras);

		//JSeparator separator_3 = new JSeparator();
		//separator_3.setBounds(10, 203, 206, 14);
		//contentPane.add(separator_3);
	}
	
	private void analizarCodigo(File file) {
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
	}
	
	private void confirmarSalir() {
		int option = JOptionPane.showConfirmDialog(frame, "Esta seguro que quiere salir?",
													"Cerrando aplicacion", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			frame.dispose();
		}
	}
}