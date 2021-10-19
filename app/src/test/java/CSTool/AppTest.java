package CSTool;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class AppTest {

	
	@Test
	public void someDataExists() throws SQLException {
		
		Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:entryDB", "SA", "");
		assertTrue(conn != null);
		
		String select_stm = "SELECT * from entries";
		ResultSet rs = conn.createStatement().executeQuery(select_stm);
		assertTrue(rs.next());
		conn.close();
	}
	
	@Test
	public void dbExists() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:entryDB", "SA", "");
		assertTrue(conn != null);
		conn.close();
	}
}
