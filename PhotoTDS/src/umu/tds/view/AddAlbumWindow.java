package umu.tds.view;

import javax.swing.JFrame;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import umu.tds.controller.Controller;
import umu.tds.model.Photo;
import umu.tds.model.Publication;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Toolkit;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddAlbumWindow {

	private JFrame frame;
	private JTextField tituloField;

	private String user;
	private List<Publication> publicacionesAlbum;
	private JLabel selectedPhotos;
	private JList<Publication> publicationList;

	/**
	 * Create the application.
	 */
	public AddAlbumWindow(String user, SelfProfileWindow spw) {
		this.user = user;
		this.publicacionesAlbum = null;
		this.publicationList = spw.getPublicationList();
		initialize();
	}

	public void show() {
		frame.setVisible(true);
	}

	public void setPublicacionesAlbum(List<Publication> publicacionesAlbum) {
		if (publicacionesAlbum.size() != 0) {
			this.publicacionesAlbum = publicacionesAlbum;
			selectedPhotos.setText("Seleccionadas " + publicacionesAlbum.size() + " fotos");
			selectedPhotos.setVisible(true);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(AddAlbumWindow.class.getResource("/images/ig32.png")));
		frame.setSize(450, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelNorte = new JPanel();
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);

		JLabel newAlbum = new JLabel("Nuevo álbum");
		newAlbum.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		panelNorte.add(newAlbum);

		JPanel panelCentral = new JPanel();
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[] { 15, 0, 0, 0, 15, 0 };
		gbl_panelCentral.rowHeights = new int[] { 15, 0, 0, 0, 15, 0, 15, 0 };
		gbl_panelCentral.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelCentral.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelCentral.setLayout(gbl_panelCentral);

		JLabel lblTítulo = new JLabel("Título: ");
		GridBagConstraints gbc_lblTítulo = new GridBagConstraints();
		gbc_lblTítulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTítulo.anchor = GridBagConstraints.EAST;
		gbc_lblTítulo.gridx = 1;
		gbc_lblTítulo.gridy = 1;
		panelCentral.add(lblTítulo, gbc_lblTítulo);

		tituloField = new JTextField();
		tituloField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (tituloField.getText().length() > Constantes.MAX_TITLE_PUB_LENGTH) {
					tituloField.setText(tituloField.getText().substring(0, Constantes.MAX_TITLE_PUB_LENGTH));
				}
			}
		});

		GridBagConstraints gbc_tituloField = new GridBagConstraints();
		gbc_tituloField.fill = GridBagConstraints.BOTH;
		gbc_tituloField.gridwidth = 2;
		gbc_tituloField.insets = new Insets(0, 0, 5, 5);
		gbc_tituloField.gridx = 2;
		gbc_tituloField.gridy = 1;
		panelCentral.add(tituloField, gbc_tituloField);
		tituloField.setColumns(10);

		JLabel lblDescripcion = new JLabel("Descripción: ");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 2;
		panelCentral.add(lblDescripcion, gbc_lblDescripcion);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 2;
		panelCentral.add(scrollPane, gbc_scrollPane);

		JTextArea descripArea = new JTextArea();
		descripArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (descripArea.getText().length() > Constantes.MAX_DESCRIP_PUB_LENGTH) {
					descripArea.setText(descripArea.getText().substring(0, Constantes.MAX_DESCRIP_PUB_LENGTH));
				}

				int numHashtags = 0;
				Matcher matcher = Constantes.HASHTAG_PAT.matcher(descripArea.getText());
				while (matcher.find() && numHashtags < Constantes.MAX_HASH_LENGTH) {
					if (numHashtags == 4) {
						JOptionPane.showMessageDialog(frame, "Sólo puedes poner 4 hashtags por foto", null,
								JOptionPane.ERROR_MESSAGE);
						descripArea.setText(descripArea.getText().substring(0, matcher.start()));
					} else {
						numHashtags++;
						int length = matcher.group(2).length();
						if (length > 15) {
							JOptionPane.showMessageDialog(frame,
									"El hashtag " + matcher.group(1) + " es inválido. Máximo 15 letras por hashtag",
									null, JOptionPane.ERROR_MESSAGE);
							descripArea.setText(descripArea.getText().substring(0, matcher.start() + 16));
						}
					}
				}
			}
		});
		scrollPane.setViewportView(descripArea);

		JLabel lblFotoElegida = new JLabel("Seleccionar fotos:");
		GridBagConstraints gbc_lblFotoElegida = new GridBagConstraints();
		gbc_lblFotoElegida.insets = new Insets(0, 0, 5, 5);
		gbc_lblFotoElegida.gridx = 1;
		gbc_lblFotoElegida.gridy = 3;
		panelCentral.add(lblFotoElegida, gbc_lblFotoElegida);

		JButton btnSubir = new JButton("Subir");
		btnSubir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String titulo = tituloField.getText();

				if (titulo.isEmpty())
					JOptionPane.showMessageDialog(frame, "El título no puede estar vacío", null,
							JOptionPane.ERROR_MESSAGE);
				else if (publicacionesAlbum == null)
					JOptionPane.showMessageDialog(frame, "Debes seleccionar fotos", null, JOptionPane.ERROR_MESSAGE);
				else {
					boolean albumExist = Controller.getInstancia().albumExist(user, titulo);

					if (albumExist) {
						JOptionPane.showMessageDialog(frame, "Ya existe un álbum con ese nombre", null,
								JOptionPane.ERROR_MESSAGE);
					} else {
						Controller.getInstancia().createAlbum(user, titulo, descripArea.getText(), publicacionesAlbum);
						JOptionPane.showMessageDialog(frame, "Álbum creado", null, JOptionPane.INFORMATION_MESSAGE);
						DefaultListModel<Publication> albumList = Controller.getInstancia().getAlbumsProfile(user);
						publicationList.setModel(albumList);
						frame.dispose();
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnSubir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnSubir.setCursor(Cursor.getDefaultCursor());
			}
		});

		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				List<Publication> allP = Collections.list(Controller.getInstancia().getPhotosProfile(user).elements());
				DefaultListModel<Photo> allPhotos = new DefaultListModel<>();
				for (Publication p : allP) {
					if (p instanceof Photo) {
						allPhotos.addElement((Photo) p);
					}
				}
				Utilities.selectPhotosForAlbum(allPhotos, AddAlbumWindow.this);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnSeleccionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnSeleccionar.setCursor(Cursor.getDefaultCursor());
			}
		});

		selectedPhotos = new JLabel("");
		if (publicacionesAlbum == null) {
			selectedPhotos.setVisible(false);
		}
		GridBagConstraints gbc_selectedPhotos = new GridBagConstraints();
		gbc_selectedPhotos.insets = new Insets(0, 0, 5, 5);
		gbc_selectedPhotos.gridx = 2;
		gbc_selectedPhotos.gridy = 3;
		panelCentral.add(selectedPhotos, gbc_selectedPhotos);
		GridBagConstraints gbc_btnSeleccionar = new GridBagConstraints();
		gbc_btnSeleccionar.insets = new Insets(0, 0, 5, 5);
		gbc_btnSeleccionar.gridx = 3;
		gbc_btnSeleccionar.gridy = 3;
		panelCentral.add(btnSeleccionar, gbc_btnSeleccionar);
		GridBagConstraints gbc_btnSubir = new GridBagConstraints();
		gbc_btnSubir.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubir.gridx = 2;
		gbc_btnSubir.gridy = 5;
		panelCentral.add(btnSubir, gbc_btnSubir);
	}

}
