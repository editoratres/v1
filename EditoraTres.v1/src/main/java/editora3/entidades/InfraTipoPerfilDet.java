package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the infra_tipo_perfil_det database table.
 * 
 */
@Entity
@Table(name="infra_tipo_perfil_det")
@NamedQuery(name="InfraTipoPerfilDet.findAll", query="SELECT i FROM InfraTipoPerfilDet i")
public class InfraTipoPerfilDet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idtipoperfildet;

	private Boolean acessar;

	private Boolean criar;

	private Boolean editar;

	private Boolean excluir;

	//bi-directional many-to-one association to InfraModulo
	@ManyToOne()
	@JoinColumn(name="idmodulo")
	private InfraModulo infraModulo;

	//bi-directional many-to-one association to InfraTipoPerfilUsuario
	@ManyToOne()
	@JoinColumn(name="idtipoperfil")
	private InfraTipoPerfilUsuario infraTipoPerfilUsuario;

	public InfraTipoPerfilDet() {
	}

	public Integer getIdtipoperfildet() {
		return this.idtipoperfildet;
	}

	public void setIdtipoperfildet(Integer idtipoperfildet) {
		this.idtipoperfildet = idtipoperfildet;
	}

	public Boolean getAcessar() {
		return this.acessar;
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

	public InfraModulo getInfraModulo() {
		return this.infraModulo;
	}

	public void setInfraModulo(InfraModulo infraModulo) {
		this.infraModulo = infraModulo;
	}

	public InfraTipoPerfilUsuario getInfraTipoPerfilUsuario() {
		return this.infraTipoPerfilUsuario;
	}

	public void setInfraTipoPerfilUsuario(InfraTipoPerfilUsuario infraTipoPerfilUsuario) {
		this.infraTipoPerfilUsuario = infraTipoPerfilUsuario;
	}

}