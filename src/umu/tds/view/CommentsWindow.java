package umu.tds.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import umu.tds.controller.Controller;
import umu.tds.model.Comment;
import umu.tds.model.CommentListRender;
import umu.tds.model.Publication;
import umu.tds.model.User;

public class CommentsWindow {

	private JFrame frame;
	private JTextField textoComentario;
	private User user;
	private Publication pub;
	private JList<Comment> commentsList;
	private Comment removeComment;
	private PublicationWindow publicationW;

	/**
	 * Create the application.
	 */
	public CommentsWindow(Publication pub, User user, PublicationWindow pubW) {
		this.pub = pub;
		this.user = user;
		this.publicationW = pubW;
		initialize();
	}

	public void show() {
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(CommentsWindow.class.getResource("/images/ig64.png")));
		frame.setSize(450, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 15, 15, 15, 0, 0, 15, 15, 15, 0 };
		gridBagLayout.rowHeights = new int[] { 15, 15, 0, 15, 15, 0, 15, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel lblComena = new JLabel("Comentarios");
		lblComena.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_lblComena = new GridBagConstraints();
		gbc_lblComena.gridwidth = 2;
		gbc_lblComena.insets = new Insets(0, 0, 5, 5);
		gbc_lblComena.gridx = 3;
		gbc_lblComena.gridy = 1;
		frame.getContentPane().add(lblComena, gbc_lblComena);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 2;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);

		DefaultListModel<Comment> comments = Controller.INSTANCE.getComments(pub.getTitle());
		commentsList = new JList<>(comments);

		scrollPane.setViewportView(commentsList);
		commentsList.setCellRenderer(new CommentListRender());

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 3;
		gbc_scrollPane_1.gridy = 5;
		frame.getContentPane().add(scrollPane_1, gbc_scrollPane_1);

		textoComentario = new JTextField();

		scrollPane_1.setViewportView(textoComentario);
		textoComentario.setColumns(10);

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnEnviar.getText().equals("Enviar")) {
					Controller.INSTANCE.addComment(pub, textoComentario.getText(), user.getUsername());
					DefaultListModel<Comment> comments = Controller.INSTANCE.getComments(pub.getTitle());
					commentsList.setModel(comments);
					textoComentario.setText(null);
					publicationW.updateCommentsNumber();
				} else if (btnEnviar.getText().equals("Borrar")) {
					DefaultListModel<Comment> commentBo = (DefaultListModel<Comment>) commentsList.getModel();
					if (user.getUsername().equals(removeComment.getAuthor())
							|| user.getUsername().equals(pub.getUser())) {
						Controller.INSTANCE.removeComment(pub, removeComment);
						removeComment = null;
						publicationW.updateCommentsNumber();
						int selectedIndex = commentsList.getSelectedIndex();
						if (selectedIndex != -1) {
							commentBo.removeElementAt(selectedIndex);
							frame.getContentPane().revalidate();
							frame.getContentPane().repaint();
							btnEnviar.setText("Enviar");
						}
					} else {
						JOptionPane.showMessageDialog(frame,
								"No tienes permiso para borrar el comentario " + "seleccionado", null,
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		commentsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				btnEnviar.setText("Borrar");
				if (!e.getValueIsAdjusting()) {
					removeComment = commentsList.getSelectedValue();
				}
				frame.repaint();
			}
		});

		textoComentario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnEnviar.setText("Enviar");
			}
		});

		GridBagConstraints gbc_btnEnviar = new GridBagConstraints();
		gbc_btnEnviar.insets = new Insets(0, 0, 5, 5);
		gbc_btnEnviar.gridx = 4;
		gbc_btnEnviar.gridy = 5;
		frame.getContentPane().add(btnEnviar, gbc_btnEnviar);
	}

}
