package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the brinde_devolucao_itens database table.
 * 
 */
@Entity
@Table(name="brinde_devolucao_itens")
@NamedQuery(name="BrindeDevolucaoIten.findAll", query="SELECT b FROM BrindeDevolucaoIten b")
public class BrindeDevolucaoIten implements Serializable {
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
		BrindeDevolucaoIten other = (BrindeDevolucaoIten) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne()
	@JoinColumn(name="brindeBean", referencedColumnName="codigo")
	private Brinde brindeBean;

	private String descricao;

	private double quantidade;

	//bi-directional many-to-one association to BrindaDevolucao
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigobrindedevolucao")
	private BrindeDevolucao brindaDevolucao;

	public BrindeDevolucaoIten() {
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

	public BrindeDevolucao getBrindaDevolucao() {
		return this.brindaDevolucao;
	}

	public void setBrindaDevolucao(BrindeDevolucao brindaDevolucao) {
		this.brindaDevolucao = brindaDevolucao;
	}

	public Brinde getBrindeBean() {
		return brindeBean;
	}

	public void setBrindeBean(Brinde brindeBean) {
		this.brindeBean = brindeBean;
	}

}