package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the contrato database table.
 * 
 */
@Entity
@NamedQuery(name="Contrato.findAll", query="SELECT c FROM Contrato c")
public class Contrato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer codigo;
	
	private Integer codigocontrato;

	@JoinColumn(name="CanalBean", referencedColumnName="codigo")
	private Canal canal;

	private String cartaobeneficio;

	@JoinColumn(name="equipeBean", referencedColumnName="codigo")
	private Equipe equipe;

	@Temporal(TemporalType.TIMESTAMP)
	private Date inclusao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date nascimento;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datageracao;
	
	@JoinColumn(name="relatorioBean", referencedColumnName="codigo")
	private Relatorio relatorioBean;

	@JoinColumn(name="subcanlBean", referencedColumnName="codigo")
	private Subcanal subcanlBean;
	 
	@JoinColumn(name="vendedorBean", referencedColumnName="codigo")
	private Vendedor vendedorBean;
	 

	//bi-directional many-to-one association to ContratoBrinde
	@OneToMany(mappedBy="contrato", fetch=FetchType.EAGER)
	private List<ContratoBrinde> contratoBrindes;

	//bi-directional many-to-one association to ContratoPagamento
	@OneToMany(mappedBy="contrato", fetch=FetchType.EAGER)
	private List<ContratoPagamento> contratoPagamentos;

	//bi-directional many-to-one association to ContratoProduto
	@OneToMany(mappedBy="contrato", fetch=FetchType.EAGER)
	private List<ContratoProduto> contratoProdutos;

	public Contrato() {
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

 

	public String getCartaobeneficio() {
		return this.cartaobeneficio;
	}

	public void setCartaobeneficio(String cartaobeneficio) {
		this.cartaobeneficio = cartaobeneficio;
	}

	 

	public Date getInclusao() {
		return this.inclusao;
	}

	public void setInclusao(Date inclusao) {
		this.inclusao = inclusao;
	}

	public Date getNascimento() {
		return this.nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	 

	
	public Canal getCanal() {
		return canal;
	}

	public void setCanal(Canal canal) {
		this.canal = canal;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public Relatorio getRelatorioBean() {
		return relatorioBean;
	}

	public void setRelatorioBean(Relatorio relatorioBean) {
		this.relatorioBean = relatorioBean;
	}

	public Subcanal getSubcanlBean() {
		return subcanlBean;
	}

	public void setSubcanlBean(Subcanal subcanlBean) {
		this.subcanlBean = subcanlBean;
	}

	public Vendedor getVendedorBean() {
		return vendedorBean;
	}

	public void setVendedorBean(Vendedor vendedorBean) {
		this.vendedorBean = vendedorBean;
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
		Contrato other = (Contrato) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public List<ContratoBrinde> getContratoBrindes() {
		return this.contratoBrindes;
	}

	public void setContratoBrindes(List<ContratoBrinde> contratoBrindes) {
		this.contratoBrindes = contratoBrindes;
	}

	public ContratoBrinde addContratoBrinde(ContratoBrinde contratoBrinde) {
		getContratoBrindes().add(contratoBrinde);
		contratoBrinde.setContrato(this);

		return contratoBrinde;
	}

	public ContratoBrinde removeContratoBrinde(ContratoBrinde contratoBrinde) {
		getContratoBrindes().remove(contratoBrinde);
		contratoBrinde.setContrato(null);

		return contratoBrinde;
	}

	public List<ContratoPagamento> getContratoPagamentos() {
		return this.contratoPagamentos;
	}

	public void setContratoPagamentos(List<ContratoPagamento> contratoPagamentos) {
		this.contratoPagamentos = contratoPagamentos;
	}

	public ContratoPagamento addContratoPagamento(ContratoPagamento contratoPagamento) {
		getContratoPagamentos().add(contratoPagamento);
		contratoPagamento.setContrato(this);

		return contratoPagamento;
	}

	public ContratoPagamento removeContratoPagamento(ContratoPagamento contratoPagamento) {
		getContratoPagamentos().remove(contratoPagamento);
		contratoPagamento.setContrato(null);

		return contratoPagamento;
	}

	public List<ContratoProduto> getContratoProdutos() {
		return this.contratoProdutos;
	}

	public void setContratoProdutos(List<ContratoProduto> contratoProdutos) {
		this.contratoProdutos = contratoProdutos;
	}

	public ContratoProduto addContratoProduto(ContratoProduto contratoProduto) {
		getContratoProdutos().add(contratoProduto);
		contratoProduto.setContrato(this);

		return contratoProduto;
	}

	public ContratoProduto removeContratoProduto(ContratoProduto contratoProduto) {
		getContratoProdutos().remove(contratoProduto);
		contratoProduto.setContrato(null);

		return contratoProduto;
	}

	public Date getDatageracao() {
		return datageracao;
	}

	public void setDatageracao(Date datageracao) {
		this.datageracao = datageracao;
	}

	public Integer getCodigocontrato() {
		return codigocontrato;
	}

	public void setCodigocontrato(Integer codigocontrato) {
		this.codigocontrato = codigocontrato;
	}

}