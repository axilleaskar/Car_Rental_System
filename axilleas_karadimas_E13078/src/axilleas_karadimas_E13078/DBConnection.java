package axilleas_karadimas_E13078;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public Connection conn;

    public DBConnection() {
        
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "mydatabase";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";
        
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection) DriverManager.getConnection(url + dbName, userName, password);
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
    
    public  void closeConn() throws SQLException{
        this.conn.close();
    }
}