package br.univel.trabalho;

import br.univel.enums.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Main {

	public Main(Cliente c1, Cliente c2, Cliente c3) throws SQLException {
		deletaTabela();
        criaTabela();
        salva(c1);
        salva(c2);
        salva(c3);

        listaTodos();
        busca(1);

        c1.setNomeCliente("VACA AMARELA");
        c1.setEnd("CAGO NA PANELA");
        c1.setTelefone("171717171");
        c1.setEstadoCivil(EstadoCivil.CASADO);

        atualiza(c1);
        exclui(2);

        listaTodos();
    }

    public void criaTabela() throws SQLException {
        Montando ex = new Montando();
        Conexao c = new Conexao();
        Connection con = c.getConnection();
        PreparedStatement ps = con.prepareStatement(ex.getCreateTable(new Cliente()));
		
		int res = ps.executeUpdate();
        con.close();
    }

    public void deletaTabela() throws SQLException {
        Montando ex = new Montando();
        Conexao c = new Conexao();
        Connection con = c.getConnection();
        PreparedStatement ps = con.prepareStatement(ex.getDropTable(new Cliente()));
        int res = ps.executeUpdate();
    }

    public void salva(Cliente cl) {
        ImpDao dao = new ImpDao();
        dao.salvar(cl);
    }

    public void atualiza(Cliente cl) {
        ImpDao dao = new ImpDao();
        dao.atualizar(cl);
    }
    public void busca(Integer id) {
        ImpDao dao = new ImpDao();
        Cliente cl = dao.buscar(id);

        System.out.println("ID: \t\t\t" + cl.getId());
        System.out.println("NOME: \t\t\t" + cl.getNomeCliente());
        System.out.println("ENDERECO: \t\t" + cl.getEnd());
        System.out.println("TELEFONE: \t\t" + cl.getTelefone());
        System.out.println("ESTADO CIVIL: \t" + cl.getEstadocivil());
        System.out.println();
    }
    public void exclui(Integer id) {
        ImpDao dao = new ImpDao();
        dao.excluir(2);
    }

    

    public void listaTodos() {
    	ImpDao dao = new ImpDao();
        List<Cliente> list = dao.listarTodos();

        System.out.println("LISTAR TODOS\n");
        for (Cliente l: list) {
            System.out.println("ID: \t\t" + l.getId());
            System.out.println("NOME: \t\t" + l.getNomeCliente());
            System.out.println("ENDERECO: \t" + l.getEnd());
            System.out.println("TELEFONE: \t" + l.getTelefone());
            System.out.println("ESTADO CIVIL: \t" + l.getEstadocivil());
        }
    }

    public static void main(String[] args) {
        Cliente c1 = new Cliente(1, "MOHAMED CRISTHAN", "RUA Bomba de hiroshima.", "24543555523", EstadoCivil.CASADO);
        Cliente c2 = new Cliente(2, "ALI HALBASSARA", "RUA Israel rumo a Jerusalém.", "64564563456", EstadoCivil.SOLTEIRO);
        Cliente c3 = new Cliente(3, "JHOKASTA FLOMBEK", "RUA A volta dos que não foram.", "34523452345", EstadoCivil.VIUVO);
        
        try {
			new Main(c1, c2, c3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}