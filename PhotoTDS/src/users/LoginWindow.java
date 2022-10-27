package users;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class LoginWindow {

	private JFrame frmPhototdsLogin;
	private JPasswordField passwordField;
	private JTextField txtUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
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
	public LoginWindow() {
		initialize();
	}

	public static void launchLogin(JFrame old) {
		old.setVisible(false);
		old.dispose();
		LoginWindow window = new LoginWindow();
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
				.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginWindow.class.getResource("/images/ig64.png")));
		frmPhototdsLogin.setBounds(100, 100, 450, 299);
		frmPhototdsLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelNorte = new JPanel();
		frmPhototdsLogin.getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		panelNorte.add(panel);

		JLabel labelImage = new JLabel("");
		labelImage.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/ig32.png")));
		labelImage.setVerticalAlignment(SwingConstants.BOTTOM);
		labelImage.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(labelImage);

		JLabel labelBienvenidoTDS = new JLabel("Bienvenido a PhotoTDS");
		labelBienvenidoTDS.setHorizontalTextPosition(SwingConstants.CENTER);
		labelBienvenidoTDS.setHorizontalAlignment(SwingConstants.RIGHT);
		labelBienvenidoTDS.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		panel.add(labelBienvenidoTDS);

		JPanel panelCentral = new JPanel();
		frmPhototdsLogin.getContentPane().add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[] { 15, 0, 0, 15, 15, 0 };
		gbl_panelCentral.rowHeights = new int[] { 0, 0, 0, 15, 0, 0, 15, 0 };
		gbl_panelCentral.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelCentral.rowWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelCentral.setLayout(gbl_panelCentral);

		JLabel labelUser = new JLabel("Usuario:");
		GridBagConstraints gbc_labelUser = new GridBagConstraints();
		gbc_labelUser.insets = new Insets(0, 0, 5, 5);
		gbc_labelUser.anchor = GridBagConstraints.SOUTHEAST;
		gbc_labelUser.gridx = 1;
		gbc_labelUser.gridy = 0;
		panelCentral.add(labelUser, gbc_labelUser);

		txtUsername = new JTextField();
		txtUsername.setToolTipText("nombre de usuario/email");
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.gridwidth = 2;
		gbc_txtUsername.anchor = GridBagConstraints.SOUTH;
		gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.gridx = 2;
		gbc_txtUsername.gridy = 0;
		panelCentral.add(txtUsername, gbc_txtUsername);
		txtUsername.setColumns(10);

		JLabel labelPassword = new JLabel("Contrase\u00F1a:");
		GridBagConstraints gbc_labelPassword = new GridBagConstraints();
		gbc_labelPassword.insets = new Insets(0, 0, 5, 5);
		gbc_labelPassword.anchor = GridBagConstraints.NORTHEAST;
		gbc_labelPassword.gridx = 1;
		gbc_labelPassword.gridy = 1;
		panelCentral.add(labelPassword, gbc_labelPassword);

		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.anchor = GridBagConstraints.NORTH;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 1;
		panelCentral.add(passwordField, gbc_passwordField);

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
		GridBagConstraints gbc_chckbxVisiblePassword = new GridBagConstraints();
		gbc_chckbxVisiblePassword.anchor = GridBagConstraints.NORTH;
		gbc_chckbxVisiblePassword.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxVisiblePassword.gridx = 3;
		gbc_chckbxVisiblePassword.gridy = 1;
		panelCentral.add(chckbxVisiblePassword, gbc_chckbxVisiblePassword);

		JButton loginButton = new JButton("Iniciar sesi\u00F3n");
		GridBagConstraints gbc_loginButton = new GridBagConstraints();
		gbc_loginButton.gridwidth = 5;
		gbc_loginButton.insets = new Insets(0, 0, 5, 5);
		gbc_loginButton.gridx = 0;
		gbc_loginButton.gridy = 2;
		panelCentral.add(loginButton, gbc_loginButton);

		JLabel labelNotRegistered = new JLabel("\u00BFA\u00FAn no tienes una cuenta?");
		GridBagConstraints gbc_labelNotRegistered = new GridBagConstraints();
		gbc_labelNotRegistered.gridwidth = 5;
		gbc_labelNotRegistered.insets = new Insets(0, 0, 5, 5);
		gbc_labelNotRegistered.gridx = 0;
		gbc_labelNotRegistered.gridy = 4;
		panelCentral.add(labelNotRegistered, gbc_labelNotRegistered);

		JButton registerButton = new JButton("Registrar");
		registerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterWindow.launchRegister(frmPhototdsLogin);
			}
		});
		GridBagConstraints gbc_registerButton = new GridBagConstraints();
		gbc_registerButton.gridwidth = 5;
		gbc_registerButton.insets = new Insets(0, 0, 5, 5);
		gbc_registerButton.gridx = 0;
		gbc_registerButton.gridy = 5;
		panelCentral.add(registerButton, gbc_registerButton);

		JMenuBar menuBar = new JMenuBar();
		frmPhototdsLogin.setJMenuBar(menuBar);

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
					SwingUtilities.updateComponentTreeUI(frmPhototdsLogin);
					mntmNewMenuItem.setText("Modo claro");
				} else if (mntmNewMenuItem.getText() == "Modo claro") {
					FlatLightLaf.setup();
					SwingUtilities.updateComponentTreeUI(frmPhototdsLogin);
					mntmNewMenuItem.setText("Modo oscuro");
				}
			}
		});
		menuBar.add(mntmNewMenuItem);
	}

}
