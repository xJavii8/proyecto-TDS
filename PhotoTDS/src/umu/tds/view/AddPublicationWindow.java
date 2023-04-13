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
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import umu.tds.controller.Controller;
import umu.tds.model.Publication;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Toolkit;

public class AddPublicationWindow {

	private JFrame frame;
	private JTextField tituloField;
	private String picPublication;
	
	private String user;

	/**
	 * Create the application.
	 */
	public AddPublicationWindow(String user) {
		this.user = user;
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
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(AddPublicationWindow.class.getResource("/images/ig32.png")));
		frame.setSize(450, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelNorte = new JPanel();
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);

		JLabel NewPublication = new JLabel("Nueva publicación");
		NewPublication.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		panelNorte.add(NewPublication);

		JPanel panelCentral = new JPanel();
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[] { 15, 0, 0, 0, 15, 0 };
		gbl_panelCentral.rowHeights = new int[] { 15, 0, 0, 0, 15, 0, 15, 0 };
		gbl_panelCentral.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelCentral.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelCentral.setLayout(gbl_panelCentral);

		JLabel lblTítulo = new JLabel("Título: ");
		GridBagConstraints gbc_lblTítulo = new GridBagConstraints();
		gbc_lblTítulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTítulo.anchor = GridBagConstraints.EAST;
		gbc_lblTítulo.gridx = 1;
		gbc_lblTítulo.gridy = 1;
		panelCentral.add(lblTítulo, gbc_lblTítulo);

		tituloField = new JTextField();

		GridBagConstraints gbc_tituloField = new GridBagConstraints();
		gbc_tituloField.fill = GridBagConstraints.BOTH;
		gbc_tituloField.gridwidth = 2;
		gbc_tituloField.insets = new Insets(0, 0, 5, 5);
		gbc_tituloField.gridx = 2;
		gbc_tituloField.gridy = 1;
		panelCentral.add(tituloField, gbc_tituloField);
		tituloField.setColumns(10);

		JLabel lblDescripcion = new JLabel("Descripción: ");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 2;
		panelCentral.add(lblDescripcion, gbc_lblDescripcion);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 2;
		panelCentral.add(scrollPane, gbc_scrollPane);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JLabel lblFotoElegida = new JLabel("Foto elegida: ");
		GridBagConstraints gbc_lblFotoElegida = new GridBagConstraints();
		gbc_lblFotoElegida.insets = new Insets(0, 0, 5, 5);
		gbc_lblFotoElegida.gridx = 1;
		gbc_lblFotoElegida.gridy = 3;
		panelCentral.add(lblFotoElegida, gbc_lblFotoElegida);

		JPanel photoPicPanel = new JPanel();
		GridBagConstraints gbc_photoPicPanel = new GridBagConstraints();
		gbc_photoPicPanel.insets = new Insets(0, 0, 5, 5);
		gbc_photoPicPanel.fill = GridBagConstraints.BOTH;
		gbc_photoPicPanel.gridx = 2;
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
				if (picPublication == null) {
					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes (*.png, *.jpg)", "png",
							"jpg");
					chooser.setFileFilter(filtro);
					int resultado = chooser.showOpenDialog(frame);
					if (resultado == JFileChooser.APPROVE_OPTION) {
						picPublication = chooser.getSelectedFile().getAbsolutePath();
						String HTMLProfilePic = picPublication;
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
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSeleccionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnSeleccionar.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_btnSeleccionar = new GridBagConstraints();
		gbc_btnSeleccionar.insets = new Insets(0, 0, 5, 5);
		gbc_btnSeleccionar.gridx = 3;
		gbc_btnSeleccionar.gridy = 3;
		panelCentral.add(btnSeleccionar, gbc_btnSeleccionar);

		JButton btnSubir = new JButton("Subir");
		btnSubir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String titulo = tituloField.getText();
				boolean existe;
				
				Optional<Publication> publ = Controller.getInstancia().getPublication(titulo);
				
				if (publ.isEmpty()) {
					existe = false;
				}else {
					existe = publ.get().getTitle().equals(titulo);
				}
				
				
				if (existe) {
					JOptionPane.showMessageDialog(frame,
							"Ya existe una  publicación con ese nombre" , null,
							JOptionPane.ERROR_MESSAGE);
				}else {
					Controller.getInstancia().createPhoto(user, titulo, titulo, titulo);
					JOptionPane.showMessageDialog(frame,
							"Publicación subida" , null,
							JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
				}
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSubir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnSubir.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_btnSubir = new GridBagConstraints();
		gbc_btnSubir.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubir.gridx = 2;
		gbc_btnSubir.gridy = 5;
		panelCentral.add(btnSubir, gbc_btnSubir);
	}

}
