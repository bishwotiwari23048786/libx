package com.Libx.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection 
{
	private final static String username = "root";
	private final static String password = "";
	private final static String url = "jdbc:mysql://localhost:3306/library_management";
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, username, password);

	}
}
