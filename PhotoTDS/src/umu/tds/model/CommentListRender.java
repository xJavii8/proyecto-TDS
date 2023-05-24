package umu.tds.model;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import umu.tds.controller.Controller;
import umu.tds.view.Constantes;
import umu.tds.view.Utilities;

@SuppressWarnings("serial")
public class CommentListRender extends JPanel implements ListCellRenderer<Comment> {

	private JLabel commentLabel;

	public CommentListRender() {
		commentLabel = new JLabel();
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		add(commentLabel);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Comment> list, Comment value, int index,
			boolean isSelected, boolean cellHasFocus) {

		User usuario = Controller.INSTANCE.getUser(value.getAuthor());
		ImageIcon pic = Utilities.getCircleIcon(usuario.getProfilePic());
		if (pic.getIconHeight() != Constantes.SELF_USER_PIC_SIZE
				|| pic.getIconWidth() != Constantes.SELF_USER_PIC_SIZE) {
			pic = new ImageIcon(pic.getImage().getScaledInstance(Constantes.SELF_USER_PIC_SIZE,
					Constantes.SELF_USER_PIC_SIZE, Image.SCALE_DEFAULT));
		}
		commentLabel.setIcon(pic);
		commentLabel.setText(value.getAuthor() + " - " + value.getText());

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
