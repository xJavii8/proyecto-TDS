package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import umu.tds.controller.Controller;
import umu.tds.model.Photo;
import umu.tds.model.ProfilePhotoListRender;

public class PremiumMenuWindow {

	private JFrame frame;
	private String nickname;
	private boolean isPremium;
	private MainWindow mw;

	/**
	 * Create the application.
	 */
	public PremiumMenuWindow(String nickname, MainWindow mw) {
		this.nickname = nickname;
		this.isPremium = Controller.INSTANCE.getUser(nickname).isPremium();
		this.mw = mw;
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
		GridBagLayout gbl_panelNoPremium = new GridBagLayout();
		gbl_panelNoPremium.columnWidths = new int[] { 15, 236, 202, 15, 0 };
		gbl_panelNoPremium.rowHeights = new int[] { 15, 23, 0, 0 };
		gbl_panelNoPremium.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelNoPremium.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelNoPremium.setLayout(gbl_panelNoPremium);

		JLabel price = new JLabel("Precio: " + Controller.INSTANCE.getUser(nickname).getFinalPrice() + "€");
		price.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		price.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_price = new GridBagConstraints();
		gbc_price.gridwidth = 2;
		gbc_price.insets = new Insets(0, 0, 5, 5);
		gbc_price.gridx = 1;
		gbc_price.gridy = 1;
		panelNoPremium.add(price, gbc_price);

		JButton getPremium = new JButton("Adquirir premium");
		getPremium.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_getPremium = new GridBagConstraints();
		gbc_getPremium.gridwidth = 2;
		gbc_getPremium.insets = new Insets(0, 0, 0, 5);
		gbc_getPremium.anchor = GridBagConstraints.NORTH;
		gbc_getPremium.gridx = 1;
		gbc_getPremium.gridy = 2;
		panelNoPremium.add(getPremium, gbc_getPremium);

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
					if (Controller.INSTANCE.createExcel(nickname, fichero))
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
					if (Controller.INSTANCE.createPDF(nickname, fichero))
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
				DefaultListModel<Photo> top10 = Controller.INSTANCE.getTop10LikedPhotos(nickname);
				top10LikedPublications(mw, nickname, top10);
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
				Controller.INSTANCE.setPremium(nickname);
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

	public void top10LikedPublications(MainWindow mw, String selfUser, DefaultListModel<Photo> photos) {
		JList<Photo> publicationList = new JList<>(photos);
		publicationList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		publicationList.setVisibleRowCount(-1);
		publicationList.ensureIndexIsVisible(publicationList.getHeight());
		publicationList.setCellRenderer(new ProfilePhotoListRender());
		JScrollPane scrollPubPanel = new JScrollPane(publicationList);
		JFrame publicationListPanel = new JFrame();

		publicationList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					JPanel panelCentral = mw.getPanelCentral();
					JPanel panelPublication = new PublicationWindow(selfUser, publicationList.getSelectedValue(),
							publicationList.getSelectedValue().getUser(), mw).getPublicationPanel();
					panelCentral.add(panelPublication, "panelPublication");
					CardLayout cL = (CardLayout) panelCentral.getLayout();
					cL.show(panelCentral, "panelPublication");
					publicationListPanel.dispose();
					frame.dispose();
				}
			}
		});

		publicationList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				publicationList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				publicationList.setCursor(Cursor.getDefaultCursor());
			}
		});

		JPanel panelNorte = new JPanel();
		publicationListPanel.getContentPane().add(panelNorte, BorderLayout.NORTH);
		JLabel foundUsers = new JLabel("Top 10 publicaciones por likes");
		foundUsers.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		panelNorte.add(foundUsers);

		if (UIManager.getLookAndFeel().getName() == "FlatLaf Light") {
			publicationList.setBackground(Constantes.LIGHT_BARS);
		} else if (UIManager.getLookAndFeel().getName() == "FlatLaf Dark") {
			publicationList.setBackground(Constantes.DARK_BARS);
		}

		publicationListPanel
				.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/ig64.png")));
		publicationListPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		publicationListPanel.setSize(560, 560);
		publicationListPanel.setLocationRelativeTo(null);
		publicationListPanel.getContentPane().add(scrollPubPanel);
		publicationListPanel.setVisible(true);
	}

}
