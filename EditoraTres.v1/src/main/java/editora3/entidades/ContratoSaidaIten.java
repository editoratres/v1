package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the contrato_entrada_itens database table.
 * 
 */
@Entity
@Table(name="contrato_saida_itens")
@NamedQuery(name="ContratoSaidaIten.findAll", query="SELECT c FROM ContratoSaidaIten c")
public class ContratoSaidaIten implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
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
		ContratoSaidaIten other = (ContratoSaidaIten) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	//bi-directional one-to-one association to ContratoEntrada
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigocontratoSaida")
	private ContratoSaida contratoSaida;

	public ContratoSaidaIten() {
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

	public ContratoSaida getContratoSaida() {
		return contratoSaida;
	}

	public void setContratoSaida(ContratoSaida contratoSaida) {
		this.contratoSaida = contratoSaida;
	}

	 
}