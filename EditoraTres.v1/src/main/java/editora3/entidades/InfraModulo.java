package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the infra_modulos database table.
 * 
 */  
@Entity
@Table(name="infra_modulos")
@NamedQuery(name="InfraModulo.findAll", query="SELECT i FROM InfraModulo i")
public class InfraModulo implements Serializable {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idmodulo == null) ? 0 : idmodulo.hashCode());
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
		InfraModulo other = (InfraModulo) obj;
		if (idmodulo == null) {
			if (other.idmodulo != null)
				return false;
		} else if (!idmodulo.equals(other.idmodulo))
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idmodulo;

	private String descricao;

	private String icone;

	private String modulo;

	private String nomeclasse;

	//bi-directional many-to-one association to InfraTipoPerfilDet
	@OneToMany(mappedBy="infraModulo")
	private List<InfraTipoPerfilDet> infraTipoPerfilDets;

	//bi-directional many-to-one association to MenuOpcoe
	@OneToMany(mappedBy="infraModulo")
	private List<MenuOpcoe> menuOpcoes;

	public InfraModulo() {
	}

	public Integer getIdmodulo() {
		return this.idmodulo;
	}

	public void setIdmodulo(Integer idmodulo) {
		this.idmodulo = idmodulo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getIcone() {
		return this.icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public String getModulo() {
		return this.modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getNomeclasse() {
		return this.nomeclasse;
	}

	public void setNomeclasse(String nomeclasse) {
		this.nomeclasse = nomeclasse;
	}

	public List<InfraTipoPerfilDet> getInfraTipoPerfilDets() {
		return this.infraTipoPerfilDets;
	}

	public void setInfraTipoPerfilDets(List<InfraTipoPerfilDet> infraTipoPerfilDets) {
		this.infraTipoPerfilDets = infraTipoPerfilDets;
	}

	public InfraTipoPerfilDet addInfraTipoPerfilDet(InfraTipoPerfilDet infraTipoPerfilDet) {
		getInfraTipoPerfilDets().add(infraTipoPerfilDet);
		infraTipoPerfilDet.setInfraModulo(this);

		return infraTipoPerfilDet;
	}

	public InfraTipoPerfilDet removeInfraTipoPerfilDet(InfraTipoPerfilDet infraTipoPerfilDet) {
		getInfraTipoPerfilDets().remove(infraTipoPerfilDet);
		infraTipoPerfilDet.setInfraModulo(null);

		return infraTipoPerfilDet;
	}

	public List<MenuOpcoe> getMenuOpcoes() {
		return this.menuOpcoes;
	}

	public void setMenuOpcoes(List<MenuOpcoe> menuOpcoes) {
		this.menuOpcoes = menuOpcoes;
	}

	public MenuOpcoe addMenuOpcoe(MenuOpcoe menuOpcoe) {
		getMenuOpcoes().add(menuOpcoe);
		menuOpcoe.setInfraModulo(this);

		return menuOpcoe;
	}

	public MenuOpcoe removeMenuOpcoe(MenuOpcoe menuOpcoe) {
		getMenuOpcoes().remove(menuOpcoe);
		menuOpcoe.setInfraModulo(null);

		return menuOpcoe;
	}

}