package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the infra_usuario database table.
 * 
 */
@Entity
@Table(name="infra_usuario")
@NamedQuery(name="InfraUsuario.findAll", query="SELECT i FROM InfraUsuario i")
public class InfraUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idusuario;

	private Boolean ativo;

	private byte[] foto;

	private Integer idusuariocontato;

	private String nome;

	private String senha;

	private String usuario;

	//bi-directional many-to-one association to InfraUsuarioPerfil
	@OneToMany(mappedBy="infraUsuario")
	private List<InfraUsuarioPerfil> infraUsuarioPerfils;

	public InfraUsuario() {
	}

	public Integer getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public Boolean getAtivo() {
		return this.ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Integer getIdusuariocontato() {
		return this.idusuariocontato;
	}

	public void setIdusuariocontato(Integer idusuariocontato) {
		this.idusuariocontato = idusuariocontato;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<InfraUsuarioPerfil> getInfraUsuarioPerfils() {
		return this.infraUsuarioPerfils;
	}

	public void setInfraUsuarioPerfils(List<InfraUsuarioPerfil> infraUsuarioPerfils) {
		this.infraUsuarioPerfils = infraUsuarioPerfils;
	}

	public InfraUsuarioPerfil addInfraUsuarioPerfil(InfraUsuarioPerfil infraUsuarioPerfil) {
		getInfraUsuarioPerfils().add(infraUsuarioPerfil);
		infraUsuarioPerfil.setInfraUsuario(this);

		return infraUsuarioPerfil;
	}

	public InfraUsuarioPerfil removeInfraUsuarioPerfil(InfraUsuarioPerfil infraUsuarioPerfil) {
		getInfraUsuarioPerfils().remove(infraUsuarioPerfil);
		infraUsuarioPerfil.setInfraUsuario(null);

		return infraUsuarioPerfil;
	}

}