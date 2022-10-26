package users;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;

public class RegisterWindow {

	private JFrame frmRegisterPhotoTDS;
	private JTextField textField;
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRegisterPhotoTDS = new JFrame();
		frmRegisterPhotoTDS.setIconImage(Toolkit.getDefaultToolkit().getImage(RegisterWindow.class.getResource("/images/instagram.png")));
		frmRegisterPhotoTDS.setTitle("PhotoTDS - Registro");
		frmRegisterPhotoTDS.setBounds(100, 100, 450, 338);
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
		
		JPanel panelSur = new JPanel();
		frmRegisterPhotoTDS.getContentPane().add(panelSur, BorderLayout.SOUTH);
		
		JButton registerButton = new JButton("Registrar");
		registerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				registerButton.setText("X: " + e.getXOnScreen() + ", Y: " + e.getYOnScreen());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				registerButton.setText("Registrar");
			}
		});
		
		JButton loginButton = new JButton("Iniciar sesi\u00F3n");
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				LoginWindow loginPanel = new LoginWindow();
				frmRegisterPhotoTDS.dispose();
				
			}
		});
		panelSur.add(loginButton);
		registerButton.setIcon(new ImageIcon(RegisterWindow.class.getResource("/images/enter.png")));
		panelSur.add(registerButton);
		
		JPanel panelCentro = new JPanel();
		frmRegisterPhotoTDS.getContentPane().add(panelCentro, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[]{0, 0, 0, 15, 0};
		gbl_panelCentro.rowHeights = new int[]{15, 0, 0, 46, 15, 0};
		gbl_panelCentro.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelCentro.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panelCentro.setLayout(gbl_panelCentro);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panelCentro.add(lblNewLabel, gbc_lblNewLabel);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		panelCentro.add(textField, gbc_textField);
		textField.setColumns(20);
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panelCentro.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(20);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 2;
		panelCentro.add(passwordField, gbc_passwordField);
		
		JButton btnNewButton = new JButton("Clave");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 2;
		panelCentro.add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("G\u00E9nero:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 3;
		panelCentro.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Hombre", "Mujer", "No binario"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 3;
		panelCentro.add(comboBox, gbc_comboBox);
		
		JMenuBar menuBar = new JMenuBar();
		frmRegisterPhotoTDS.setJMenuBar(menuBar);
		
		JMenu menuPhotoTDS = new JMenu("PhotoTDS");
		menuBar.add(menuPhotoTDS);
		
		JMenuItem loginMenuButton = new JMenuItem("Login");
		menuPhotoTDS.add(loginMenuButton);
		
		JSeparator separator = new JSeparator();
		menuPhotoTDS.add(separator);
		
		JMenuItem exitMenuButton = new JMenuItem("Salida");
		exitMenuButton.setIcon(new ImageIcon(RegisterWindow.class.getResource("/images/exit.png")));
		menuPhotoTDS.add(exitMenuButton);
	}

}
