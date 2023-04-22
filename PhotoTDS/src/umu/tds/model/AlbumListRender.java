package umu.tds.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import umu.tds.controller.Controller;
import umu.tds.view.Constantes;
import umu.tds.view.Utilities;

@SuppressWarnings("serial")
public class AlbumListRender extends JPanel implements ListCellRenderer<Album> {

	private JLabel albumLabel;
	private JLabel nameLabel;

	public AlbumListRender() {
		albumLabel = new JLabel();
		nameLabel = new JLabel();
		setLayout(new BorderLayout(5, 5));
		add(nameLabel, BorderLayout.NORTH);
		add(albumLabel, BorderLayout.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Album> list, Album a, int index, boolean isSelected,
			boolean cellHasFocus) {
		nameLabel.setText(a.getTitle());
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		/*ImageIcon publicationIcon = new ImageIcon(new ImageIcon(a.get).getImage().getScaledInstance(
				Constantes.PROFILE_PUBLICATION_PIC_SIZE, Constantes.PROFILE_PUBLICATION_PIC_SIZE, Image.SCALE_SMOOTH));
		albumLabel.setIcon(publicationIcon;*/
		// setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

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
