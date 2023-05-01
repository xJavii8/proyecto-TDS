package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import umu.tds.controller.Controller;

public class StartWindow {

	private JFrame frame;
	private JPanel panelLogin;
	private JTextField userField_Login;
	private JPasswordField passwordField_Login;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartWindow window = new StartWindow();
					window.frame.setVisible(true);
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

	public JPanel getPanelLogin() {
		return panelLogin;
	}

	public void setLoginSize() {
		frame.setSize(Constantes.INITIAL_WIDTH, Constantes.INITIAL_HEIGHT);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH", Locale.US);
		Date date = new Date(System.currentTimeMillis());
		if (Integer.parseInt(formatter.format(date)) >= 8 && Integer.parseInt(formatter.format(date)) <= 20) {
			FlatLightLaf.setup();
		} else {
			FlatDarkLaf.setup();
		}

		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(StartWindow.class.getResource("/images/ig64.png")));
		frame.setSize(Constantes.INITIAL_WIDTH, Constantes.INITIAL_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelNorte = new JPanel();
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		panelNorte.add(panel);

		JLabel labelBienvenidoTDS = new JLabel("PhotoTDS");
		labelBienvenidoTDS.setIcon(new ImageIcon(StartWindow.class.getResource("/images/ig32.png")));
		labelBienvenidoTDS.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		panel.add(labelBienvenidoTDS);

		JPanel panelCentral = new JPanel();
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
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

			@Override
			public void mouseEntered(MouseEvent e) {
				chckbxVisiblePassword_Login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				chckbxVisiblePassword_Login.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_chckbxVisiblePassword_Login = new GridBagConstraints();
		gbc_chckbxVisiblePassword_Login.anchor = GridBagConstraints.NORTH;
		gbc_chckbxVisiblePassword_Login.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxVisiblePassword_Login.gridx = 3;
		gbc_chckbxVisiblePassword_Login.gridy = 1;
		panelLogin.add(chckbxVisiblePassword_Login, gbc_chckbxVisiblePassword_Login);

		JButton loginButton_Login = new JButton("Iniciar sesi\u00F3n");
		loginButton_Login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				loginButton_Login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				loginButton_Login.setCursor(Cursor.getDefaultCursor());
			}
		});
		loginButton_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (userField_Login.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "El campo \"Usuario\" no puede estar vacío.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (passwordField_Login.getPassword().length == 0) {
					JOptionPane.showMessageDialog(frame, "El campo \"Contraseña\" no puede estar vacío.", null,
							JOptionPane.ERROR_MESSAGE);
				} else if (passwordField_Login.getPassword().length < Constantes.MIN_PASSWORD_LENGTH) {
					JOptionPane.showMessageDialog(frame,
							"La contraseña ha de tener mínimo " + Constantes.MIN_PASSWORD_LENGTH + " caracteres.", null,
							JOptionPane.ERROR_MESSAGE);
				} else {
					if (Controller.getInstancia().login(userField_Login.getText(),
							String.valueOf(passwordField_Login.getPassword()))) {
						String profilePicPath = Controller.getInstancia().getProfilePicPath(userField_Login.getText());
						MainWindow mainView = new MainWindow(userField_Login.getText(), profilePicPath);
						frame.setVisible(false);
						mainView.show();
					} else {
						JOptionPane.showMessageDialog(frame, "Los datos son incorrectos.", null,
								JOptionPane.ERROR_MESSAGE);
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
		registerButton_Login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				registerButton_Login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				registerButton_Login.setCursor(Cursor.getDefaultCursor());
			}
		});
		registerButton_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel panelRegister = new RegisterWindow(StartWindow.this, panelCentral).getRegisterPanel();
				panelCentral.add(panelRegister, "panelRegister");
				CardLayout cL = (CardLayout) panelCentral.getLayout();
				cL.show(panelCentral, "panelRegister");
				frame.setSize(Constantes.INITIAL_WIDTH, 496);
				frame.setLocationRelativeTo(null);
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

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenuItem mntmModoClaroOscuro = new JMenuItem();

		if (UIManager.getLookAndFeel().getName() == "FlatLaf Light") {
			menuBar.setBorder(new MatteBorder(1, 1, 1, 1, Constantes.LIGHT_BARS));
			mntmModoClaroOscuro.setBorder(new MatteBorder(0, 0, 0, 1, Constantes.LIGHT_BARS));
			mntmModoClaroOscuro.setText("Modo oscuro");
		} else if (UIManager.getLookAndFeel().getName() == "FlatLaf Dark") {
			menuBar.setBorder(new MatteBorder(1, 1, 1, 1, Constantes.DARK_BARS));
			mntmModoClaroOscuro.setBorder(new MatteBorder(0, 0, 0, 1, Constantes.DARK_BARS));
			mntmModoClaroOscuro.setText("Modo claro");
		}
		mntmModoClaroOscuro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (mntmModoClaroOscuro.getText() == "Modo oscuro") {
					FlatDarkLaf.setup();
					SwingUtilities.updateComponentTreeUI(frame);
					menuBar.setBorder(new MatteBorder(1, 1, 1, 1, Constantes.DARK_BARS));
					mntmModoClaroOscuro.setBorder(new MatteBorder(0, 0, 0, 1, Constantes.DARK_BARS));
					mntmModoClaroOscuro.setText("Modo claro");
				} else if (mntmModoClaroOscuro.getText() == "Modo claro") {
					FlatLightLaf.setup();
					SwingUtilities.updateComponentTreeUI(frame);
					mntmModoClaroOscuro.setText("Modo oscuro");
					menuBar.setBorder(new MatteBorder(1, 1, 1, 1, Constantes.LIGHT_BARS));
					mntmModoClaroOscuro.setBorder(new MatteBorder(0, 0, 0, 1, Constantes.LIGHT_BARS));

				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mntmModoClaroOscuro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mntmModoClaroOscuro.setCursor(Cursor.getDefaultCursor());
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

			@Override
			public void mouseEntered(MouseEvent e) {
				mntmHelp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mntmHelp.setCursor(Cursor.getDefaultCursor());
			}
		});
		menuBar.add(mntmHelp);
	}

}