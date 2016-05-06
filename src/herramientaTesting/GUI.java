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
		textFieldActual.setBounds(340, 88, 179, 26);
		contentPane.add(textFieldActual);
		textFieldActual.setColumns(10);
		//JSeparator separator_3 = new JSeparator();
		//separator_3.setBounds(10, 203, 206, 14);
		//contentPane.add(separator_3);
	}	
	private void analizarPath(File file) {
		//Verificar si es una carpeta o un archivo
        if(file.isDirectory()){
        	carpetaBase = new Carpeta(file);
        }
        else{
        	carpetaBase = new Archivo(file);
        }
        arbolDirectorios = new JTree(((Analizable) carpetaBase).colocarEnArbol(new DefaultMutableTreeNode()));
        scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 48, 179, 265);
		contentPane.add(scrollPane);
        scrollPane.add(arbolDirectorios);
        scrollPane.setViewportView(arbolDirectorios);
        carpetaBase.analizar();
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
}