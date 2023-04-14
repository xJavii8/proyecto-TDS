package umu.tds.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import umu.tds.controller.Controller;
import umu.tds.model.User;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
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
	
	/**
	 * Create the application.
	 */
	public SelfProfileWindow(String user, JLabel selfProfile) {
		this.user = Controller.getInstancia().getUser(user);
		this.selfProfile = selfProfile;
		initialize();
	}
	
	public JPanel getPanelPerfilPersonal() {
		return panelPerfilPersonal;
	}
	
	public JLabel getPublicationsLabel() {
		return publications;
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
		gbl_panelPerfilPersonal.columnWidths = new int[] { 15, 0, 0, 0, 15, 15, 0, 112, 0, 15, 0, 0 };
		gbl_panelPerfilPersonal.rowHeights = new int[] { 15, 0, 0, 0, 0, 0, 0 };
		gbl_panelPerfilPersonal.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panelPerfilPersonal.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelPerfilPersonal.setLayout(gbl_panelPerfilPersonal);
		
		profilePic = new JLabel("");
		profilePic.setIcon(Utilities.genSelfProfilePic(user.getProfilePic()));
		GridBagConstraints gbc_profilePic = new GridBagConstraints();
		gbc_profilePic.gridheight = 4;
		gbc_profilePic.gridwidth = 4;
		gbc_profilePic.insets = new Insets(0, 0, 5, 5);
		gbc_profilePic.gridx = 1;
		gbc_profilePic.gridy = 1;
		panelPerfilPersonal.add(profilePic, gbc_profilePic);
		
		nickname = new JLabel("");
		nickname.setText(user.getUsername());
		nickname.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_nickname = new GridBagConstraints();
		gbc_nickname.insets = new Insets(0, 0, 5, 5);
		gbc_nickname.gridx = 6;
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
		gbc_edit.gridx = 7;
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
				PremiumMenuWindow pmw = new PremiumMenuWindow(nickname.getText());
				pmw.show();
			}
		});
		GridBagConstraints gbc_premium = new GridBagConstraints();
		gbc_premium.insets = new Insets(0, 0, 5, 5);
		gbc_premium.gridx = 8;
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
		gbc_publications.gridx = 6;
		gbc_publications.gridy = 3;
		panelPerfilPersonal.add(publications, gbc_publications);
		
		JLabel siguiendo = new JLabel("");
		int numSelfFollowing = user.getUsersFollowing().size();
		if (numSelfFollowing == 1)
			siguiendo.setText(numSelfFollowing + " seguido");
		else
			siguiendo.setText(numSelfFollowing + " seguidos");
		siguiendo.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_siguiendo = new GridBagConstraints();
		gbc_siguiendo.insets = new Insets(0, 0, 5, 5);
		gbc_siguiendo.gridx = 7;
		gbc_siguiendo.gridy = 3;
		panelPerfilPersonal.add(siguiendo, gbc_siguiendo);
		
		JLabel seguidores = new JLabel("");
		int numSelfFollowers = user.getFollowers().size();
		if (numSelfFollowers == 1)
			seguidores.setText(numSelfFollowers + " seguidor");
		else
			seguidores.setText(numSelfFollowers + " seguidores");
		seguidores.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_seguidores = new GridBagConstraints();
		gbc_seguidores.insets = new Insets(0, 0, 5, 5);
		gbc_seguidores.gridx = 8;
		gbc_seguidores.gridy = 3;
		panelPerfilPersonal.add(seguidores, gbc_seguidores);
		
		fullname = new JLabel("");
		fullname.setText(user.getFullName());
		fullname.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_fullname = new GridBagConstraints();
		gbc_fullname.insets = new Insets(0, 0, 5, 5);
		gbc_fullname.gridx = 6;
		gbc_fullname.gridy = 4;
		panelPerfilPersonal.add(fullname, gbc_fullname);
	}
	
	public void updateProfile(String username, String fullname, String profilePicPath) {
		this.nickname.setText(username);
		this.fullname.setText(fullname);
		this.selfProfile.setText(username);
		this.selfProfile.setIcon(Utilities.genIconSelfProfileLabel(profilePicPath));
		this.profilePic.setIcon(Utilities.genSelfProfilePic(profilePicPath));
		this.user = Controller.getInstancia().getUser(username);
	}

}
