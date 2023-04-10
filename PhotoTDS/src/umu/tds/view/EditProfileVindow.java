package umu.tds.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Cursor;

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
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;

public class EditProfileVindow {

	private JFrame frame;
	private JTextField usernameField;
	private User user;
	private String newProfilePic;
	private MainWindow mw;
	private JTextField fullnameField;
	private JTextField correoField;
	private JPasswordField contraseñaField;
	private int fortalezaPass;

	/**
	 * Create the application.
	 */
	public EditProfileVindow(String username, MainWindow mw) {
		this.user = Controller.getInstancia().getUser(username);
		this.mw = mw;
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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(EditProfileVindow.class.getResource("/images/ig32.png")));
		frame.setSize(450, 450);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panelNorte = new JPanel();
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);

		JLabel editProfile = new JLabel("Editar perfil");
		editProfile.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		panelNorte.add(editProfile);

		JPanel panelCentral = new JPanel();
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new CardLayout(0, 0));

		JPanel panelInfoPerfil = new JPanel();
		panelCentral.add(panelInfoPerfil, "panelInfoPerfil");
		GridBagLayout gbl_panelInfoPerfil = new GridBagLayout();
		gbl_panelInfoPerfil.columnWidths = new int[] { 15, 119, 0, 0, 15, 0 };
		gbl_panelInfoPerfil.rowHeights = new int[] { 15, 0, 0, 0, 0, 15, 0, 0, 0, 15, 0 };
		gbl_panelInfoPerfil.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelInfoPerfil.rowWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panelInfoPerfil.setLayout(gbl_panelInfoPerfil);

		JLabel fullname = new JLabel("Nombre completo:");
		fullname.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_fullname = new GridBagConstraints();
		gbc_fullname.anchor = GridBagConstraints.EAST;
		gbc_fullname.insets = new Insets(0, 0, 5, 5);
		gbc_fullname.gridx = 1;
		gbc_fullname.gridy = 1;
		panelInfoPerfil.add(fullname, gbc_fullname);

		fullnameField = new JTextField();
		fullnameField.setText(user.getFullName());
		GridBagConstraints gbc_fullnameField = new GridBagConstraints();
		gbc_fullnameField.gridwidth = 2;
		gbc_fullnameField.insets = new Insets(0, 0, 5, 5);
		gbc_fullnameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_fullnameField.gridx = 2;
		gbc_fullnameField.gridy = 1;
		panelInfoPerfil.add(fullnameField, gbc_fullnameField);
		fullnameField.setColumns(10);

		JLabel username = new JLabel("Nombre de usuario:");
		username.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_username = new GridBagConstraints();
		gbc_username.anchor = GridBagConstraints.EAST;
		gbc_username.insets = new Insets(0, 0, 5, 5);
		gbc_username.gridx = 1;
		gbc_username.gridy = 2;
		panelInfoPerfil.add(username, gbc_username);

		usernameField = new JTextField();
		usernameField.setText(user.getUsername());
		usernameField.setColumns(10);
		GridBagConstraints gbc_usernameField = new GridBagConstraints();
		gbc_usernameField.fill = GridBagConstraints.BOTH;
		gbc_usernameField.gridwidth = 2;
		gbc_usernameField.insets = new Insets(0, 0, 5, 5);
		gbc_usernameField.gridx = 2;
		gbc_usernameField.gridy = 2;
		panelInfoPerfil.add(usernameField, gbc_usernameField);

		JLabel lblDescripcion = new JLabel("Descripción: ");
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 3;
		panelInfoPerfil.add(lblDescripcion, gbc_lblDescripcion);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 3;
		panelInfoPerfil.add(scrollPane, gbc_scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setText(user.getDescription());
		scrollPane.setViewportView(textArea);

		JLabel lblFotoDePerfil = new JLabel("Foto de perfil:");
		lblFotoDePerfil.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblFotoDePerfil = new GridBagConstraints();
		gbc_lblFotoDePerfil.anchor = GridBagConstraints.EAST;
		gbc_lblFotoDePerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblFotoDePerfil.gridx = 1;
		gbc_lblFotoDePerfil.gridy = 4;
		panelInfoPerfil.add(lblFotoDePerfil, gbc_lblFotoDePerfil);

		JPanel photoPicPanel = new JPanel();
		GridBagConstraints gbc_photoPicPanel = new GridBagConstraints();
		gbc_photoPicPanel.fill = GridBagConstraints.BOTH;
		gbc_photoPicPanel.insets = new Insets(0, 0, 5, 5);
		gbc_photoPicPanel.gridx = 2;
		gbc_photoPicPanel.gridy = 4;
		panelInfoPerfil.add(photoPicPanel, gbc_photoPicPanel);

		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		String HTMLProfilePic = user.getProfilePic();
		if (HTMLProfilePic.contains(" ")) {
			HTMLProfilePic = HTMLProfilePic.replaceAll(" ", "%20");
		}
		editorPane.setText("<html><img src=file:\"" + HTMLProfilePic + "\"" + " " + "width=75 height=75></img>");
		photoPicPanel.add(editorPane);

		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes (*.png, *.jpg)", "png", "jpg");
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
		gbc_btnSeleccionar.gridy = 4;
		panelInfoPerfil.add(btnSeleccionar, gbc_btnSeleccionar);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (newProfilePic == null)
					newProfilePic = user.getProfilePic();

				if (fullnameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "El campo \"Nombre completo\" no puede estar vacío.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (usernameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "El campo \"Usuario\" no puede estar vacío.", null,
							JOptionPane.ERROR_MESSAGE);
				} else {
					if (Controller.getInstancia().updateUser(user, fullnameField.getText(), usernameField.getText(),
							textArea.getText(), newProfilePic)) {
						JOptionPane.showMessageDialog(frame, "Perfil actualizado.", null,
								JOptionPane.INFORMATION_MESSAGE);
						frame.dispose();
						mw.exit();
						mw = new MainWindow(usernameField.getText(), newProfilePic);
						mw.show();
					} else {
						JOptionPane.showMessageDialog(frame,
								"Ha ocurrido un error al actualizar la información del perfil.", null,
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnActualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnActualizar.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_btnActualizar = new GridBagConstraints();
		gbc_btnActualizar.insets = new Insets(0, 0, 5, 5);
		gbc_btnActualizar.gridx = 2;
		gbc_btnActualizar.gridy = 6;
		panelInfoPerfil.add(btnActualizar, gbc_btnActualizar);

		JButton btnActualizarInfoSensible = new JButton("Correo y contraseña");
		GridBagConstraints gbc_btnActualizarInfoSensible = new GridBagConstraints();
		gbc_btnActualizarInfoSensible.insets = new Insets(0, 0, 5, 5);
		gbc_btnActualizarInfoSensible.gridx = 2;
		gbc_btnActualizarInfoSensible.gridy = 7;
		panelInfoPerfil.add(btnActualizarInfoSensible, gbc_btnActualizarInfoSensible);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnSalir.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_btnSalir = new GridBagConstraints();
		gbc_btnSalir.insets = new Insets(0, 0, 5, 5);
		gbc_btnSalir.gridx = 2;
		gbc_btnSalir.gridy = 8;
		panelInfoPerfil.add(btnSalir, gbc_btnSalir);

		JPanel panelInfoSensible = new JPanel();
		panelCentral.add(panelInfoSensible, "panelInfoSensible");
		GridBagLayout gbl_panelInfoSensible = new GridBagLayout();
		gbl_panelInfoSensible.columnWidths = new int[] { 15, 92, 176, 0, 15, 0 };
		gbl_panelInfoSensible.rowHeights = new int[] { 15, 0, 0, 0, 15, 0, 0, 0, 15, 0 };
		gbl_panelInfoSensible.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelInfoSensible.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panelInfoSensible.setLayout(gbl_panelInfoSensible);

		btnActualizarInfoSensible.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cL = (CardLayout) panelCentral.getLayout();
				cL.show(panelCentral, "panelInfoSensible");
				frame.setSize(450, 250);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnActualizarInfoSensible.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnActualizarInfoSensible.setCursor(Cursor.getDefaultCursor());
			}
		});

		JLabel lblCorreo = new JLabel("Email:");
		lblCorreo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
		gbc_lblCorreo.anchor = GridBagConstraints.EAST;
		gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCorreo.gridx = 1;
		gbc_lblCorreo.gridy = 1;
		panelInfoSensible.add(lblCorreo, gbc_lblCorreo);

		correoField = new JTextField();
		correoField.setText(user.getEmail());
		GridBagConstraints gbc_correoField = new GridBagConstraints();
		gbc_correoField.gridwidth = 2;
		gbc_correoField.insets = new Insets(0, 0, 5, 5);
		gbc_correoField.fill = GridBagConstraints.HORIZONTAL;
		gbc_correoField.gridx = 2;
		gbc_correoField.gridy = 1;
		panelInfoSensible.add(correoField, gbc_correoField);
		correoField.setColumns(10);

		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblContraseña = new GridBagConstraints();
		gbc_lblContraseña.anchor = GridBagConstraints.EAST;
		gbc_lblContraseña.insets = new Insets(0, 0, 5, 5);
		gbc_lblContraseña.gridx = 1;
		gbc_lblContraseña.gridy = 2;
		panelInfoSensible.add(lblContraseña, gbc_lblContraseña);

		JCheckBox chckboxVisible = new JCheckBox("Visible");
		chckboxVisible.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckboxVisible.isSelected()) {
					contraseñaField.setEchoChar((char) 0);
				} else {
					contraseñaField.setEchoChar('•');
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				chckboxVisible.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				chckboxVisible.setCursor(Cursor.getDefaultCursor());
			}
		});

		contraseñaField = new JPasswordField();
		contraseñaField.setText(user.getPassword());
		GridBagConstraints gbc_contraseñaField = new GridBagConstraints();
		gbc_contraseñaField.insets = new Insets(0, 0, 5, 5);
		gbc_contraseñaField.fill = GridBagConstraints.HORIZONTAL;
		gbc_contraseñaField.gridx = 2;
		gbc_contraseñaField.gridy = 2;
		panelInfoSensible.add(contraseñaField, gbc_contraseñaField);
		GridBagConstraints gbc_chckboxVisible = new GridBagConstraints();
		gbc_chckboxVisible.insets = new Insets(0, 0, 5, 5);
		gbc_chckboxVisible.gridx = 3;
		gbc_chckboxVisible.gridy = 2;
		panelInfoSensible.add(chckboxVisible, gbc_chckboxVisible);

		JButton btnActualizarSensible = new JButton("Actualizar");
		btnActualizarSensible.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Matcher registerEmailMatch = StartWindow.emailPat.matcher(correoField.getText());
				if (correoField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "El campo \"Email\" no puede estar vacío.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (!registerEmailMatch.matches()) {
					JOptionPane.showMessageDialog(frame, "El email no es válido.", null, JOptionPane.ERROR_MESSAGE);
				} else if (contraseñaField.getPassword().length == 0) {
					JOptionPane.showMessageDialog(frame, "El campo \"Contraseña\" no puede estar vacío.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (fortalezaPass < 50) {
					JOptionPane.showMessageDialog(frame, "La contraseña no es lo suficientemente fuerte.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (contraseñaField.getPassword().length < StartWindow.MIN_PASSWORD_LENGTH) {
					JOptionPane.showMessageDialog(frame,
							"La contraseña ha de tener mínimo " + StartWindow.MIN_PASSWORD_LENGTH + " caracteres.",
							null, JOptionPane.ERROR_MESSAGE);
				} else {
					if (Controller.getInstancia().updateUserSensibleInfo(user, correoField.getText(),
							String.valueOf(contraseñaField.getPassword()))) {
						JOptionPane.showMessageDialog(frame, "Correo y contraseña actualizados.", null,
								JOptionPane.INFORMATION_MESSAGE);
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(frame,
								"Ha ocurrido un error al actualizar el correo y la contraseña.", null,
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnActualizarSensible.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnActualizarSensible.setCursor(Cursor.getDefaultCursor());
			}
		});

		JProgressBar barraProgreso = new JProgressBar();
		barraProgreso.setToolTipText("Fortaleza de la contraseña");
		GridBagConstraints gbc_barraProgreso = new GridBagConstraints();
		gbc_barraProgreso.fill = GridBagConstraints.HORIZONTAL;
		gbc_barraProgreso.insets = new Insets(0, 0, 5, 5);
		gbc_barraProgreso.gridx = 2;
		gbc_barraProgreso.gridy = 3;
		panelInfoSensible.add(barraProgreso, gbc_barraProgreso);
		GridBagConstraints gbc_btnActualizarSensible = new GridBagConstraints();
		gbc_btnActualizarSensible.insets = new Insets(0, 0, 5, 5);
		gbc_btnActualizarSensible.gridx = 2;
		gbc_btnActualizarSensible.gridy = 5;
		panelInfoSensible.add(btnActualizarSensible, gbc_btnActualizarSensible);

		contraseñaField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				fortalezaPass = StartWindow.fortalezaContraseña(contraseñaField);
				barraProgreso.setValue(fortalezaPass);

				if (fortalezaPass >= 0 && fortalezaPass <= 25) {
					barraProgreso.setForeground(Color.RED);
				} else if (fortalezaPass > 25 && fortalezaPass <= 50) {
					barraProgreso.setForeground(Color.ORANGE);
				} else if (fortalezaPass > 50 && fortalezaPass <= 75) {
					barraProgreso.setForeground(Color.YELLOW);
				} else {
					barraProgreso.setForeground(Color.GREEN);
				}

			}
		});

		fortalezaPass = StartWindow.fortalezaContraseña(contraseñaField);
		barraProgreso.setValue(fortalezaPass);

		if (fortalezaPass >= 0 && fortalezaPass <= 25) {
			barraProgreso.setForeground(Color.RED);
		} else if (fortalezaPass > 25 && fortalezaPass <= 50) {
			barraProgreso.setForeground(Color.ORANGE);
		} else if (fortalezaPass > 50 && fortalezaPass <= 75) {
			barraProgreso.setForeground(Color.YELLOW);
		} else {
			barraProgreso.setForeground(Color.GREEN);
		}

		JButton back = new JButton("Volver");
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cL = (CardLayout) panelCentral.getLayout();
				cL.show(panelCentral, "panelInfoPerfil");
				frame.setSize(450, 450);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				back.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_back = new GridBagConstraints();
		gbc_back.insets = new Insets(0, 0, 5, 5);
		gbc_back.gridx = 2;
		gbc_back.gridy = 6;
		panelInfoSensible.add(back, gbc_back);

		JButton leave = new JButton("Salir");
		leave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				leave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				leave.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_leave = new GridBagConstraints();
		gbc_leave.insets = new Insets(0, 0, 5, 5);
		gbc_leave.gridx = 2;
		gbc_leave.gridy = 7;
		panelInfoSensible.add(leave, gbc_leave);
	}

}
