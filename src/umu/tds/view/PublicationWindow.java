package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import umu.tds.controller.Controller;
import umu.tds.model.Photo;
import umu.tds.model.Publication;
import umu.tds.model.User;

public class PublicationWindow {

	private JFrame frame;
	private JPanel publicationPanel;
	private Photo p;
	private User userLogged;
	private User user;
	private MainWindow mw;
	private JLabel southUserLabel;
	private JLabel descriptionPhoto;
	private JLabel likeButton;
	private JLabel likes;
	private JLabel comment;
	private boolean liked;
	private JLabel titulo;
	private JButton borrar;
	private JPanel panelCentral;
	private JPanel panelPerfilPersonal;
	private JLabel viewComments;

	/**
	 * Create the application.
	 */
	public PublicationWindow(String usernameLogged, Publication p, String username, MainWindow mw) {
		this.userLogged = Controller.INSTANCE.getUser(usernameLogged);
		this.p = (Photo) p;
		this.user = Controller.INSTANCE.getUser(username);
		this.liked = Controller.INSTANCE.userLikedPub(userLogged.getUsername(), p);
		this.mw = mw;
		this.panelCentral = mw.getPanelCentral();
		this.panelPerfilPersonal = mw.getSPW().getPanelPerfilPersonal();
		panelCentral.add(panelPerfilPersonal, "panelPerfilPersonal");
		initialize();
	}

	public JPanel getPublicationPanel() {
		return publicationPanel;
	}

	public void updateCommentsNumber() {
		int commentsSize = p.getComments().size();
		if (commentsSize == 0) {
			viewComments.setVisible(false);
		} else {
			viewComments.setVisible(true);
		}

		if (commentsSize == 1)
			viewComments.setText("Ver " + commentsSize + " comentario");
		else
			viewComments.setText("Ver " + commentsSize + " comentarios");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 620, 583);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		publicationPanel = new JPanel();
		frame.getContentPane().add(publicationPanel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 15, 25, 50, 0, 15, 0, 0, 75, 0, 0, 200, 0 };
		gbl_panel.rowHeights = new int[] { 15, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		publicationPanel.setLayout(gbl_panel);
		ImageIcon pic = Utilities.getCircleIcon(user.getProfilePic());
		if (pic.getIconHeight() != Constantes.SELF_USER_PIC_SIZE
				|| pic.getIconWidth() != Constantes.SELF_USER_PIC_SIZE) {
			pic = new ImageIcon(pic.getImage().getScaledInstance(Constantes.SELF_USER_PIC_SIZE,
					Constantes.SELF_USER_PIC_SIZE, Image.SCALE_DEFAULT));
		}

		JLabel photo = new JLabel("");
		ImageIcon photoPub = new ImageIcon(new ImageIcon(p.getPath()).getImage().getScaledInstance(
				Constantes.PUBLICATION_PIC_SIZE, Constantes.PUBLICATION_PIC_SIZE, Image.SCALE_SMOOTH));

		JLabel userLabel = new JLabel("");
		userLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (user.getUsername().equals(userLogged.getUsername())) {
					panelPerfilPersonal = new SelfProfileWindow(userLogged.getUsername(), mw).getPanelPerfilPersonal();
					panelCentral.add(panelPerfilPersonal, "panelPerfilPersonal");
					CardLayout cL = (CardLayout) panelCentral.getLayout();
					cL.show(panelCentral, "panelPerfilPersonal");
				} else {
					JPanel panelPerfil = new ProfileWindow(userLogged.getUsername(), user.getUsername(), mw)
							.getPanelPerfil();
					panelCentral.add(panelPerfil, "panelPerfil");
					CardLayout cL = (CardLayout) panelCentral.getLayout();
					cL.show(panelCentral, "panelPerfil");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				userLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				userLabel.setCursor(Cursor.getDefaultCursor());
			}
		});
		userLabel.setText(user.getUsername());
		userLabel.setIcon(pic);
		userLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_userLabel = new GridBagConstraints();
		gbc_userLabel.insets = new Insets(0, 0, 5, 5);
		gbc_userLabel.gridx = 3;
		gbc_userLabel.gridy = 1;
		publicationPanel.add(userLabel, gbc_userLabel);

		titulo = new JLabel("- " + p.getTitle());
		titulo.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		GridBagConstraints gbc_titulo = new GridBagConstraints();
		gbc_titulo.gridwidth = 2;
		gbc_titulo.insets = new Insets(0, 0, 5, 5);
		gbc_titulo.gridx = 4;
		gbc_titulo.gridy = 1;
		publicationPanel.add(titulo, gbc_titulo);

		borrar = new JButton("Borrar publicación");
		borrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Controller.INSTANCE.deletePhoto(p)) {
					JOptionPane.showMessageDialog(frame, "La publicación " + p.getTitle() + " ha sido borrada.", null,
							JOptionPane.INFORMATION_MESSAGE);
					List<String> deletedAlbums = Controller.INSTANCE.deleteEmptyAlbums(userLogged.getUsername());
					if (!deletedAlbums.isEmpty()) {
						String message = "Se han borrado los siguientes álbumes, ya que estaban vacíos: ";
						for (int i = 0; i < deletedAlbums.size(); i++) {
							String albumTitle = deletedAlbums.get(i);
							message += albumTitle;
							if (i < deletedAlbums.size() - 1) {
								message += ", ";
							}
						}
						JOptionPane.showMessageDialog(frame, message, null, JOptionPane.INFORMATION_MESSAGE);
					}
					SelfProfileWindow newSPW = new SelfProfileWindow(userLogged.getUsername(), mw);
					mw.setSPW(newSPW);
					panelCentral.add(newSPW.getPanelPerfilPersonal(), "panelPerfilPersonal");
					CardLayout cL = (CardLayout) panelCentral.getLayout();
					cL.show(panelCentral, "panelPerfilPersonal");
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame,
							"Ha ocurrido un error al borrar la publicación " + p.getTitle() + ".", null,
							JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				borrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				borrar.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_borrar = new GridBagConstraints();
		gbc_borrar.insets = new Insets(0, 0, 5, 5);
		gbc_borrar.gridx = 9;
		gbc_borrar.gridy = 1;
		publicationPanel.add(borrar, gbc_borrar);
		if (!p.getUser().equals(userLogged.getUsername())) {
			borrar.setVisible(false);
		}
		photo.setIcon(photoPub);
		GridBagConstraints gbc_photo = new GridBagConstraints();
		gbc_photo.gridwidth = 7;
		gbc_photo.gridheight = 9;
		gbc_photo.insets = new Insets(0, 0, 5, 5);
		gbc_photo.gridx = 3;
		gbc_photo.gridy = 3;
		publicationPanel.add(photo, gbc_photo);

		likeButton = new JLabel("");
		if (liked) {
			likeButton.setIcon(new ImageIcon(PublicationWindow.class.getResource("/images/liked.png")));
		} else {
			likeButton.setIcon(new ImageIcon(PublicationWindow.class.getResource("/images/notLiked.png")));
		}
		GridBagConstraints gbc_likeButton = new GridBagConstraints();
		gbc_likeButton.insets = new Insets(0, 0, 5, 5);
		gbc_likeButton.gridx = 3;
		gbc_likeButton.gridy = 13;
		publicationPanel.add(likeButton, gbc_likeButton);

		likes = new JLabel("");
		likes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultListModel<User> users = Controller.INSTANCE.getUsersWhoLikedPublication(p);
				Utilities.listaUsuarios(mw, userLogged.getUsername(), users);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				likes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				likes.setCursor(Cursor.getDefaultCursor());
			}
		});
		if (p.getLikes() == 1) {
			likes.setText(p.getLikes() + " like");
		} else {
			likes.setText(p.getLikes() + " likes");
		}

		comment = new JLabel("");
		comment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CommentsWindow commentW = new CommentsWindow((Publication) p, userLogged, PublicationWindow.this);
				commentW.show();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				comment.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				comment.setCursor(Cursor.getDefaultCursor());
			}
		});
		comment.setIcon(new ImageIcon(PublicationWindow.class.getResource("/images/comment.png")));
		GridBagConstraints gbc_comment = new GridBagConstraints();
		gbc_comment.insets = new Insets(0, 0, 5, 5);
		gbc_comment.gridx = 4;
		gbc_comment.gridy = 13;
		publicationPanel.add(comment, gbc_comment);
		likes.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_likes = new GridBagConstraints();
		gbc_likes.insets = new Insets(0, 0, 5, 5);
		gbc_likes.gridx = 3;
		gbc_likes.gridy = 14;
		publicationPanel.add(likes, gbc_likes);

		likeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (liked) {
					Controller.INSTANCE.dislike(userLogged.getUsername(), p);
					likeButton.setIcon(new ImageIcon(PublicationWindow.class.getResource("/images/notLiked.png")));
					liked = false;
					if (p.getLikes() == 1) {
						likes.setText(p.getLikes() + " like");
					} else {
						likes.setText(p.getLikes() + " likes");
					}
				} else {
					Controller.INSTANCE.like(userLogged.getUsername(), p);
					likeButton.setIcon(new ImageIcon(PublicationWindow.class.getResource("/images/liked.png")));
					liked = true;
					if (p.getLikes() == 1) {
						likes.setText(p.getLikes() + " like");
					} else {
						likes.setText(p.getLikes() + " likes");
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				likeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				likeButton.setCursor(Cursor.getDefaultCursor());
			}
		});

		southUserLabel = new JLabel("");
		southUserLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (user.getUsername().equals(userLogged.getUsername())) {
					panelPerfilPersonal = new SelfProfileWindow(userLogged.getUsername(), mw).getPanelPerfilPersonal();
					panelCentral.add(panelPerfilPersonal, "panelPerfilPersonal");
					CardLayout cL = (CardLayout) panelCentral.getLayout();
					cL.show(panelCentral, "panelPerfilPersonal");
				} else {
					JPanel panelPerfil = new ProfileWindow(userLogged.getUsername(), user.getUsername(), mw)
							.getPanelPerfil();
					panelCentral.add(panelPerfil, "panelPerfil");
					CardLayout cL = (CardLayout) panelCentral.getLayout();
					cL.show(panelCentral, "panelPerfil");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				southUserLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				southUserLabel.setCursor(Cursor.getDefaultCursor());
			}
		});
		southUserLabel.setText(p.getUser());
		southUserLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_southUserLabel = new GridBagConstraints();
		gbc_southUserLabel.insets = new Insets(0, 0, 5, 5);
		gbc_southUserLabel.gridx = 3;
		gbc_southUserLabel.gridy = 15;
		publicationPanel.add(southUserLabel, gbc_southUserLabel);

		descriptionPhoto = new JLabel("");
		descriptionPhoto.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
		descriptionPhoto.setText(p.getDescription());
		GridBagConstraints gbc_descriptionPhoto = new GridBagConstraints();
		gbc_descriptionPhoto.anchor = GridBagConstraints.WEST;
		gbc_descriptionPhoto.gridwidth = 7;
		gbc_descriptionPhoto.insets = new Insets(0, 0, 5, 0);
		gbc_descriptionPhoto.gridx = 4;
		gbc_descriptionPhoto.gridy = 15;
		publicationPanel.add(descriptionPhoto, gbc_descriptionPhoto);

		viewComments = new JLabel("");
		int commentsSize = p.getComments().size();
		if (commentsSize == 1)
			viewComments.setText("Ver " + commentsSize + " comentario");
		else
			viewComments.setText("Ver " + commentsSize + " comentarios");
		viewComments.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CommentsWindow commentW = new CommentsWindow((Publication) p, userLogged, PublicationWindow.this);
				commentW.show();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				viewComments.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				viewComments.setCursor(Cursor.getDefaultCursor());
			}
		});
		viewComments.setForeground(Color.GRAY);
		if (commentsSize == 0)
			viewComments.setVisible(false);
		viewComments.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		GridBagConstraints gbc_viewComments = new GridBagConstraints();
		gbc_viewComments.gridwidth = 5;
		gbc_viewComments.insets = new Insets(0, 0, 5, 0);
		gbc_viewComments.gridx = 3;
		gbc_viewComments.gridy = 16;
		publicationPanel.add(viewComments, gbc_viewComments);

	}

}
