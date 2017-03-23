import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;

public class SchoolInfo extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Connection Cnn = null;
	int rCount = 0;
	JLabel lblRCount = new JLabel("");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SchoolInfo frame = new SchoolInfo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void refreshTable(){
		try {
			String query = "select cSchoolID as '學校編號', convert(unhex(cShortName) using big5) as '學校名稱' ";
					query = query + "from school where cOurClient='Y' and cCompanyID='SB'";
			PreparedStatement pst = Cnn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			rCount=0;
			while(rs.next()){
				rCount++;
			}
			lblRCount.setText("搜尋結果"+Integer.toString(rCount)+" 間");
			rs.beforeFirst();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * Create the frame.
	 */

	public SchoolInfo() {
		setTitle("School Information");
		Cnn = MySQLConnector.dbConnector("sb");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 988, 710);
		contentPane.add(tabbedPane);
		
		JPanel pnlBrowse = new JPanel();
		tabbedPane.addTab("Browse", null, pnlBrowse, null);
		tabbedPane.setEnabledAt(0, true);
		pnlBrowse.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 62, 963, 609);
		pnlBrowse.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(886, 30, 87, 23);
		pnlBrowse.add(btnSearch);
		lblRCount.setBounds(806, 551, 167, 15);
		pnlBrowse.add(lblRCount);
		
		
		lblRCount.setHorizontalAlignment(SwingConstants.RIGHT);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		});
		
		JPanel pnlMaintain = new JPanel();
		tabbedPane.addTab("Maintainance", null, pnlMaintain, null);
	}
}
