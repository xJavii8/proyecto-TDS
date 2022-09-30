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
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginWindow {

	private JFrame frmLoginPhototds;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frmLoginPhototds.setVisible(true);
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLoginPhototds = new JFrame();
		frmLoginPhototds.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginWindow.class.getResource("/images/instagram.png")));
		frmLoginPhototds.setTitle("Login PhotoTDS");
		frmLoginPhototds.setBounds(100, 100, 450, 300);
		frmLoginPhototds.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelNorte = new JPanel();
		frmLoginPhototds.getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BorderLayout(5, 0));
		
		JLabel labelImage = new JLabel("");
		labelImage.setVerticalAlignment(SwingConstants.BOTTOM);
		labelImage.setHorizontalAlignment(SwingConstants.CENTER);
		labelImage.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/ig64.png")));
		panelNorte.add(labelImage, BorderLayout.CENTER);
		
		JLabel labelBienvenidoTDS = new JLabel("Bienvenido a PhotoTDS");
		panelNorte.add(labelBienvenidoTDS, BorderLayout.NORTH);
		labelBienvenidoTDS.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		labelBienvenidoTDS.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelSur = new JPanel();
		frmLoginPhototds.getContentPane().add(panelSur, BorderLayout.SOUTH);
		
		JButton loginButton = new JButton("Login");
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				loginButton.setText("X: " + e.getXOnScreen() + ", Y: " + e.getYOnScreen());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				loginButton.setText("Login");
			}
		});
		loginButton.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/enter.png")));
		panelSur.add(loginButton);
		
		JPanel panelCentro = new JPanel();
		frmLoginPhototds.getContentPane().add(panelCentro, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[]{0, 0, 0, 15, 0};
		gbl_panelCentro.rowHeights = new int[]{0, 0, 46, 15, 0};
		gbl_panelCentro.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelCentro.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panelCentro.setLayout(gbl_panelCentro);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panelCentro.add(lblNewLabel, gbc_lblNewLabel);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panelCentro.add(textField, gbc_textField);
		textField.setColumns(20);
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panelCentro.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(20);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 1;
		panelCentro.add(passwordField, gbc_passwordField);
		
		JButton btnNewButton = new JButton("Clave");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 1;
		panelCentro.add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("G\u00E9nero:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panelCentro.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Hombre", "Mujer", "No binario"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		panelCentro.add(comboBox, gbc_comboBox);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 2;
		panelCentro.add(scrollPane_1, gbc_scrollPane_1);
		
		JList list = new JList();
		list.setVisibleRowCount(4);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Opci\u00F3n 1", "Opci\u00F3n 2", "Opci\u00F3n 3", "Opci\u00F3n 4", "Opci\u00F3n 5", "Opci\u00F3n 6"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane_1.setViewportView(list);
		
		JMenuBar menuBar = new JMenuBar();
		frmLoginPhototds.setJMenuBar(menuBar);
		
		JMenu menuPhotoTDS = new JMenu("PhotoTDS");
		menuBar.add(menuPhotoTDS);
		
		JMenuItem registerButton = new JMenuItem("Register");
		menuPhotoTDS.add(registerButton);
		
		JSeparator separator = new JSeparator();
		menuPhotoTDS.add(separator);
		
		JMenuItem exitButton = new JMenuItem("Exit");
		exitButton.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/exit.png")));
		menuPhotoTDS.add(exitButton);
	}

}
