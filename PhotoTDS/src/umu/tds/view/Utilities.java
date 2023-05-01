package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import umu.tds.controller.Controller;
import umu.tds.model.Photo;
import umu.tds.model.PhotoListRender;
import umu.tds.model.ProfilePhotoListRender;
import umu.tds.model.Publication;
import umu.tds.model.PublicationListRender;
import umu.tds.model.User;
import umu.tds.model.UserListRender;

public class Utilities {
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

	public static ImageIcon getCircleIcon(String picPath) {
		BufferedImage rawPic = null;
		try {
			if (picPath.contains("%")) {
				try {
					picPath = URLDecoder.decode(picPath, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			rawPic = ImageIO.read(new File(picPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ImageIcon(getCircularImage(rawPic));
	}

	public static int fortalezaContraseña(JPasswordField contraseña) {
		int fortaleza = 0;
		char[] password = contraseña.getPassword();

		String passwordString = new String(password);

		if (passwordString.length() >= 8 && passwordString.length() <= 10) {
			fortaleza += 20;
		} else if (passwordString.length() > 10) {
			fortaleza += 40;
		}

		// Agregamos puntos por complejidad
		boolean tieneMinsucula = false;
		boolean tieneMayucula = false;
		boolean tieneDigitos = false;
		boolean tieneCaracterEspecial = false;

		for (char c : password) {
			if (Character.isLowerCase(c)) {
				tieneMinsucula = true;
			} else if (Character.isUpperCase(c)) {
				tieneMayucula = true;
			} else if (Character.isDigit(c)) {
				tieneDigitos = true;
			} else {
				tieneCaracterEspecial = true;
			}
		}

		int complejidad = 0;

		if (tieneMinsucula) {
			complejidad++;
		}

		if (tieneMayucula) {
			complejidad++;
		}

		if (tieneDigitos) {
			complejidad++;
		}

		if (tieneCaracterEspecial) {
			complejidad++;
		}

		fortaleza += complejidad * 15;

		return fortaleza;
	}

	public static void listaUsuarios(MainWindow mw, String user, DefaultListModel<User> users) {
		JList<User> userList = new JList<>(users);
		userList.setCellRenderer(new UserListRender());
		JScrollPane scrollUserPanel = new JScrollPane(userList);
		JFrame matchingUsersPanel = new JFrame();

		userList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					JPanel panelCentral = mw.getPanelCentral();
					JPanel panelPerfil = new ProfileWindow(user, userList.getSelectedValue().getUsername(), mw)
							.getPanelPerfil();
					panelCentral.add(panelPerfil, "panelPerfil");
					CardLayout cL = (CardLayout) panelCentral.getLayout();
					cL.show(panelCentral, "panelPerfil");
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
			userList.setBackground(Constantes.LIGHT_BARS);
		} else if (UIManager.getLookAndFeel().getName() == "FlatLaf Dark") {
			userList.setBackground(Constantes.DARK_BARS);
		}

		matchingUsersPanel
				.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/ig64.png")));
		matchingUsersPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		matchingUsersPanel.setSize(300, 300);
		matchingUsersPanel.setLocationRelativeTo(null);
		matchingUsersPanel.getContentPane().add(scrollUserPanel);
		matchingUsersPanel.setVisible(true);
	}

	public static void listaPublicaciones(MainWindow mw, String selfUser, DefaultListModel<Publication> publications) {
		JList<Publication> publicationList = new JList<>(publications);
		publicationList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		publicationList.setVisibleRowCount(-1);
		publicationList.ensureIndexIsVisible(publicationList.getHeight());
		publicationList.setCellRenderer(new PublicationListRender());
		JScrollPane scrollPubPanel = new JScrollPane(publicationList);
		JFrame publicationListPanel = new JFrame();

		publicationList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					JPanel panelCentral = mw.getPanelCentral();
					JPanel panelPublication = new PublicationWindow(selfUser, publicationList.getSelectedValue(),
							publicationList.getSelectedValue().getUser(), mw).getPublicationPanel();
					panelCentral.add(panelPublication, "panelPublication");
					CardLayout cL = (CardLayout) panelCentral.getLayout();
					cL.show(panelCentral, "panelPublication");
					publicationListPanel.dispose();
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

		JPanel panelNorte = new JPanel();
		publicationListPanel.getContentPane().add(panelNorte, BorderLayout.NORTH);
		JLabel foundUsers = new JLabel("Publicaciones encontradas: " + publications.size());
		foundUsers.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		panelNorte.add(foundUsers);

		if (UIManager.getLookAndFeel().getName() == "FlatLaf Light") {
			publicationList.setBackground(Constantes.LIGHT_BARS);
		} else if (UIManager.getLookAndFeel().getName() == "FlatLaf Dark") {
			publicationList.setBackground(Constantes.DARK_BARS);
		}

		publicationListPanel
				.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/ig64.png")));
		publicationListPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		publicationListPanel.setSize(560, 560);
		publicationListPanel.setLocationRelativeTo(null);
		publicationListPanel.getContentPane().add(scrollPubPanel);
		publicationListPanel.setVisible(true);
	}
	
	public static String guardarImagenRelativa(String origen) {
		if (origen.contains("%")) {
			try {
				origen = URLDecoder.decode(origen, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		Path rutaOrigen = Paths.get(origen);
	    String nombreArchivo = rutaOrigen.getFileName().toString();
	    Path rutaDestino = Paths.get("src/umu/tds/photos/" + nombreArchivo);

	    if (!Files.exists(rutaDestino)) {
		    try {
		        Files.copy(rutaOrigen, rutaDestino);
		    } catch (Exception e) {
		        System.err.println("Error al copiar el archivo: " + e.getMessage());
		    }
	    }
	    
	    return rutaDestino.toString();
	}

	public static void top10LikedPublications(MainWindow mw, String selfUser, DefaultListModel<Photo> photos,
			PremiumMenuWindow pmw) {
		JList<Photo> publicationList = new JList<>(photos);
		publicationList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		publicationList.setVisibleRowCount(-1);
		publicationList.ensureIndexIsVisible(publicationList.getHeight());
		publicationList.setCellRenderer(new ProfilePhotoListRender());
		JScrollPane scrollPubPanel = new JScrollPane(publicationList);
		JFrame publicationListPanel = new JFrame();

		publicationList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					JPanel panelCentral = mw.getPanelCentral();
					JPanel panelPublication = new PublicationWindow(selfUser, publicationList.getSelectedValue(),
							publicationList.getSelectedValue().getUser(), mw).getPublicationPanel();
					panelCentral.add(panelPublication, "panelPublication");
					CardLayout cL = (CardLayout) panelCentral.getLayout();
					cL.show(panelCentral, "panelPublication");
					publicationListPanel.dispose();
					pmw.dispose();
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

		JPanel panelNorte = new JPanel();
		publicationListPanel.getContentPane().add(panelNorte, BorderLayout.NORTH);
		JLabel foundUsers = new JLabel("Top 10 publicaciones por likes");
		foundUsers.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		panelNorte.add(foundUsers);

		if (UIManager.getLookAndFeel().getName() == "FlatLaf Light") {
			publicationList.setBackground(Constantes.LIGHT_BARS);
		} else if (UIManager.getLookAndFeel().getName() == "FlatLaf Dark") {
			publicationList.setBackground(Constantes.DARK_BARS);
		}

		publicationListPanel
				.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/ig64.png")));
		publicationListPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		publicationListPanel.setSize(560, 560);
		publicationListPanel.setLocationRelativeTo(null);
		publicationListPanel.getContentPane().add(scrollPubPanel);
		publicationListPanel.setVisible(true);
	}

	public static void selectPhotosForAlbum(DefaultListModel<Photo> photos, AddAlbumWindow aaw) {
		JList<Photo> publicationList = new JList<>(photos);
		List<Publication> photosList = new LinkedList<>();
		publicationList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		publicationList.setVisibleRowCount(-1);
		publicationList.ensureIndexIsVisible(publicationList.getHeight());
		publicationList.setCellRenderer(new ProfilePhotoListRender());
		JScrollPane scrollPubPanel = new JScrollPane(publicationList);
		JFrame publicationListFrame = new JFrame();

		publicationList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if (photosList.size() == Constantes.ALBUM_MAX_NUM_PHOTOS) {
						JOptionPane.showMessageDialog(publicationListFrame,
								"No puedes poner más de 16 fotos en un álbum", null, JOptionPane.ERROR_MESSAGE);
					} else {
						int selectedIndex = publicationList.getSelectedIndex();
						if (selectedIndex != -1) {
							photosList.add(publicationList.getSelectedValue());
							photos.removeElementAt(selectedIndex);
							publicationListFrame.getContentPane().revalidate();
							publicationListFrame.getContentPane().repaint();
						}
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

		JPanel panelNorte = new JPanel();
		publicationListFrame.getContentPane().add(panelNorte, BorderLayout.NORTH);
		JLabel selectPhotos = new JLabel("Seleccionar fotos para el álbum");
		selectPhotos.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		panelNorte.add(selectPhotos);

		if (UIManager.getLookAndFeel().getName() == "FlatLaf Light") {
			publicationList.setBackground(Constantes.LIGHT_BARS);
		} else if (UIManager.getLookAndFeel().getName() == "FlatLaf Dark") {
			publicationList.setBackground(Constantes.DARK_BARS);
		}

		JPanel panelSur = new JPanel();
		publicationListFrame.getContentPane().add(panelSur, BorderLayout.SOUTH);
		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				aaw.setPublicacionesAlbum(photosList);
				publicationListFrame.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				aceptarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				aceptarButton.setCursor(Cursor.getDefaultCursor());
			}
		});
		panelSur.add(aceptarButton);

		publicationListFrame
				.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/ig64.png")));
		publicationListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		publicationListFrame.setSize(560, 560);
		publicationListFrame.setLocationRelativeTo(null);
		publicationListFrame.getContentPane().add(scrollPubPanel);
		publicationListFrame.setVisible(true);
	}

	public static ImageIcon getIconAlbum(List<File> fotos) {
		BufferedImage imagenGrande = new BufferedImage(Constantes.PROFILE_PUBLICATION_PIC_SIZE,
				Constantes.PROFILE_PUBLICATION_PIC_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = imagenGrande.createGraphics();

		// Agregamos cada imagen pequeña a la imagen grande
		int x = 0;
		int y = 0;
		for(int i = 0; i < Math.min(fotos.size(), Constantes.ALBUM_MINI_PHOTOS); i++) {
			try {
				BufferedImage imagenPequena = ImageIO.read(fotos.get(i));
				g2d.drawImage(imagenPequena, x, y, 32, 32, null);
				x += 32;
				if (x >= 96) {
					x = 0;
					y += 32;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		g2d.dispose();

		// Creamos el ImageIcon a partir de la imagen grande y lo devolvemos
		return new ImageIcon(imagenGrande);
	}

	public static Date stringToDate(String fechaString) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = null;
		try {
			fecha = formato.parse(fechaString);
		} catch (ParseException e) {
			System.err.println("Error al convertir la fecha: " + e.getMessage());
		}
		return fecha;
	}
	
	public static Date stringToDateHours(String fechaString) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date fecha = null;
		try {
			fecha = formato.parse(fechaString);
		} catch (ParseException e) {
			System.err.println("Error al convertir la fecha: " + e.getMessage());
		}
		return fecha;
	}

	public static String dateToString(Date fechaDate) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		String fechaString = formato.format(fechaDate);
		return fechaString;
	}

}
