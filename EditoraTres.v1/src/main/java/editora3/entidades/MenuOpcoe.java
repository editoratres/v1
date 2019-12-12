package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the menu_opcoes database table.
 * 
 */
@Entity
@Table(name="menu_opcoes")
@NamedQuery(name="MenuOpcoe.findAll", query="SELECT m FROM MenuOpcoe m")
public class MenuOpcoe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to InfraModulo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idmodulo")
	private InfraModulo infraModulo;

	//bi-directional many-to-one association to Menu
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="menu")
	private MenuApp menu;

	public MenuOpcoe() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public InfraModulo getInfraModulo() {
		return this.infraModulo;
	}

	public void setInfraModulo(InfraModulo infraModulo) {
		this.infraModulo = infraModulo;
	}

	public MenuApp getMenu() {
		return this.menu;
	}

	public void setMenu(MenuApp menu) {
		this.menu = menu;
	}

}