package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.toedter.calendar.JDateChooser;

import umu.tds.controller.Controller;
import umu.tds.model.NotificationListRender;
import umu.tds.model.Photo;
import umu.tds.model.Publication;
import umu.tds.model.User;

public class MainWindow {

	private JFrame frame;

	private String selfUsername;
	private String selfProfilePicPath;
	private JTextField searchField;
	private JPanel panelCentral;
	private JLabel selfProfile;
	private SelfProfileWindow spw;
	private User user;
	private JList<Photo> photosList;
	private DefaultListModel<Photo> photosLastLogin;

	/**
	 * Create the application.
	 */
	public MainWindow(String username, String profilePicPath) {
		this.user = Controller.getInstancia().getUser(username);
		photosLastLogin = Controller.getInstancia().getAllPhotosFromDate(user, user.getLastLogin());
		this.selfUsername = user.getUsername();

		if (profilePicPath.contains("%")) {
			try {
				profilePicPath = URLDecoder.decode(profilePicPath, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		this.selfProfilePicPath = profilePicPath;
		this.selfProfile = new JLabel(username);
		this.photosList = new JList<>();
		this.spw = new SelfProfileWindow(selfUsername, MainWindow.this);
		initialize();
	}

	public void show() {
		frame.setVisible(true);
	}

	public JPanel getPanelCentral() {
		return panelCentral;
	}

	public JLabel getSelfProfile() {
		return selfProfile;
	}

	public SelfProfileWindow getSPW() {
		return spw;
	}

	public void setSPW(SelfProfileWindow spw) {
		this.spw = spw;
	}

	public JList<Photo> getPhotosList() {
		return photosList;
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

		ImageIcon pic = Utilities.getCircleIcon(selfProfilePicPath);
		if (pic.getIconHeight() != Constantes.SELF_USER_PIC_SIZE
				|| pic.getIconWidth() != Constantes.SELF_USER_PIC_SIZE) {
			pic = new ImageIcon(pic.getImage().getScaledInstance(Constantes.SELF_USER_PIC_SIZE,
					Constantes.SELF_USER_PIC_SIZE, Image.SCALE_DEFAULT));
		}
		selfProfile.setIcon(pic);

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
		gbl_panelPrincipal.columnWidths = new int[] { 15, 15, 15, 122, 128, 0, 0, 15, 15, 0 };
		gbl_panelPrincipal.rowHeights = new int[] { 15, 15, 0, 15, 15, 15, 0, 15, 15, 0 };
		gbl_panelPrincipal.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panelPrincipal.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		panelPrincipal.setLayout(gbl_panelPrincipal);

		JLabel newPubs = new JLabel("");
		if (photosLastLogin.isEmpty())
			newPubs.setText("No tienes publicaciones nuevas");
		else
			newPubs.setText("Tienes nuevas publicaciones");
		newPubs.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_newPubs = new GridBagConstraints();
		gbc_newPubs.gridwidth = 4;
		gbc_newPubs.insets = new Insets(0, 0, 5, 5);
		gbc_newPubs.gridx = 3;
		gbc_newPubs.gridy = 3;
		panelPrincipal.add(newPubs, gbc_newPubs);

		JDateChooser datePub = new JDateChooser();

		GridBagConstraints gbc_datePub = new GridBagConstraints();
		gbc_datePub.fill = GridBagConstraints.HORIZONTAL;
		gbc_datePub.anchor = GridBagConstraints.SOUTH;
		gbc_datePub.gridwidth = 2;
		gbc_datePub.insets = new Insets(0, 0, 5, 5);
		gbc_datePub.gridx = 5;
		gbc_datePub.gridy = 5;
		panelPrincipal.add(datePub, gbc_datePub);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 6;
		panelPrincipal.add(scrollPane, gbc_scrollPane);

		photosList = new JList<>(photosLastLogin);
		if (photosLastLogin.isEmpty()) {
			photosList.setVisible(false);
			scrollPane.setVisible(false);
		}
		photosList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && photosList.getSelectedValue() != null) {
					JPanel panelCentral = getPanelCentral();
					JPanel panelPublication = new PublicationWindow(selfUsername,
							(Publication) photosList.getSelectedValue(), photosList.getSelectedValue().getUser(),
							MainWindow.this).getPublicationPanel();

					panelCentral.add(panelPublication, "panelPublication");
					CardLayout cL = (CardLayout) panelCentral.getLayout();
					cL.show(panelCentral, "panelPublication");
				}
			}
		});
		scrollPane.setViewportView(photosList);
		photosList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		photosList.setVisibleRowCount(-1);
		photosList.ensureIndexIsVisible(photosList.getHeight());
		photosList.setCellRenderer(new NotificationListRender());

		datePub.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					Date date = (Date) evt.getNewValue();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
					Date newDate = calendar.getTime();

					DefaultListModel<Photo> photosSince = Controller.getInstancia().getAllPhotosFromDate(user, newDate);
					if (!photosSince.isEmpty()) {
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
						String fechaFormateada = format.format(newDate);
						newPubs.setText("Publicaciones desde la fecha " + fechaFormateada);
						photosList.setModel(photosSince);
						photosList.setVisible(true);
						scrollPane.setVisible(true);
					} else {
						newPubs.setText("No hay publicaciones");
						photosList.setVisible(false);
						scrollPane.setVisible(false);
					}
				}

			}
		});

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
				spw = new SelfProfileWindow(selfUsername, MainWindow.this);
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

		JMenuItem mmtmLogout = new JMenuItem("Cerrar sesión");
		mmtmLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StartWindow.main(null);
				frame.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mmtmLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mmtmLogout.setCursor(Cursor.getDefaultCursor());
			}
		});
		menuBar.add(mmtmLogout);
	}

	private void buscar(String texto) {
		if (texto.isEmpty() == false) {
			if (texto.contains("#")) {
				DefaultListModel<Publication> matchingPublications = Controller.getInstancia()
						.searchPublicationsByHashtags(texto);
				Utilities.listaPublicaciones(MainWindow.this, selfUsername, matchingPublications);
			} else {
				DefaultListModel<User> matchingUsers = Controller.getInstancia().search(selfUsername, texto);
				Utilities.listaUsuarios(MainWindow.this, selfUsername, matchingUsers);
			}
		}
	}

}
