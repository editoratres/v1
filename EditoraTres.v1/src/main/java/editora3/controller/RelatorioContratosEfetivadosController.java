package editora3.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import editora3.entidades.Contrato;
import editora3.entidades.Equipe;
import editora3.entidades.PontoDeVenda;
import editora3.facade.EquipeFacade;
import editora3.facade.PontoDeVendaFacade;
import editora3.infra.JPAUtil;
import editora3.jreport.JasperRelatorio;
import editora3.jreport.JasperRelatorioProduzir;
import editora3.seguranca.LoginInfo;
import editora3.util.JsfUtil;
import editora3.util.ManipulacaoArquivo;

@Named("relatorioContratosEfetivadosController")
@RequestScoped
public class RelatorioContratosEfetivadosController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LoginInfo loginInfo;
	private List<Equipe> equipesDisponiveis;
	@Inject
	private EquipeFacade equipeFacade;

	@Inject
	private PontoDeVendaFacade pontoDeVendaFacade;
	@Inject
	private JasperRelatorioProduzir jasperRelatorioProduzir;
	private String nomePDF;

	private List<PontoDeVenda> pontosDeVendadisponiveis;

	private ParametrosRelContratos parametrosRelContratos = new ParametrosRelContratos();
	@Inject
	private FlashApp flashapp;

	/*
	 * @Inject private JPAUtil jpaUtil; private String nomePDF; private
	 * JasperRelatorio jasperRelatorio; private DateFormat df = new
	 * SimpleDateFormat("ddMyyyy_hhmmss"); private Connection cnx; private String
	 * nomeArquivoJasper="contratosefetivados.jasper"; private String raizServidor =
	 * new File(JsfUtil.RetornarCaminhoAbsoluto(File.separator)).getParent();
	 * private String raizContexto = new
	 * File(JsfUtil.RetornarCaminhoAbsoluto(File.separator)).toString(); private
	 * String nomePastaPDF = "pdf"; private String pastaPDF =
	 * raizContexto+File.separator+nomePastaPDF;
	 */
	@PostConstruct
	private void iniciar() {
		try {

			jasperRelatorioProduzir.iniciar("contratosefetivados.jasper");

		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "iniciar");
			// TODO: handle exception
		}
	}

	/*
	 * private String getNomeArquivoJasper() { StringBuilder
	 * retornarCaminhoAbsolutoPDF = new StringBuilder(); retornarCaminhoAbsolutoPDF
	 * .append(this.raizServidor) .append(File.separator) .append("relatorio")
	 * .append(File.separator) .append(this.nomeArquivoJasper);
	 * 
	 * return retornarCaminhoAbsolutoPDF.toString();
	 * 
	 * }
	 * 
	 *//*
		 * private String getNomeArquivoPDF() { StringBuilder retornarCaminhoAbsolutoPDF
		 * = new StringBuilder(); retornarCaminhoAbsolutoPDF
		 * .append("contratosefetivados_") .append(df.format(new Date()))
		 * .append(".pdf");
		 * 
		 * return retornarCaminhoAbsolutoPDF.toString(); }
		 */

	public ParametrosRelContratos getItem() {
		ParametrosRelContratos item = (ParametrosRelContratos) getFlashapp().getValoresPorID("relatorioContratoForm")
				.get("item");
		if (item == null) {
			item = new ParametrosRelContratos();
			setItens(item);
		}
		// TODO Auto-generated method stub
		return item;
	}

	public void setItens(ParametrosRelContratos item) {
		// TODO Auto-generated method stub
		getFlashapp().getValoresPorID("relatorioContratoForm").put("item", item);

	}

	public void gerarRelatorio() {

		try {
			ParametrosRelContratos item = getItem();
			if (item != null) {
				if (item.getDatainicial() == null) {
					JsfUtil.addErrorMessage("Data inicial não informada", "Procedimento não realizado");
					FacesContext.getCurrentInstance().validationFailed();
					return;
				}
				
				if (item.getDatafinal() == null) {
					JsfUtil.addErrorMessage("Data final não informada", "Procedimento não realizado");
					FacesContext.getCurrentInstance().validationFailed();
					return;
				}
				
				HashMap<String , Object> parametros = new HashMap<>();
				parametros.put("datainicial", item.getDatainicial());
				parametros.put("datafinal", item.getDatafinal());
				parametros.put("equipe", item.getEquipe()==null ? 0 : item.getEquipe().getCodigo() );
				parametros.put("pdv", item.getPdv()==null ? 0 : item.getPdv().getCodigo() );
				parametros.put("pdvNome", item.getPdv()==null ? null : item.getPdv().getDescricao() );
				parametros.put("equipeNome", item.getEquipe()==null ? null : item.getEquipe().getDescricao() );
				 
				this.jasperRelatorioProduzir.setParametros(parametros);
				this.jasperRelatorioProduzir.gerarRelatorio();
				if(this.jasperRelatorioProduzir.isRelatorioVazio()) {
					JsfUtil.addErrorMessage("Nenhuma informação localizada para os critérios informados", "Procedimento não realizado");
					FacesContext.getCurrentInstance().validationFailed();
					return;
				}else {
					setNomePDF(this.jasperRelatorioProduzir.getNomePDF());
				}
			}

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			JsfUtil.addErrorMessage(ex, "iniciar");
			FacesContext.getCurrentInstance().validationFailed();
		}
	}

	public List<Equipe> getEquipesDisponiveis() {
		if (equipesDisponiveis == null) {
			equipesDisponiveis = getEquipeFacade().findAllEquipes(getLoginInfo().getCodigoEquipeVinculada());
		}
		return equipesDisponiveis;
	}

	public void setEquipesDisponiveis(List<Equipe> equipesDisponiveis) {
		this.equipesDisponiveis = equipesDisponiveis;
	}

	public List<PontoDeVenda> getPontosDeVendadisponiveis() {
		if (pontosDeVendadisponiveis == null) {
			ParametrosRelContratos item = getItem();
			if (item.getEquipe() != null) {
				pontosDeVendadisponiveis = getPontoDeVendaFacade().findAllPorEquipe(item.getEquipe().getCodigo());
			}
		}
		return pontosDeVendadisponiveis;
	}

	public void setPontosDeVendadisponiveis(List<PontoDeVenda> pontosDeVendadisponiveis) {
		this.pontosDeVendadisponiveis = pontosDeVendadisponiveis;
	}

	public String getNomePDF() {

		return nomePDF;
	}

	public void setNomePDF(String nomePDF) {
		this.nomePDF = nomePDF;
	}

	public ParametrosRelContratos getParametrosRelContratos() {
		return parametrosRelContratos;
	}

	public void setParametrosRelContratos(ParametrosRelContratos parametrosRelContratos) {
		this.parametrosRelContratos = parametrosRelContratos;
	}

	public EquipeFacade getEquipeFacade() {
		return equipeFacade;
	}

	public void setEquipeFacade(EquipeFacade equipeFacade) {
		this.equipeFacade = equipeFacade;
	}

	public PontoDeVendaFacade getPontoDeVendaFacade() {
		return pontoDeVendaFacade;
	}

	public void setPontoDeVendaFacade(PontoDeVendaFacade pontoDeVendaFacade) {
		this.pontoDeVendaFacade = pontoDeVendaFacade;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public FlashApp getFlashapp() {
		return flashapp;
	}

	public void setFlashapp(FlashApp flashapp) {
		this.flashapp = flashapp;
	}

	public class ParametrosRelContratos {
		private PontoDeVenda pdv;
		private Equipe equipe;
		private String nomepdv;
		private String nomeequipe;
		private Date datainicial;
		private Date datafinal;

		public PontoDeVenda getPdv() {
			return pdv;
		}

		public void setPdv(PontoDeVenda pdv) {
			this.pdv = pdv;
		}

		public Equipe getEquipe() {
			return equipe;
		}

		public void setEquipe(Equipe equipe) {
			this.equipe = equipe;
		}

		public String getNomepdv() {
			return nomepdv;
		}

		public void setNomepdv(String nomepdv) {
			this.nomepdv = nomepdv;
		}

		public String getNomeequipe() {
			return nomeequipe;
		}

		public void setNomeequipe(String nomeequipe) {
			this.nomeequipe = nomeequipe;
		}

		public Date getDatainicial() {
			return datainicial;
		}

		public void setDatainicial(Date datainicial) {
			this.datainicial = datainicial;
		}

		public Date getDatafinal() {
			return datafinal;
		}

		public void setDatafinal(Date datafinal) {
			this.datafinal = datafinal;
		}

	}
}
