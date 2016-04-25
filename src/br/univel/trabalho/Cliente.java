package br.univel.trabalho;

import br.univel.enums.EstadoCivil;
import br.univel.Cliente;
import br.univel.annotations.Coluna;
import br.univel.annotations.Tabela;

@Tabela ("Cadastro_Cliente")
public class Cliente {
	@Coluna(pk=true)
	private int Cadid;

	@Coluna(nome="CadNome", tamanho=100)
	private String nomeCliente;
	
	@Coluna(nome="CadEnd", tamanho=80)
	private String end;
	
	@Coluna(nome="CadTelefone", tamanho=11)
	private String telefone;
	
	@Coluna(nome="CadEstadoCivil")
	private Estado_Civil estadocivil;
	
	public Estado_Civil getEstadocivil() {
		return estadocivil;
	}

	public void setEstadocivil(Estado_Civil estadocivil) {
		this.estadocivil = estadocivil;
	}

	public int getId() {
		return Cadid;
	}

	public void setId(int id) {
		this.Cadid = id;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Cliente() {
		this(0, null, null, null, null);
	}

	public Cliente(int id, String nome, String endereco, String telefone, Estado_Civil estCivil) {
		super();
		this.Cadid = id;
		this.nomeCliente = nome;
		this.end = endereco;
		this.telefone = telefone;
		this.estadocivil = estCivil;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Cadid;
		result = prime * result + ((nomeCliente == null) ? 0 : nomeCliente.hashCode());
		result = prime * result + ((end == null ) ? 0 : end.hashCode());
		result = prime * result + ((telefone == null ) ? 0 : telefone.hashCode());
		result = prime * result + ((estadocivil == null ) ? 0 : estadocivil.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Cliente other = (Cliente) obj;
		
		if (Cadid != other.Cadid)
			return false;
		
		if (nomeCliente == null) {
			if (other.nomeCliente != null)
				return false;
		} else if (!nomeCliente.equals(other.nomeCliente))
			return false;
		
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		
		if (estadocivil == null) {
			if (other.estadocivil != null)
				return false;
		} else if (!estadocivil.equals(other.estadocivil))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + Cadid + ", nome=" + nomeCliente +", end=" + end + ", telefone="+ telefone + ", estadocivil" + estadocivil +"]";
	}

}



}