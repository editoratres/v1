package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;


/**
 * The persistent class for the equipe database table.
 * 
 */
@Entity
@NamedQuery(name="Equipe.findAll", query="SELECT e FROM Equipe e")
public class Equipe implements Serializable {
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
		Equipe other = (Equipe) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer codigo;

	private String agencia;

	private String bairro;

	private String banco;

	private String cep;

	private String cidade;

	private String cnpjcpf;

	private String comissao;

	private String complemento;

	private String conta;

	private String descricao;

	private String endereco;

	private String estado;

	private String favorecido;

	private String fone1;

	private String fone2;

	private String numero;

	private String rg;

	private String tipoconta;

	private String tipopessoa;
	
	@ManyToOne()
	@JoinColumn(name="infraUsuarioBean", referencedColumnName="idusuario")
	private InfraUsuario infraUsuarioBean;

	//bi-directional many-to-one association to Vendedor
	@OneToMany(mappedBy="equipeBean")
	private Set<Vendedor> vendedors=new HashSet<>();

	public Equipe() {
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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

	public String getCnpjcpf() {
		return this.cnpjcpf;
	}

	public void setCnpjcpf(String cnpjcpf) {
		this.cnpjcpf = cnpjcpf;
	}

	public String getComissao() {
		return this.comissao;
	}

	public void setComissao(String comissao) {
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

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public String getTipoconta() {
		return this.tipoconta;
	}

	public void setTipoconta(String tipoconta) {
		this.tipoconta = tipoconta;
	}

	public String getTipopessoa() {
		return this.tipopessoa;
	}

	public void setTipopessoa(String tipopessoa) {
		this.tipopessoa = tipopessoa;
	}

	public Set<Vendedor> getVendedors() {
		return this.vendedors;
	}

	public void setVendedors(Set<Vendedor> vendedors) {
		this.vendedors = vendedors;
	}

	public Vendedor addVendedor(Vendedor vendedor) {
		getVendedors().add(vendedor);
		vendedor.setEquipeBean(this);

		return vendedor;
	}

	public Vendedor removeVendedor(Vendedor vendedor) {
		getVendedors().remove(vendedor);
		vendedor.setEquipeBean(null);

		return vendedor;
	}

	public InfraUsuario getInfraUsuarioBean() {
		return infraUsuarioBean;
	}

	public void setInfraUsuarioBean(InfraUsuario infraUsuarioBean) {
		this.infraUsuarioBean = infraUsuarioBean;
	}

}