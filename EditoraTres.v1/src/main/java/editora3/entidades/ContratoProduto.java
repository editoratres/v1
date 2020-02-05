package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the contrato_produto database table.
 * 
 */
@Entity
@Table(name="contrato_produto")
@NamedQuery(name="ContratoProduto.findAll", query="SELECT c FROM ContratoProduto c")
public class ContratoProduto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne()
	@JoinColumn(name="produtoBean", referencedColumnName="codigo")
	private Produto produtoBean;

	private String edicao;
	@ManyToOne()
	@JoinColumn(name="ofertaBean", referencedColumnName="codigo")
	private Oferta ofertaBean;
	
	private Integer parcelas;

	private Integer quantidade;

	private double valorparcela;

	private double valortotal;

	//bi-directional many-to-one association to Contrato
	@ManyToOne
	@JoinColumn(name="codigocontrato")
	private Contrato contrato;

	public ContratoProduto() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
 

	public String getEdicao() {
		return this.edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	 

	public Produto getProdutoBean() {
		return produtoBean;
	}

	public void setProdutoBean(Produto produtoBean) {
		this.produtoBean = produtoBean;
	}

	public Oferta getOfertaBean() {
		return ofertaBean;
	}

	public void setOfertaBean(Oferta ofertaBean) {
		this.ofertaBean = ofertaBean;
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
		ContratoProduto other = (ContratoProduto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getParcelas() {
		return this.parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}

	public Integer getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public double getValorparcela() {
		return this.valorparcela;
	}

	public void setValorparcela(double valorparcela) {
		this.valorparcela = valorparcela;
	}

	public double getValortotal() {
		return this.valortotal;
	}

	public void setValortotal(double valortotal) {
		this.valortotal = valortotal;
	}

	public Contrato getContrato() {
		return this.contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

}