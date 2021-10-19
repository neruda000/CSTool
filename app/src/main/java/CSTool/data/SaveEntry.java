package CSTool.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CSTool.App;
import CSTool.model.DBEventEntry;

public class SaveEntry {

	private static Connection conn;
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(SaveEntry.class);
	

	private final static String CREATION_STATEMENT = "CREATE TABLE IF NOT EXISTS entries ("
			+ "    id varchar(24),"
			+ "    type VARCHAR(32),"
			+ "    host VARCHAR(128),"
			+ "    duration INT,"
			+ "	   alert boolean,"
			+ "    primary key (id)"
			+ ");";
	
	private final static String INSERT_STATEMENT = "INSERT INTO ENTRIES (id, type, host, duration, alert) values (?, ?, ?, ?, ?)";
	
	private static void initializeConnection() throws SQLException {
		conn = DriverManager.getConnection("jdbc:hsqldb:file:entryDB", "SA", "");
		conn.createStatement().execute(CREATION_STATEMENT);
	}
	
	public static void save(DBEventEntry entry) throws SQLException {
		if(conn == null) {
			initializeConnection();
		}
		
		PreparedStatement ps = conn.prepareStatement(INSERT_STATEMENT);	
		ps.setString(1, entry.getId());
		ps.setString(2, entry.getType());
		ps.setString(3, entry.getHost());
		ps.setInt(4, entry.getDuration());
		ps.setBoolean(5, entry.isAlert());
		
		if (ps.executeUpdate() > 0) {
			LOGGER.info("SAVED: " + entry.getId() + " alert: " + entry.isAlert() + " duration: " + entry.getDuration());
			conn.commit();
		}else {
			LOGGER.error("Error saving " + entry.getId());
		}
		
		if(ps != null) ps.close();
		
	}

	public static void shutDown() {
		try {
			if (conn != null) conn.close();
		} catch (SQLException e) {
			LOGGER.error("Error closing DB connection");
		}
		
	}

}
