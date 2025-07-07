package umu.tds.model;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import umu.tds.view.Constantes;
import umu.tds.view.Utilities;

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
		ImageIcon pic = Utilities.getCircleIcon(user.getProfilePic());
		if (pic.getIconHeight() != Constantes.SELF_USER_PIC_SIZE
				|| pic.getIconWidth() != Constantes.SELF_USER_PIC_SIZE) {
			pic = new ImageIcon(pic.getImage().getScaledInstance(Constantes.SELF_USER_PIC_SIZE,
					Constantes.SELF_USER_PIC_SIZE, Image.SCALE_DEFAULT));
		}
		userLabel.setIcon(pic);

		if (isSelected) {
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
