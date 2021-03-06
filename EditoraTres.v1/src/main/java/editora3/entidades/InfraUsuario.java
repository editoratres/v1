package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the infra_usuario database table.
 * 
 */
@Entity
@Table(name="infra_usuario")
@NamedQuery(name="InfraUsuario.findAll", query="SELECT i FROM InfraUsuario i")
public class InfraUsuario implements Serializable {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idusuario == null) ? 0 : idusuario.hashCode());
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
		InfraUsuario other = (InfraUsuario) obj;
		if (idusuario == null) {
			if (other.idusuario != null)
				return false;
		} else if (!idusuario.equals(other.idusuario))
			return false;
		return true;
	}

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
	
	private String email;
	
	private boolean usuarioadm;

	//bi-directional many-to-one association to InfraUsuarioPerfil
	@OneToMany(mappedBy="infraUsuario" ,  cascade = {CascadeType.ALL} ,fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<InfraUsuarioPerfil> infraUsuarioPerfils;

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

	public Set<InfraUsuarioPerfil> getInfraUsuarioPerfils() {
		return this.infraUsuarioPerfils;
	}

	public void setInfraUsuarioPerfils(Set<InfraUsuarioPerfil> infraUsuarioPerfils) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isUsuarioadm() {
		return usuarioadm;
	}

	public void setUsuarioadm(boolean usuarioadm) {
		this.usuarioadm = usuarioadm;
	}

}