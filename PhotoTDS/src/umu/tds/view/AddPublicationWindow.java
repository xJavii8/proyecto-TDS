package umu.tds.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class AddPublicationWindow {

	private JFrame frame;
	private JTextField tituloField;
	private JTextField descripcionField;
	private String picPublication;


	/**
	 * Create the application.
	 */
	public AddPublicationWindow() {
		initialize();
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorte = new JPanel();
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);
		
		JLabel NewPublication = new JLabel("Nueva Publicación");
		NewPublication.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelNorte.add(NewPublication);
		
		JPanel panelCentral = new JPanel();
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelCentral.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelCentral.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelCentral.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelCentral.setLayout(gbl_panelCentral);
		
		JLabel lblTítulo = new JLabel("Título: ");
		GridBagConstraints gbc_lblTítulo = new GridBagConstraints();
		gbc_lblTítulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTítulo.anchor = GridBagConstraints.EAST;
		gbc_lblTítulo.gridx = 3;
		gbc_lblTítulo.gridy = 1;
		panelCentral.add(lblTítulo, gbc_lblTítulo);
		
		tituloField = new JTextField();
		
		
		GridBagConstraints gbc_tituloField = new GridBagConstraints();
		gbc_tituloField.fill = GridBagConstraints.BOTH;
		gbc_tituloField.gridwidth = 2;
		gbc_tituloField.insets = new Insets(0, 0, 5, 5);
		gbc_tituloField.gridx = 4;
		gbc_tituloField.gridy = 1;
		panelCentral.add(tituloField, gbc_tituloField);
		tituloField.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripción: ");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 3;
		gbc_lblDescripcion.gridy = 2;
		panelCentral.add(lblDescripcion, gbc_lblDescripcion);
		
		descripcionField = new JTextField();
		GridBagConstraints gbc_descripcionField = new GridBagConstraints();
		gbc_descripcionField.fill = GridBagConstraints.BOTH;
		gbc_descripcionField.gridwidth = 2;
		gbc_descripcionField.insets = new Insets(0, 0, 5, 5);
		gbc_descripcionField.gridx = 4;
		gbc_descripcionField.gridy = 2;
		panelCentral.add(descripcionField, gbc_descripcionField);
		descripcionField.setColumns(10);
		
		JLabel lblFotoElegida = new JLabel("Foto elegida: ");
		GridBagConstraints gbc_lblFotoElegida = new GridBagConstraints();
		gbc_lblFotoElegida.insets = new Insets(0, 0, 5, 5);
		gbc_lblFotoElegida.gridx = 3;
		gbc_lblFotoElegida.gridy = 3;
		panelCentral.add(lblFotoElegida, gbc_lblFotoElegida);
		
		JPanel photoPicPanel = new JPanel();
		GridBagConstraints gbc_photoPicPanel = new GridBagConstraints();
		gbc_photoPicPanel.insets = new Insets(0, 0, 5, 5);
		gbc_photoPicPanel.fill = GridBagConstraints.BOTH;
		gbc_photoPicPanel.gridx = 4;
		gbc_photoPicPanel.gridy = 3;
		panelCentral.add(photoPicPanel, gbc_photoPicPanel);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		photoPicPanel.add(editorPane);
		
		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LookAndFeel actualLF = UIManager.getLookAndFeel();
				JFileChooser chooser = null;
				if (picPublication == null) {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						chooser = new JFileChooser();
						UIManager.setLookAndFeel(actualLF);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					chooser.showOpenDialog(frame);
					picPublication = chooser.getSelectedFile().getAbsolutePath();
					String HTMLProfilePic = picPublication;
					if (picPublication != null) {
						if (picPublication.contains(".png") || picPublication.contains(".jpg")) {
							if (HTMLProfilePic.contains(" ")) {
								HTMLProfilePic = HTMLProfilePic.replaceAll(" ", "%20");
							}
							editorPane.setText("<html><img src=file:\"" + HTMLProfilePic + "\"" + " "
									+ "width=75 height=75></img>");
							frame.setSize(frame.getWidth() + 75, frame.getHeight() + 75);
							frame.setLocationRelativeTo(null);
							btnSeleccionar.setText("Borrar");
						} else {
							JFrame ventanaMultipleProfilePicture = new JFrame();
							JOptionPane.showMessageDialog(ventanaMultipleProfilePicture,
									"La imagen debe ser formato .png o .jpg");
						}
					}
				} else {
					picPublication = null;
					editorPane.setText("");
					btnSeleccionar.setText("Seleccionar");
					frame.setSize(frame.getWidth() - 75, frame.getHeight() - 75);
					frame.setLocationRelativeTo(null);
				}
			}
		});
		GridBagConstraints gbc_btnSeleccionar = new GridBagConstraints();
		gbc_btnSeleccionar.insets = new Insets(0, 0, 5, 5);
		gbc_btnSeleccionar.gridx = 5;
		gbc_btnSeleccionar.gridy = 3;
		panelCentral.add(btnSeleccionar, gbc_btnSeleccionar);
		
		JButton btnSubir = new JButton("Subir");
		GridBagConstraints gbc_btnSubir = new GridBagConstraints();
		gbc_btnSubir.insets = new Insets(0, 0, 0, 5);
		gbc_btnSubir.gridx = 4;
		gbc_btnSubir.gridy = 6;
		panelCentral.add(btnSubir, gbc_btnSubir);
	}

}
