package umu.tds.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

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

	public static ImageIcon genIconSelfProfileLabel(String picPath) {
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

		ImageIcon pic = new ImageIcon(getCircularImage(rawPic));
		if (pic.getIconHeight() != 32 || pic.getIconWidth() != 32) {
			pic = new ImageIcon(pic.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT));
		}
		return pic;
	}

	public static ImageIcon genSelfProfilePic(String picPath) {
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

		ImageIcon pic = new ImageIcon(getCircularImage(rawPic));
		if (pic.getIconHeight() != 128 || pic.getIconWidth() != 128) {
			pic = new ImageIcon(pic.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
		}
		return pic;
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
}
