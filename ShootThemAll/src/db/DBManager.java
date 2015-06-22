package db;

import java.sql.Connection;

public class DBManager {
	private static DBManager dbinstances;

	private static final String URL = "";
	private static final String USERNAME = "";
	private static final String PASSWORD = "";

	Connection c;

	private DBManager() {
		
		connectToDB();
	}

	private void connectToDB() {
		// connect

	}

	public Connection getConnection() {
		if (c == null) {

		}
		return c;
	}

	public static DBManager getDBManager() {
		if (dbinstances == null) {
			return new DBManager();
		}
		return dbinstances;

	}

}
