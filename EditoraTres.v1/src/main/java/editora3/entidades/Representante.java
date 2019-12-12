package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the representante database table.
 * 
 */
@Entity
@NamedQuery(name="Representante.findAll", query="SELECT r FROM Representante r")
public class Representante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idrepresentante;

	private String bairro;

	private String cep;

	private String cnpj;

	private String codmunicipio;

	private BigDecimal comissao;

	private BigDecimal comissaoemcobranca;

	private Timestamp dtcadastro;

	private String email;

	private String endereco;

	private String fone;

	private String fone2;

	@Column(name="id_conta_carteira_resp_ctt")
	private Integer idContaCarteiraRespCtt;

	@Column(name="id_conta_carteira_resp_rep")
	private Integer idContaCarteiraRespRep;

	@Column(name="id_conta_carteira_resp_ter")
	private Integer idContaCarteiraRespTer;

	private Integer idcontratado;
	
	@JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO")
    @ManyToOne(cascade = CascadeType.ALL)
	private InfraUsuario idusuario;

	@Column(name="imp_pdv_valor")
	private double impPdvValor;

	@Column(name="imp_pre_valor")
	private double impPreValor;

	@Column(name="imp_ret_valor")
	private double impRetValor;

	private String municipio;

	private String nomefantasia;

	private String numero;

	private String obs;

	@Column(name="pdv_valor")
	private double pdvValor;

	@Column(name="pre_valor")
	private double preValor;

	private String razao;

	@Column(name="ret_valor")
	private double retValor;

	private String rg;

	private String status;

	private String tipopessoa;

	private String uf;
	
	 

	public Representante() {
	}

	public Integer getIdrepresentante() {
		return this.idrepresentante;
	}

	public void setIdrepresentante(Integer idrepresentante) {
		this.idrepresentante = idrepresentante;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCodmunicipio() {
		return this.codmunicipio;
	}

	public void setCodmunicipio(String codmunicipio) {
		this.codmunicipio = codmunicipio;
	}

	public BigDecimal getComissao() {
		return this.comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public BigDecimal getComissaoemcobranca() {
		return this.comissaoemcobranca;
	}

	public void setComissaoemcobranca(BigDecimal comissaoemcobranca) {
		this.comissaoemcobranca = comissaoemcobranca;
	}

	public Timestamp getDtcadastro() {
		return this.dtcadastro;
	}

	public void setDtcadastro(Timestamp dtcadastro) {
		this.dtcadastro = dtcadastro;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getFone() {
		return this.fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getFone2() {
		return this.fone2;
	}

	public void setFone2(String fone2) {
		this.fone2 = fone2;
	}

	public Integer getIdContaCarteiraRespCtt() {
		return this.idContaCarteiraRespCtt;
	}

	public void setIdContaCarteiraRespCtt(Integer idContaCarteiraRespCtt) {
		this.idContaCarteiraRespCtt = idContaCarteiraRespCtt;
	}

	public Integer getIdContaCarteiraRespRep() {
		return this.idContaCarteiraRespRep;
	}

	public void setIdContaCarteiraRespRep(Integer idContaCarteiraRespRep) {
		this.idContaCarteiraRespRep = idContaCarteiraRespRep;
	}

	public Integer getIdContaCarteiraRespTer() {
		return this.idContaCarteiraRespTer;
	}

	public void setIdContaCarteiraRespTer(Integer idContaCarteiraRespTer) {
		this.idContaCarteiraRespTer = idContaCarteiraRespTer;
	}

	public Integer getIdcontratado() {
		return this.idcontratado;
	}

	public void setIdcontratado(Integer idcontratado) {
		this.idcontratado = idcontratado;
	}

	public InfraUsuario getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(InfraUsuario idusuario) {
		this.idusuario = idusuario;
	}

	public double getImpPdvValor() {
		return this.impPdvValor;
	}

	public void setImpPdvValor(double impPdvValor) {
		this.impPdvValor = impPdvValor;
	}

	public double getImpPreValor() {
		return this.impPreValor;
	}

	public void setImpPreValor(double impPreValor) {
		this.impPreValor = impPreValor;
	}

	public double getImpRetValor() {
		return this.impRetValor;
	}

	public void setImpRetValor(double impRetValor) {
		this.impRetValor = impRetValor;
	}

	public String getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getNomefantasia() {
		return this.nomefantasia;
	}

	public void setNomefantasia(String nomefantasia) {
		this.nomefantasia = nomefantasia;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getObs() {
		return this.obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public double getPdvValor() {
		return this.pdvValor;
	}

	public void setPdvValor(double pdvValor) {
		this.pdvValor = pdvValor;
	}

	public double getPreValor() {
		return this.preValor;
	}

	public void setPreValor(double preValor) {
		this.preValor = preValor;
	}

	public String getRazao() {
		return this.razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public double getRetValor() {
		return this.retValor;
	}

	public void setRetValor(double retValor) {
		this.retValor = retValor;
	}

	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTipopessoa() {
		return this.tipopessoa;
	}

	public void setTipopessoa(String tipopessoa) {
		this.tipopessoa = tipopessoa;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}