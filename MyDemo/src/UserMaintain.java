import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import org.apache.log4j.BasicConfigurator;

import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;

public class UserMaintain extends JFrame {

	// commit added by web.
	private JPanel contentPane;
	private JTable table;
	private JCheckBox chkHideInactive;
	private Connection Cnn = null;
	private JLabel lblTableSum;
	private int rCount = 0;
	private JScrollPane scrollPane;
	private JButton btnAddUser;
	private JButton btnChange;
	private JButton btnDelete;
	private MaskFormatter mf;
	private JTextField txtName;
	private JPasswordField txtPassword;
	private JTextField txtDepartment;
	private JTextField txtRgtType;
	private JTextField txtGroup;
	private JTextField txtDB;
	private JTextField txtSchRgt;
	private JLabel lblNewLabel_3;
	private JTextField txtRemark;
	private JTextField txtID;
	private JComboBox cboStatus;
	
	
    
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMaintain frame = new UserMaintain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void FieldsReset(){
		txtID.setText("");
		txtName.setText("");
		txtPassword.setText("");
		txtDepartment.setText("");
		txtRgtType.setText("");
		txtGroup.setText("");
		txtDB.setText("");
		cboStatus.setSelectedItem("");
		cboStatus.setSelectedIndex(0);
		txtSchRgt.setText("");
		txtRemark.setText("");
	}
	private boolean userExist(String id){
		try{
			String query = "select cID from sysdb.sysuser where cID='"+id+"'";
			PreparedStatement pst = Cnn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			boolean result = rs.next();
			rs.close();
			pst.clearBatch();
			return result;
		} catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	private void refreshTable(){

		try {
			String query = "select cID, cName, cDepartment, cDB, cSchRgt,";
			query+=" cStatus from sysdb.sysuser";
			if (chkHideInactive.isSelected()) {
				query = query + " where cstatus='A'";
			}
			PreparedStatement pst = Cnn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			table.setRowHeight(20);
			table.setModel(DbUtils.resultSetToTableModel(rs));

			//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(50);
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
			table.getColumnModel().getColumn(4).setPreferredWidth(50);
			table.getColumnModel().getColumn(5).setPreferredWidth(50);
			
			rCount = table.getRowCount();
			lblTableSum.setText("Total:"+Integer.toString(rCount));
			
			pst.close();
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	private void tableToField(){
		try {
			int row = table.getSelectedRow();
			String id = table.getModel().getValueAt(row, 0).toString();
			String query = "select cID, cName, cDepartment, cRgtType, cGroup, cDB, cStatus, cSchRgt, cRmk from sysdb.sysuser where cID='"+id+"'";
			PreparedStatement pst = Cnn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				txtID.setText(rs.getString("cID"));
				txtName.setText(rs.getString("cName"));
				txtPassword.setText("");
				txtDepartment.setText(rs.getString("cDepartment"));
				txtRgtType.setText(rs.getString("cRgtType"));
				txtGroup.setText(rs.getString("cGroup"));
				txtDB.setText(rs.getString("cDB"));
				//txtStatus.setText(rs.getString("cStatus"));
				cboStatus.setSelectedItem(rs.getString("cStatus"));
				txtSchRgt.setText(rs.getString("cSchRgt"));
			}
			rs.close();
			pst.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}

	public UserMaintain() {
		setTitle("User Maintainance");
		setIconImage(new ImageIcon(getClass().getResource("/sblogo24x24.png")).getImage());
		Cnn = MySQLConnector.dbConnector("sb");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1024, 532);
		setLocationRelativeTo(null);  // Set form to center of screen
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 721, 450);
		contentPane.add(scrollPane);
		
		table = new JTable() {
			public boolean isCellEditable(int row, int column) {
	        // This is how we disable editing:
	        return false;
			}
	    };
	    
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
					tableToField();
				}
			}
		});
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tableToField();
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnLoadUser = new JButton("Load User");
		btnLoadUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		});
		btnLoadUser.setBounds(741, 39, 109, 23);
		contentPane.add(btnLoadUser);
		
		chkHideInactive = new JCheckBox("Hide inactive user");
		chkHideInactive.setSelected(true);
		chkHideInactive.setBounds(741, 10, 257, 23);
		contentPane.add(chkHideInactive);
		
		lblTableSum = new JLabel("");
		lblTableSum.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTableSum.setBounds(564, 469, 167, 15);
		contentPane.add(lblTableSum);
		
		btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!userExist(txtID.getText())) {
						String query = "insert into sysdb.sysuser (cID, cName, cPassword, cDepartment, ";
						query += "cRgtType, cGroup, cStatus, cDB, cSchRgt, cRmk, cAddBy, dAddDate, cModBy, dModDate) ";
						query += "values (?,?,MD5(?),?,?,?,?,?,?,?,?,NOW(),?,NOW())";
						PreparedStatement pst = Cnn.prepareStatement(query);
						pst.setString(1, txtID.getText());
						pst.setString(2, txtName.getText());
						pst.setString(3, txtPassword.getText());
						pst.setString(4, txtDepartment.getText());
						pst.setString(5, txtRgtType.getText());
						pst.setString(6, txtGroup.getText());
						//pst.setString(7, cboStatus.getText());
						pst.setString(7, cboStatus.getSelectedItem().toString());
						pst.setString(8, txtDB.getText());
						pst.setString(9, txtSchRgt.getText());
						pst.setString(10, txtRemark.getText());
						if(Login.gUserName==null){
							pst.setString(11, "");
							pst.setString(12, "");
						} else {
							pst.setString(11, Login.gUserName);
							pst.setString(12, Login.gUserName);
						}
						
						pst.execute();
						JOptionPane.showMessageDialog(null, "User added");
						refreshTable();
						pst.close();
					} else {
						JOptionPane.showMessageDialog(null, "User already exist");
					}
				} catch (SQLException e1){
					e1.printStackTrace();
				}
				
			}
		});
		
		btnAddUser.setBounds(889, 399, 109, 23);
		contentPane.add(btnAddUser);
		
		btnChange = new JButton("Change");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (userExist(txtID.getText())){
					try{
						String query = "update sysdb.sysuser set cName='"+txtName.getText()+"'";
						if (txtPassword.getText()!="") query += ",cPassword=MD5('"+txtPassword.getText()+"')";
						query += ",cDepartment='" + txtDepartment.getText()+"'";
						query += ",cRgtType='" + txtRgtType.getText()+"'";
						query += ",cGroup='" + txtGroup.getText()+"'";
						query += ",cStatus='"+cboStatus.getSelectedItem().toString()+"'";
						query += ",cDB='"+txtDB.getText()+"'";
						query += ",cSchRgt='"+txtSchRgt.getText()+"'";
						query += ",crmk='"+txtRemark.getText()+"'";
						query += "where cID='"+txtID.getText()+"'";
						PreparedStatement pst = Cnn.prepareStatement(query);
						pst.execute();
						JOptionPane.showMessageDialog(null, "User updated");
						refreshTable();
						pst.close();
					} catch (SQLException e2){
						e2.printStackTrace();
					}
				}
			}
		});

		btnChange.setBounds(889, 421, 109, 23);
		contentPane.add(btnChange);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(null, "Do you really want to delete?","DELETE USER",JOptionPane.YES_NO_OPTION);
				if(answer==0){
					if(userExist(txtID.getText())) {
						try {
							String query = "delete from sysdb.sysuser where cid='"+txtID.getText()+"'";
							PreparedStatement pst = Cnn.prepareStatement(query);
							pst.execute();
							JOptionPane.showMessageDialog(null, "User deleted");
							refreshTable();
							pst.close();
						} catch (SQLException e1){
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "User not exist");
					}
				}
			}
		});
		btnDelete.setBounds(889, 442, 109, 23);
		contentPane.add(btnDelete);
		
		JLabel lblId = new JLabel("ID");
		//lblId.setFont(new Font("þý°ç´°þýŽéþý", Font.BOLD, 12));
		lblId.setForeground(Color.RED);
		lblId.setBounds(745, 154, 46, 15);
		contentPane.add(lblId);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(745, 176, 46, 15);
		contentPane.add(lblName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(745, 197, 76, 15);
		contentPane.add(lblPassword);
		
		txtName = new JTextField();
		txtName.setBounds(831, 173, 167, 21);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(831, 194, 167, 21);
		contentPane.add(txtPassword);
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setBounds(745, 222, 78, 15);
		contentPane.add(lblDepartment);
		
		txtDepartment = new JTextField();
		txtDepartment.setBounds(831, 219, 167, 21);
		contentPane.add(txtDepartment);
		txtDepartment.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Type");
		lblNewLabel.setBounds(745, 247, 76, 15);
		contentPane.add(lblNewLabel);
		
		txtRgtType = new JTextField();
		txtRgtType.setText("U");
		txtRgtType.setBounds(831, 244, 167, 21);
		contentPane.add(txtRgtType);
		txtRgtType.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Group");
		lblNewLabel_1.setBounds(745, 272, 78, 15);
		contentPane.add(lblNewLabel_1);
		
		txtGroup = new JTextField();
		txtGroup.setBounds(831, 269, 167, 21);
		contentPane.add(txtGroup);
		txtGroup.setColumns(10);
		
		JLabel lblDb = new JLabel("DB");
		lblDb.setBounds(745, 297, 78, 15);
		contentPane.add(lblDb);
		
		txtDB = new JTextField();
		txtDB.setToolTipText("Use (;) to separate database");
		txtDB.setBounds(831, 294, 167, 21);
		contentPane.add(txtDB);
		txtDB.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Status");
		lblNewLabel_2.setBounds(745, 322, 78, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblSchoolRight = new JLabel("School Right");
		lblSchoolRight.setBounds(745, 347, 78, 15);
		contentPane.add(lblSchoolRight);
		
		txtSchRgt = new JTextField();
		txtSchRgt.setBounds(831, 344, 167, 21);
		contentPane.add(txtSchRgt);
		txtSchRgt.setColumns(10);
		
		lblNewLabel_3 = new JLabel("Remarks");
		lblNewLabel_3.setBounds(745, 372, 78, 15);
		contentPane.add(lblNewLabel_3);
		
		txtRemark = new JTextField();
		txtRemark.setBounds(831, 369, 167, 21);
		contentPane.add(txtRemark);
		txtRemark.setColumns(10);
		
		txtID = new JTextField();
		txtID.setToolTipText("Add, Change, Delete buttons are according to this ID field.");
		txtID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				txtID.setText(txtID.getText().replaceAll("\\s+", ""));
				txtID.setText(txtID.getText().toUpperCase());
				
			}
		});
		txtID.setBounds(831, 151, 167, 21);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FieldsReset();
			}
		});
		btnReset.setBounds(911, 118, 87, 23);
		contentPane.add(btnReset);
		
		cboStatus = new JComboBox();
		cboStatus.setBounds(831, 319, 167, 21);
		contentPane.add(cboStatus);
		
		JButton btnReport = new JButton("Print Report");
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//JasperPrint jprint = JasperFillManager.fillReport("Blank_A4.jasper",new HashMap());
					//JasperPrint jprint = JasperFillManager.fillReport("Report/Blank_A4.jasper", null); 
					 //JasperReport jasperReport = JasperCompileManager.compileReport("Blank_A4.jrxml");
					 //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,new HashMap(), new JREmptyDataSource());
					BasicConfigurator.configure();
					 String reportPath = "C:\\Download\\Blank_A4.jrxml";
					 JasperReport jr = JasperCompileManager.compileReport(reportPath);
					 JasperPrint jp = JasperFillManager.fillReport(jr,null,Cnn);
					 JasperViewer.viewReport(jp);
					 //JasperExportManager.exportReportToPdfFile(jasperPrint, "sample.pdf");
//					JasperViewer jasperViewer = new JasperViewer(jprint);
//		            jasperViewer.setVisible(true);
				} catch (JRException ex){
					ex.printStackTrace();
				}
				
			}
		});
		btnReport.setBounds(741, 72, 109, 23);
		contentPane.add(btnReport);
		
		try{
			String query = "select ckey2 from sysdb.syscode where ckey1='VALIDSTATUS' and cCOID='sb'";
			PreparedStatement pst = Cnn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				cboStatus.addItem(rs.getString("ckey2"));
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
}
