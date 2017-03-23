import java.awt.EventQueue;
import java.sql.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Login {

	private JFrame frmSailingBoatGroup;
	public static String gUserName;
	public static String gDB;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmSailingBoatGroup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection=null;
	private JTextField txtUserName;
	private JPasswordField txtPassword;
	private JLabel label;
	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		connection = MySQLConnector.dbConnector("sb");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSailingBoatGroup = new JFrame();
		
		frmSailingBoatGroup.setTitle("Sailing Boat Group Integrated System");
		frmSailingBoatGroup.setIconImage(new ImageIcon(getClass().getResource("/sblogo24x24.png")).getImage());
		frmSailingBoatGroup.setBounds(100, 100, 441, 261);
		frmSailingBoatGroup.setLocationRelativeTo(null);
		frmSailingBoatGroup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSailingBoatGroup.getContentPane().setLayout(null);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblUserName.setBounds(152, 74, 65, 15);
		frmSailingBoatGroup.getContentPane().add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblPassword.setBounds(152, 119, 65, 15);
		frmSailingBoatGroup.getContentPane().add(lblPassword);
		
		txtUserName = new JTextField();
		txtUserName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				txtUserName.setText(txtUserName.getText().replaceAll("\\s+", ""));
				txtUserName.setText(txtUserName.getText().toUpperCase());
			}
		});
		txtUserName.setBounds(227, 71, 176, 21);
		frmSailingBoatGroup.getContentPane().add(txtUserName);
		txtUserName.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(227, 116, 176, 21);
		frmSailingBoatGroup.getContentPane().add(txtPassword);
		
		JButton btnLogin = new JButton("Login");
		//Image okimg = new ImageIcon(this.getClass().getResource("/ok.png")).getImage();
		//btnLogin.setIcon(new ImageIcon(okimg));
		btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				try {
					String query="select cName, cDB from sysdb.sysuser where cid=? and cpassword=md5(?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, txtUserName.getText().toUpperCase());
					pst.setString(2, txtPassword.getText());
					ResultSet rs = pst.executeQuery();
					int count=0;
					String UserName="";
					while(rs.next()){
						count=count+1;
						gUserName=rs.getString("cName");
						gDB=rs.getString("cDB");
					}
					if (count==1){
						// JOptionPane.showMessageDialog(null, "Welcome "+UserName);
						frmSailingBoatGroup.dispose();
						MainWindow mainWindow = new MainWindow();
						mainWindow.setVisible(true);
					} else if(count>1) {
						JOptionPane.showMessageDialog(null, "Duplicate username and password");
					} else {
						JOptionPane.showMessageDialog(null, "Username and password is not correct. Try again...");
					}
					rs.close();
					pst.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnLogin.setBounds(292, 166, 120, 36);
		frmSailingBoatGroup.getContentPane().add(btnLogin);
		
		label = new JLabel("");
		Image logoimg = new ImageIcon(this.getClass().getResource("/sblogo.png")).getImage();
		label.setIcon(new ImageIcon(logoimg));
		label.setBounds(15, 50, 120, 120);
		frmSailingBoatGroup.getContentPane().add(label);
	}
}
