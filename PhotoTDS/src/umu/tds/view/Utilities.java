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
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
					JPanel panelPerfil = new ProfileWindow(user,
							userList.getSelectedValue().getUsername(), mw).getPanelPerfil();
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
}
