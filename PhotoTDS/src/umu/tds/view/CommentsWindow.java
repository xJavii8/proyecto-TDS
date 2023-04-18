package umu.tds.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JTextPane;

import umu.tds.controller.Controller;
import umu.tds.model.Comment;
import umu.tds.model.CommentListRender;
import umu.tds.model.PhotoListRender;
import umu.tds.model.Publication;
import umu.tds.model.User;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class CommentsWindow {

	private JFrame frame;
	private JTextField textoComentario;
	private User user;
	private Publication pub;
	private JList<Comment> commentsList;

	/**
	 * Create the application.
	 */
	public CommentsWindow(Publication pub, User user) {
		this.pub = pub;
		this.user = user;
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
		gridBagLayout.columnWidths = new int[]{15, 15, 15, 0, 0, 15, 15, 15, 0};
		gridBagLayout.rowHeights = new int[]{15, 15, 0, 15, 15, 0, 15, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		DefaultListModel<Comment> comments = Controller.getInstancia().getComments(pub.getTitle());
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
				Controller.getInstancia().addComment(pub, textoComentario.getText(), user.getUsername());
				DefaultListModel<Comment> comments = Controller.getInstancia().getComments(pub.getTitle());
				commentsList.setModel(comments);
			}
		});
		GridBagConstraints gbc_btnEnviar = new GridBagConstraints();
		gbc_btnEnviar.insets = new Insets(0, 0, 5, 5);
		gbc_btnEnviar.gridx = 4;
		gbc_btnEnviar.gridy = 5;
		frame.getContentPane().add(btnEnviar, gbc_btnEnviar);
	}

}
