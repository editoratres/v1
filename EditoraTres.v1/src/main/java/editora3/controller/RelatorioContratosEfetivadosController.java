package editora3.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import editora3.infra.JPAUtil;
import editora3.jreport.JasperRelatorio;
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
	private JPAUtil jpaUtil;
	private String nomePDF;
	private JasperRelatorio jasperRelatorio;
	private DateFormat df = new SimpleDateFormat("ddMyyyy_hhmmss");
	private Connection cnx;	
	private String nomeArquivoJasper="contratosefetivados.jasper";
	private String raizServidor = new File(JsfUtil.RetornarCaminhoAbsoluto(File.separator)).getParent();
	private String raizContexto = new File(JsfUtil.RetornarCaminhoAbsoluto(File.separator)).toString();
	private String nomePastaPDF = "pdf";
	private String pastaPDF = raizContexto+File.separator+nomePastaPDF;
	@PostConstruct
	private void iniciar() {
		try {
			cnx=jpaUtil.getConnection();
			
			this.setNomePDF("");
		
			ManipulacaoArquivo.prepararPastaParaUso(pastaPDF);
			 
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "iniciar");
			// TODO: handle exception
		}
	}
	 
	private String getNomeArquivoJasper() {
		StringBuilder  retornarCaminhoAbsolutoPDF = new StringBuilder();
		retornarCaminhoAbsolutoPDF
		.append(this.raizServidor)
		.append(File.separator)
		.append("relatorio")
		.append(File.separator)
		.append(this.nomeArquivoJasper);
		
		return retornarCaminhoAbsolutoPDF.toString();
		 
	}
	
	private String getNomeArquivoPDF() {
		StringBuilder  retornarCaminhoAbsolutoPDF = new StringBuilder();
		retornarCaminhoAbsolutoPDF
		.append("contratosefetivados_")
		.append(df.format(new Date()))
		.append(".pdf");
		
		return retornarCaminhoAbsolutoPDF.toString();
	}
	 
	public void gerarRelatorio() {

		try {
			String nomeArquivoPDF= getNomeArquivoPDF();
			StringBuilder nomeArquivoPDFFisico = new StringBuilder();
			nomeArquivoPDFFisico
			.append(this.pastaPDF)
			.append(File.separator)
			.append(getNomeArquivoPDF()); 
			
			jasperRelatorio=new JasperRelatorio(getNomeArquivoJasper(),nomeArquivoPDFFisico.toString(), new HashMap<String, Object>(),this.cnx);
			jasperRelatorio.gerarRelatorio();
			
			StringBuilder urlPDF  = new  StringBuilder();
			urlPDF
			.append(JsfUtil.UrlContexto())
			.append("/")
			.append(this.nomePastaPDF)
			.append("/")
			.append(nomeArquivoPDF);
		 
			 setNomePDF(urlPDF.toString());
			 
			
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			JsfUtil.addErrorMessage(ex, "iniciar");
			FacesContext.getCurrentInstance().validationFailed();			
		}
	}

	public String getNomePDF() {
		
		return nomePDF;
	}

	public void setNomePDF(String nomePDF) {
		this.nomePDF = nomePDF;
	}
}
