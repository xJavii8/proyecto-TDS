package users;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.JPasswordField;
import javax.swing.JFileChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.toedter.calendar.JDateChooser;

import beans.Entidad;

public class RegisterWindow {

	private JFrame frmRegisterPhotoTDS;
	private JTextField textFullname;
	private JTextField textEmail;
	private JTextField textUsername;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterWindow window = new RegisterWindow();
					window.frmRegisterPhotoTDS.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegisterWindow() {
		initialize();
	}

	public static void launchRegister(JFrame old) {
		old.setVisible(false);
		old.dispose();
		RegisterWindow window = new RegisterWindow();
		window.frmRegisterPhotoTDS.setVisible(true);
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
		frmRegisterPhotoTDS = new JFrame();
		frmRegisterPhotoTDS.setIconImage(
				Toolkit.getDefaultToolkit().getImage(RegisterWindow.class.getResource("/images/ig64.png")));
		frmRegisterPhotoTDS.setBounds(100, 100, 450, 496);
		frmRegisterPhotoTDS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelNorte = new JPanel();
		frmRegisterPhotoTDS.getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		panelNorte.add(panel);

		JLabel labelImage = new JLabel("");
		panel.add(labelImage);
		labelImage.setVerticalAlignment(SwingConstants.BOTTOM);
		labelImage.setHorizontalAlignment(SwingConstants.CENTER);
		labelImage.setIcon(new ImageIcon(RegisterWindow.class.getResource("/images/ig32.png")));

		JLabel labelBienvenidoTDS = new JLabel("Bienvenido a PhotoTDS");
		labelBienvenidoTDS.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(labelBienvenidoTDS);
		labelBienvenidoTDS.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		labelBienvenidoTDS.setHorizontalAlignment(SwingConstants.RIGHT);

		JPanel panelCentral = new JPanel();
		frmRegisterPhotoTDS.getContentPane().add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[] { 15, 0, 0, 15, 15 };
		gbl_panelCentral.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0 };
		gbl_panelCentral.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0 };
		gbl_panelCentral.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0,
				Double.MIN_VALUE };
		panelCentral.setLayout(gbl_panelCentral);

		JLabel labelEmail = new JLabel("* Email:");
		GridBagConstraints gbc_labelEmail = new GridBagConstraints();
		gbc_labelEmail.anchor = GridBagConstraints.SOUTHEAST;
		gbc_labelEmail.insets = new Insets(0, 0, 5, 5);
		gbc_labelEmail.gridx = 1;
		gbc_labelEmail.gridy = 0;
		panelCentral.add(labelEmail, gbc_labelEmail);

		textEmail = new JTextField();
		GridBagConstraints gbc_textEmail = new GridBagConstraints();
		gbc_textEmail.gridwidth = 2;
		gbc_textEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textEmail.gridx = 2;
		gbc_textEmail.gridy = 0;
		panelCentral.add(textEmail, gbc_textEmail);
		textEmail.setColumns(10);

		JLabel labelFullName = new JLabel("* Nombre completo:");
		GridBagConstraints gbc_labelFullName = new GridBagConstraints();
		gbc_labelFullName.anchor = GridBagConstraints.EAST;
		gbc_labelFullName.insets = new Insets(0, 0, 5, 5);
		gbc_labelFullName.gridx = 1;
		gbc_labelFullName.gridy = 1;
		panelCentral.add(labelFullName, gbc_labelFullName);

		textFullname = new JTextField();
		GridBagConstraints gbc_textFullname = new GridBagConstraints();
		gbc_textFullname.gridwidth = 2;
		gbc_textFullname.insets = new Insets(0, 0, 5, 5);
		gbc_textFullname.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFullname.gridx = 2;
		gbc_textFullname.gridy = 1;
		panelCentral.add(textFullname, gbc_textFullname);
		textFullname.setColumns(10);

		JLabel labelUser = new JLabel("* Usuario:");
		GridBagConstraints gbc_labelUser = new GridBagConstraints();
		gbc_labelUser.fill = GridBagConstraints.VERTICAL;
		gbc_labelUser.anchor = GridBagConstraints.EAST;
		gbc_labelUser.insets = new Insets(0, 0, 5, 5);
		gbc_labelUser.gridx = 1;
		gbc_labelUser.gridy = 2;
		panelCentral.add(labelUser, gbc_labelUser);

		textUsername = new JTextField();
		GridBagConstraints gbc_textUsername = new GridBagConstraints();
		gbc_textUsername.gridwidth = 2;
		gbc_textUsername.insets = new Insets(0, 0, 5, 5);
		gbc_textUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_textUsername.gridx = 2;
		gbc_textUsername.gridy = 2;
		panelCentral.add(textUsername, gbc_textUsername);
		textUsername.setColumns(10);

		JLabel labelPassword = new JLabel("* Contrase\u00F1a:");
		GridBagConstraints gbc_labelPassword = new GridBagConstraints();
		gbc_labelPassword.fill = GridBagConstraints.VERTICAL;
		gbc_labelPassword.anchor = GridBagConstraints.EAST;
		gbc_labelPassword.insets = new Insets(0, 0, 5, 5);
		gbc_labelPassword.gridx = 1;
		gbc_labelPassword.gridy = 3;
		panelCentral.add(labelPassword, gbc_labelPassword);

		JCheckBox chckbxVisiblePassword = new JCheckBox("Visible");
		chckbxVisiblePassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckbxVisiblePassword.isSelected()) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('•');
				}
			}
		});

		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 3;
		panelCentral.add(passwordField, gbc_passwordField);
		GridBagConstraints gbc_chckbxVisiblePassword = new GridBagConstraints();
		gbc_chckbxVisiblePassword.fill = GridBagConstraints.VERTICAL;
		gbc_chckbxVisiblePassword.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxVisiblePassword.gridx = 3;
		gbc_chckbxVisiblePassword.gridy = 3;
		panelCentral.add(chckbxVisiblePassword, gbc_chckbxVisiblePassword);

		JLabel labelBirthdayDate = new JLabel("* Fecha de nacimiento:");
		GridBagConstraints gbc_labelBirthdayDate = new GridBagConstraints();
		gbc_labelBirthdayDate.anchor = GridBagConstraints.EAST;
		gbc_labelBirthdayDate.insets = new Insets(0, 0, 5, 5);
		gbc_labelBirthdayDate.gridx = 1;
		gbc_labelBirthdayDate.gridy = 4;
		panelCentral.add(labelBirthdayDate, gbc_labelBirthdayDate);

		JDateChooser dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.gridwidth = 2;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.gridx = 2;
		gbc_dateChooser.gridy = 4;
		panelCentral.add(dateChooser, gbc_dateChooser);

		JLabel labelPresentation = new JLabel("Presentaci\u00F3n:");
		GridBagConstraints gbc_labelPresentation = new GridBagConstraints();
		gbc_labelPresentation.anchor = GridBagConstraints.NORTHEAST;
		gbc_labelPresentation.insets = new Insets(0, 0, 5, 5);
		gbc_labelPresentation.gridx = 1;
		gbc_labelPresentation.gridy = 5;
		panelCentral.add(labelPresentation, gbc_labelPresentation);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 5;
		panelCentral.add(scrollPane, gbc_scrollPane);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JLabel labelProfilePhoto = new JLabel("Foto de perfil:");
		GridBagConstraints gbc_labelProfilePhoto = new GridBagConstraints();
		gbc_labelProfilePhoto.anchor = GridBagConstraints.EAST;
		gbc_labelProfilePhoto.insets = new Insets(0, 0, 5, 5);
		gbc_labelProfilePhoto.gridx = 1;
		gbc_labelProfilePhoto.gridy = 6;
		panelCentral.add(labelProfilePhoto, gbc_labelProfilePhoto);

		JButton btnSelectPhoto = new JButton("Seleccionar");
		btnSelectPhoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LookAndFeel actualLF = UIManager.getLookAndFeel();
				JFileChooser chooser = null;
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					chooser = new JFileChooser();
					UIManager.setLookAndFeel(actualLF);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				chooser.showOpenDialog(frmRegisterPhotoTDS);
				File currentFile = chooser.getSelectedFile();
			}
		});
		GridBagConstraints gbc_btnSelectPhoto = new GridBagConstraints();
		gbc_btnSelectPhoto.anchor = GridBagConstraints.WEST;
		gbc_btnSelectPhoto.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelectPhoto.gridx = 2;
		gbc_btnSelectPhoto.gridy = 6;
		panelCentral.add(btnSelectPhoto, gbc_btnSelectPhoto);

		JButton registerButton = new JButton("Registrar");
		registerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textEmail.getText().isEmpty() || textFullname.getText().isEmpty()
						|| textUsername.getText().isEmpty() || passwordField.getPassword().length == 0
						|| dateChooser.getDate() == null) {
					JOptionPane.showMessageDialog(frmRegisterPhotoTDS, "Faltan campos por rellenar. Revísalo", null, JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frmRegisterPhotoTDS, "Registrado con éxito", null, JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_registerButton = new GridBagConstraints();
		gbc_registerButton.gridwidth = 5;
		gbc_registerButton.insets = new Insets(0, 0, 5, 5);
		gbc_registerButton.gridx = 0;
		gbc_registerButton.gridy = 8;
		panelCentral.add(registerButton, gbc_registerButton);

		JLabel labelMandatoryFields = new JLabel("Los campos con * son obligatorios");
		labelMandatoryFields.setForeground(Color.RED);
		GridBagConstraints gbc_labelMandatoryFields = new GridBagConstraints();
		gbc_labelMandatoryFields.gridwidth = 5;
		gbc_labelMandatoryFields.insets = new Insets(0, 0, 5, 5);
		gbc_labelMandatoryFields.gridx = 0;
		gbc_labelMandatoryFields.gridy = 9;
		panelCentral.add(labelMandatoryFields, gbc_labelMandatoryFields);

		JLabel labelAlreadyRegistered = new JLabel("\u00BFYa tienes una cuenta?");
		GridBagConstraints gbc_labelAlreadyRegistered = new GridBagConstraints();
		gbc_labelAlreadyRegistered.anchor = GridBagConstraints.SOUTH;
		gbc_labelAlreadyRegistered.gridwidth = 5;
		gbc_labelAlreadyRegistered.insets = new Insets(0, 0, 5, 5);
		gbc_labelAlreadyRegistered.gridx = 0;
		gbc_labelAlreadyRegistered.gridy = 10;
		panelCentral.add(labelAlreadyRegistered, gbc_labelAlreadyRegistered);

		JButton loginButton = new JButton("Iniciar sesi\u00F3n");
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginWindow.launchLogin(frmRegisterPhotoTDS);
			}
		});
		GridBagConstraints gbc_loginButton = new GridBagConstraints();
		gbc_loginButton.insets = new Insets(0, 0, 0, 5);
		gbc_loginButton.anchor = GridBagConstraints.NORTH;
		gbc_loginButton.gridwidth = 5;
		gbc_loginButton.gridx = 0;
		gbc_loginButton.gridy = 11;
		panelCentral.add(loginButton, gbc_loginButton);

		JMenuBar menuBar = new JMenuBar();
		frmRegisterPhotoTDS.setJMenuBar(menuBar);

		JMenuItem mntmNewMenuItem = new JMenuItem();
		if (UIManager.getLookAndFeel().getName() == "FlatLaf Light") {
			mntmNewMenuItem.setText("Modo oscuro");
		} else if (UIManager.getLookAndFeel().getName() == "FlatLaf Dark") {
			mntmNewMenuItem.setText("Modo claro");
		}
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (mntmNewMenuItem.getText() == "Modo oscuro") {
					FlatDarkLaf.setup();
					SwingUtilities.updateComponentTreeUI(frmRegisterPhotoTDS);
					mntmNewMenuItem.setText("Modo claro");
				} else if (mntmNewMenuItem.getText() == "Modo claro") {
					FlatLightLaf.setup();
					SwingUtilities.updateComponentTreeUI(frmRegisterPhotoTDS);
					mntmNewMenuItem.setText("Modo oscuro");
				}
			}
		});
		menuBar.add(mntmNewMenuItem);
	}

}
