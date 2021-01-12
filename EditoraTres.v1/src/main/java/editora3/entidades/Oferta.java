package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the oferta database table.
 * 
 */
@Entity
@NamedQuery(name="Oferta.findAll", query="SELECT o FROM Oferta o")
public class Oferta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer codigo;

	@ManyToOne()
	@JoinColumn(name="brindeBean", referencedColumnName="codigo")
	private Brinde brindeBean;

	private Boolean ativa;
	public Brinde getBrindeBean() {
		return brindeBean;
	}

	public void setBrindeBean(Brinde brindeBean) {
		this.brindeBean = brindeBean;
	}

	public Produto getProdutoBean() {
		return produtoBean;
	}

	public void setProdutoBean(Produto produtoBean) {
		this.produtoBean = produtoBean;
	}

	@ManyToOne()
	@JoinColumn(name="produtoBean", referencedColumnName="codigo")
	private Produto produtoBean;
	
	
	@ManyToOne()
	@JoinColumn(name="equipeBean", referencedColumnName="codigo")
	private Equipe equipeBean;

	private String tipoassinatura;
	
	private Integer vigencia;
	
	//bi-directional many-to-one association to OfertaIten
	@OneToMany(mappedBy="oferta", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	//@IndexColumn(name="codigo")
	// @Fetch(FetchMode.SUBSELECT)
	//@LazyCollection(LazyCollectionOption.FALSE)
	private List<OfertaIten> ofertaItens=new ArrayList<>();
	

	//bi-directional many-to-one association to OfertaIten
	@OneToMany(mappedBy="oferta", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	// @Fetch(FetchMode.SUBSELECT)
	//@IndexColumn(name="codigo")
	//@LazyCollection(LazyCollectionOption.FALSE)
	private List<OfertaBrindes> ofertaBrindes=new ArrayList<>();
	
	@OneToMany(mappedBy="oferta", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	// @Fetch(FetchMode.SUBSELECT)
	////@IndexColumn(name="codigo")
	//@LazyCollection(LazyCollectionOption.FALSE)
	private List<OfertaEquipe> ofertaEquipe=new ArrayList<>();
	

	public List<OfertaBrindes> getOfertaBrindes() {
		return ofertaBrindes;
	}
	
	private Double desconto;

	public void setOfertaBrindes(List<OfertaBrindes> ofertaBrindes) {
		this.ofertaBrindes = ofertaBrindes;
	}

	public Oferta() {
	}

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
		Oferta other = (Oferta) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	
	public String getTipoassinatura() {
		return this.tipoassinatura;
	}

	public void setTipoassinatura(String tipoassinatura) {
		this.tipoassinatura = tipoassinatura;
	}

	public List<OfertaIten> getOfertaItens() {
		return this.ofertaItens;
	}

	public void setOfertaItens(List<OfertaIten> ofertaItens) {
		this.ofertaItens = ofertaItens;
	}

	public OfertaIten addOfertaIten(OfertaIten ofertaIten) {
		getOfertaItens().add(ofertaIten);
		ofertaIten.setOferta(this);

		return ofertaIten;
	}

	public OfertaIten removeOfertaIten(OfertaIten ofertaIten) {
		getOfertaItens().remove(ofertaIten);
		ofertaIten.setOferta(null);

		return ofertaIten;
	}

	public Boolean getAtiva() {
		return ativa;
	}

	public void setAtiva(Boolean ativa) {
		this.ativa = ativa;
	}

	 
	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Equipe getEquipeBean() {
		return equipeBean;
	}

	public void setEquipeBean(Equipe equipeBean) {
		this.equipeBean = equipeBean;
	}

	public Integer getVigencia() {
		return vigencia;
	}

	public void setVigencia(Integer vigencia) {
		this.vigencia = vigencia;
	}

	public List<OfertaEquipe> getOfertaEquipe() {
		return ofertaEquipe;
	}

	public void setOfertaEquipe(List<OfertaEquipe> ofertaEquipe) {
		this.ofertaEquipe = ofertaEquipe;
	}

}