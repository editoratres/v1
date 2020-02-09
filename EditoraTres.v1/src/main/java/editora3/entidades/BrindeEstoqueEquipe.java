package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the brinde_saida_itens database table.
 * 
 */
@Entity
@Table(name="brinde_estoque_equipe")
@NamedQuery(name="BrindeEstoqueEquipe.findAll", query="SELECT b FROM BrindeEstoqueEquipe b")
public class BrindeEstoqueEquipe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne()
	@JoinColumn(name="brindeBean", referencedColumnName="codigo")
	private Brinde brindeBean;
	 
	
	@ManyToOne()
	@JoinColumn(name="equipelBean", referencedColumnName="codigo")
	private Equipe equipeBean;
	
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
		BrindeEstoqueEquipe other = (BrindeEstoqueEquipe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

 
	private double quantidade=0d;

	 
	public BrindeEstoqueEquipe() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	 

	public double getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public Equipe getEquipeBean() {
		return equipeBean;
	}

	public void setEquipeBean(Equipe equipeBean) {
		this.equipeBean = equipeBean;
	}

	public Brinde getBrindeBean() {
		return brindeBean;
	}

	public void setBrindeBean(Brinde brindeBean) {
		this.brindeBean = brindeBean;
	}

	 
	 
}