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
@Table(name="contrato_cancelamento")
@NamedQuery(name="ContratoCancelamento.findAll", query="SELECT c FROM ContratoCancelamento c")
public class ContratoCancelamento implements Serializable {
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
	
	private String cancelamento;

	//bi-directional one-to-one association to ContratoEntradaIten
	 
	@OneToMany(mappedBy="contratoCancelamento", cascade = CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
	private List<ContratoCancelamentoIten> contratoCancelamentoIten=new ArrayList<>();

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
		ContratoCancelamento other = (ContratoCancelamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public ContratoCancelamento() {
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

	public List<ContratoCancelamentoIten> getContratoCancelamentoIten() {
		return contratoCancelamentoIten;
	}

	public void setContratoCancelamentoIten(List<ContratoCancelamentoIten> contratoCancelamentoIten) {
		this.contratoCancelamentoIten = contratoCancelamentoIten;
	}

	public String getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(String cancelamento) {
		this.cancelamento = cancelamento;
	}

}