import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
public class DBManager {
    public Connection conn;
    DBManager() throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url="jdbc:mysql://localhost/spacegame";
    try {
        conn = DriverManager.getConnection(url,"root", "root");
        System.out.println("Database connection successful!");
    } catch (SQLException e) {
        System.err.println("Database connection failed: " + e.getMessage());
        e.printStackTrace();
        throw e; 
    }    }
}