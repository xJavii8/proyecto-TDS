package umu.tds.view;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import umu.tds.controller.Controller;
import umu.tds.model.Album;
import umu.tds.model.AlbumPublicationListRender;
import umu.tds.model.Photo;
import umu.tds.model.ProfilePhotoListRender;
import umu.tds.model.Publication;
import umu.tds.model.User;

public class ProfileWindow {

	private JFrame frame;
	private JPanel panelPerfil;
	private String selfUser;
	private String searchedUser;
	private User profileUser;
	private JButton switchAlbum;
	private MainWindow mw;

	/**
	 * Create the application.
	 */
	public ProfileWindow(String selfUser, String searchedUser, MainWindow mw) {
		this.selfUser = selfUser;
		this.searchedUser = searchedUser;
		this.profileUser = Controller.getInstancia().getUser(searchedUser);
		this.mw = mw;
		initialize();
	}

	public JPanel getPanelPerfil() {
		return panelPerfil;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 602, 583);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panelPerfil = new JPanel();
		GridBagLayout gbl_panelPerfil = new GridBagLayout();
		gbl_panelPerfil.columnWidths = new int[] { 15, 15, 15, 0, 93, 114, 0, 15, 0 };
		gbl_panelPerfil.rowHeights = new int[] { 15, 0, 0, 0, 0, 0, 15, 0 };
		gbl_panelPerfil.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelPerfil.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panelPerfil.setLayout(gbl_panelPerfil);

		JLabel profilePic = new JLabel("");
		ImageIcon pic = Utilities.getCircleIcon(profileUser.getProfilePic());
		if (pic.getIconHeight() != Constantes.PROFILE_PIC_SIZE || pic.getIconWidth() != Constantes.PROFILE_PIC_SIZE) {
			pic = new ImageIcon(pic.getImage().getScaledInstance(Constantes.PROFILE_PIC_SIZE,
					Constantes.PROFILE_PIC_SIZE, Image.SCALE_DEFAULT));
		}
		profilePic.setIcon(pic);
		GridBagConstraints gbc_profilePic = new GridBagConstraints();
		gbc_profilePic.gridheight = 4;
		gbc_profilePic.insets = new Insets(0, 0, 5, 5);
		gbc_profilePic.gridx = 1;
		gbc_profilePic.gridy = 1;
		panelPerfil.add(profilePic, gbc_profilePic);

		JLabel nickname = new JLabel(searchedUser);
		nickname.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_nickname = new GridBagConstraints();
		gbc_nickname.insets = new Insets(0, 0, 5, 5);
		gbc_nickname.gridx = 3;
		gbc_nickname.gridy = 2;
		panelPerfil.add(nickname, gbc_nickname);

		JButton followButton = new JButton("");
		if (Controller.getInstancia().userIsFollower(selfUser, searchedUser))
			followButton.setText("Dejar de seguir");
		else
			followButton.setText("Seguir");
		GridBagConstraints gbc_followButton = new GridBagConstraints();
		gbc_followButton.insets = new Insets(0, 0, 5, 5);
		gbc_followButton.gridx = 4;
		gbc_followButton.gridy = 2;
		panelPerfil.add(followButton, gbc_followButton);

		JLabel publications = new JLabel();
		publications.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_publications = new GridBagConstraints();
		gbc_publications.insets = new Insets(0, 0, 5, 5);
		gbc_publications.gridx = 3;
		gbc_publications.gridy = 3;
		panelPerfil.add(publications, gbc_publications);

		JLabel following = new JLabel();
		following.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultListModel<User> followingList = Controller.getInstancia().getFollowingUsers(searchedUser);
				Utilities.listaUsuarios(mw, searchedUser, followingList);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				following.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				following.setCursor(Cursor.getDefaultCursor());
			}
		});
		following.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_following = new GridBagConstraints();
		gbc_following.insets = new Insets(0, 0, 5, 5);
		gbc_following.gridx = 4;
		gbc_following.gridy = 3;
		panelPerfil.add(following, gbc_following);

		JLabel followers = new JLabel();
		followers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultListModel<User> followersList = Controller.getInstancia().getFollowers(searchedUser);
				Utilities.listaUsuarios(mw, searchedUser, followersList);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				followers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				followers.setCursor(Cursor.getDefaultCursor());
			}
		});
		followers.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_follows = new GridBagConstraints();
		gbc_follows.insets = new Insets(0, 0, 5, 5);
		gbc_follows.gridx = 5;
		gbc_follows.gridy = 3;
		panelPerfil.add(followers, gbc_follows);

		JLabel fullname = new JLabel("");
		fullname.setText(profileUser.getFullName());
		fullname.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_fullname = new GridBagConstraints();
		gbc_fullname.insets = new Insets(0, 0, 5, 5);
		gbc_fullname.gridx = 3;
		gbc_fullname.gridy = 4;
		panelPerfil.add(fullname, gbc_fullname);

		switchAlbum = new JButton("Álbumes");
		GridBagConstraints gbc_switchAlbum = new GridBagConstraints();
		gbc_switchAlbum.insets = new Insets(0, 0, 5, 5);
		gbc_switchAlbum.gridx = 4;
		gbc_switchAlbum.gridy = 4;
		panelPerfil.add(switchAlbum, gbc_switchAlbum);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 5;
		panelPerfil.add(scrollPane, gbc_scrollPane);

		DefaultListModel<Publication> photoList = Controller.getInstancia().getPhotosProfile(searchedUser);

		JList<Publication> publicationList = new JList<>(photoList);
		scrollPane.setViewportView(publicationList);
		publicationList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		publicationList.setVisibleRowCount(-1);
		publicationList.ensureIndexIsVisible(publicationList.getHeight());
		publicationList.setCellRenderer(new ProfilePhotoListRender());

		publicationList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if (publicationList.getSelectedValue() instanceof Photo) {
						JPanel panelCentral = mw.getPanelCentral();
						JPanel panelPublication = new PublicationWindow(selfUser, publicationList.getSelectedValue(),
								publicationList.getSelectedValue().getUser(), mw).getPublicationPanel();
						panelCentral.add(panelPublication, "panelPublication");
						CardLayout cL = (CardLayout) panelCentral.getLayout();
						cL.show(panelCentral, "panelPublication");
					} else if (publicationList.getSelectedValue() instanceof Album) {
						AlbumWindow aw = new AlbumWindow((Album) publicationList.getSelectedValue(), selfUser, mw);
						aw.show();
					}

				}
			}
		});

		publicationList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				publicationList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				publicationList.setCursor(Cursor.getDefaultCursor());
			}
		});

		int numPub = profileUser.getPublications().size();
		if (numPub == 1)
			publications.setText(numPub + " publicación");
		else
			publications.setText(numPub + " publicaciones");

		int numFollowing = profileUser.getUsersFollowing().size();
		if (numFollowing == 1)
			following.setText(numFollowing + " seguido");
		else
			following.setText(numFollowing + " seguidos");

		int numFollowers = profileUser.getFollowers().size();
		if (numFollowers == 1)
			followers.setText(numFollowers + " seguidor");
		else
			followers.setText(numFollowers + " seguidores");

		followButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (followButton.getText().equals("Seguir")) {
					Controller.getInstancia().follow(selfUser, searchedUser);
					followButton.setText("Dejar de seguir");
				} else if (followButton.getText().equals("Dejar de seguir")) {
					Controller.getInstancia().unfollow(selfUser, searchedUser);
					followButton.setText("Seguir");
				}

				DefaultListModel<Photo> newMWPhotos = Controller.getInstancia()
						.getAllPhotos(Controller.getInstancia().getUser(selfUser).getUsersFollowing());

				mw.getPhotosList().setModel(newMWPhotos);

				int numFollowingUsers = Controller.getInstancia().getUser(selfUser).getUsersFollowing().size();
				if (numFollowingUsers == 1)
					mw.getSPW().getFollowingLabel().setText(numFollowingUsers + " seguido");
				else
					mw.getSPW().getFollowingLabel().setText(numFollowingUsers + " seguidos");

				int numFollowers = profileUser.getFollowers().size();
				if (numFollowers == 1)
					followers.setText(numFollowers + " seguidor");
				else
					followers.setText(numFollowers + " seguidores");
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

		switchAlbum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (switchAlbum.getText().equals("Álbumes")) {
					publicationList.setModel(new DefaultListModel<Publication>());
					publicationList.setCellRenderer(new AlbumPublicationListRender());
					DefaultListModel<Publication> albums = Controller.getInstancia().getAlbumsProfile(searchedUser);
					publicationList.setModel(albums);
					switchAlbum.setText("Fotos");
				} else if (switchAlbum.getText().equals("Fotos")) {
					publicationList.setModel(new DefaultListModel<Publication>());
					publicationList.setCellRenderer(new ProfilePhotoListRender());
					DefaultListModel<Publication> fotos = Controller.getInstancia().getPhotosProfile(searchedUser);
					publicationList.setModel(fotos);
					switchAlbum.setText("Álbumes");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				switchAlbum.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				switchAlbum.setCursor(Cursor.getDefaultCursor());
			}
		});
	}

}
