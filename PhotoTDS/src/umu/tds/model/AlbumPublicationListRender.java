package umu.tds.model;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import umu.tds.view.Constantes;

@SuppressWarnings("serial")
public class AlbumPublicationListRender extends JPanel implements ListCellRenderer<Publication> {

	private JLabel albumLabel;
	private JLabel nameLabel;

	public AlbumPublicationListRender() {
		albumLabel = new JLabel();
		nameLabel = new JLabel();
		setLayout(new BorderLayout(5, 5));
		add(nameLabel, BorderLayout.NORTH);
		add(albumLabel, BorderLayout.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Publication> list, Publication p, int index,
			boolean isSelected, boolean cellHasFocus) {
		String title = p.getTitle();
		if (title.length() > Constantes.MAX_TITLE_ALBUM_LIST) {
			title = title.substring(0, Constantes.MAX_TITLE_ALBUM_LIST - 3) + "...";
		}
		nameLabel.setText(title);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(new Font("Bahnschrift", Font.BOLD, 14));

		Album a = (Album) p;
		albumLabel.setIcon(new ImageIcon(a.getIconAlbumPath()));

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
