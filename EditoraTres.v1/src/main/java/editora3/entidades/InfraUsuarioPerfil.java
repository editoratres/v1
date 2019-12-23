package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the infra_usuario_perfil database table.
 * 
 */
@Entity
@Table(name="infra_usuario_perfil")
@NamedQuery(name="InfraUsuarioPerfil.findAll", query="SELECT i FROM InfraUsuarioPerfil i")
public class InfraUsuarioPerfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idperfil;

	//bi-directional many-to-one association to InfraTipoPerfilUsuario
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idtipoperfil")
	private InfraTipoPerfilUsuario infraTipoPerfilUsuario;

	//bi-directional many-to-one association to InfraUsuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idusuario")
	private InfraUsuario infraUsuario;

	public InfraUsuarioPerfil() {
	}

	public Integer getIdperfil() {
		return this.idperfil;
	}

	public void setIdperfil(Integer idperfil) {
		this.idperfil = idperfil;
	}

	public InfraTipoPerfilUsuario getInfraTipoPerfilUsuario() {
		return this.infraTipoPerfilUsuario;
	}

	public void setInfraTipoPerfilUsuario(InfraTipoPerfilUsuario infraTipoPerfilUsuario) {
		this.infraTipoPerfilUsuario = infraTipoPerfilUsuario;
	}

	public InfraUsuario getInfraUsuario() {
		return this.infraUsuario;
	}

	public void setInfraUsuario(InfraUsuario infraUsuario) {
		this.infraUsuario = infraUsuario;
	}

}