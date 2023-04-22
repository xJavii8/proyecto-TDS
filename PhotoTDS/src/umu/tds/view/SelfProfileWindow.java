package umu.tds.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import umu.tds.controller.Controller;
import umu.tds.model.Album;
import umu.tds.model.Photo;
import umu.tds.model.PhotoListRender;
import umu.tds.model.ProfilePhotoListRender;
import umu.tds.model.Publication;
import umu.tds.model.User;
import umu.tds.model.UserListRender;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelfProfileWindow {

	private JFrame frame;
	private JPanel panelPerfilPersonal;
	private User user;
	private JLabel selfProfile;
	private JLabel profilePic;
	private JLabel nickname;
	private JLabel fullname;
	private JLabel publications;
	private JLabel siguiendo;
	private MainWindow mw;
	private JScrollPane scrollPane;
	private JList<Publication> publicationList;
	private JButton switchAlbum;
	private JButton newAlbum;

	/**
	 * Create the application.
	 */
	public SelfProfileWindow(String user, MainWindow mw) {
		this.user = Controller.getInstancia().getUser(user);
		this.selfProfile = mw.getSelfProfile();
		this.mw = mw;
		initialize();
	}

	public JPanel getPanelPerfilPersonal() {
		return panelPerfilPersonal;
	}

	public JLabel getPublicationsLabel() {
		return publications;
	}

	public JLabel getFollowingLabel() {
		return siguiendo;
	}

	public JList<Publication> getPublicationList() {
		return publicationList;
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 602, 583);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panelPerfilPersonal = new JPanel();
		frame.getContentPane().add(panelPerfilPersonal, BorderLayout.CENTER);
		GridBagLayout gbl_panelPerfilPersonal = new GridBagLayout();
		gbl_panelPerfilPersonal.columnWidths = new int[] { 15, 15, 15, 0, 92, 112, 0, 15, 0 };
		gbl_panelPerfilPersonal.rowHeights = new int[] { 15, 0, 0, 0, 0, 0, 15, 0 };
		gbl_panelPerfilPersonal.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelPerfilPersonal.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panelPerfilPersonal.setLayout(gbl_panelPerfilPersonal);

		profilePic = new JLabel("");
		ImageIcon pic = Utilities.getCircleIcon(user.getProfilePic());
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
		panelPerfilPersonal.add(profilePic, gbc_profilePic);

		nickname = new JLabel("");
		nickname.setText(user.getUsername());
		nickname.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_nickname = new GridBagConstraints();
		gbc_nickname.insets = new Insets(0, 0, 5, 5);
		gbc_nickname.gridx = 3;
		gbc_nickname.gridy = 2;
		panelPerfilPersonal.add(nickname, gbc_nickname);

		JButton edit = new JButton("Editar perfil");
		edit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				edit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				edit.setCursor(Cursor.getDefaultCursor());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				EditProfileVindow epw = new EditProfileVindow(user.getUsername(), SelfProfileWindow.this);
				epw.show();
			}
		});
		GridBagConstraints gbc_edit = new GridBagConstraints();
		gbc_edit.insets = new Insets(0, 0, 5, 5);
		gbc_edit.gridx = 4;
		gbc_edit.gridy = 2;
		panelPerfilPersonal.add(edit, gbc_edit);

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
				PremiumMenuWindow pmw = new PremiumMenuWindow(nickname.getText(), mw);
				pmw.show();
			}
		});
		GridBagConstraints gbc_premium = new GridBagConstraints();
		gbc_premium.insets = new Insets(0, 0, 5, 5);
		gbc_premium.gridx = 5;
		gbc_premium.gridy = 2;
		panelPerfilPersonal.add(premium, gbc_premium);

		publications = new JLabel("");
		int numSelfPub = user.getPublications().size();
		if (numSelfPub == 1)
			publications.setText(numSelfPub + " publicación");
		else
			publications.setText(numSelfPub + " publicaciones");
		publications.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_publications = new GridBagConstraints();
		gbc_publications.insets = new Insets(0, 0, 5, 5);
		gbc_publications.gridx = 3;
		gbc_publications.gridy = 3;
		panelPerfilPersonal.add(publications, gbc_publications);

		siguiendo = new JLabel("");
		siguiendo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultListModel<User> following = Controller.getInstancia().getFollowingUsers(user.getUsername());
				Utilities.listaUsuarios(mw, user.getUsername(), following);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				siguiendo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				siguiendo.setCursor(Cursor.getDefaultCursor());
			}
		});
		int numSelfFollowing = user.getUsersFollowing().size();
		if (numSelfFollowing == 1)
			siguiendo.setText(numSelfFollowing + " seguido");
		else
			siguiendo.setText(numSelfFollowing + " seguidos");
		siguiendo.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_siguiendo = new GridBagConstraints();
		gbc_siguiendo.insets = new Insets(0, 0, 5, 5);
		gbc_siguiendo.gridx = 4;
		gbc_siguiendo.gridy = 3;
		panelPerfilPersonal.add(siguiendo, gbc_siguiendo);

		JLabel seguidores = new JLabel("");
		seguidores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultListModel<User> followers = Controller.getInstancia().getFollowers(user.getUsername());
				Utilities.listaUsuarios(mw, user.getUsername(), followers);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				seguidores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				seguidores.setCursor(Cursor.getDefaultCursor());
			}
		});
		int numSelfFollowers = user.getFollowers().size();
		if (numSelfFollowers == 1)
			seguidores.setText(numSelfFollowers + " seguidor");
		else
			seguidores.setText(numSelfFollowers + " seguidores");
		seguidores.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_seguidores = new GridBagConstraints();
		gbc_seguidores.insets = new Insets(0, 0, 5, 5);
		gbc_seguidores.gridx = 5;
		gbc_seguidores.gridy = 3;
		panelPerfilPersonal.add(seguidores, gbc_seguidores);

		fullname = new JLabel("");
		fullname.setText(user.getFullName());
		fullname.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_fullname = new GridBagConstraints();
		gbc_fullname.insets = new Insets(0, 0, 5, 5);
		gbc_fullname.gridx = 3;
		gbc_fullname.gridy = 4;
		panelPerfilPersonal.add(fullname, gbc_fullname);
		
		switchAlbum = new JButton("Álbumes");
		GridBagConstraints gbc_switchAlbum = new GridBagConstraints();
		gbc_switchAlbum.insets = new Insets(0, 0, 5, 5);
		gbc_switchAlbum.gridx = 4;
		gbc_switchAlbum.gridy = 4;
		panelPerfilPersonal.add(switchAlbum, gbc_switchAlbum);
		
		newAlbum = new JButton("Nuevo álbum");
		newAlbum.setVisible(false);
		newAlbum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddAlbumWindow aaw = new AddAlbumWindow(user.getUsername(), SelfProfileWindow.this);
				aaw.show();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				newAlbum.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				newAlbum.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_newAlbum = new GridBagConstraints();
		gbc_newAlbum.insets = new Insets(0, 0, 5, 5);
		gbc_newAlbum.gridx = 5;
		gbc_newAlbum.gridy = 4;
		panelPerfilPersonal.add(newAlbum, gbc_newAlbum);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 5;
		panelPerfilPersonal.add(scrollPane, gbc_scrollPane);

		DefaultListModel<Publication> photoList = Controller.getInstancia().getPhotosProfile(user.getUsername());

		publicationList = new JList<>(photoList);
		scrollPane.setViewportView(publicationList);
		publicationList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		publicationList.setVisibleRowCount(-1);
		publicationList.ensureIndexIsVisible(publicationList.getHeight());
		publicationList.setCellRenderer(new ProfilePhotoListRender());

		publicationList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if(publicationList.getSelectedValue() instanceof Photo) {
						JPanel panelCentral = mw.getPanelCentral();
						JPanel panelPublication = new PublicationWindow(user.getUsername(),
								publicationList.getSelectedValue(), publicationList.getSelectedValue().getUser(), mw)
								.getPublicationPanel();
						panelCentral.add(panelPublication, "panelPublication");
						CardLayout cL = (CardLayout) panelCentral.getLayout();
						cL.show(panelCentral, "panelPublication");
					} else if(publicationList.getSelectedValue() instanceof Album) {
						AlbumWindow aw = new AlbumWindow((Album) publicationList.getSelectedValue(), user.getUsername(), mw);
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
		
		switchAlbum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(switchAlbum.getText().equals("Álbumes")) {
					DefaultListModel<Publication> albums = Controller.getInstancia().getAlbumsProfile(user.getUsername());
					publicationList.setModel(albums);
					newAlbum.setVisible(true);
					switchAlbum.setText("Fotos");
				} else if(switchAlbum.getText().equals("Fotos")) {
					DefaultListModel<Publication> fotos = Controller.getInstancia().getPhotosProfile(user.getUsername());
					publicationList.setModel(fotos);
					newAlbum.setVisible(false);
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
	
	public void updateProfile(String username, String fullname, String profilePicPath) {
		this.nickname.setText(username);
		this.fullname.setText(fullname);
		this.selfProfile.setText(username);
		ImageIcon pic = Utilities.getCircleIcon(profilePicPath);
		if (pic.getIconHeight() != Constantes.PROFILE_PIC_SIZE || pic.getIconWidth() != Constantes.PROFILE_PIC_SIZE) {
			pic = new ImageIcon(pic.getImage().getScaledInstance(Constantes.PROFILE_PIC_SIZE,
					Constantes.PROFILE_PIC_SIZE, Image.SCALE_DEFAULT));
		}
		this.profilePic.setIcon(pic);
		if (pic.getIconHeight() != Constantes.SELF_USER_PIC_SIZE
				|| pic.getIconWidth() != Constantes.SELF_USER_PIC_SIZE) {
			pic = new ImageIcon(pic.getImage().getScaledInstance(Constantes.SELF_USER_PIC_SIZE,
					Constantes.SELF_USER_PIC_SIZE, Image.SCALE_DEFAULT));
		}
		this.selfProfile.setIcon(pic);
		this.user = Controller.getInstancia().getUser(username);
	}

}
