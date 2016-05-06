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
import javax.swing.JScrollPane;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static GUI frame;
	private JLabel lblLines;	
	private JLabel lblBlanks;	
	private JLabel lblComment;
	private JLabel lblMulticomment;
	private JLabel lblFile;
	private JLabel lblTotalLines;
	private String extension;
	
	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
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
		            File file = fc.getSelectedFile();
		            //Verificar si es una carpeta o un archivo
		            Analizable a;
		            if(file.isDirectory())
		            	a = new Carpeta(file);
		            else
		            	a = new Archivo(file);
		            a.analizar();
//		            ClaseJava claseJava = new ClaseJava(file);
		            lblFile.setText(file.getAbsolutePath());
		            lblLines.setText(a.getCantidadLineasDeCodigo().toString());
		        	lblBlanks.setText(String.valueOf(a.getCantidadLineasEnBlanco()));
		        	lblComment.setText(String.valueOf(a.getCantidadLineasComentadas()));
		        	//lblMulticomment;
		        	lblTotalLines.setText(String.valueOf(a.getCantidadDeLineas()));
		            //This is where a real application would open the file.
		            //log.append("Opening: " + file.getName() + "." + newline);
		        } else {
		            //log.append("Open command cancelled by user." + newline);
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
		lblLneasDeCdigo.setBounds(10, 115, 115, 14);
		contentPane.add(lblLneasDeCdigo);
		
		JLabel lblLneasEnBlanco = new JLabel("L\u00EDneas en blanco");
		lblLneasEnBlanco.setBounds(10, 140, 115, 14);
		contentPane.add(lblLneasEnBlanco);
		
		JLabel lblComentariosDeLnea = new JLabel("Comentarios");
		lblComentariosDeLnea.setBounds(10, 165, 179, 14);
		contentPane.add(lblComentariosDeLnea);
		
		//JLabel lblComentariosMultilnea = new JLabel("Comentarios multil\u00EDnea");
		//lblComentariosMultilnea.setBounds(10, 190, 131, 14);
		//contentPane.add(lblComentariosMultilnea);
		
		JLabel lblArchivo = new JLabel("Archivo:");
		lblArchivo.setBounds(10, 11, 46, 14);
		contentPane.add(lblArchivo);
		
		JLabel lblLneasTotales = new JLabel("L\u00EDneas totales");
		lblLneasTotales.setBounds(10, 90, 115, 14);
		contentPane.add(lblLneasTotales);
		
		lblTotalLines = new JLabel("");
		lblTotalLines.setBackground(Color.GRAY);
		lblTotalLines.setBounds(124, 90, 121, 14);
		contentPane.add(lblTotalLines);
		
		lblFile = new JLabel("");
		lblFile.setBounds(57, 11, 462, 14);
		contentPane.add(lblFile);
		
		lblLines = new JLabel("");
		lblLines.setBounds(124, 115, 121, 14);
		contentPane.add(lblLines);
		
		lblBlanks = new JLabel("");
		lblBlanks.setBounds(124, 140, 121, 14);
		contentPane.add(lblBlanks);
		
		lblComment = new JLabel("");
		lblComment.setBounds(124, 165, 121, 14);
		contentPane.add(lblComment);
		
		//lblMulticomment = new JLabel("");
		//lblMulticomment.setBounds(135, 190, 79, 14);
		//contentPane.add(lblMulticomment);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 128, 115, 14);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 153, 115, 14);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 178, 115, 14);
		contentPane.add(separator_2);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(10, 103, 115, 14);
		contentPane.add(separator_4);
		
		
		
		//JSeparator separator_3 = new JSeparator();
		//separator_3.setBounds(10, 203, 206, 14);
		//contentPane.add(separator_3);
	}
	
	private void confirmarSalir() {
		int option = JOptionPane.showConfirmDialog(frame, "Esta seguro que quiere salir?",
													"Cerrando aplicacion", JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			frame.dispose();
		}
	}
	
	public void setLenguaje(String lang) {
		extension = lang;
	}
}