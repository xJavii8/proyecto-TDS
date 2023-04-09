package umu.tds.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainWindow {

	private JFrame frame;
	private String username;
	private String profilePicPath;

	/**
	 * Create the application.
	 */
	public MainWindow(String username, String profilePicPath) {
		this.username = username;
		if (profilePicPath.contains("%")) {
			try {
				profilePicPath = URLDecoder.decode(profilePicPath, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		this.profilePicPath = profilePicPath;
		initialize();
	}

	public void show() {
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH", Locale.US);
		Date date = new Date(System.currentTimeMillis());
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/ig64.png")));
		frame.setSize(521, 583);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelNorte = new JPanel();
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);
		GridBagLayout gbl_panelNorte = new GridBagLayout();
		gbl_panelNorte.columnWidths = new int[] { 27, 109, 140, 106, 63, 0, 0 };
		gbl_panelNorte.rowHeights = new int[] { 12, 32, 0 };
		gbl_panelNorte.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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

		JLabel profile = new JLabel("username");
		ImageIcon icon = new ImageIcon(profilePicPath);
		if (icon.getIconHeight() > 32 || icon.getIconWidth() > 32) {
			icon = new ImageIcon(icon.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT));
		}

		JLabel uploadPhoto = new JLabel("");
		uploadPhoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddPublicationWindow publicationView = new AddPublicationWindow();
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
		uploadPhoto.setIcon(new ImageIcon(MainWindow.class.getResource("/images/uploadPhoto.png")));
		GridBagConstraints gbc_uploadPhoto = new GridBagConstraints();
		gbc_uploadPhoto.insets = new Insets(0, 0, 0, 5);
		gbc_uploadPhoto.gridx = 3;
		gbc_uploadPhoto.gridy = 1;
		panelNorte.add(uploadPhoto, gbc_uploadPhoto);
		profile.setIcon(icon);
		profile.setText(username);
		profile.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_profile = new GridBagConstraints();
		gbc_profile.insets = new Insets(0, 0, 0, 5);
		gbc_profile.anchor = GridBagConstraints.WEST;
		gbc_profile.gridx = 4;
		gbc_profile.gridy = 1;
		panelNorte.add(profile, gbc_profile);

		JPanel panelCentral = new JPanel();
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new CardLayout(0, 0));
		
		JPanel panelPrincipal = new JPanel();
		panelCentral.add(panelPrincipal, "panelPrincipal");
		
		JPanel panelPerfilPersonal = new JPanel();
		panelCentral.add(panelPerfilPersonal, "panelPerfilPersonal");
		GridBagLayout gbl_panelPerfilPersonal = new GridBagLayout();
		gbl_panelPerfilPersonal.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelPerfilPersonal.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelPerfilPersonal.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelPerfilPersonal.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelPerfilPersonal.setLayout(gbl_panelPerfilPersonal);
		
		JLabel nickname = new JLabel("");
		nickname.setText(username);
		GridBagConstraints gbc_nickname = new GridBagConstraints();
		gbc_nickname.insets = new Insets(0, 0, 5, 5);
		gbc_nickname.gridx = 7;
		gbc_nickname.gridy = 1;
		panelPerfilPersonal.add(nickname, gbc_nickname);
		
		JButton editProfile = new JButton("Editar perfil");
		GridBagConstraints gbc_editProfile = new GridBagConstraints();
		gbc_editProfile.insets = new Insets(0, 0, 5, 5);
		gbc_editProfile.gridx = 8;
		gbc_editProfile.gridy = 1;
		panelPerfilPersonal.add(editProfile, gbc_editProfile);
		
		JButton premium = new JButton("Premium");
		GridBagConstraints gbc_premium = new GridBagConstraints();
		gbc_premium.insets = new Insets(0, 0, 5, 0);
		gbc_premium.gridx = 9;
		gbc_premium.gridy = 1;
		panelPerfilPersonal.add(premium, gbc_premium);
		
		JLabel publications = new JLabel("publicaciones");
		GridBagConstraints gbc_publications = new GridBagConstraints();
		gbc_publications.insets = new Insets(0, 0, 0, 5);
		gbc_publications.gridx = 6;
		gbc_publications.gridy = 2;
		panelPerfilPersonal.add(publications, gbc_publications);
		
		JLabel following = new JLabel("seguidos");
		GridBagConstraints gbc_following = new GridBagConstraints();
		gbc_following.insets = new Insets(0, 0, 0, 5);
		gbc_following.gridx = 7;
		gbc_following.gridy = 2;
		panelPerfilPersonal.add(following, gbc_following);
		
		JLabel follows = new JLabel("seguidores");
		GridBagConstraints gbc_follows = new GridBagConstraints();
		gbc_follows.insets = new Insets(0, 0, 0, 5);
		gbc_follows.gridx = 8;
		gbc_follows.gridy = 2;
		panelPerfilPersonal.add(follows, gbc_follows);
		
		JPanel panelPerfil = new JPanel();
		panelCentral.add(panelPerfil, "panelPerfil");
		
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
		
		profile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cL = (CardLayout) panelCentral.getLayout();
				cL.show(panelCentral, "panelPerfilPersonal");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				profile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				profile.setCursor(Cursor.getDefaultCursor());
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
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
					SwingUtilities.updateComponentTreeUI(frame);
					uploadPhoto.setIcon(new ImageIcon(MainWindow.class.getResource("/images/uploadPhotoDark.png")));
					menuBar.setBorder(new MatteBorder(1, 1, 1, 1, darkBars));
					mntmModoClaroOscuro.setBorder(new MatteBorder(0, 0, 0, 1, darkBars));
					mntmModoClaroOscuro.setText("Modo claro");
				} else if (mntmModoClaroOscuro.getText() == "Modo claro") {
					FlatLightLaf.setup();
					SwingUtilities.updateComponentTreeUI(frame);
					uploadPhoto.setIcon(new ImageIcon(MainWindow.class.getResource("/images/uploadPhoto.png")));
					mntmModoClaroOscuro.setText("Modo oscuro");
					menuBar.setBorder(new MatteBorder(1, 1, 1, 1, lightBars));
					mntmModoClaroOscuro.setBorder(new MatteBorder(0, 0, 0, 1, lightBars));

				}
			}
		});
		menuBar.add(mntmModoClaroOscuro);
	}

}
