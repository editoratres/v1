package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the brinde_saida_itens database table.
 * 
 */
@Entity
@Table(name="brinde_saida_itens")
@NamedQuery(name="BrindeSaidaIten.findAll", query="SELECT b FROM BrindeSaidaIten b")
public class BrindeSaidaIten implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne()
	@JoinColumn(name="brindeBean", referencedColumnName="codigo")
	private Brinde brindeBean;

	 

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
		BrindeSaidaIten other = (BrindeSaidaIten) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	private String descricao;

	private Double quantidade=0d;

	//bi-directional many-to-one association to BrindeSaida
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigobrindesaida")
	private BrindeSaida brindeSaida;

	public BrindeSaidaIten() {
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

	public Double getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public BrindeSaida getBrindeSaida() {
		return this.brindeSaida;
	}

	public void setBrindeSaida(BrindeSaida brindeSaida) {
		this.brindeSaida = brindeSaida;
	}

	public Brinde getBrindeBean() {
		return brindeBean;
	}

	public void setBrindeBean(Brinde brindeBean) {
		this.brindeBean = brindeBean;
	}

}