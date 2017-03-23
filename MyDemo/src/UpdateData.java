import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class UpdateData extends JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateData frame = new UpdateData();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection Cnn = null;
	/**
	 * Create the frame.
	 */
	public UpdateData() {
		Cnn = MySQLConnector.dbConnector("sb");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(51, 55, 46, 15);
		contentPane.add(lblId);
		
		JLabel lblText = new JLabel("Text");
		lblText.setBounds(51, 93, 46, 15);
		contentPane.add(lblText);
		
		txtID = new JTextField();
		txtID.setBounds(112, 52, 96, 21);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		txtText = new JTextField();
		txtText.setBounds(112, 90, 96, 21);
		contentPane.add(txtText);
		txtText.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "insert into test.gntest (nTrxNo, ctext) values (?,hex(convert(? using big5)))";
					PreparedStatement pst = Cnn.prepareStatement(query);
					pst.setString(1,txtID.getText());
					pst.setString(2, txtText.getText());
					pst.execute();
					pst.close();
					
					JOptionPane.showMessageDialog(null, "Data updated");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(112, 164, 87, 23);
		contentPane.add(btnAdd);
	}
}
