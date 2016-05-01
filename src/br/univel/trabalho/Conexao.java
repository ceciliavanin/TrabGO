package br.univel.trabalho;

import java.sql.Connection;
import java.sql.SQLException;

public class Conexao {
	private static final String URL = "http://192.168.0.107:8082/login.do?jsessionid=b001fd38f1de491b1881b939221f8ed3";
	private static final String USER = "sa";
	private static final String PASSWORD = "sa";
	
	public static Connection getConnection() {

		System.out.println("Erro.");
		return null;
	
	}
}