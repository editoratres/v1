package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the contrato_entrada_itens database table.
 * 
 */
@Entity
@Table(name="contrato_cancelamento_itens")
@NamedQuery(name="ContratoCancelamentoIten.findAll", query="SELECT c FROM ContratoCancelamentoIten c")
public class ContratoCancelamentoIten implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
	@JoinColumn(name="contratoBean", referencedColumnName="codigo")
	private Contrato contratoBean;

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
		ContratoCancelamentoIten other = (ContratoCancelamentoIten) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	//bi-directional one-to-one association to ContratoEntrada
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigocontratoCancelamento")
	private ContratoCancelamento contratoCancelamento;

	public ContratoCancelamentoIten() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	 
 

	public Contrato getContratoBean() {
		return contratoBean;
	}

	public void setContratoBean(Contrato contratoBean) {
		this.contratoBean = contratoBean;
	}

	public ContratoCancelamento getContratoCancelamento() {
		return contratoCancelamento;
	}

	public void setContratoCancelamento(ContratoCancelamento contratoCancelamento) {
		this.contratoCancelamento = contratoCancelamento;
	}

	 

}