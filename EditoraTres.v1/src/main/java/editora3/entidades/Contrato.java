package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Fetch;

import java.util.ArrayList;
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
	
	@ManyToOne()
	@JoinColumn(name="CanalBean", referencedColumnName="codigo")
	private Canal canalBean;

	public Canal getCanalBean() {
		return canalBean;
	}

	public void setCanalBean(Canal canalBean) {
		this.canalBean = canalBean;
	}

	public Equipe getEquipeBean() {
		return equipeBean;
	}

	public void setEquipeBean(Equipe equipeBean) {
		this.equipeBean = equipeBean;
	}

	private String cartaobeneficio;

	@ManyToOne()
	@JoinColumn(name="equipeBean", referencedColumnName="codigo")
	private Equipe equipeBean;

	@Temporal(TemporalType.TIMESTAMP)
	private Date inclusao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date nascimento;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datacancelamento;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datasaida;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datageracao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datavenda;
	
	@ManyToOne()
	@JoinColumn(name="relatorioBean", referencedColumnName="codigo")
	private Relatorio relatorioBean;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="assinanteBean", referencedColumnName="codigo")
	private Assinante assinanteBean=new Assinante();
	
	@ManyToOne()
	@JoinColumn(name="subcanlBean", referencedColumnName="codigo")
	private Subcanal subcanlBean;
	
	@ManyToOne() 
	@JoinColumn(name="vendedorBean", referencedColumnName="codigo")
	private Vendedor vendedorBean;
	
	@ManyToOne(cascade = CascadeType.ALL) 
	@JoinColumn(name="pagamentoBean", referencedColumnName="id")
	private ContratoPagamento pagamentoBean=new  ContratoPagamento();
	 

	//bi-directional many-to-one association to ContratoBrinde
	@OneToMany(mappedBy="contrato", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	//@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<ContratoBrinde> contratoBrindes=new ArrayList<>();

	//bi-directional many-to-one association to ContratoPagamento
	

	//bi-directional many-to-one association to ContratoProduto
	@OneToMany(mappedBy="contrato", fetch=FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval=true)
	//@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<ContratoProduto> contratoProdutos=new ArrayList<>();

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

	public Assinante getAssinanteBean() {
		return assinanteBean;
	}

	public void setAssinanteBean(Assinante assinanteBean) {
		this.assinanteBean = assinanteBean;
	}

	public Date getDatavenda() {
		return datavenda;
	}

	public void setDatavenda(Date datavenda) {
		this.datavenda = datavenda;
	}

	public ContratoPagamento getPagamentoBean() {
		return pagamentoBean;
	}

	public void setPagamentoBean(ContratoPagamento pagamentoBean) {
		this.pagamentoBean = pagamentoBean;
	}

	public Date getDatacancelamento() {
		return datacancelamento;
	}

	public void setDatacancelamento(Date datacancelamento) {
		this.datacancelamento = datacancelamento;
	}

	public Date getDatasaida() {
		return datasaida;
	}

	public void setDatasaida(Date datasaida) {
		this.datasaida = datasaida;
	}

}