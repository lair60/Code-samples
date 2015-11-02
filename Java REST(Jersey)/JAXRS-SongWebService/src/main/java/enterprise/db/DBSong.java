package enterprise.db;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.configDB.ConfigDB;
import com.mysql.jdbc.Connection;

public class DBSong {
   public static final String IP_RDS = "52.28.246.243:3306";
   public static final String DB_NAME = "/db_first";
   public static final String URL_RDS = "dbinstance.cdoi5yaxt5b2.eu-central-1.rds.amazonaws.com:3306";
	
   // JDBC driver name and database URL
   private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   private static final String DB_IP_RDS = "jdbc:mysql://" + IP_RDS + DB_NAME;
   private static final String DB_URL_RDS = "jdbc:mysql://" + URL_RDS + DB_NAME;

   //  Database credentials
   private static final String USER = ConfigDB.USER;
   private static final String PASS = ConfigDB.PASS;
   
   private static Connection conn;
   private static DBSong db = null;
   
   private Statement statement;
   
   private DBSong() {
       try {
           Class.forName(JDBC_DRIVER).newInstance();
           conn = (Connection)DriverManager.getConnection(DB_IP_RDS,USER,PASS);
       }
       catch (Exception sqle) {
           sqle.printStackTrace();
       }
   }
   /**
   *
   * @return MysqlConnect Database connection object
   */
   public static DBSong getDbCon() {
      try {
		if ( db == null || !conn.isValid(5) ) {
		      db = new DBSong();
		  }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      return db;
  }
   /**
   *
   * @param query String The query to be executed
   * @return a ResultSet object containing the results or null if not available
   * @throws SQLException
   */
  public synchronized ResultSet query(String query) throws SQLException{
      statement = db.conn.createStatement();
      ResultSet res = statement.executeQuery(query);
      return res;
  }
  /**
   * @desc Method to insert data to a table
   * @param insertQuery String The Insert query
   * @return boolean
   * @throws SQLException
   */
  public synchronized int insert(String insertQuery) throws SQLException {
      statement = db.conn.createStatement();
      int result = statement.executeUpdate(insertQuery);
      return result;
  }
  
  public synchronized int delete(String deleteQuery) throws SQLException {
      statement = db.conn.createStatement();
      int result = statement.executeUpdate(deleteQuery);
      return result;
  }
  
}
