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
@Table(name="contrato_entrada")
@NamedQuery(name="ContratoEntrada.findAll", query="SELECT c FROM ContratoEntrada c")
public class ContratoEntrada implements Serializable {
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
	
	private Integer saidas;
	
	private Integer cancelamentos;
	
	//bi-directional one-to-one association to ContratoEntradaIten
	 
	public Integer getSaidas() {
		return saidas;
	}

	public void setSaidas(Integer saidas) {
		this.saidas = saidas;
	}

	public Integer getCancelamentos() {
		return cancelamentos;
	}

	public void setCancelamentos(Integer cancelamentos) {
		this.cancelamentos = cancelamentos;
	}

	@OneToMany(mappedBy="contratoEntrada", cascade = CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
	private List<ContratoEntradaIten> contratoEntradaIten=new ArrayList<>();

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
		ContratoEntrada other = (ContratoEntrada) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public ContratoEntrada() {
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

	public List<ContratoEntradaIten> getContratoEntradaIten() {
		return this.contratoEntradaIten;
	}

	public void setContratoEntradaIten(List<ContratoEntradaIten> contratoEntradaIten) {
		this.contratoEntradaIten = contratoEntradaIten;
	}

	public Date getDatacancelamento() {
		return datacancelamento;
	}

	public void setDatacancelamento(Date datacancelamento) {
		this.datacancelamento = datacancelamento;
	}

}