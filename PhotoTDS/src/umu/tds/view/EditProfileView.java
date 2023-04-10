package umu.tds.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import umu.tds.controller.Controller;
import umu.tds.model.User;

import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class EditProfileView {

	private JFrame frame;
	private JTextField usernameField;
	private User user;
	private String newProfilePic;

	/**
	 * Create the application.
	 */
	public EditProfileView(String username) {
		this.user = Controller.getInstancia().getUser(username);
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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(EditProfileView.class.getResource("/images/ig32.png")));
		frame.setSize(450, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panelNorte = new JPanel();
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);
		
		JLabel editProfile = new JLabel("Editar perfil");
		editProfile.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		panelNorte.add(editProfile);
		
		JPanel panelCentral = new JPanel();
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[]{15, 0, 0, 0, 15, 0};
		gbl_panelCentral.rowHeights = new int[]{15, 0, 0, 0, 15, 0, 15, 0};
		gbl_panelCentral.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelCentral.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelCentral.setLayout(gbl_panelCentral);
		
		JLabel username = new JLabel("Nombre de usuario:");
		GridBagConstraints gbc_username = new GridBagConstraints();
		gbc_username.anchor = GridBagConstraints.EAST;
		gbc_username.insets = new Insets(0, 0, 5, 5);
		gbc_username.gridx = 1;
		gbc_username.gridy = 1;
		panelCentral.add(username, gbc_username);
		
		usernameField = new JTextField();
		usernameField.setText(user.getUsername());
		usernameField.setColumns(10);
		GridBagConstraints gbc_usernameField = new GridBagConstraints();
		gbc_usernameField.fill = GridBagConstraints.BOTH;
		gbc_usernameField.gridwidth = 2;
		gbc_usernameField.insets = new Insets(0, 0, 5, 5);
		gbc_usernameField.gridx = 2;
		gbc_usernameField.gridy = 1;
		panelCentral.add(usernameField, gbc_usernameField);
		
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
		textArea.setText(user.getDescription());
		scrollPane.setViewportView(textArea);
		
		JLabel lblFotoDePerfil = new JLabel("Foto de perfil:");
		GridBagConstraints gbc_lblFotoDePerfil = new GridBagConstraints();
		gbc_lblFotoDePerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblFotoDePerfil.gridx = 1;
		gbc_lblFotoDePerfil.gridy = 3;
		panelCentral.add(lblFotoDePerfil, gbc_lblFotoDePerfil);
		
		JPanel photoPicPanel = new JPanel();
		GridBagConstraints gbc_photoPicPanel = new GridBagConstraints();
		gbc_photoPicPanel.fill = GridBagConstraints.BOTH;
		gbc_photoPicPanel.insets = new Insets(0, 0, 5, 5);
		gbc_photoPicPanel.gridx = 2;
		gbc_photoPicPanel.gridy = 3;
		panelCentral.add(photoPicPanel, gbc_photoPicPanel);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		String HTMLProfilePic = user.getProfilePic();
		if (HTMLProfilePic.contains(" ")) {
			HTMLProfilePic = HTMLProfilePic.replaceAll(" ", "%20");
		}
		editorPane.setText("<html><img src=file:\"" + HTMLProfilePic + "\"" + " "
				+ "width=75 height=75></img>");
		photoPicPanel.add(editorPane);
		
		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes (*.png, *.jpg)", "png",
						"jpg");
				chooser.setFileFilter(filtro);
				int resultado = chooser.showOpenDialog(frame);
				if (resultado == JFileChooser.APPROVE_OPTION) {
					newProfilePic = chooser.getSelectedFile().getAbsolutePath();
					String HTMLNewProfilePic = newProfilePic;
					if (newProfilePic.contains(".png") || newProfilePic.contains(".jpg")) {
						if (HTMLNewProfilePic.contains(" ")) {
							HTMLNewProfilePic = HTMLNewProfilePic.replaceAll(" ", "%20");
						}
						editorPane.setText("<html><img src=file:\"" + HTMLNewProfilePic + "\"" + " "
								+ "width=75 height=75></img>");
						frame.setLocationRelativeTo(null);
					} else {
						JOptionPane.showMessageDialog(frame, "La imagen debe ser formato .png o .jpg", null,
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		GridBagConstraints gbc_btnSeleccionar = new GridBagConstraints();
		gbc_btnSeleccionar.insets = new Insets(0, 0, 5, 5);
		gbc_btnSeleccionar.gridx = 3;
		gbc_btnSeleccionar.gridy = 3;
		panelCentral.add(btnSeleccionar, gbc_btnSeleccionar);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(Controller.getInstancia().updateUser(user, usernameField.getText(), textArea.getText(), newProfilePic)) {
					JOptionPane.showMessageDialog(frame, "Perfil actualizado", null,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frame, "Ha ocurrido un error", null,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnActualizar = new GridBagConstraints();
		gbc_btnActualizar.insets = new Insets(0, 0, 5, 5);
		gbc_btnActualizar.gridx = 2;
		gbc_btnActualizar.gridy = 5;
		panelCentral.add(btnActualizar, gbc_btnActualizar);
	}

}
