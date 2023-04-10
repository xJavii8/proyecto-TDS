package umu.tds.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.Cursor;

import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import umu.tds.controller.Controller;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PremiumMenuWindow {

	private JFrame frame;
	private String nickname;
	private boolean isPremium;

	/**
	 * Create the application.
	 */
	public PremiumMenuWindow(String nickname) {
		this.nickname = nickname;
		this.isPremium = Controller.getInstancia().isPremium(nickname);
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
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(PremiumMenuWindow.class.getResource("/images/ig32.png")));
		frame.setSize(450, 263);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panelCentral = new JPanel();
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new CardLayout(0, 0));

		JPanel panelNoPremium = new JPanel();
		panelCentral.add(panelNoPremium, "panelNoPremium");

		JButton getPremium = new JButton("Adquirir premium");
		panelNoPremium.add(getPremium);

		JPanel panelPremium = new JPanel();
		panelCentral.add(panelPremium, "panelPremium");
		GridBagLayout gbl_panelPremium = new GridBagLayout();
		gbl_panelPremium.columnWidths = new int[] { 125, 187, 15, 0 };
		gbl_panelPremium.rowHeights = new int[] { 15, 23, 15, 0, 15, 0, 0 };
		gbl_panelPremium.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelPremium.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelPremium.setLayout(gbl_panelPremium);

		JButton excel = new JButton("Generar Excel con tus seguidores");
		excel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				excel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				excel.setCursor(Cursor.getDefaultCursor());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de Excel (*.xls)", "xls");
				chooser.setFileFilter(filtro);
				int resultado = chooser.showSaveDialog(null);
				if (resultado == JFileChooser.APPROVE_OPTION) {
					String fichero = chooser.getSelectedFile().getAbsolutePath();
					int extension = fichero.lastIndexOf('.');
					if (extension < 0) {
						fichero = fichero.concat(".xls");
					} else {
						fichero = fichero.substring(0, extension) + ".xls";
					}
					if (Controller.getInstancia().createExcel(nickname, fichero))
						JOptionPane.showMessageDialog(frame, "Archivo Excel generado", null,
								JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(frame, "Ha ocurrido un error al generar el archivo Excel", null,
								JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_excel = new GridBagConstraints();
		gbc_excel.fill = GridBagConstraints.HORIZONTAL;
		gbc_excel.anchor = GridBagConstraints.NORTH;
		gbc_excel.insets = new Insets(0, 0, 5, 5);
		gbc_excel.gridx = 1;
		gbc_excel.gridy = 1;
		panelPremium.add(excel, gbc_excel);

		JButton pdf = new JButton("Generar PDF con tus seguidores");
		pdf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo PDF (*.pdf)", "pdf");
				chooser.setFileFilter(filtro);
				int resultado = chooser.showSaveDialog(null);
				if (resultado == JFileChooser.APPROVE_OPTION) {
					String fichero = chooser.getSelectedFile().getAbsolutePath();
					int extension = fichero.lastIndexOf('.');
					if (extension < 0) {
						fichero = fichero.concat(".pdf");
					} else {
						fichero = fichero.substring(0, extension) + ".pdf";
					}
					if (Controller.getInstancia().createPDF(nickname, fichero))
						JOptionPane.showMessageDialog(frame, "Archivo PDF generado", null,
								JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(frame, "Ha ocurrido un error al generar el archivo PDF", null,
								JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				pdf.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				pdf.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_pdf = new GridBagConstraints();
		gbc_pdf.fill = GridBagConstraints.HORIZONTAL;
		gbc_pdf.insets = new Insets(0, 0, 5, 5);
		gbc_pdf.anchor = GridBagConstraints.NORTH;
		gbc_pdf.gridx = 1;
		gbc_pdf.gridy = 2;
		panelPremium.add(pdf, gbc_pdf);

		JButton top10 = new JButton("Ver 10 fotos con más \"me gusta\"");
		top10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				top10.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				top10.setCursor(Cursor.getDefaultCursor());
			}
		});
		GridBagConstraints gbc_top10 = new GridBagConstraints();
		gbc_top10.fill = GridBagConstraints.HORIZONTAL;
		gbc_top10.insets = new Insets(0, 0, 5, 5);
		gbc_top10.gridx = 1;
		gbc_top10.gridy = 3;
		panelPremium.add(top10, gbc_top10);

		if (isPremium) {
			CardLayout cL = (CardLayout) panelCentral.getLayout();
			cL.show(panelCentral, "panelPremium");
		} else {
			CardLayout cL = (CardLayout) panelCentral.getLayout();
			cL.show(panelCentral, "panelNoPremium");
		}

		JPanel panelNorte = new JPanel();
		frame.getContentPane().add(panelNorte, BorderLayout.NORTH);
		GridBagLayout gbl_panelNorte = new GridBagLayout();
		gbl_panelNorte.columnWidths = new int[] { 15, 236, 202, 15, 0 };
		gbl_panelNorte.rowHeights = new int[] { 15, 20, 0, 0, 0 };
		gbl_panelNorte.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelNorte.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelNorte.setLayout(gbl_panelNorte);

		JLabel premiumMenu = new JLabel("Menú premium");
		premiumMenu.setHorizontalAlignment(SwingConstants.CENTER);
		premiumMenu.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_premiumMenu = new GridBagConstraints();
		gbc_premiumMenu.gridwidth = 2;
		gbc_premiumMenu.insets = new Insets(0, 0, 5, 5);
		gbc_premiumMenu.fill = GridBagConstraints.HORIZONTAL;
		gbc_premiumMenu.gridx = 1;
		gbc_premiumMenu.gridy = 1;
		panelNorte.add(premiumMenu, gbc_premiumMenu);

		JLabel user = new JLabel("Usuario: " + nickname);
		user.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_user = new GridBagConstraints();
		gbc_user.insets = new Insets(0, 0, 5, 5);
		gbc_user.gridx = 1;
		gbc_user.gridy = 2;
		panelNorte.add(user, gbc_user);

		String premiumStr = "no premium";
		if (isPremium)
			premiumStr = "premium";

		JLabel status = new JLabel("Estado: " + premiumStr);
		status.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		GridBagConstraints gbc_status = new GridBagConstraints();
		gbc_status.insets = new Insets(0, 0, 5, 5);
		gbc_status.anchor = GridBagConstraints.NORTH;
		gbc_status.gridx = 2;
		gbc_status.gridy = 2;
		panelNorte.add(status, gbc_status);
		
		getPremium.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Controller.getInstancia().setPremium(nickname);
				JOptionPane.showMessageDialog(frame, "Has adquirido el estado premium", null,
						JOptionPane.INFORMATION_MESSAGE);
				status.setText("Estado: premium");
				CardLayout cL = (CardLayout) panelCentral.getLayout();
				cL.show(panelCentral, "panelPremium");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				getPremium.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				getPremium.setCursor(Cursor.getDefaultCursor());
			}
		});
	}

}
