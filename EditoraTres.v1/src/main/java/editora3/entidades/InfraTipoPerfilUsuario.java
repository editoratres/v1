package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Collection;
import java.util.List;


/**
 * The persistent class for the infra_tipo_perfil_usuario database table.
 * 
 */
@Entity
@Table(name="infra_tipo_perfil_usuario")
@NamedQuery(name="InfraTipoPerfilUsuario.findAll", query="SELECT i FROM InfraTipoPerfilUsuario i")
public class InfraTipoPerfilUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idtipoperfil;

	private Boolean ativo;

	private String tipoperfil;

	//bi-directional many-to-one association to InfraTipoPerfilDet
	@OneToMany(mappedBy="infraTipoPerfilUsuario",cascade = {CascadeType.ALL} , fetch = FetchType.EAGER )
	@OrderBy("idtipoperfildet")
	private Collection<InfraTipoPerfilDet> infraTipoPerfilDets;

	/*//bi-directional many-to-one association to InfraUsuarioPerfil
	@OneToMany(mappedBy="infraTipoPerfilUsuario")
	private Collection<InfraUsuarioPerfil> infraUsuarioPerfils;
*/
	public InfraTipoPerfilUsuario() {
	}

	public Integer getIdtipoperfil() {
		return this.idtipoperfil;
	}

	public void setIdtipoperfil(Integer idtipoperfil) {
		this.idtipoperfil = idtipoperfil;
	}

	public Boolean getAtivo() {
		return this.ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getTipoperfil() {
		return this.tipoperfil;
	}

	public void setTipoperfil(String tipoperfil) {
		this.tipoperfil = tipoperfil;
	}

	public Collection<InfraTipoPerfilDet> getInfraTipoPerfilDets() {
		return this.infraTipoPerfilDets;
	}

	public void setInfraTipoPerfilDets(Collection<InfraTipoPerfilDet> infraTipoPerfilDets) {
		this.infraTipoPerfilDets = infraTipoPerfilDets;
	}

	public InfraTipoPerfilDet addInfraTipoPerfilDet(InfraTipoPerfilDet infraTipoPerfilDet) {
		getInfraTipoPerfilDets().add(infraTipoPerfilDet);
		infraTipoPerfilDet.setInfraTipoPerfilUsuario(this);

		return infraTipoPerfilDet;
	}

	public InfraTipoPerfilDet removeInfraTipoPerfilDet(InfraTipoPerfilDet infraTipoPerfilDet) {
		getInfraTipoPerfilDets().remove(infraTipoPerfilDet);
		infraTipoPerfilDet.setInfraTipoPerfilUsuario(null);

		return infraTipoPerfilDet;
	}
	 @Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (idtipoperfil != null ? idtipoperfil.hashCode() : 0);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object object) {
	        // TODO: Warning - this method won't work in the case the id fields are not set
	        if (!(object instanceof InfraTipoPerfilUsuario)) {
	            return false;
	        }
	        InfraTipoPerfilUsuario other = (InfraTipoPerfilUsuario) object;
	        if ((this.idtipoperfil == null && other.idtipoperfil != null) || (this.idtipoperfil != null && !this.idtipoperfil.equals(other.idtipoperfil))) {
	            return false;
	        }
	        return true;
	    }

/*	public Collection<InfraUsuarioPerfil> getInfraUsuarioPerfils() {
		return this.infraUsuarioPerfils;
	}

	public void setInfraUsuarioPerfils(Collection<InfraUsuarioPerfil> infraUsuarioPerfils) {
		this.infraUsuarioPerfils = infraUsuarioPerfils;
	}
*/
/*	public InfraUsuarioPerfil addInfraUsuarioPerfil(InfraUsuarioPerfil infraUsuarioPerfil) {
		getInfraUsuarioPerfils().add(infraUsuarioPerfil);
		infraUsuarioPerfil.setInfraTipoPerfilUsuario(this);

		return infraUsuarioPerfil;
	}

	public InfraUsuarioPerfil removeInfraUsuarioPerfil(InfraUsuarioPerfil infraUsuarioPerfil) {
		getInfraUsuarioPerfils().remove(infraUsuarioPerfil);
		infraUsuarioPerfil.setInfraTipoPerfilUsuario(null);

		return infraUsuarioPerfil;
	}*/

}