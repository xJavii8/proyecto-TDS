package umu.tds.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import umu.tds.controller.Controller;
import umu.tds.model.User;
import umu.tds.model.UserListRender;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

public class MainWindow {

	private JFrame frame;

	private String selfUsername;
	private String selfProfilePicPath;
	private JTextField searchField;
	private JPanel panelCentral;
	private JLabel selfProfile;
	private SelfProfileWindow spw;

	/**
	 * Create the application.
	 */
	public MainWindow(String username, String profilePicPath) {
		this.selfUsername = username;
		if (profilePicPath.contains("%")) {
			try {
				profilePicPath = URLDecoder.decode(profilePicPath, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		this.selfProfilePicPath = profilePicPath;
		this.spw = new SelfProfileWindow(selfUsername, selfProfile, MainWindow.this);
		initialize();
	}

	public void show() {
		frame.setVisible(true);
	}
	
	public JPanel getPanelCentral() {
		return panelCentral;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/ig64.png")));
		frame.setSize(602, 583);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelNorte = new JPanel();
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);
		GridBagLayout gbl_panelNorte = new GridBagLayout();
		gbl_panelNorte.columnWidths = new int[] { 27, 109, 15, 140, 0, 106, 63, 0, 0 };
		gbl_panelNorte.rowHeights = new int[] { 12, 32, 0 };
		gbl_panelNorte.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelNorte.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panelNorte.setLayout(gbl_panelNorte);

		JLabel logo = new JLabel("PhotoTDS");
		logo.setIcon(new ImageIcon(MainWindow.class.getResource("/images/ig32.png")));
		logo.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_logo = new GridBagConstraints();
		gbc_logo.insets = new Insets(0, 0, 0, 5);
		gbc_logo.gridx = 1;
		gbc_logo.gridy = 1;
		panelNorte.add(logo, gbc_logo);

		selfProfile = new JLabel("username");
		selfProfile.setIcon(Utilities.genIconSelfProfileLabel(selfProfilePicPath));

		JLabel uploadPhoto = new JLabel("");
		uploadPhoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddPublicationWindow publicationView = new AddPublicationWindow(selfProfile.getText(), spw);
				publicationView.show();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				uploadPhoto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				uploadPhoto.setCursor(Cursor.getDefaultCursor());
			}
		});

		searchField = new JTextField();
		GridBagConstraints gbc_searchField = new GridBagConstraints();
		gbc_searchField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchField.insets = new Insets(0, 0, 0, 5);
		gbc_searchField.gridx = 3;
		gbc_searchField.gridy = 1;
		panelNorte.add(searchField, gbc_searchField);
		searchField.setColumns(10);

		JButton searchButton = new JButton("Buscar");
		searchButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				buscar(searchField.getText());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				searchButton.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_searchButton = new GridBagConstraints();
		gbc_searchButton.insets = new Insets(0, 0, 0, 5);
		gbc_searchButton.gridx = 4;
		gbc_searchButton.gridy = 1;
		panelNorte.add(searchButton, gbc_searchButton);

		uploadPhoto.setIcon(new ImageIcon(MainWindow.class.getResource("/images/uploadPhoto.png")));
		GridBagConstraints gbc_uploadPhoto = new GridBagConstraints();
		gbc_uploadPhoto.insets = new Insets(0, 0, 0, 5);
		gbc_uploadPhoto.gridx = 5;
		gbc_uploadPhoto.gridy = 1;
		panelNorte.add(uploadPhoto, gbc_uploadPhoto);
		selfProfile.setText(selfUsername);
		selfProfile.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_profile = new GridBagConstraints();
		gbc_profile.insets = new Insets(0, 0, 0, 5);
		gbc_profile.anchor = GridBagConstraints.WEST;
		gbc_profile.gridx = 6;
		gbc_profile.gridy = 1;
		panelNorte.add(selfProfile, gbc_profile);

		panelCentral = new JPanel();
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new CardLayout(0, 0));

		JPanel panelPrincipal = new JPanel();
		panelCentral.add(panelPrincipal, "panelPrincipal");
		GridBagLayout gbl_panelPrincipal = new GridBagLayout();
		gbl_panelPrincipal.columnWidths = new int[] { 0, 0, 0, 122, 128, 0, 0, 0 };
		gbl_panelPrincipal.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panelPrincipal.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelPrincipal.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelPrincipal.setLayout(gbl_panelPrincipal);

		logo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cL = (CardLayout) panelCentral.getLayout();
				cL.show(panelCentral, "panelPrincipal");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				logo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				logo.setCursor(Cursor.getDefaultCursor());
			}
		});

		selfProfile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panelPerfilPersonal = spw.getPanelPerfilPersonal();
				panelCentral.add(panelPerfilPersonal, "panelPerfilPersonal");
				CardLayout cL = (CardLayout) panelCentral.getLayout();
				cL.show(panelCentral, "panelPerfilPersonal");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				selfProfile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				selfProfile.setCursor(Cursor.getDefaultCursor());
			}
		});

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
					uploadPhoto.setIcon(new ImageIcon(MainWindow.class.getResource("/images/uploadPhotoDark.png")));
					menuBar.setBorder(new MatteBorder(1, 1, 1, 1, Constantes.DARK_BARS));
					mntmModoClaroOscuro.setBorder(new MatteBorder(0, 0, 0, 1, Constantes.DARK_BARS));
					mntmModoClaroOscuro.setText("Modo claro");
				} else if (mntmModoClaroOscuro.getText() == "Modo claro") {
					FlatLightLaf.setup();
					SwingUtilities.updateComponentTreeUI(frame);
					uploadPhoto.setIcon(new ImageIcon(MainWindow.class.getResource("/images/uploadPhoto.png")));
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
	}

	private void buscar(String texto) {
		if (texto.isEmpty() == false) {
			if (texto.startsWith("#")) {

			} else {
				DefaultListModel<User> matchingUsers = Controller.getInstancia().search(selfUsername, texto);
				Utilities.listaUsuarios(MainWindow.this, selfUsername, matchingUsers);
			}
		}
	}

}
