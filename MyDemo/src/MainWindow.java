import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class MainWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("Sailing Boat Group Integrated System");
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setIconImage(new ImageIcon(getClass().getResource("/sblogo24x24.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 146);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnSchool = new JButton("School");
		btnSchool.setBounds(328, 63, 124, 35);
		btnSchool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SchoolInfo frmSchool = new SchoolInfo();
				frmSchool.setVisible(true);
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnSchool);
		
		JButton btnUserMaintainance = new JButton("User Maintainance");
		btnUserMaintainance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserMaintain frmUser = new UserMaintain();
				frmUser.setVisible(true);
			}
		});
		btnUserMaintainance.setBounds(453, 63, 119, 35);
		contentPane.add(btnUserMaintainance);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 634, 21);
		contentPane.add(menuBar);
		
		JMenu mnSystemMaintainance = new JMenu("System Maintainance");
		menuBar.add(mnSystemMaintainance);
		
		JMenuItem mntmSchoolit = new JMenuItem("School");
		mnSystemMaintainance.add(mntmSchoolit);
		
		JMenuItem mntmUser = new JMenuItem("User");
		mntmUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserMaintain frmUser = new UserMaintain();
				frmUser.setVisible(true);
			}
		});
		mnSystemMaintainance.add(mntmUser);
		
		JSeparator separator = new JSeparator();
		mnSystemMaintainance.add(separator);
		
		JMenuItem mnExit = new JMenuItem("Exit");
		mnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		mnSystemMaintainance.add(mnExit);
	}
}
