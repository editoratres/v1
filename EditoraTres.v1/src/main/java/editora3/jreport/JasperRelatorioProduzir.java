package editora3.jreport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jpa.internal.EntityManagerImpl;

import editora3.infra.JPAUtil;
import editora3.util.JsfUtil;
import editora3.util.ManipulacaoArquivo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Named("jasperRelatorioProduzir")
@RequestScoped
public class JasperRelatorioProduzir implements Serializable {

	
  	private static final long serialVersionUID = 1L;

	
  	
  	
	private String nomePDF;
	private JasperRelatorio jasperRelatorio;
	private DateFormat df = new SimpleDateFormat("ddMyyyy_hhmmss");
	private Connection cnx;	
	private String nomeArquivoJasper;//="contratosefetivados.jasper";
	private String raizServidor = new File(JsfUtil.RetornarCaminhoAbsoluto(File.separator)).getParent();
	private String raizContexto = new File(JsfUtil.RetornarCaminhoAbsoluto(File.separator)).toString();
	private String nomePastaPDF = "pdf";
	private String pastaPDF = raizContexto+File.separator+nomePastaPDF;
	private String nomeArquivoPDF="";
	private HashMap<String, Object> parametros=new HashMap<>();
	
	 
	@PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;
	
	public Connection getConnection() throws NamingException, SQLException   {
		InitialContext initialContext = new InitialContext();
		DataSource dataSource = (DataSource)initialContext.lookup("java:/editora3");
		Connection connection = dataSource.getConnection();
		return connection; 
	    
	}
	
	public void iniciar(String NomeArquivoJasper) {
		iniciar(NomeArquivoJasper,null);
	}
	 
	public void iniciar(String NomeArquivoJasper,HashMap<String, Object> parametros) {
		try {
			this.nomeArquivoJasper=NomeArquivoJasper;
			this.nomeArquivoPDF = NomeArquivoJasper.substring(0, NomeArquivoJasper.lastIndexOf("."));
			this.setParametros(parametros);
			
			
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
		.append(nomeArquivoPDF)
		.append("_")
		.append(df.format(new Date()))
		.append(".pdf");
		
		return retornarCaminhoAbsolutoPDF.toString();
	}
	 
	public void gerarRelatorio() throws JRException, IOException, SQLException, NamingException {

	 
			String nomeArquivoPDF= getNomeArquivoPDF();
			StringBuilder nomeArquivoPDFFisico = new StringBuilder();
			nomeArquivoPDFFisico
			.append(this.pastaPDF)
			.append(File.separator)
			.append(getNomeArquivoPDF()); 
			
			//jasperRelatorio=new JasperRelatorio(getNomeArquivoJasper(),nomeArquivoPDFFisico.toString(), this.parametros,this.cnx);
			preencherRelatorio(nomeArquivoPDFFisico.toString(),getNomeArquivoJasper());
			if(!this.relatorioVazio) {
			StringBuilder urlPDF  = new  StringBuilder();
			urlPDF
			.append(JsfUtil.UrlContexto())
			.append("/")
			.append(this.nomePastaPDF)
			.append("/")
			.append(nomeArquivoPDF);
		 
			 setNomePDF(urlPDF.toString());
			}
			 
	/*		
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			JsfUtil.addErrorMessage(ex, "iniciar");
			FacesContext.getCurrentInstance().validationFailed();			
		}*/
	}

	public String getNomePDF() {
		
		return nomePDF;
	}

	public void setNomePDF(String nomePDF) {
		this.nomePDF = nomePDF;
	}

	public HashMap<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(HashMap<String, Object> parametros) {
		this.parametros = parametros;
	}	
	private boolean relatorioVazio=false;
	public void preencherRelatorio(String nomeArquivoPDF , String nomeArquivoJasper) throws JRException, IOException, SQLException, NamingException {
		this.cnx=getConnection();
		 JasperPrint fillReport =null;
		try {
			  fillReport = JasperFillManager.fillReport(nomeArquivoJasper, this.parametros, this.cnx );
		} catch (NullPointerException e) {
			this.relatorioVazio=true;
			// TODO: handle exception
		} catch (JRException e) {
			throw e;			// TODO: handle exception
		}
		
		 this.cnx.close();
		// this.relatorioVazio = fillReport.getPages().isEmpty();
		 if(!this.relatorioVazio) {
			 byte[] exportReportToPdf = JasperExportManager.exportReportToPdf(fillReport);
			 
			 if(exportReportToPdf!=null) {
				 OutputStream streamPDF=new  FileOutputStream( new File(nomeArquivoPDF));
				 if(streamPDF!=null) {
					 streamPDF.write(exportReportToPdf, 0, exportReportToPdf.length);
					 streamPDF.close();
				 }
				 
			 }
		 }
		 
	}

	public boolean isRelatorioVazio() {
		return relatorioVazio;
	}

	public void setRelatorioVazio(boolean relatorioVazio) {
		this.relatorioVazio = relatorioVazio;
	}
}
