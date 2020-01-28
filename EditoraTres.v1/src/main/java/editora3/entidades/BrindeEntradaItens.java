package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the brinda_entrada_itens database table.
 * 
 */
@Entity
@Table(name="brinde_entrada_itens")
@NamedQuery(name="BrindeEntradaItens.findAll", query="SELECT b FROM BrindeEntradaItens b")
public class BrindeEntradaItens implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

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
		BrindeEntradaItens other = (BrindeEntradaItens) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@ManyToOne()
	@JoinColumn(name="brindeBean", referencedColumnName="codigo")
	private Brinde brindeBean;
	 

	private String descricao;

	private double quantidade;

	private double subtotal;

	private double valorunitario;

	//bi-directional many-to-one association to BrindeEntrada
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigobrindeentrada")
	private BrindeEntrada brindeEntrada;

	public BrindeEntradaItens() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
 
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public double getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getValorunitario() {
		return this.valorunitario;
	}

	public void setValorunitario(double valorunitario) {
		this.valorunitario = valorunitario;
	}

	public BrindeEntrada getBrindeEntrada() {
		return this.brindeEntrada;
	}

	public void setBrindeEntrada(BrindeEntrada brindeEntrada) {
		this.brindeEntrada = brindeEntrada;
	}

	public Brinde getBrindeBean() {
		return brindeBean;
	}

	public void setBrindeBean(Brinde brindeBean) {
		this.brindeBean = brindeBean;
	}

}