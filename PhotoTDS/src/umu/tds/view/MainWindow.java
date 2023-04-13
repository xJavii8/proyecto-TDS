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
	private String anotherProfileUsername;
	private Color lightBars;
	private Color darkBars;
	private JPanel panelCentral;
	private JLabel nickname;
	private JLabel publications;
	private JLabel following;
	private JLabel followers;
	private JLabel profilePic;
	private JButton followButton;
	private int numSelfPub;
	private int numSelfFollowing;
	private int numSelfFollowers;
	private int numPub;
	private int numFollowing;
	private int numFollowers;

	/**
	 * Create the application.
	 */
	public MainWindow(String username, String profilePicPath) {
		lightBars = new Color(230, 230, 230, 230);
		darkBars = new Color(75, 77, 78);
		this.selfUsername = username;
		if (profilePicPath.contains("%")) {
			try {
				profilePicPath = URLDecoder.decode(profilePicPath, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		this.selfProfilePicPath = profilePicPath;
		initialize();
	}

	public void exit() {
		frame.dispose();
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

		JLabel profile = new JLabel("username");
		BufferedImage rawPic = null;
		try {
			rawPic = ImageIO.read(new File(selfProfilePicPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (rawPic != null) {
			ImageIcon pic = new ImageIcon(getCircularImage(rawPic));
			if (pic.getIconHeight() != 32 || pic.getIconWidth() != 32) {
				pic = new ImageIcon(pic.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT));
			}
			profile.setIcon(pic);
		}

		JLabel uploadPhoto = new JLabel("");
		uploadPhoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddPublicationWindow publicationView = new AddPublicationWindow(profile.getText());
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
		profile.setText(selfUsername);
		profile.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_profile = new GridBagConstraints();
		gbc_profile.insets = new Insets(0, 0, 0, 5);
		gbc_profile.anchor = GridBagConstraints.WEST;
		gbc_profile.gridx = 6;
		gbc_profile.gridy = 1;
		panelNorte.add(profile, gbc_profile);

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

		JPanel panelPerfilPersonal = new JPanel();
		panelCentral.add(panelPerfilPersonal, "panelPerfilPersonal");
		GridBagLayout gbl_panelPerfilPersonal = new GridBagLayout();
		gbl_panelPerfilPersonal.columnWidths = new int[] { 15, 0, 0, 0, 15, 15, 0, 112, 0, 15, 0, 0 };
		gbl_panelPerfilPersonal.rowHeights = new int[] { 15, 0, 0, 0, 0, 0, 0 };
		gbl_panelPerfilPersonal.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panelPerfilPersonal.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelPerfilPersonal.setLayout(gbl_panelPerfilPersonal);

		JLabel selfNickname = new JLabel("");
		selfNickname.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		selfNickname.setText(selfUsername);
		GridBagConstraints gbc_selfNickname = new GridBagConstraints();
		gbc_selfNickname.insets = new Insets(0, 0, 5, 5);
		gbc_selfNickname.gridx = 6;
		gbc_selfNickname.gridy = 2;
		panelPerfilPersonal.add(selfNickname, gbc_selfNickname);

		JButton selfEditProfile = new JButton("Editar perfil");
		selfEditProfile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				selfEditProfile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				selfEditProfile.setCursor(Cursor.getDefaultCursor());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				EditProfileVindow epw = new EditProfileVindow(selfUsername, MainWindow.this);
				epw.show();
			}
		});
		GridBagConstraints gbc_selfEditProfile = new GridBagConstraints();
		gbc_selfEditProfile.insets = new Insets(0, 0, 5, 5);
		gbc_selfEditProfile.gridx = 7;
		gbc_selfEditProfile.gridy = 2;
		panelPerfilPersonal.add(selfEditProfile, gbc_selfEditProfile);

		JLabel selfProfilePic = new JLabel("");

		if (rawPic != null) {
			ImageIcon pic = new ImageIcon(getCircularImage(rawPic));
			if (pic.getIconHeight() != 128 || pic.getIconWidth() != 128) {
				pic = new ImageIcon(pic.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
			}
			selfProfilePic.setIcon(pic);
		}
		GridBagConstraints gbc_selfProfilePic = new GridBagConstraints();
		gbc_selfProfilePic.gridwidth = 4;
		gbc_selfProfilePic.gridheight = 4;
		gbc_selfProfilePic.insets = new Insets(0, 0, 5, 5);
		gbc_selfProfilePic.gridx = 1;
		gbc_selfProfilePic.gridy = 1;

		JButton premium = new JButton("Premium");
		premium.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				premium.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				premium.setCursor(Cursor.getDefaultCursor());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				PremiumMenuWindow pmw = new PremiumMenuWindow(selfUsername);
				pmw.show();
			}
		});
		GridBagConstraints gbc_premium = new GridBagConstraints();
		gbc_premium.insets = new Insets(0, 0, 5, 5);
		gbc_premium.gridx = 8;
		gbc_premium.gridy = 2;
		panelPerfilPersonal.add(premium, gbc_premium);
		panelPerfilPersonal.add(selfProfilePic, gbc_selfProfilePic);

		JLabel selfPublications;
		numSelfPub = Controller.getInstancia().getNumPublications(selfUsername);
		if (numSelfPub == 1)
			selfPublications = new JLabel(numSelfPub + " publicación");
		else
			selfPublications = new JLabel(numSelfPub + " publicaciones");
		selfPublications.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_selfPublications = new GridBagConstraints();
		gbc_selfPublications.insets = new Insets(0, 0, 5, 5);
		gbc_selfPublications.gridx = 6;
		gbc_selfPublications.gridy = 3;
		panelPerfilPersonal.add(selfPublications, gbc_selfPublications);

		JLabel selfFollowing;
		numSelfFollowing = Controller.getInstancia().getNumUsersFollowing(selfUsername);
		if (numSelfFollowing == 1)
			selfFollowing = new JLabel(numSelfFollowing + " seguido");
		else
			selfFollowing = new JLabel(numSelfFollowing + " seguidos");
		selfFollowing.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_selfFollowing = new GridBagConstraints();
		gbc_selfFollowing.insets = new Insets(0, 0, 5, 5);
		gbc_selfFollowing.gridx = 7;
		gbc_selfFollowing.gridy = 3;
		panelPerfilPersonal.add(selfFollowing, gbc_selfFollowing);

		JLabel selfFollows;
		numSelfFollowers = Controller.getInstancia().getNumFollowers(selfUsername);
		if (numSelfFollowers == 1)
			selfFollows = new JLabel(numSelfFollowers + " seguidor");
		else
			selfFollows = new JLabel(numSelfFollowers + " seguidores");
		selfFollows.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_selfFollows = new GridBagConstraints();
		gbc_selfFollows.insets = new Insets(0, 0, 5, 5);
		gbc_selfFollows.gridx = 8;
		gbc_selfFollows.gridy = 3;
		panelPerfilPersonal.add(selfFollows, gbc_selfFollows);

		JPanel panelPerfil = new JPanel();
		panelCentral.add(panelPerfil, "panelPerfil");
		GridBagLayout gbl_panelPerfil = new GridBagLayout();
		gbl_panelPerfil.columnWidths = new int[] { 15, 0, 0, 0, 0, 15, 0, 112, 0, 0, 15, 0 };
		gbl_panelPerfil.rowHeights = new int[] { 15, 0, 0, 0, 0, 0, 0 };
		gbl_panelPerfil.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panelPerfil.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelPerfil.setLayout(gbl_panelPerfil);

		profilePic = new JLabel("");
		GridBagConstraints gbc_profilePic = new GridBagConstraints();
		gbc_profilePic.gridwidth = 4;
		gbc_profilePic.gridheight = 4;
		gbc_profilePic.anchor = GridBagConstraints.WEST;
		gbc_profilePic.insets = new Insets(0, 0, 5, 5);
		gbc_profilePic.gridx = 1;
		gbc_profilePic.gridy = 1;
		panelPerfil.add(profilePic, gbc_profilePic);

		nickname = new JLabel("username");
		nickname.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_nickname = new GridBagConstraints();
		gbc_nickname.insets = new Insets(0, 0, 5, 5);
		gbc_nickname.gridx = 6;
		gbc_nickname.gridy = 2;
		panelPerfil.add(nickname, gbc_nickname);

		followButton = new JButton("dev");

		GridBagConstraints gbc_followButton = new GridBagConstraints();
		gbc_followButton.insets = new Insets(0, 0, 5, 5);
		gbc_followButton.gridx = 7;
		gbc_followButton.gridy = 2;
		panelPerfil.add(followButton, gbc_followButton);

		publications = new JLabel("0 publicaciones");
		publications.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_publications = new GridBagConstraints();
		gbc_publications.insets = new Insets(0, 0, 5, 5);
		gbc_publications.gridx = 6;
		gbc_publications.gridy = 3;
		panelPerfil.add(publications, gbc_publications);

		following = new JLabel("0 seguidos");
		following.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_following = new GridBagConstraints();
		gbc_following.insets = new Insets(0, 0, 5, 5);
		gbc_following.gridx = 7;
		gbc_following.gridy = 3;
		panelPerfil.add(following, gbc_following);

		followers = new JLabel("0 seguidores");
		followers.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_follows = new GridBagConstraints();
		gbc_follows.insets = new Insets(0, 0, 5, 5);
		gbc_follows.gridx = 8;
		gbc_follows.gridy = 3;
		panelPerfil.add(followers, gbc_follows);

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

		followButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (followButton.getText().equals("Seguir")) {
					Controller.getInstancia().follow(selfUsername, anotherProfileUsername);
					followButton.setText("Dejar de seguir");
					numSelfFollowing = Controller.getInstancia().getNumUsersFollowing(selfUsername);
					if (numSelfFollowing == 1)
						selfFollowing.setText(numSelfFollowing + " seguido");
					else
						selfFollowing.setText(numSelfFollowing + " seguidos");
					numFollowers = Controller.getInstancia().getNumFollowers(anotherProfileUsername);
					if (numFollowers == 1)
						followers.setText(numFollowers + " seguidor");
					else
						followers.setText(numFollowers + " seguidores");
				} else if (followButton.getText().equals("Dejar de seguir")) {
					Controller.getInstancia().unfollow(selfUsername, anotherProfileUsername);
					followButton.setText("Seguir");
					numSelfFollowing = Controller.getInstancia().getNumUsersFollowing(selfUsername);
					if (numSelfFollowing == 1)
						selfFollowing.setText(numSelfFollowing + " seguido");
					else
						selfFollowing.setText(numSelfFollowing + " seguidos");
					numFollowers = Controller.getInstancia().getNumFollowers(anotherProfileUsername);
					if (numFollowers == 1)
						followers.setText(numFollowers + " seguidor");
					else
						followers.setText(numFollowers + " seguidores");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				followButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				followButton.setCursor(Cursor.getDefaultCursor());
			}
		});

		logo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cL = (CardLayout) panelCentral.getLayout();
				cL.show(panelCentral, "panelPrincipal");
				anotherProfileUsername = null;
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
				anotherProfileUsername = null;
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

		if (UIManager.getLookAndFeel().getName() == "FlatLaf Light") {
			menuBar.setBorder(new MatteBorder(1, 1, 1, 1, lightBars));
			mntmModoClaroOscuro.setBorder(new MatteBorder(0, 0, 0, 1, lightBars));
			mntmModoClaroOscuro.setText("Modo oscuro");
		} else if (UIManager.getLookAndFeel().getName() == "FlatLaf Dark") {
			menuBar.setBorder(new MatteBorder(1, 1, 1, 1, darkBars));
			mntmModoClaroOscuro.setBorder(new MatteBorder(0, 0, 0, 1, darkBars));
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
				DefaultListModel<User> matchingUsers = Controller.getInstancia().searchByUsername(selfUsername, texto);
				JList<User> userList = new JList<>(matchingUsers);
				userList.setCellRenderer(new UserListRender());
				JScrollPane scrollUserPanel = new JScrollPane(userList);
				JFrame matchingUsersPanel = new JFrame();

				userList.addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (!e.getValueIsAdjusting()) {
							cambiarPerfil(userList.getSelectedValue().getUsername());
							matchingUsersPanel.dispose();
						}
					}
				});

				userList.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						userList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					}

					@Override
					public void mouseExited(MouseEvent e) {
						userList.setCursor(Cursor.getDefaultCursor());
					}
				});

				JPanel panelNorte = new JPanel();
				matchingUsersPanel.getContentPane().add(panelNorte, BorderLayout.NORTH);
				JLabel foundUsers = new JLabel("Usuarios encontrados");
				foundUsers.setFont(new Font("Bahnschrift", Font.BOLD, 16));
				panelNorte.add(foundUsers);

				if (UIManager.getLookAndFeel().getName() == "FlatLaf Light") {
					userList.setBackground(lightBars);
				} else if (UIManager.getLookAndFeel().getName() == "FlatLaf Dark") {
					userList.setBackground(darkBars);
				}

				matchingUsersPanel.setIconImage(
						Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/ig64.png")));
				matchingUsersPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				matchingUsersPanel.setSize(300, 300);
				matchingUsersPanel.setLocationRelativeTo(null);
				matchingUsersPanel.getContentPane().add(scrollUserPanel);
				matchingUsersPanel.setVisible(true);
			}
		}
	}

	private void cambiarPerfil(String username) {
		this.anotherProfileUsername = username;
		nickname.setText(username);
		numPub = Controller.getInstancia().getNumPublications(username);
		if (numPub == 1)
			publications.setText(numPub + " publicación");
		else
			publications.setText(numPub + " publicaciones");

		numFollowing = Controller.getInstancia().getNumUsersFollowing(username);
		if (numFollowing == 1)
			following.setText(numFollowing + " seguido");
		else
			following.setText(numFollowing + " seguidos");

		numFollowers = Controller.getInstancia().getNumFollowers(username);
		if (numFollowers == 1)
			followers.setText(numFollowers + " seguidor");
		else
			followers.setText(numFollowers + " seguidores");
		BufferedImage searchedUserPic = null;
		try {
			String userPicPath = Controller.getInstancia().getProfilePicPath(username, false);
			if (userPicPath.contains("%")) {
				try {
					userPicPath = URLDecoder.decode(userPicPath, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			searchedUserPic = ImageIO.read(new File(userPicPath));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		if (searchedUserPic != null) {
			ImageIcon searchedPic = new ImageIcon(getCircularImage(searchedUserPic));
			if (searchedPic.getIconHeight() != 128 || searchedPic.getIconWidth() != 128) {
				searchedPic = new ImageIcon(searchedPic.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
			}
			profilePic.setIcon(searchedPic);
		}
		if (Controller.getInstancia().userIsFollower(selfUsername, username))
			followButton.setText("Dejar de seguir");
		else
			followButton.setText("Seguir");
		CardLayout cL = (CardLayout) panelCentral.getLayout();
		cL.show(panelCentral, "panelPerfil");
	}

	public static BufferedImage getCircularImage(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int diameter = Math.min(width, height);

		BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = masked.createGraphics();

		// Crear un área circular
		Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, diameter, diameter);
		g2d.setClip(circle);

		// Dibujar la imagen original en el contexto usando el área circular como
		// máscara
		g2d.drawImage(image, 0, 0, null);

		// Desechar el contexto gráfico y devolver la imagen recortada
		g2d.dispose();
		return masked;
	}

}
