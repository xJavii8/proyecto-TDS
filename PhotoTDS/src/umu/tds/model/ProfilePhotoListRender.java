package umu.tds.model;

import java.awt.Color;
import java.awt.Component;

import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import umu.tds.view.Constantes;
import umu.tds.view.Utilities;

@SuppressWarnings("serial")
public class ProfilePhotoListRender extends JPanel implements ListCellRenderer<Publication> {

	private JLabel photoLabel;

	public ProfilePhotoListRender() {
		photoLabel = new JLabel();
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		add(photoLabel);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Publication> list, Publication p, int index, boolean isSelected,
			boolean cellHasFocus) {
		ImageIcon publicationIcon = new ImageIcon();
		if(p instanceof Photo) {
			Photo f = (Photo) p;
			 publicationIcon = new ImageIcon(new ImageIcon(f.getPath()).getImage().getScaledInstance(
						Constantes.PROFILE_PUBLICATION_PIC_SIZE, Constantes.PROFILE_PUBLICATION_PIC_SIZE, Image.SCALE_SMOOTH));
		} else if(p instanceof Album) {
			Album a = (Album) p;
			List<File> fotos = new LinkedList<>();
			for(Photo ph : a.getPhotos()) {
				fotos.add(new File(ph.getPath()));
			}
			publicationIcon = Utilities.getIconAlbum(fotos);
		}
		photoLabel.setIcon(publicationIcon);

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