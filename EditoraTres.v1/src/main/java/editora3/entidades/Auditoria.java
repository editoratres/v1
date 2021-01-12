package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the auditoria database table.
 * 
 */
@Entity
@NamedQuery(name="Auditoria.findAll", query="SELECT a FROM Auditoria a")
public class Auditoria implements Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean acessar;

	private Boolean criar;

	private Timestamp datahora;

	private Boolean editar;

	private Boolean excluir;
	
	private String detalhe;

	public String getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//private Integer idmodulo;
	@ManyToOne()
	@JoinColumn(name="idmodulo", referencedColumnName="idmodulo")
	private InfraModulo idmodulo;

	//private Integer idusuario;
	@ManyToOne()
	@JoinColumn(name="idusuario", referencedColumnName="idusuario")
	private InfraUsuario idusuario;

	public InfraModulo getIdmodulo() {
		return idmodulo;
	}

	public void setIdmodulo(InfraModulo idmodulo) {
		this.idmodulo = idmodulo;
	}

	public InfraUsuario getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(InfraUsuario idusuario) {
		this.idusuario = idusuario;
	}

	public Auditoria() {
	}

	public Boolean getAcessar() {
		return this.acessar;
	}

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
		Auditoria other = (Auditoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void setAcessar(Boolean acessar) {
		this.acessar = acessar;
	}

	public Boolean getCriar() {
		return this.criar;
	}

	public void setCriar(Boolean criar) {
		this.criar = criar;
	}

	public Timestamp getDatahora() {
		return this.datahora;
	}

	public void setDatahora(Timestamp datahora) {
		this.datahora = datahora;
	}

	public Boolean getEditar() {
		return this.editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public Boolean getExcluir() {
		return this.excluir;
	}

	public void setExcluir(Boolean excluir) {
		this.excluir = excluir;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public Integer getIdmodulo() {
//		return this.idmodulo;
//	}
//
//	public void setIdmodulo(Integer idmodulo) {
//		this.idmodulo = idmodulo;
//	}

//	public Integer getIdusuario() {
//		return this.idusuario;
//	}
//
//	public void setIdusuario(Integer idusuario) {
//		this.idusuario = idusuario;
//	}

}