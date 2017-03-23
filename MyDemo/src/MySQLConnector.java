import java.sql.*;
import javax.swing.*;

public class MySQLConnector {
	Connection Cnn = null;
	public static Connection dbConnector(String cDB){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.201:3306/"+cDB,"AppUser","sbcm");
			// JOptionPane.showMessageDialog(null, "Connection Successfull");
			return conn;
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
