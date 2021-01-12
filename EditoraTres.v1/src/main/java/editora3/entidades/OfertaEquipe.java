package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the oferta_itens database table.
 * 
 */
@Entity
@Table(name="oferta_equipe")
@NamedQuery(name="OfertaEquipe.findAll", query="SELECT o FROM OfertaEquipe o")
public class OfertaEquipe implements Serializable {
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
		OfertaEquipe other = (OfertaEquipe) obj;
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
	
 

	//bi-directional many-to-one association to Oferta
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigooferta")
	private Oferta oferta;


	@ManyToOne()
	@JoinColumn(name="equipeBean", referencedColumnName="codigo")
	private Equipe equipeBean;
 
	
	public OfertaEquipe() {
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	
	public Oferta getOferta() {
		return this.oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public Equipe getEquipeBean() {
		return equipeBean;
	}

	public void setEquipeBean(Equipe equipeBean) {
		this.equipeBean = equipeBean;
	}

	 

}