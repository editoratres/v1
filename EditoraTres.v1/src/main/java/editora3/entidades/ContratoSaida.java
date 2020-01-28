package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the contrato_entrada database table.
 * 
 */
@Entity
@Table(name="contrato_saida")
@NamedQuery(name="ContratoSaida.findAll", query="SELECT c FROM ContratoSaida c")
public class ContratoSaida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer codigo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datacancelamento;

	private Integer faixafinal;
 
	private Integer faixainicial;
	 
	@ManyToOne()
	@JoinColumn(name="equipelBean", referencedColumnName="codigo")
	private Equipe equipeBean;

	public Equipe getEquipeBean() {
		return equipeBean;
	}

	public void setEquipeBean(Equipe equipeBean) {
		this.equipeBean = equipeBean;
	}

	public Vendedor getVendedorBean() {
		return vendedorBean;
	}

	public void setVendedorBean(Vendedor vendedorBean) {
		this.vendedorBean = vendedorBean;
	}

	@ManyToOne()
	@JoinColumn(name="vendedorBean", referencedColumnName="codigo")
	private Vendedor vendedorBean;

	//bi-directional one-to-one association to ContratoEntradaIten
	 
	@OneToMany(mappedBy="contratoSaida", cascade = CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
	private List<ContratoSaidaIten> contratoSaidaIten=new ArrayList<>();

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
		ContratoSaida other = (ContratoSaida) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public ContratoSaida() {
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

	public Integer getFaixafinal() {
		return this.faixafinal;
	}

	public void setFaixafinal(Integer faixafinal) {
		this.faixafinal = faixafinal;
	}

	public Integer getFaixainicial() {
		return this.faixainicial;
	}

	public void setFaixainicial(Integer faixainicial) {
		this.faixainicial = faixainicial;
	}

	 

	public Date getDatacancelamento() {
		return datacancelamento;
	}

	public void setDatacancelamento(Date datacancelamento) {
		this.datacancelamento = datacancelamento;
	}

	public List<ContratoSaidaIten> getContratoSaidaIten() {
		return contratoSaidaIten;
	}

	public void setContratoSaidaIten(List<ContratoSaidaIten> contratoSaidaIten) {
		this.contratoSaidaIten = contratoSaidaIten;
	}

}