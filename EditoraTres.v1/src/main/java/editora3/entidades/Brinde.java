package editora3.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the brinde database table.
 * 
 */
@Entity
@Table(name="brinde")
@NamedQuery(name="Brinde.findAll", query="SELECT b FROM Brinde b")
public class Brinde implements Serializable {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Brinde other = (Brinde) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer codigo;

	private Integer codigoassociado;

	private String descricao;

	private Integer quantidade=0;

	private double valor;
	
	private Boolean status;

	@OneToMany(mappedBy="brindeBean", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	private List<BrindeEstoqueEquipe> brindeEstoqueEquipe=new ArrayList<BrindeEstoqueEquipe>();
	
	public Brinde() {
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigoassociado() {
		return this.codigoassociado;
	}

	public void setCodigoassociado(Integer codigoassociado) {
		this.codigoassociado = codigoassociado;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<BrindeEstoqueEquipe> getBrindeEstoqueEquipe() {
		return brindeEstoqueEquipe;
	}

	public void setBrindeEstoqueEquipe(List<BrindeEstoqueEquipe> brindeEstoqueEquipe) {
		this.brindeEstoqueEquipe = brindeEstoqueEquipe;
	}

}