package umu.tds.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Predicate;
import java.util.regex.Matcher;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import umu.tds.controller.Controller;

public class RegisterWindow {

	private JFrame frame;
	private StartWindow sw;
	private JPanel panelRegister;
	private String profilePic_Register;
	private JPanel panelCentral;

	/**
	 * Create the application.
	 */
	public RegisterWindow(StartWindow sw, JPanel panelCentral) {
		this.sw = sw;
		this.panelCentral = panelCentral;
		initialize();
	}

	public JPanel getRegisterPanel() {
		return panelRegister;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panelRegister = new JPanel();
		GridBagLayout gbl_panelRegister = new GridBagLayout();
		gbl_panelRegister.columnWidths = new int[] { 15, 0, 0, 15, 0, 15, 0 };
		gbl_panelRegister.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 32, 15, 0, 0, 0, -12, 0, 0 };
		gbl_panelRegister.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelRegister.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0,
				0.0, Double.MIN_VALUE };
		panelRegister.setLayout(gbl_panelRegister);

		JLabel labelEmail_Register = new JLabel("* Email:");
		GridBagConstraints gbc_labelEmail_Register = new GridBagConstraints();
		gbc_labelEmail_Register.anchor = GridBagConstraints.SOUTHEAST;
		gbc_labelEmail_Register.insets = new Insets(0, 0, 5, 5);
		gbc_labelEmail_Register.gridx = 1;
		gbc_labelEmail_Register.gridy = 0;
		panelRegister.add(labelEmail_Register, gbc_labelEmail_Register);

		JTextField emailField_Register = new JTextField();
		emailField_Register.setColumns(10);
		GridBagConstraints gbc_emailField_Register = new GridBagConstraints();
		gbc_emailField_Register.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailField_Register.gridwidth = 2;
		gbc_emailField_Register.insets = new Insets(0, 0, 5, 5);
		gbc_emailField_Register.gridx = 2;
		gbc_emailField_Register.gridy = 0;
		panelRegister.add(emailField_Register, gbc_emailField_Register);

		JLabel labelFullName_Register = new JLabel("* Nombre completo:");
		GridBagConstraints gbc_labelFullName_Register = new GridBagConstraints();
		gbc_labelFullName_Register.anchor = GridBagConstraints.EAST;
		gbc_labelFullName_Register.insets = new Insets(0, 0, 5, 5);
		gbc_labelFullName_Register.gridx = 1;
		gbc_labelFullName_Register.gridy = 1;
		panelRegister.add(labelFullName_Register, gbc_labelFullName_Register);

		JTextField fullnameField_Register = new JTextField();
		fullnameField_Register.setColumns(10);
		GridBagConstraints gbc_fullnameField_Register = new GridBagConstraints();
		gbc_fullnameField_Register.fill = GridBagConstraints.HORIZONTAL;
		gbc_fullnameField_Register.gridwidth = 2;
		gbc_fullnameField_Register.insets = new Insets(0, 0, 5, 5);
		gbc_fullnameField_Register.gridx = 2;
		gbc_fullnameField_Register.gridy = 1;
		panelRegister.add(fullnameField_Register, gbc_fullnameField_Register);

		JLabel labelUser_Register = new JLabel("* Usuario:");
		GridBagConstraints gbc_labelUser_Register = new GridBagConstraints();
		gbc_labelUser_Register.fill = GridBagConstraints.VERTICAL;
		gbc_labelUser_Register.anchor = GridBagConstraints.EAST;
		gbc_labelUser_Register.insets = new Insets(0, 0, 5, 5);
		gbc_labelUser_Register.gridx = 1;
		gbc_labelUser_Register.gridy = 2;
		panelRegister.add(labelUser_Register, gbc_labelUser_Register);

		JTextField userField_Register = new JTextField();
		userField_Register.setColumns(10);
		GridBagConstraints gbc_userField_Register = new GridBagConstraints();
		gbc_userField_Register.fill = GridBagConstraints.HORIZONTAL;
		gbc_userField_Register.gridwidth = 2;
		gbc_userField_Register.insets = new Insets(0, 0, 5, 5);
		gbc_userField_Register.gridx = 2;
		gbc_userField_Register.gridy = 2;
		panelRegister.add(userField_Register, gbc_userField_Register);

		JLabel labelPassword_Register = new JLabel("* Contrase\u00F1a:");
		GridBagConstraints gbc_labelPassword_Register = new GridBagConstraints();
		gbc_labelPassword_Register.fill = GridBagConstraints.VERTICAL;
		gbc_labelPassword_Register.anchor = GridBagConstraints.EAST;
		gbc_labelPassword_Register.insets = new Insets(0, 0, 5, 5);
		gbc_labelPassword_Register.gridx = 1;
		gbc_labelPassword_Register.gridy = 3;
		panelRegister.add(labelPassword_Register, gbc_labelPassword_Register);

		JProgressBar barraProgreso = new JProgressBar();
		barraProgreso.setToolTipText("Fortaleza de la contraseña");
		GridBagConstraints gbc_barraProgreso = new GridBagConstraints();
		gbc_barraProgreso.insets = new Insets(0, 0, 5, 5);
		gbc_barraProgreso.gridx = 2;
		gbc_barraProgreso.gridy = 4;
		panelRegister.add(barraProgreso, gbc_barraProgreso);

		JPasswordField passwordField_Register = new JPasswordField();
		passwordField_Register.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

				int valor = Utilities.fortalezaContraseña(passwordField_Register);
				barraProgreso.setValue(valor);

				if (valor >= 0 && valor <= 25) {
					barraProgreso.setForeground(Color.RED);
				} else if (valor > 25 && valor <= 50) {
					barraProgreso.setForeground(Color.ORANGE);
				} else if (valor > 50 && valor <= 75) {
					barraProgreso.setForeground(Color.YELLOW);
				} else {
					barraProgreso.setForeground(Color.GREEN);
				}

			}
		});
		GridBagConstraints gbc_passwordField_Register = new GridBagConstraints();
		gbc_passwordField_Register.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_Register.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_Register.gridx = 2;
		gbc_passwordField_Register.gridy = 3;
		panelRegister.add(passwordField_Register, gbc_passwordField_Register);

		JCheckBox chckbxVisiblePassword_Register = new JCheckBox("Visible");
		chckbxVisiblePassword_Register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckbxVisiblePassword_Register.isSelected()) {
					passwordField_Register.setEchoChar((char) 0);
				} else {
					passwordField_Register.setEchoChar('•');
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				chckbxVisiblePassword_Register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				chckbxVisiblePassword_Register.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_chckbxVisiblePassword_Register = new GridBagConstraints();
		gbc_chckbxVisiblePassword_Register.fill = GridBagConstraints.VERTICAL;
		gbc_chckbxVisiblePassword_Register.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxVisiblePassword_Register.gridx = 3;
		gbc_chckbxVisiblePassword_Register.gridy = 3;
		panelRegister.add(chckbxVisiblePassword_Register, gbc_chckbxVisiblePassword_Register);

		JLabel labelBirthdayDate_Register = new JLabel("* Fecha de nacimiento:");
		GridBagConstraints gbc_labelBirthdayDate_Register = new GridBagConstraints();
		gbc_labelBirthdayDate_Register.anchor = GridBagConstraints.EAST;
		gbc_labelBirthdayDate_Register.insets = new Insets(0, 0, 5, 5);
		gbc_labelBirthdayDate_Register.gridx = 1;
		gbc_labelBirthdayDate_Register.gridy = 5;
		panelRegister.add(labelBirthdayDate_Register, gbc_labelBirthdayDate_Register);

		JDateChooser dateChooser_Register = new JDateChooser();
		dateChooser_Register.getCalendarButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				dateChooser_Register.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				dateChooser_Register.getCalendarButton().setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_dateChooser_Register = new GridBagConstraints();
		gbc_dateChooser_Register.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser_Register.gridwidth = 2;
		gbc_dateChooser_Register.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser_Register.gridx = 2;
		gbc_dateChooser_Register.gridy = 5;
		panelRegister.add(dateChooser_Register, gbc_dateChooser_Register);

		JLabel labelPresentation_Register = new JLabel("Presentaci\u00F3n:");
		GridBagConstraints gbc_labelPresentation_Register = new GridBagConstraints();
		gbc_labelPresentation_Register.anchor = GridBagConstraints.NORTHEAST;
		gbc_labelPresentation_Register.insets = new Insets(0, 0, 5, 5);
		gbc_labelPresentation_Register.gridx = 1;
		gbc_labelPresentation_Register.gridy = 6;
		panelRegister.add(labelPresentation_Register, gbc_labelPresentation_Register);

		JScrollPane scrollPane_Register = new JScrollPane();
		GridBagConstraints gbc_scrollPane_Register = new GridBagConstraints();
		gbc_scrollPane_Register.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_Register.gridwidth = 2;
		gbc_scrollPane_Register.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_Register.gridx = 2;
		gbc_scrollPane_Register.gridy = 6;
		panelRegister.add(scrollPane_Register, gbc_scrollPane_Register);

		JTextArea description_Register = new JTextArea();
		description_Register.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (description_Register.getText().length() > Constantes.MAX_DESCRIP_REGISTER_LENGTH) {
					description_Register.setText(description_Register.getText().substring(0, Constantes.MAX_DESCRIP_REGISTER_LENGTH));
				}
			}
		});
		scrollPane_Register.setViewportView(description_Register);

		JLabel labelProfilePhoto_Register = new JLabel("Foto de perfil:");
		GridBagConstraints gbc_labelProfilePhoto_Register = new GridBagConstraints();
		gbc_labelProfilePhoto_Register.anchor = GridBagConstraints.EAST;
		gbc_labelProfilePhoto_Register.insets = new Insets(0, 0, 5, 5);
		gbc_labelProfilePhoto_Register.gridx = 1;
		gbc_labelProfilePhoto_Register.gridy = 7;
		panelRegister.add(labelProfilePhoto_Register, gbc_labelProfilePhoto_Register);

		JPanel photoPicPanel = new JPanel();
		GridBagConstraints gbc_photoPicPanel = new GridBagConstraints();
		gbc_photoPicPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_photoPicPanel.insets = new Insets(0, 0, 5, 5);
		gbc_photoPicPanel.gridx = 2;
		gbc_photoPicPanel.gridy = 7;
		panelRegister.add(photoPicPanel, gbc_photoPicPanel);

		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		photoPicPanel.add(editorPane);

		JButton btnSelectPhoto_Register = new JButton("Seleccionar");
		btnSelectPhoto_Register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (profilePic_Register == null) {
					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes (*.png, *.jpg)", "png",
							"jpg");
					chooser.setFileFilter(filtro);
					int resultado = chooser.showOpenDialog(frame);
					if (resultado == JFileChooser.APPROVE_OPTION) {
						profilePic_Register = chooser.getSelectedFile().getAbsolutePath();
						String HTMLProfilePic = profilePic_Register;
						if (profilePic_Register.contains(".png") || profilePic_Register.contains(".jpg")) {
							if (HTMLProfilePic.contains(" ")) {
								HTMLProfilePic = HTMLProfilePic.replaceAll(" ", "%20");
							}
							editorPane.setText("<html><img src=file:\"" + HTMLProfilePic + "\"" + " "
									+ "width=75 height=75></img>");
							frame.setSize(frame.getWidth() + Constantes.HTML_PHOTO_SELECTED_SIZE,
									frame.getHeight() + Constantes.HTML_PHOTO_SELECTED_SIZE);
							btnSelectPhoto_Register.setText("Borrar");
						} else {
							JOptionPane.showMessageDialog(frame, "La imagen debe ser formato .png o .jpg", null,
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} else {
					profilePic_Register = null;
					editorPane.setText("");
					btnSelectPhoto_Register.setText("Seleccionar");
					frame.setSize(frame.getWidth() - Constantes.HTML_PHOTO_SELECTED_SIZE,
							frame.getHeight() - Constantes.HTML_PHOTO_SELECTED_SIZE);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnSelectPhoto_Register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnSelectPhoto_Register.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_btnSelectPhoto_Register = new GridBagConstraints();
		gbc_btnSelectPhoto_Register.anchor = GridBagConstraints.WEST;
		gbc_btnSelectPhoto_Register.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelectPhoto_Register.gridx = 3;
		gbc_btnSelectPhoto_Register.gridy = 7;
		panelRegister.add(btnSelectPhoto_Register, gbc_btnSelectPhoto_Register);

		JButton registerButton_Register = new JButton("Registrar");
		registerButton_Register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Matcher registerEmailMatch = Constantes.EMAIL_PAT.matcher(emailField_Register.getText());
				int fortalezaPass = Utilities.fortalezaContraseña(passwordField_Register);
				if (emailField_Register.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "El campo \"Email\" no puede estar vacío.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (!registerEmailMatch.matches()) {
					JOptionPane.showMessageDialog(frame, "El email no es válido.", null, JOptionPane.ERROR_MESSAGE);
				} else if (fullnameField_Register.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "El campo \"Nombre completo\" no puede estar vacío.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (userField_Register.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "El campo \"Usuario\" no puede estar vacío.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (passwordField_Register.getPassword().length == 0) {
					JOptionPane.showMessageDialog(frame, "El campo \"Contraseña\" no puede estar vacío.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (fortalezaPass < 50) {
					JOptionPane.showMessageDialog(frame, "La contraseña no es lo suficientemente fuerte.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (passwordField_Register.getPassword().length < Constantes.MIN_PASSWORD_LENGTH) {
					JOptionPane.showMessageDialog(frame,
							"La contraseña ha de tener mínimo " + Constantes.MIN_PASSWORD_LENGTH + " caracteres.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (dateChooser_Register.getDate() == null) {
					JOptionPane.showMessageDialog(frame, "El campo \"Fecha de nacimiento\" es incorrecto.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (menorDeEdad(dateChooser_Register.getDate())) {
					JOptionPane.showMessageDialog(frame, "Debes de ser mayor de edad para registrarte en PhotoTDS.",
							null, JOptionPane.ERROR_MESSAGE);
				} else {
					if (profilePic_Register == null)
						profilePic_Register = StartWindow.class.getResource("/images/defaultUserPic128.png").getPath()
								.substring(1);
					if (Controller.getInstancia().createUser(emailField_Register.getText(),
							fullnameField_Register.getText(), userField_Register.getText(),
							String.valueOf(passwordField_Register.getPassword()), dateChooser_Register.getDate(),
							profilePic_Register, description_Register.getText()) == true) {
						JOptionPane.showMessageDialog(frame, "Registrado con éxito", null,
								JOptionPane.INFORMATION_MESSAGE);
						CardLayout cL = (CardLayout) panelCentral.getLayout();
						cL.show(panelCentral, "panelLogin");
						sw.setLoginSize();
						editorPane.setText("");
						btnSelectPhoto_Register.setText("Seleccionar");
						emailField_Register.setText(null);
						fullnameField_Register.setText(null);
						dateChooser_Register.setDate(null);
						userField_Register.setText(null);
						passwordField_Register.setText(null);
						description_Register.setText(null);
						chckbxVisiblePassword_Register.setSelected(false);
						profilePic_Register = null;
					} else {
						JOptionPane.showMessageDialog(frame, "¡Ya estás registrado!", null, JOptionPane.ERROR_MESSAGE);
					}

				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				registerButton_Register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				registerButton_Register.setCursor(Cursor.getDefaultCursor());
			}
		});

		GridBagConstraints gbc_registerButton_Register = new GridBagConstraints();
		gbc_registerButton_Register.gridwidth = 6;
		gbc_registerButton_Register.insets = new Insets(0, 0, 5, 0);
		gbc_registerButton_Register.gridx = 0;
		gbc_registerButton_Register.gridy = 9;
		panelRegister.add(registerButton_Register, gbc_registerButton_Register);

		JLabel labelMandatoryFields_Register = new JLabel("Los campos con * son obligatorios");
		labelMandatoryFields_Register.setForeground(Color.RED);
		GridBagConstraints gbc_labelMandatoryFields_Register = new GridBagConstraints();
		gbc_labelMandatoryFields_Register.gridwidth = 6;
		gbc_labelMandatoryFields_Register.insets = new Insets(0, 0, 5, 0);
		gbc_labelMandatoryFields_Register.gridx = 0;
		gbc_labelMandatoryFields_Register.gridy = 10;
		panelRegister.add(labelMandatoryFields_Register, gbc_labelMandatoryFields_Register);

		JLabel labelAlreadyRegistered_Register = new JLabel("\u00BFYa tienes una cuenta?");
		GridBagConstraints gbc_labelAlreadyRegistered_Register = new GridBagConstraints();
		gbc_labelAlreadyRegistered_Register.anchor = GridBagConstraints.SOUTH;
		gbc_labelAlreadyRegistered_Register.gridwidth = 6;
		gbc_labelAlreadyRegistered_Register.insets = new Insets(0, 0, 5, 0);
		gbc_labelAlreadyRegistered_Register.gridx = 0;
		gbc_labelAlreadyRegistered_Register.gridy = 11;
		panelRegister.add(labelAlreadyRegistered_Register, gbc_labelAlreadyRegistered_Register);

		JButton loginButton_Register = new JButton("Iniciar sesi\u00F3n");
		loginButton_Register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				loginButton_Register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				loginButton_Register.setCursor(Cursor.getDefaultCursor());
			}
		});
		loginButton_Register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cL = (CardLayout) panelCentral.getLayout();
				cL.show(panelCentral, "panelLogin");
				sw.setLoginSize();
				emailField_Register.setText(null);
				fullnameField_Register.setText(null);
				dateChooser_Register.setDate(null);
				userField_Register.setText(null);
				passwordField_Register.setText(null);
				description_Register.setText(null);
				chckbxVisiblePassword_Register.setSelected(false);
			}
		});
		GridBagConstraints gbc_loginButton_Register = new GridBagConstraints();
		gbc_loginButton_Register.insets = new Insets(0, 0, 5, 0);
		gbc_loginButton_Register.anchor = GridBagConstraints.NORTH;
		gbc_loginButton_Register.gridwidth = 6;
		gbc_loginButton_Register.gridx = 0;
		gbc_loginButton_Register.gridy = 12;
		panelRegister.add(loginButton_Register, gbc_loginButton_Register);
	}

	private boolean menorDeEdad(Date date) {
		LocalDate fechaDeNacimiento = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Predicate<LocalDate> esMenorDeEdad = d -> Period.between(d, LocalDate.now()).getYears() < 18;
		return esMenorDeEdad.test(fechaDeNacimiento);
	}

}
