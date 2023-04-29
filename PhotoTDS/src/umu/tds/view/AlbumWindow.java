package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import umu.tds.controller.Controller;
import umu.tds.model.Album;
import umu.tds.model.Photo;
import umu.tds.model.ProfilePhotoListRender;

public class AlbumWindow {

	private JFrame frame;
	private Album album;
	private String user;
	private boolean liked;
	private MainWindow mw;
	private JPanel panelCentral;

	/**
	 * Create the application.
	 */
	public AlbumWindow(Album album, String userLogged, MainWindow mw) {
		this.album = album;
		this.user = userLogged;
		this.mw = mw;
		this.panelCentral = mw.getPanelCentral();
		this.liked = Controller.getInstancia().userLikedPub(userLogged, album);
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
		frame.setSize(560, 560);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		DefaultListModel<Photo> photos = new DefaultListModel<>();
		for (Photo p : album.getPhotos()) {
			photos.addElement(p);
		}
		JList<Photo> publicationList = new JList<>(photos);
		publicationList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		publicationList.setVisibleRowCount(-1);
		publicationList.ensureIndexIsVisible(publicationList.getHeight());
		publicationList.setCellRenderer(new ProfilePhotoListRender());
		JScrollPane scrollPubPanel = new JScrollPane(publicationList);

		publicationList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					JPanel panelPublication = new PublicationWindow(user, publicationList.getSelectedValue(),
							publicationList.getSelectedValue().getUser(), mw).getPublicationPanel();
					panelCentral.add(panelPublication, "panelPublication");
					CardLayout cL = (CardLayout) panelCentral.getLayout();
					cL.show(panelCentral, "panelPublication");
					frame.dispose();
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

		JPanel panelNorte = new JPanel(new BorderLayout(10, 10));
		panelNorte.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);
		JLabel selectPhotos = new JLabel("Álbum " + album.getTitle() + " - Likes: " + album.getLikes());
		selectPhotos.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		panelNorte.add(selectPhotos, BorderLayout.CENTER);
		selectPhotos.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel descrip = new JLabel(album.getDescription());
		descrip.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
		panelNorte.add(descrip, BorderLayout.SOUTH);
		descrip.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
		frame.getContentPane().add(panelSur, BorderLayout.SOUTH);
		JLabel likeButton = new JLabel("");
		if (liked) {
			likeButton.setIcon(new ImageIcon(PublicationWindow.class.getResource("/images/liked.png")));
		} else {
			likeButton.setIcon(new ImageIcon(PublicationWindow.class.getResource("/images/notLiked.png")));
		}
		likeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (liked) {
					Controller.getInstancia().dislike(user, album);
					likeButton.setIcon(new ImageIcon(PublicationWindow.class.getResource("/images/notLiked.png")));
					liked = false;
				} else {
					Controller.getInstancia().like(user, album);
					likeButton.setIcon(new ImageIcon(PublicationWindow.class.getResource("/images/liked.png")));
					liked = true;
				}
				selectPhotos.setText("Álbum " + album.getTitle() + " - Likes: " + album.getLikes());
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
		panelSur.add(likeButton);

		if (album.getUser().equals(user)) {
			JButton borrar = new JButton("Borrar");
			borrar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int option = JOptionPane.showConfirmDialog(frame,
							"¿Seguro que quieres borrar el álbum " + album.getTitle()
									+ "? Las fotos también se borrarán.",
							null, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if (option == JOptionPane.YES_OPTION) {
						if (Controller.getInstancia().deleteAlbum(album)) {
							JOptionPane.showMessageDialog(frame, "El álbum " + album.getTitle() + " ha sido borrado.",
									null, JOptionPane.INFORMATION_MESSAGE);
							List<String> deletedAlbums = Controller.getInstancia().deleteEmptyAlbums(user);
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
							SelfProfileWindow newSPW = new SelfProfileWindow(user, mw);
							mw.setSPW(newSPW);
							panelCentral.add(newSPW.getPanelPerfilPersonal(), "panelPerfilPersonal");
							CardLayout cL = (CardLayout) panelCentral.getLayout();
							cL.show(panelCentral, "panelPerfilPersonal");
							frame.dispose();
						} else {
							JOptionPane.showMessageDialog(frame,
									"Ha ocurrido un error al borrar el álbum " + album.getTitle() + ".", null,
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(frame, "No se ha borrado el álbum " + album.getTitle() + ".",
								null, JOptionPane.INFORMATION_MESSAGE);
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
			panelSur.add(borrar);
		}

		if (UIManager.getLookAndFeel().getName() == "FlatLaf Light") {
			publicationList.setBackground(Constantes.LIGHT_BARS);
		} else if (UIManager.getLookAndFeel().getName() == "FlatLaf Dark") {
			publicationList.setBackground(Constantes.DARK_BARS);
		}

		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/ig64.png")));
		frame.getContentPane().add(scrollPubPanel);
	}

}
