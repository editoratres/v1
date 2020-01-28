package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the brinda_devolucao database table.
 * 
 */
@Entity
@Table(name="brinda_devolucao")
@NamedQuery(name="BrindeDevolucao.findAll", query="SELECT b FROM BrindeDevolucao b")
public class BrindeDevolucao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer codigo;

	@ManyToOne()
	@JoinColumn(name="canalBean", referencedColumnName="codigo")
	private Canal canalBean;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data; 
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCancelamento; 
	 
	@ManyToOne()
	@JoinColumn(name="equipelBean", referencedColumnName="codigo")
	private Equipe equipeBean;

	private Boolean naocontabilizar;

	private String obs;
	@ManyToOne()
	@JoinColumn(name="subcanlBean", referencedColumnName="codigo")
	private Subcanal subcanlBean;
	@ManyToOne()
	@JoinColumn(name="vendedorBean", referencedColumnName="codigo")
	private Vendedor vendedorBean;

	//bi-directional many-to-one association to BrindeDevolucaoIten
	@OneToMany(mappedBy="brindaDevolucao", cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<BrindeDevolucaoIten> brindeDevolucaoItens=new ArrayList<BrindeDevolucaoIten>();

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
		BrindeDevolucao other = (BrindeDevolucao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public BrindeDevolucao() {
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	 

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	 

	public Boolean getNaocontabilizar() {
		return this.naocontabilizar;
	}

	public void setNaocontabilizar(Boolean naocontabilizar) {
		this.naocontabilizar = naocontabilizar;
	}

	public String getObs() {
		return this.obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	 
	public Canal getCanalBean() {
		return canalBean;
	}

	public void setCanalBean(Canal canalBean) {
		this.canalBean = canalBean;
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

	public List<BrindeDevolucaoIten> getBrindeDevolucaoItens() {
		return this.brindeDevolucaoItens;
	}

	public void setBrindeDevolucaoItens(List<BrindeDevolucaoIten> brindeDevolucaoItens) {
		this.brindeDevolucaoItens = brindeDevolucaoItens;
	}

	public BrindeDevolucaoIten addBrindeDevolucaoIten(BrindeDevolucaoIten brindeDevolucaoIten) {
		getBrindeDevolucaoItens().add(brindeDevolucaoIten);
		brindeDevolucaoIten.setBrindaDevolucao(this);

		return brindeDevolucaoIten;
	}

	public BrindeDevolucaoIten removeBrindeDevolucaoIten(BrindeDevolucaoIten brindeDevolucaoIten) {
		getBrindeDevolucaoItens().remove(brindeDevolucaoIten);
		brindeDevolucaoIten.setBrindaDevolucao(null);

		return brindeDevolucaoIten;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

}