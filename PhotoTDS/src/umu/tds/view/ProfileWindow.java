package umu.tds.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import umu.tds.app.PhotoTDS.view.PanelFoto;
import umu.tds.app.PhotoTDS.view.VentanaInicio;
import umu.tds.controller.Controller;
import umu.tds.model.Photo;
import umu.tds.model.User;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

public class ProfileWindow {

	private JFrame frame;
	private JPanel panelPerfil;
	private String selfUser;
	private String searchedUser;
	private User profileUser;
	private MainWindow mw;
	
	private static List<Photo> p;

	
	private ImageIcon createImageIconFromPath(String path) {
	    if (path == null) {
	        System.err.println("Path is null!!!");
	        return null;
	    }

	    java.net.URL imgURL = getClass().getResource(path);

	    if (imgURL != null) {
	        return new ImageIcon(imgURL);
	    } else {
	        ImageIcon img = new ImageIcon(path);
	        if (img != null) {
	            return img;
	        }else {
	        	return null;
	        }
	    }
	}
	
	
	
	
	
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panelPerfil = new JPanel();
		GridBagLayout gbl_panelPerfil = new GridBagLayout();
		gbl_panelPerfil.columnWidths = new int[] { 39, 0, 0, 0, 112, 0, 0, 0 };
		gbl_panelPerfil.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelPerfil.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panelPerfil.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		gbc_profilePic.anchor = GridBagConstraints.WEST;
		gbc_profilePic.insets = new Insets(0, 0, 5, 5);
		gbc_profilePic.gridx = 0;
		gbc_profilePic.gridy = 0;
		panelPerfil.add(profilePic, gbc_profilePic);

		JLabel nickname = new JLabel(searchedUser);
		nickname.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_nickname = new GridBagConstraints();
		gbc_nickname.insets = new Insets(0, 0, 5, 5);
		gbc_nickname.gridx = 2;
		gbc_nickname.gridy = 1;
		panelPerfil.add(nickname, gbc_nickname);

		JButton followButton = new JButton("");
		if (Controller.getInstancia().userIsFollower(selfUser, searchedUser))
			followButton.setText("Dejar de seguir");
		else
			followButton.setText("Seguir");
		GridBagConstraints gbc_followButton = new GridBagConstraints();
		gbc_followButton.insets = new Insets(0, 0, 5, 5);
		gbc_followButton.gridx = 4;
		gbc_followButton.gridy = 1;
		panelPerfil.add(followButton, gbc_followButton);

		JLabel publications = new JLabel();
		publications.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_publications = new GridBagConstraints();
		gbc_publications.insets = new Insets(0, 0, 5, 5);
		gbc_publications.gridx = 2;
		gbc_publications.gridy = 2;
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
		gbc_following.gridy = 2;
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
		gbc_follows.insets = new Insets(0, 0, 5, 0);
		gbc_follows.gridx = 6;
		gbc_follows.gridy = 2;
		panelPerfil.add(followers, gbc_follows);

		JLabel fullname = new JLabel("");
		fullname.setText(profileUser.getFullName());
		fullname.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_fullname = new GridBagConstraints();
		gbc_fullname.insets = new Insets(0, 0, 5, 5);
		gbc_fullname.gridx = 2;
		gbc_fullname.gridy = 3;
		panelPerfil.add(fullname, gbc_fullname);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 5;
		panelPerfil.add(scrollPane, gbc_scrollPane);
		
		
		List<JLabel> publicacionesIconos = new LinkedList<>();
		p = Controller.getInstancia().getPhothosProfile(selfUser);
		for (Photo p: p) {
			JLabel etiqueta = new JLabel();
			Image imagen = createImageIconFromPath(p.getPath()).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(imagen);
			etiqueta.setIcon(icon);
			publicacionesIconos.add(etiqueta);
		}
		
		DefaultListModel<Component> photoList = new DefaultListModel<>();
		photoList.addAll(publicacionesIconos);
		
		JList<Component> list = new JList(photoList);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		list.ensureIndexIsVisible(list.getHeight());
		list.setCellRenderer(createListRenderer());
		scrollPane.setViewportView(list);

		int numPub = Controller.getInstancia().getNumPublications(searchedUser);
		if (numPub == 1)
			publications.setText(numPub + " publicación");
		else
			publications.setText(numPub + " publicaciones");

		int numFollowing = Controller.getInstancia().getNumUsersFollowing(searchedUser);
		if (numFollowing == 1)
			following.setText(numFollowing + " seguido");
		else
			following.setText(numFollowing + " seguidos");

		int numFollowers = Controller.getInstancia().getNumFollowers(searchedUser);
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
				
				int numFollowingUsers = Controller.getInstancia().getNumUsersFollowing(selfUser);
				if (numFollowingUsers == 1)
					mw.getSPW().getFollowingLabel().setText(numFollowingUsers + " seguido");
				else
					mw.getSPW().getFollowingLabel().setText(numFollowingUsers + " seguidos");

				int numFollowers = Controller.getInstancia().getNumFollowers(searchedUser);
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
	}

}
