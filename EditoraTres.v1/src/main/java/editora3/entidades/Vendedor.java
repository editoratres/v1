package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the vendedor database table.
 * 
 */
@Entity
@NamedQuery(name="Vendedor.findAll", query="SELECT v FROM Vendedor v")
public class Vendedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer codigo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date admissao;

	private String agencia;

	private String bairro;

	private String banco;

	private String cep;

	private String cidade;

	private String codigo2;

	private double comissao;

	private String complemento;

	private String conta;

	
	private String cpf;

	private String endereco;

	private String estado;

	private Boolean experiencia;

	private String favorecido;

	private String fone1;

	private String fone2;

	private String fone3;

	@Temporal(TemporalType.TIMESTAMP)
	private Date nascimento;

	private String nome;

	private String numero;

	private String rg;

	private String situacao;

	private String tipoconta;
	private Double valorOferta=0d;
	private byte[] foto;

	//bi-directional many-to-one association to Equipe
	@ManyToOne()
	@JoinColumn(name="equipeBean", referencedColumnName="codigo")
	private Equipe equipeBean;
	
	private boolean ativo;
	
	private String email;
	
	public String getEmail() {
		return email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Vendedor other = (Vendedor) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Vendedor() {
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codgo) {
		this.codigo = codgo;
	}

	public Date getAdmissao() {
		return this.admissao;
	}

	public void setAdmissao(Date admissao) {
		this.admissao = admissao;
	}

	public String getAgencia() {
		return this.agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getBanco() {
		return this.banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCodigo2() {
		return this.codigo2;
	}

	public void setCodigo2(String codigo2) {
		this.codigo2 = codigo2;
	}

	public double getComissao() {
		return this.comissao;
	}

	public void setComissao(double comissao) {
		this.comissao = comissao;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getConta() {
		return this.conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Boolean getExperiencia() {
		return this.experiencia;
	}

	public void setExperiencia(Boolean experiencia) {
		this.experiencia = experiencia;
	}

	public String getFavorecido() {
		return this.favorecido;
	}

	public void setFavorecido(String favorecido) {
		this.favorecido = favorecido;
	}

	public String getFone1() {
		return this.fone1;
	}

	public void setFone1(String fone1) {
		this.fone1 = fone1;
	}

	public String getFone2() {
		return this.fone2;
	}

	public void setFone2(String fone2) {
		this.fone2 = fone2;
	}

	public String getFone3() {
		return this.fone3;
	}

	public void setFone3(String fone3) {
		this.fone3 = fone3;
	}

	public Date getNascimento() {
		return this.nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getSituacao() {
		return this.situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getTipoconta() {
		return this.tipoconta;
	}

	public void setTipoconta(String tipoconta) {
		this.tipoconta = tipoconta;
	}

	public Equipe getEquipeBean() {
		return this.equipeBean;
	}

	public void setEquipeBean(Equipe equipeBean) {
		this.equipeBean = equipeBean;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Double getValorOferta() {
		return valorOferta;
	}

	public void setValorOferta(Double valorOferta) {
		this.valorOferta = valorOferta;
	}

}