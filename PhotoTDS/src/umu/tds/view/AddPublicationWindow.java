package umu.tds.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class AddPublicationWindow {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public AddPublicationWindow() {
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		
		JButton btnNewButton = new JButton("New button");
		panel.add(btnNewButton);
	}

}
