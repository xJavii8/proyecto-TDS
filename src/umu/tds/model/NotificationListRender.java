package umu.tds.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import umu.tds.view.Constantes;

@SuppressWarnings("serial")
public class NotificationListRender extends JPanel implements ListCellRenderer<Photo> {

	private JLabel photoLabel;
	private JLabel userLabel;
	private ImageIcon pic;

	public NotificationListRender() {
		photoLabel = new JLabel();
		userLabel = new JLabel();
		setLayout(new BorderLayout(5, 5));
		add(userLabel, BorderLayout.NORTH);
		add(photoLabel, BorderLayout.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Photo> list, Photo p, int index, boolean isSelected,
			boolean cellHasFocus) {
		userLabel.setIcon(pic);
		userLabel.setText(p.getUser());
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLabel.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		ImageIcon publicationIcon = new ImageIcon(new ImageIcon(p.getPath()).getImage().getScaledInstance(
				Constantes.PROFILE_PUBLICATION_PIC_SIZE, Constantes.PROFILE_PUBLICATION_PIC_SIZE, Image.SCALE_SMOOTH));
		photoLabel.setIcon(publicationIcon);
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

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
