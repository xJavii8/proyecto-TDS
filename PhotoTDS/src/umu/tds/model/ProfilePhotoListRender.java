package umu.tds.model;

import java.awt.Component;

import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import umu.tds.view.Constantes;

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
		Photo f = (Photo)p;
		ImageIcon publicationIcon = new ImageIcon(new ImageIcon(f.getPath()).getImage().getScaledInstance(
						Constantes.PROFILE_PUBLICATION_PIC_SIZE, Constantes.PROFILE_PUBLICATION_PIC_SIZE, Image.SCALE_SMOOTH));
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