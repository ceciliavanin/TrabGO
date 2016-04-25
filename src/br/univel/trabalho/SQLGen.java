package br.univel.trabalho;

import java.sql.Connection;
import java.sql.PreparedStatement;

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

		}

