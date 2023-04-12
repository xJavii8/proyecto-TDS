package umu.tds.model;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import umu.tds.view.MainWindow;

@SuppressWarnings("serial")
public class UserListRender extends JPanel implements ListCellRenderer<User> {

	private JLabel userLabel;

	public UserListRender() {
		userLabel = new JLabel();
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		add(userLabel);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends User> list, User user, int index, boolean isSelected,
			boolean cellHasFocus) {
		userLabel.setText(user.getUsername());
		userLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		BufferedImage rawPic = null;
		try {
			String URI = user.getProfilePic();
			if (URI.contains("%")) {
				try {
					URI = URLDecoder.decode(URI, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			rawPic = ImageIO.read(new File(URI));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (rawPic != null) {
			ImageIcon pic = new ImageIcon(MainWindow.getCircularImage(rawPic));
			if (pic.getIconHeight() != 32 || pic.getIconWidth() != 32) {
				pic = new ImageIcon(pic.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT));
			}
			userLabel.setIcon(pic);
		}
		
		if(isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getSelectionForeground());
		}
		
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		
		return this;
	};

}
