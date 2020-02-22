package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the brinde_saida database table.
 * 
 */
@Entity
@Table(name="brinde_saida")
@NamedQuery(name="BrindeSaida.findAll", query="SELECT b FROM BrindeSaida b")
public class BrindeSaida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer codigo;

 
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCancelamento; 
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	@ManyToOne()
	@JoinColumn(name="canalBean", referencedColumnName="codigo")
	private PontoDeVenda canalBean;
	 
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
		BrindeSaida other = (BrindeSaida) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public PontoDeVenda getCanalBean() {
		return canalBean;
	}

	public void setCanalBean(PontoDeVenda canalBean) {
		this.canalBean = canalBean;
	}
	@ManyToOne()
	@JoinColumn(name="equipelBean", referencedColumnName="codigo")
	private Equipe equipeBean;
	 
	@ManyToOne()
	@JoinColumn(name="subcanlBean", referencedColumnName="codigo")
	private Subcanal subcanlBean;

	@ManyToOne()
	@JoinColumn(name="vendedorBean", referencedColumnName="codigo")
	private Vendedor vendedorBean;
	
	@ManyToOne()
	@JoinColumn(name="pontoDeVendaBean", referencedColumnName="codigo")
	private PontoDeVenda pontoDeVendaBean;
	 
	
	//bi-directional many-to-one association to BrindeSaidaIten
	@OneToMany(mappedBy="brindeSaida", cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<BrindeSaidaIten> brindeSaidaItens=new ArrayList<BrindeSaidaIten>();

	public BrindeSaida() {
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	
	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Equipe getEquipeBean() {
		return equipeBean;
	}

	public void setEquipeBean(Equipe equipeBean) {
		this.equipeBean = equipeBean;
	}

	public Subcanal getSubcanlBean() {
		return subcanlBean;
	}

	public void setSubcanlBean(Subcanal subcanlBean) {
		this.subcanlBean = subcanlBean;
	}

	public Vendedor getVendedorBean() {
		return vendedorBean;
	}

	public void setVendedorBean(Vendedor vendedorBean) {
		this.vendedorBean = vendedorBean;
	}

	public List<BrindeSaidaIten> getBrindeSaidaItens() {
		return this.brindeSaidaItens;
	}

	public void setBrindeSaidaItens(List<BrindeSaidaIten> brindeSaidaItens) {
		this.brindeSaidaItens = brindeSaidaItens;
	}

	public BrindeSaidaIten addBrindeSaidaIten(BrindeSaidaIten brindeSaidaIten) {
		getBrindeSaidaItens().add(brindeSaidaIten);
		brindeSaidaIten.setBrindeSaida(this);

		return brindeSaidaIten;
	}

	public BrindeSaidaIten removeBrindeSaidaIten(BrindeSaidaIten brindeSaidaIten) {
		getBrindeSaidaItens().remove(brindeSaidaIten);
		brindeSaidaIten.setBrindeSaida(null);

		return brindeSaidaIten;
	}

	public PontoDeVenda getPontoDeVendaBean() {
		return pontoDeVendaBean;
	}

	public void setPontoDeVendaBean(PontoDeVenda pontoDeVendaBean) {
		this.pontoDeVendaBean = pontoDeVendaBean;
	}

}