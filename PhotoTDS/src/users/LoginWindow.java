package users;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginWindow {

	private JFrame frmPhototdsLogin;
	private JPasswordField passwordField;
	private JTextField txtNombreDeUsuarioemail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frmPhototdsLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPhototdsLogin = new JFrame();
		frmPhototdsLogin.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginWindow.class.getResource("/images/ig64.png")));
		frmPhototdsLogin.setTitle("PhotoTDS - Login");
		frmPhototdsLogin.setBounds(100, 100, 450, 300);
		frmPhototdsLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelNorte = new JPanel();
		frmPhototdsLogin.getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panelNorte.add(panel);
		
		JLabel labelImage = new JLabel("");
		labelImage.setIcon(new ImageIcon(LoginWindow.class.getResource("/images/ig32.png")));
		labelImage.setVerticalAlignment(SwingConstants.BOTTOM);
		labelImage.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(labelImage);
		
		JLabel labelBienvenidoTDS = new JLabel("Bienvenido a PhotoTDS");
		labelBienvenidoTDS.setHorizontalTextPosition(SwingConstants.CENTER);
		labelBienvenidoTDS.setHorizontalAlignment(SwingConstants.RIGHT);
		labelBienvenidoTDS.setFont(new Font("Bahnschrift", Font.BOLD, 16));
		panel.add(labelBienvenidoTDS);
		
		JPanel panelSur = new JPanel();
		frmPhototdsLogin.getContentPane().add(panelSur, BorderLayout.SOUTH);
		
		JButton loginButton = new JButton("Iniciar sesi\u00F3n");
		panelSur.add(loginButton);
		
		JButton registerButton = new JButton("Registrar");
		/*registerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frmPhototdsLogin.
			}
		});*/
		panelSur.add(registerButton);
		
		JPanel panelCentral = new JPanel();
		frmPhototdsLogin.getContentPane().add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[]{0, 0, 0, 15, 0};
		gbl_panelCentral.rowHeights = new int[]{0, 0, 0};
		gbl_panelCentral.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelCentral.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panelCentral.setLayout(gbl_panelCentral);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panelCentral.add(lblNewLabel, gbc_lblNewLabel);
		
		txtNombreDeUsuarioemail = new JTextField();
		txtNombreDeUsuarioemail.setToolTipText("nombre de usuario/email");
		GridBagConstraints gbc_txtNombreDeUsuarioemail = new GridBagConstraints();
		gbc_txtNombreDeUsuarioemail.anchor = GridBagConstraints.SOUTH;
		gbc_txtNombreDeUsuarioemail.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombreDeUsuarioemail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombreDeUsuarioemail.gridx = 2;
		gbc_txtNombreDeUsuarioemail.gridy = 0;
		panelCentral.add(txtNombreDeUsuarioemail, gbc_txtNombreDeUsuarioemail);
		txtNombreDeUsuarioemail.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panelCentral.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.anchor = GridBagConstraints.NORTH;
		gbc_passwordField.insets = new Insets(0, 0, 0, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 1;
		panelCentral.add(passwordField, gbc_passwordField);
	}

}
