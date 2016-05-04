package br.univel.trabalho;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SQLGen {

		// DDL

		protected abstract String getCreateTable(Class<Cliente> cl);

		protected abstract String getDropTable(Class<Cliente> cl);

		// DML

		protected abstract PreparedStatement getSqlInsert(Connection con, Object obj);

		protected abstract PreparedStatement getSqlSelectAll(Connection con, Object obj);

		protected abstract PreparedStatement getSqlSelectById(Connection con, Object obj);

		protected abstract PreparedStatement getSqlUpdateById(Connection con, Object obj);

		protected abstract PreparedStatement getSqlDeleteById(Connection con, Object obj);

		protected String getCreateTable(Object obj) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		protected String getDropTable(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}

		protected PreparedStatement getSqlSelectById(Connection con, Object obj, int id) {
			// TODO Auto-generated method stub
			return null;
		}

		protected PreparedStatement getSqlUpdateById(Connection con, Object obj, int id) {
			// TODO Auto-generated method stub
			return null;
		}

		protected PreparedStatement getSqlDeleteById(Connection con, Object obj, int id) {
			// TODO Auto-generated method stub
			return null;
		}

		}

