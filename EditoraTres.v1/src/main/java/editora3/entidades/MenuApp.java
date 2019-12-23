package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the menu database table.
 * 
 */
@Entity 
@Table(name = "menu")
@NamedQuery(name="Menu.findAll", query="SELECT m FROM MenuApp m Where m.nomepai is null order by m.id")
public class MenuApp implements Serializable {
	private static final long serialVersionUID = 1L;

	
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Id
	private String nome;

	private String descricao;

	private String icone;

	public Set<MenuOpcoe> getMenuOpcoesCollection() {
		return menuOpcoesCollection;
	}

	public void setMenuOpcoesCollection(Set<MenuOpcoe> menuOpcoesCollection) {
		this.menuOpcoesCollection = menuOpcoesCollection;
	}

	public Set<MenuApp> getMenuCollection() {
		return menuCollection;
	}

	public void setMenuCollection(Set<MenuApp> menuCollection) {
		this.menuCollection = menuCollection;
	}

	public MenuApp getNomepai() {
		return nomepai;
	}

	public void setNomepai(MenuApp nomepai) {
		this.nomepai = nomepai;
	}

	@OneToMany(mappedBy = "menu", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<MenuOpcoe> menuOpcoesCollection = new HashSet<>();

	@OneToMany(mappedBy = "nomepai", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@OrderBy("id ASC")
	private Set<MenuApp> menuCollection = new HashSet<>();

	@JoinColumn(name = "nomepai", referencedColumnName = "id")
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private MenuApp nomepai;
	public MenuApp() {
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	
}