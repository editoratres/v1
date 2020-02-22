package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the contrato_brindes database table.
 * 
 */
@Entity
@Table(name="contrato_brindes")
@NamedQuery(name="ContratoBrinde.findAll", query="SELECT c FROM ContratoBrinde c")
public class ContratoBrinde implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne()
	@JoinColumn(name="brindeBean", referencedColumnName="codigo")
	private Brinde brindBean;

	private Double quantidade;
	private double valor;

	//bi-directional many-to-one association to Contrato
	@ManyToOne
	@JoinColumn(name="codigocontrato")
	private Contrato contrato;

	public ContratoBrinde() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	 
	public Brinde getBrindBean() {
		return brindBean;
	}

	public void setBrindBean(Brinde brindBean) {
		this.brindBean = brindBean;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ContratoBrinde other = (ContratoBrinde) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Contrato getContrato() {
		return this.contrato;
	}

	
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
	public ContratoProduto getContratoProdutoBean() {
		return contratoProdutoBean;
	}

	public void setContratoProdutoBean(ContratoProduto contratoProdutoBean) {
		this.contratoProdutoBean = contratoProdutoBean;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	@ManyToOne()
	@JoinColumn(name="contratoProdutoBean", referencedColumnName="id")
	private ContratoProduto contratoProdutoBean;

}