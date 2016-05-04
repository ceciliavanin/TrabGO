package br.univel.trabalho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {
	private static final String URL = "jdbc:h2:./test";
	private static final String USER = "sa";
	private static final String PASSWORD = "sa";
	private static final String DRIVER_CLASS = "org.h2.Driver";

	public static ResultSet rs;
	public java.sql.Statement stm;

	public static Connection getConnection() {

		try {
			Class.forName(DRIVER_CLASS);
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String[] args) {
		new Conexao();
	}

}