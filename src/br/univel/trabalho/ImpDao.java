package br.univel.trabalho;

import java.util.List;

import br.univel.enums.EstadoCivil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpDao implements Dao<Cliente, Integer> {
	private PreparedStatement ps;
    private ResultSet resultado;
    private Montando ex = new Montando();
    private Connection con = ex.Conexao();

    private List<Cliente> lista;
	@Override
	
	public void salvar(Cliente c) {
         try {
        	ps = ex.getSqlInsert(con, c);
			ps.executeUpdate();
			ps.close();
	       con.close();
	       System.out.println("Cliente cadastrado com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
         	

	}

	@Override
	public Cliente buscar(Integer i) {
		try {
	           Cliente c = new Cliente();
	           ps = ex.getSqlSelectById(con, c, i);
	           resultado = ps.executeQuery();
	           resultado.next();

	           EstadoCivil estcivil = EstadoCivil.values()[resultado.getInt("ESTADOCIVIL")];
	           c = new Cliente(resultado.getInt("id"), resultado.getString("nome"),
	           		resultado.getString("endereco"), resultado.getString("telefone"), estcivil);

	           ps.close();
	           resultado.close();
	           con.close();
	           return c;

	       } catch (SQLException e) {
	           e.printStackTrace();
	       return null;
	   }
	
	}	
	@Override
    public void atualizar(Cliente c) {
        try {
            ps = ex.getSqlUpdateById(con, c, c.getId());
            ps.executeUpdate();
            ps.close();
            con.close();
            System.out.println("Cliente atualizado!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

	}
	@Override
    public void excluir(Integer i) {
        try {
            Cliente c = new Cliente();
            ps = ex.getSqlDeleteById(con, c, i);
            ps.executeUpdate();
            ps.close();
            con.close();
            System.out.println("Cliente excluido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	@Override
    public List<Cliente> listarTodos() {
	       try {
	           Cliente cl = new Cliente();
	           lista = new ArrayList<Cliente>();
	           ps = ex.getSqlSelectAll(con, cl);
	           resultado = ps.executeQuery();

	           while (resultado.next()) {
	               EstadoCivil estcivil = EstadoCivil.values()[resultado.getInt("CADESTADOCIVIL")];
	               
	               lista.add(new Cliente(resultado.getInt("id"), resultado.getString("nome"),
	                       resultado.getString("endereco"), resultado.getString("telefone"), estcivil));
	           }

	           ps.close();
	           resultado.close();

	           if (lista != null) {
	               return lista;
	           }

	       } catch (SQLException e) {
	           e.printStackTrace();
	       }

	       return null;
	  
	}
}