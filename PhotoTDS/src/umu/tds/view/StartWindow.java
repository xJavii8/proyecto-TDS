package umu.tds.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.MatteBorder;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

import umu.tds.controller.Controller;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Predicate;
import javax.swing.JProgressBar;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//HOLA

public class StartWindow {

	private JFrame frmPhototdsLogin;
	private JTextField userField_Login;
	private JPasswordField passwordField_Login;
	private JTextField emailField_Register;
	private JTextField fullnameField_Register;
	private JTextField userField_Register;
	private JPasswordField passwordField_Register;
	private boolean profilePicture = false;

	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final String VALID_EMAIL_REGEX = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";

	private Pattern emailPat = Pattern.compile(VALID_EMAIL_REGEX);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartWindow window = new StartWindow();
					window.frmPhototdsLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartWindow() {
		initialize();
	}

	public static void launchLogin(JFrame old) {
		old.setVisible(false);
		old.dispose();
		StartWindow window = new StartWindow();
		window.frmPhototdsLogin.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH", Locale.US);
		Date date = new Date(System.currentTimeMillis());
		if (Integer.parseInt(formatter.format(date)) > 8 && Integer.parseInt(formatter.format(date)) < 20) {
			FlatLightLaf.setup();
		} else {
			FlatDarkLaf.setup();
		}
		frmPhototdsLogin = new JFrame();
		frmPhototdsLogin
				.setIconImage(Toolkit.getDefaultToolkit().getImage(StartWindow.class.getResource("/images/ig64.png")));
		frmPhototdsLogin.setBounds(100, 100, 450, 299);
		frmPhototdsLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelNorte = new JPanel();
		frmPhototdsLogin.getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		panelNorte.add(panel);

		JLabel labelBienvenidoTDS = new JLabel("PhotoTDS");
		labelBienvenidoTDS.setIcon(new ImageIcon(StartWindow.class.getResource("/images/ig32.png")));
		labelBienvenidoTDS.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		panel.add(labelBienvenidoTDS);

		JPanel panelCentral = new JPanel();
		frmPhototdsLogin.getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new CardLayout(0, 0));

		JPanel panelLogin = new JPanel();
		panelCentral.add(panelLogin, "panelLogin");
		GridBagLayout gbl_panelLogin = new GridBagLayout();
		gbl_panelLogin.columnWidths = new int[] { 15, 0, 0, 15, 15, 0 };
		gbl_panelLogin.rowHeights = new int[] { 0, 0, 0, 15, 0, 0, 15, 0 };
		gbl_panelLogin.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelLogin.rowWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelLogin.setLayout(gbl_panelLogin);

		JLabel labelUser_Login = new JLabel("Usuario/Email:");
		GridBagConstraints gbc_labelUser_Login = new GridBagConstraints();
		gbc_labelUser_Login.anchor = GridBagConstraints.SOUTHEAST;
		gbc_labelUser_Login.insets = new Insets(0, 0, 5, 5);
		gbc_labelUser_Login.gridx = 1;
		gbc_labelUser_Login.gridy = 0;
		panelLogin.add(labelUser_Login, gbc_labelUser_Login);

		userField_Login = new JTextField();
		userField_Login.setToolTipText("nombre de usuario/email");
		userField_Login.setColumns(10);
		GridBagConstraints gbc_userField_Login = new GridBagConstraints();
		gbc_userField_Login.fill = GridBagConstraints.HORIZONTAL;
		gbc_userField_Login.anchor = GridBagConstraints.SOUTH;
		gbc_userField_Login.gridwidth = 2;
		gbc_userField_Login.insets = new Insets(0, 0, 5, 5);
		gbc_userField_Login.gridx = 2;
		gbc_userField_Login.gridy = 0;
		panelLogin.add(userField_Login, gbc_userField_Login);

		JLabel labelPassword_Login = new JLabel("Contrase\u00F1a:");
		GridBagConstraints gbc_labelPassword_Login = new GridBagConstraints();
		gbc_labelPassword_Login.anchor = GridBagConstraints.NORTHEAST;
		gbc_labelPassword_Login.insets = new Insets(0, 0, 5, 5);
		gbc_labelPassword_Login.gridx = 1;
		gbc_labelPassword_Login.gridy = 1;
		panelLogin.add(labelPassword_Login, gbc_labelPassword_Login);

		passwordField_Login = new JPasswordField();
		GridBagConstraints gbc_passwordField_Login = new GridBagConstraints();
		gbc_passwordField_Login.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_Login.anchor = GridBagConstraints.NORTH;
		gbc_passwordField_Login.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_Login.gridx = 2;
		gbc_passwordField_Login.gridy = 1;
		panelLogin.add(passwordField_Login, gbc_passwordField_Login);

		JCheckBox chckbxVisiblePassword_Login = new JCheckBox("Visible");
		chckbxVisiblePassword_Login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckbxVisiblePassword_Login.isSelected()) {
					passwordField_Login.setEchoChar((char) 0);
				} else {
					passwordField_Login.setEchoChar('•');
				}
			}
		});
		GridBagConstraints gbc_chckbxVisiblePassword_Login = new GridBagConstraints();
		gbc_chckbxVisiblePassword_Login.anchor = GridBagConstraints.NORTH;
		gbc_chckbxVisiblePassword_Login.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxVisiblePassword_Login.gridx = 3;
		gbc_chckbxVisiblePassword_Login.gridy = 1;
		panelLogin.add(chckbxVisiblePassword_Login, gbc_chckbxVisiblePassword_Login);

		JButton loginButton_Login = new JButton("Iniciar sesi\u00F3n");
		loginButton_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (userField_Login.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frmPhototdsLogin, "El campo \"Usuario\" no puede estar vacio.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (passwordField_Login.getPassword().length == 0) {
					JOptionPane.showMessageDialog(frmPhototdsLogin, "El campo \"Contraseña\" no puede estar vacio.",
							null, JOptionPane.ERROR_MESSAGE);
				} else if (passwordField_Login.getPassword().length < MIN_PASSWORD_LENGTH) {
					JOptionPane.showMessageDialog(frmPhototdsLogin,
							"La contraseña ha de tener minimo " + MIN_PASSWORD_LENGTH + " caracteres.", null,
							JOptionPane.ERROR_MESSAGE);
				} else {
					Matcher loginEmailMatch = emailPat.matcher(userField_Login.getText());
					if (loginEmailMatch.matches()) {
						if (Controller.getInstancia().login(userField_Login.getText(),
								String.valueOf(passwordField_Login.getPassword()), true)) {
							// Aqui se mostrara la nueva ventana
							JOptionPane.showMessageDialog(frmPhototdsLogin, "Login con éxito (dev)", null,
									JOptionPane.INFORMATION_MESSAGE);
							frmPhototdsLogin.setLocationRelativeTo(null);
							frmPhototdsLogin.setVisible(false);
						} else {
							JOptionPane.showMessageDialog(frmPhototdsLogin, "Los datos son incorrectos.", null,
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						if (Controller.getInstancia().login(userField_Login.getText(),
								String.valueOf(passwordField_Login.getPassword()), false)) {
							// Aquí se mostrará la nueva ventana
							JOptionPane.showMessageDialog(frmPhototdsLogin, "Login con éxito (dev)", null,
									JOptionPane.INFORMATION_MESSAGE);
							frmPhototdsLogin.setLocationRelativeTo(null);
							frmPhototdsLogin.setVisible(false);
						} else {
							JOptionPane.showMessageDialog(frmPhototdsLogin, "Los datos son incorrectos.", null,
									JOptionPane.ERROR_MESSAGE);
						}
					}

				}
			}
		});
		GridBagConstraints gbc_loginButton_Login = new GridBagConstraints();
		gbc_loginButton_Login.gridwidth = 5;
		gbc_loginButton_Login.insets = new Insets(0, 0, 5, 0);
		gbc_loginButton_Login.gridx = 0;
		gbc_loginButton_Login.gridy = 2;
		panelLogin.add(loginButton_Login, gbc_loginButton_Login);

		JLabel labelNotRegistered_Login = new JLabel("\u00BFA\u00FAn no tienes una cuenta?");
		GridBagConstraints gbc_labelNotRegistered_Login = new GridBagConstraints();
		gbc_labelNotRegistered_Login.gridwidth = 5;
		gbc_labelNotRegistered_Login.insets = new Insets(0, 0, 5, 0);
		gbc_labelNotRegistered_Login.gridx = 0;
		gbc_labelNotRegistered_Login.gridy = 4;
		panelLogin.add(labelNotRegistered_Login, gbc_labelNotRegistered_Login);

		JButton registerButton_Login = new JButton("Registrar");
		registerButton_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cL = (CardLayout) panelCentral.getLayout();
				cL.show(panelCentral, "panelRegister");
				frmPhototdsLogin.setSize(frmPhototdsLogin.getWidth(), 496);
				userField_Login.setText(null);
				passwordField_Login.setText(null);
				chckbxVisiblePassword_Login.setSelected(false);
			}
		});
		GridBagConstraints gbc_registerButton_Login = new GridBagConstraints();
		gbc_registerButton_Login.gridwidth = 5;
		gbc_registerButton_Login.insets = new Insets(0, 0, 5, 5);
		gbc_registerButton_Login.gridx = 0;
		gbc_registerButton_Login.gridy = 5;
		panelLogin.add(registerButton_Login, gbc_registerButton_Login);

		JPanel panelRegister = new JPanel();
		panelCentral.add(panelRegister, "panelRegister");
		GridBagLayout gbl_panelRegister = new GridBagLayout();
		gbl_panelRegister.columnWidths = new int[] { 15, 0, 0, 15, 0, 15, 0 };
		gbl_panelRegister.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 32, 15, 0, 0, 0, -12, 0, 0 };
		gbl_panelRegister.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelRegister.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		panelRegister.setLayout(gbl_panelRegister);

		JLabel labelEmail_Register = new JLabel("* Email:");
		GridBagConstraints gbc_labelEmail_Register = new GridBagConstraints();
		gbc_labelEmail_Register.anchor = GridBagConstraints.SOUTHEAST;
		gbc_labelEmail_Register.insets = new Insets(0, 0, 5, 5);
		gbc_labelEmail_Register.gridx = 1;
		gbc_labelEmail_Register.gridy = 0;
		panelRegister.add(labelEmail_Register, gbc_labelEmail_Register);

		emailField_Register = new JTextField();
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

		fullnameField_Register = new JTextField();
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

		userField_Register = new JTextField();
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
		GridBagConstraints gbc_barraProgreso = new GridBagConstraints();
		gbc_barraProgreso.insets = new Insets(0, 0, 5, 5);
		gbc_barraProgreso.gridx = 2;
		gbc_barraProgreso.gridy = 4;
		panelRegister.add(barraProgreso, gbc_barraProgreso);

		passwordField_Register = new JPasswordField();
		passwordField_Register.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

				int valor = fortalezaContraseña(passwordField_Register);
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
				LookAndFeel actualLF = UIManager.getLookAndFeel();
				JFileChooser chooser = null;
				if (!profilePicture) {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						chooser = new JFileChooser();
						UIManager.setLookAndFeel(actualLF);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					chooser.showOpenDialog(frmPhototdsLogin);
					File currentFile = chooser.getSelectedFile();
					if (currentFile != null) {
						if (currentFile.getAbsolutePath().contains(".png")
								|| currentFile.getAbsolutePath().contains(".jpg")) {
							editorPane.setText("<html><img src=file:\"" + currentFile.getAbsolutePath() + "\"" + " "
									+ "width=75 height=75></img>");
							frmPhototdsLogin.setSize(frmPhototdsLogin.getWidth() + 75,
									frmPhototdsLogin.getHeight() + 75);
							profilePicture = true;
							btnSelectPhoto_Register.setText("Borrar");
						} else {
							JFrame ventanaMultipleProfilePicture = new JFrame();
							JOptionPane.showMessageDialog(ventanaMultipleProfilePicture,
									"La imagen debe ser formato .png o .jpg");
						}
					}

				} else {
					profilePicture = false;
					editorPane.setText("");
					btnSelectPhoto_Register.setText("Seleccionar");
					frmPhototdsLogin.setSize(frmPhototdsLogin.getWidth() - 75, frmPhototdsLogin.getHeight() - 75);

				}

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
				Matcher registerEmailMatch = emailPat.matcher(emailField_Register.getText());

				if (emailField_Register.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frmPhototdsLogin, "El campo \"Email\" no puede estar vacío.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (!registerEmailMatch.matches()) {
					JOptionPane.showMessageDialog(frmPhototdsLogin, "El email no es válido.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (fullnameField_Register.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frmPhototdsLogin,
							"El campo \"Nombre completo\" no puede estar vacío.", null, JOptionPane.ERROR_MESSAGE);
				} else if (userField_Register.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frmPhototdsLogin, "El campo \"Usuario\" no puede estar vacío.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (passwordField_Register.getPassword().length == 0) {
					JOptionPane.showMessageDialog(frmPhototdsLogin, "El campo \"Contraseña\" no puede estar vacío.",
							null, JOptionPane.ERROR_MESSAGE);
				} else if (passwordField_Register.getPassword().length < MIN_PASSWORD_LENGTH) {
					JOptionPane.showMessageDialog(frmPhototdsLogin,
							"La contraseña ha de tener mínimo " + MIN_PASSWORD_LENGTH + " caracteres.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (dateChooser_Register.getDate() == null) {
					JOptionPane.showMessageDialog(frmPhototdsLogin, "El campo \"Fecha de nacimiento\" es incorrecto.",
							null, JOptionPane.ERROR_MESSAGE);
				} else if (menorDeEdad(dateChooser_Register.getDate())) {
					JOptionPane.showMessageDialog(frmPhototdsLogin,
							"Eres menor de edad, no te puedes registrar en la aplicación", null,
							JOptionPane.ERROR_MESSAGE);
				} else {
					if (Controller.getInstancia().createUser(emailField_Register.getText(),
							fullnameField_Register.getText(), userField_Register.getText(),
							String.valueOf(passwordField_Register.getPassword()), dateChooser_Register.getDate(),
							description_Register.getText()) == true) {
						JOptionPane.showMessageDialog(frmPhototdsLogin, "Registrado con éxito", null,
								JOptionPane.INFORMATION_MESSAGE);
						CardLayout cL = (CardLayout) panelCentral.getLayout();
						cL.show(panelCentral, "panelLogin");
						frmPhototdsLogin.setSize(frmPhototdsLogin.getWidth(), 299);
						emailField_Register.setText(null);
						fullnameField_Register.setText(null);
						dateChooser_Register.setDate(null);
						userField_Register.setText(null);
						passwordField_Register.setText(null);
						description_Register.setText(null);
						chckbxVisiblePassword_Register.setSelected(false);
					} else {
						JOptionPane.showMessageDialog(frmPhototdsLogin, "Ya estás registrado!", null,
								JOptionPane.ERROR_MESSAGE);
					}

				}
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
		loginButton_Register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cL = (CardLayout) panelCentral.getLayout();
				cL.show(panelCentral, "panelLogin");
				frmPhototdsLogin.setSize(frmPhototdsLogin.getWidth(), 299);
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

		JMenuBar menuBar = new JMenuBar();
		frmPhototdsLogin.setJMenuBar(menuBar);
		JMenuItem mntmModoClaroOscuro = new JMenuItem();

		Color lightBars = new Color(230, 230, 230, 230);
		Color darkBars = new Color(75, 77, 78);

		if (Integer.parseInt(formatter.format(date)) > 8 && Integer.parseInt(formatter.format(date)) < 20) {
			menuBar.setBorder(new MatteBorder(1, 1, 1, 1, lightBars));
			mntmModoClaroOscuro.setBorder(new MatteBorder(0, 0, 0, 1, lightBars));
		} else {
			menuBar.setBorder(new MatteBorder(1, 1, 1, 1, darkBars));
			mntmModoClaroOscuro.setBorder(new MatteBorder(0, 0, 0, 1, darkBars));
		}

		if (UIManager.getLookAndFeel().getName() == "FlatLaf Light") {
			mntmModoClaroOscuro.setText("Modo oscuro");
		} else if (UIManager.getLookAndFeel().getName() == "FlatLaf Dark") {
			mntmModoClaroOscuro.setText("Modo claro");
		}
		mntmModoClaroOscuro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (mntmModoClaroOscuro.getText() == "Modo oscuro") {
					FlatDarkLaf.setup();
					SwingUtilities.updateComponentTreeUI(frmPhototdsLogin);
					menuBar.setBorder(new MatteBorder(1, 1, 1, 1, darkBars));
					mntmModoClaroOscuro.setBorder(new MatteBorder(0, 0, 0, 1, darkBars));
					mntmModoClaroOscuro.setText("Modo claro");
				} else if (mntmModoClaroOscuro.getText() == "Modo claro") {
					FlatLightLaf.setup();
					SwingUtilities.updateComponentTreeUI(frmPhototdsLogin);
					mntmModoClaroOscuro.setText("Modo oscuro");
					menuBar.setBorder(new MatteBorder(1, 1, 1, 1, lightBars));
					mntmModoClaroOscuro.setBorder(new MatteBorder(0, 0, 0, 1, lightBars));

				}
			}
		});
		menuBar.add(mntmModoClaroOscuro);

		JMenuItem mntmHelp = new JMenuItem("Ayuda");
		mntmHelp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFrame ventanaAyuda = new JFrame();
				JOptionPane.showMessageDialog(ventanaAyuda,
						"Para iniciar sesión, introduzca:\n- En el campo \"Usuario/Email\", su nombre de usuario o correo electrónico.\n"
								+ "- En el campo \"Contraseña\", su contraseña.\nPara más ayuda, contacte con el soporte.");
			}
		});
		menuBar.add(mntmHelp);
	}

	private boolean menorDeEdad(java.util.Date date) {
		LocalDate fechaDeNacimiento = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Predicate<LocalDate> esMenorDeEdad = d -> Period.between(d, LocalDate.now()).getYears() < 18;
		return esMenorDeEdad.test(fechaDeNacimiento);
	}
	
	private int fortalezaContraseña(JPasswordField contraseña) {
		int fortaleza = 0;
		char[] password = contraseña.getPassword();

		String passwordString = new String(password);
		

		if (passwordString.length() >= 8 && passwordString.length() <= 10) {
			fortaleza += 20;
		} else if (passwordString.length() > 10) {
			fortaleza += 40;
		}

		// Agregamos puntos por complejidad
		boolean tieneMinsucula = false;
		boolean tieneMayucula = false;
		boolean tieneDigitos = false;
		boolean tieneCaracterEspecial = false;

		for (char c : password) {
			if (Character.isLowerCase(c)) {
				tieneMinsucula = true;
			} else if (Character.isUpperCase(c)) {
				tieneMayucula = true;
			} else if (Character.isDigit(c)) {
				tieneDigitos = true;
			} else {
				tieneCaracterEspecial = true;
			}
		}

		int complejidad = 0;

		if (tieneMinsucula) {
			complejidad++;
		}

		if (tieneMayucula) {
			complejidad++;
		}

		if (tieneDigitos) {
			complejidad++;
		}

		if (tieneCaracterEspecial) {
			complejidad++;
		}

		fortaleza += complejidad * 15;

		return fortaleza;
	}

}