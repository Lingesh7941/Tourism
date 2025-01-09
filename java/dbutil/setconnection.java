package dbutil;

import java.sql.Connection;
import java.sql.SQLException;

public class setconnection {
	public static void main(String[] args) {
		try {
			Connection con=Dbutil.getConnection();
			System.out.println(con);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
