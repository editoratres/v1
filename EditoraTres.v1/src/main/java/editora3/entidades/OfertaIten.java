package editora3.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the oferta_itens database table.
 * 
 */
@Entity
@Table(name="oferta_itens")
@NamedQuery(name="OfertaIten.findAll", query="SELECT o FROM OfertaIten o")
public class OfertaIten implements Serializable {
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
		OfertaIten other = (OfertaIten) obj;
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
	
	private Boolean ativa;

	@Column(name="vez1")
	private Double vez1=0d;

	public Double getVez1() {
		return vez1;
	}

	public void setVez1(Double vez1) {
		this.vez1 = vez1;
	}

	public Double getVez2() {
		return vez2;
	}

	public void setVez2(Double vez2) {
		this.vez2 = vez2;
	}

	public Double getVez3() {
		return vez3;
	}

	public void setVez3(Double vez3) {
		this.vez3 = vez3;
	}

	public Double getVez4() {
		return vez4;
	}

	public void setVez4(Double vez4) {
		this.vez4 = vez4;
	}

	public Double getVez5() {
		return vez5;
	}

	public void setVez5(Double vez5) {
		this.vez5 = vez5;
	}

	public Double getVez6() {
		return vez6;
	}

	public void setVez6(Double vez6) {
		this.vez6 = vez6;
	}

	public Double getVez7() {
		return vez7;
	}

	public void setVez7(Double vez7) {
		this.vez7 = vez7;
	}

	public Double getVez8() {
		return vez8;
	}

	public void setVez8(Double vez8) {
		this.vez8 = vez8;
	}

	public Double getVez9() {
		return vez9;
	}

	public void setVez9(Double vez9) {
		this.vez9 = vez9;
	}

	public Double getVez10() {
		return vez10;
	}

	public void setVez10(Double vez10) {
		this.vez10 = vez10;
	}

	public Double getVez11() {
		return vez11;
	}

	public void setVez11(Double vez11) {
		this.vez11 = vez11;
	}

	public Double getVez12() {
		return vez12;
	}

	public void setVez12(Double vez12) {
		this.vez12 = vez12;
	}

	@Column(name="vez2")
	private Double vez2=0d;

	@Column(name="vez3")
	private Double vez3=0d;

	@Column(name="vez4")
	private Double vez4=0d; 

	@Column(name="vez5")
	private Double vez5=0d;

	@Column(name="vez6")
	private Double vez6=0d;

	@Column(name="vez7")
	private Double vez7=0d;

	@Column(name="vez8")
	private Double vez8=0d;

	@Column(name="vez9")
	private Double vez9=0d;

	@Column(name="vez10")
	private Double vez10=0d;

	@Column(name="vez11")
	private Double vez11=0d;
	
	@Column(name="vez12")
	private Double vez12=0d;

	private String edicao;

	private Double premio=0d;

	private Double valorbase=0d;

	private Double vendbase=0d;

	//bi-directional many-to-one association to Oferta
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigooferta")
	private Oferta oferta;

	public OfertaIten() {
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	public String getEdicao() {
		return this.edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	public Double getPremio() {
		return this.premio;
	}

	public void setPremio(Double premio) {
		this.premio = premio;
	}

	public Double getValorbase() {
		return this.valorbase;
	}

	public void setValorbase(Double valorbase) {
		this.valorbase = valorbase;
	}

	public Double getVendbase() {
		return this.vendbase;
	}

	public void setVendbase(Double vendbase) {
		this.vendbase = vendbase;
	}

	public Oferta getOferta() {
		return this.oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public Boolean getAtiva() {
		return ativa;
	}

	public void setAtiva(Boolean ativa) {
		this.ativa = ativa;
	}

}