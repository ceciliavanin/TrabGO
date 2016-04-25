package br.univel.trabalho;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.univel.enums.EstadoCivil;
import br.univel.annotations.*;

public class Montando extends SQLGen {
		
		public Montando () {
			String strCreateTable = getCreateTable(Cliente.class);
			System.out.println(strCreateTable);
			
			String strDropTable = getDropTable(Cliente.class);
			System.out.println(strDropTable);
			
			
			Cliente cliente = new Cliente(1, "Cecilia", "Rua Souza Naves", "45888888888", EstadoCivil.Solteiro);
			Connection con = null;

			try {

				con = new Conex();

				PreparedStatement ps = getSqlInsert(con, cliente);
				ps.executeUpdate();
				ps = getSqlSelectAll(con, cliente);
				ps = getSqlSelectById(con, cliente);

				ps.close();
				con.close();

			} catch (SQLException e) {
				e.printStackTrace();

			}
			
			
		}


}
