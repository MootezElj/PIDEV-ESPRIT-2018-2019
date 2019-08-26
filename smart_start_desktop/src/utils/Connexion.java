package utils;
import java.sql.*;

public class Connexion {
	final static String url="jdbc:mysql://127.0.0.1:3306/smartStartJava?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	final static String name="root";
	final static String password="";
	static Connection con;
	static Connexion ins;
	public static Connection getCon() {
		return con;
	}
	private Connexion() {
		try {
			con=DriverManager.getConnection(url,name,password);
			System.out.println("connexion établie");
		}
		catch (SQLException ex)
		{
			System.out.println("erreur: "+ex.getMessage());
		}
	}
	public static Connexion getInstance()
	{
		if (ins==null)
		{
			ins=new Connexion();
		}
		return ins;
	}
}
