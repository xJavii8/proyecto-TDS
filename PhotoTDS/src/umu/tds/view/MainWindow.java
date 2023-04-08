package umu.tds.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MainWindow {

	private JFrame frame;
	private String username;

	/**
	 * Create the application.
	 */
	public MainWindow(String username) {
		this.username = username;
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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/ig64.png")));
		frame.setSize(521, 583);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelNorte = new JPanel();
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);
		GridBagLayout gbl_panelNorte = new GridBagLayout();
		gbl_panelNorte.columnWidths = new int[]{27, 109, 271, 63, 0, 0};
		gbl_panelNorte.rowHeights = new int[]{12, 32, 0};
		gbl_panelNorte.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelNorte.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelNorte.setLayout(gbl_panelNorte);
		
		JLabel logo = new JLabel("PhotoTDS");
		logo.setIcon(new ImageIcon(MainWindow.class.getResource("/images/ig32.png")));
		logo.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_logo = new GridBagConstraints();
		gbc_logo.insets = new Insets(0, 0, 0, 5);
		gbc_logo.gridx = 1;
		gbc_logo.gridy = 1;
		panelNorte.add(logo, gbc_logo);
		
		JLabel profile = new JLabel("username");
		profile.setText(username);
		profile.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_profile = new GridBagConstraints();
		gbc_profile.insets = new Insets(0, 0, 0, 5);
		gbc_profile.anchor = GridBagConstraints.WEST;
		gbc_profile.gridx = 3;
		gbc_profile.gridy = 1;
		panelNorte.add(profile, gbc_profile);
		
		JPanel panelCentral = new JPanel();
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new CardLayout(0, 0));
	}

}
