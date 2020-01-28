package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the contrato_pagamentos database table.
 * 
 */
@Entity
@Table(name="contrato_pagamentos")
@NamedQuery(name="ContratoPagamento.findAll", query="SELECT c FROM ContratoPagamento c")
public class ContratoPagamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String autorizacao;

	private String bandeira;

	private String cartao;

	private String codseguranca;

	private Boolean condposparcial;

	private Boolean condpostotal;

	@Temporal(TemporalType.DATE)
	private Date data;

	private String lote;

	private String maquineta;

	private String nsu;

	private String terminal;

	private String titular;

	private String validade;

	private double valor;

	//bi-directional many-to-one association to Contrato
	@ManyToOne
	@JoinColumn(name="codigocontrato")
	private Contrato contrato;

	public ContratoPagamento() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAutorizacao() {
		return this.autorizacao;
	}

	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}

	public String getBandeira() {
		return this.bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}

	public String getCartao() {
		return this.cartao;
	}

	public void setCartao(String cartao) {
		this.cartao = cartao;
	}

	public String getCodseguranca() {
		return this.codseguranca;
	}

	public void setCodseguranca(String codseguranca) {
		this.codseguranca = codseguranca;
	}

	public Boolean getCondposparcial() {
		return this.condposparcial;
	}

	public void setCondposparcial(Boolean condposparcial) {
		this.condposparcial = condposparcial;
	}

	public Boolean getCondpostotal() {
		return this.condpostotal;
	}

	public void setCondpostotal(Boolean condpostotal) {
		this.condpostotal = condpostotal;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getLote() {
		return this.lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getMaquineta() {
		return this.maquineta;
	}

	public void setMaquineta(String maquineta) {
		this.maquineta = maquineta;
	}

	public String getNsu() {
		return this.nsu;
	}

	public void setNsu(String nsu) {
		this.nsu = nsu;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTitular() {
		return this.titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getValidade() {
		return this.validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Contrato getContrato() {
		return this.contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

}