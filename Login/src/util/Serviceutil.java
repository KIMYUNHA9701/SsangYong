package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Serviceutil {
	static Serviceutil serviceutil;
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static synchronized Serviceutil getInstance() {
		if(serviceutil==null) {
			serviceutil = new Serviceutil();
		}
		return serviceutil;
	}
	
	public Connection getconnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "C##MINIGAME", "MINIGAME");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return con;
	}
}
