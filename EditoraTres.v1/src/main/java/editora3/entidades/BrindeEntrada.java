package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 


/**
 * The persistent class for the brinde_entrada database table.
 * 
 */
@Entity
@Table(name="brinde_entrada")
@NamedQuery(name="BrindeEntrada.findAll", query="SELECT b FROM BrindeEntrada b")
public class BrindeEntrada implements Serializable {
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
		BrindeEntrada other = (BrindeEntrada) obj;
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

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	@ManyToOne()
	@JoinColumn(name="fornecedorBean", referencedColumnName="codigo")
	private Fornecedor fornecedorBean;
	
	private String notafiscal;

	private double total;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCancelamento;

	//bi-directional many-to-one association to BrindaEntradaIten
	@OneToMany(mappedBy="brindeEntrada", fetch = FetchType.EAGER, orphanRemoval=true, cascade=CascadeType.ALL)
	private List<BrindeEntradaItens> brindaEntradaItens=new ArrayList<>();

	public BrindeEntrada() {
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

	 
	public String getNotafiscal() {
		return this.notafiscal;
	}

	public void setNotafiscal(String notafiscal) {
		this.notafiscal = notafiscal;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<BrindeEntradaItens> getBrindaEntradaItens() {
		return this.brindaEntradaItens;
	}

	public void setBrindaEntradaItens(List<BrindeEntradaItens> brindaEntradaItens) {
		this.brindaEntradaItens = brindaEntradaItens;
	}

	public BrindeEntradaItens addBrindaEntradaIten(BrindeEntradaItens brindaEntradaIten) {
		getBrindaEntradaItens().add(brindaEntradaIten);
		brindaEntradaIten.setBrindeEntrada(this);

		return brindaEntradaIten;
	}

	public BrindeEntradaItens removeBrindaEntradaIten(BrindeEntradaItens brindaEntradaIten) {
		getBrindaEntradaItens().remove(brindaEntradaIten);
		brindaEntradaIten.setBrindeEntrada(null);

		return brindaEntradaIten;
	}

	public Fornecedor getFornecedorBean() {
		return fornecedorBean;
	}

	public void setFornecedorBean(Fornecedor fornecedorBean) {
		this.fornecedorBean = fornecedorBean;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

}