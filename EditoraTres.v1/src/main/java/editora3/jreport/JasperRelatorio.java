package editora3.jreport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;

 
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class JasperRelatorio {

	private String relArquivo;
	private Map parametros;
	InputStream inputStream;
	InputStream inputStream2;
	private Connection cnx;
	private String arquivoDestino;
	private OutputStream streamPDF;

	
	public JasperRelatorio(String relArquivo,String arquivoDestino,  Map parametros, Connection cnx) throws FileNotFoundException {
		super();
		this.relArquivo = relArquivo;
		this.parametros = parametros;
		this.cnx = cnx;
		this.streamPDF=streamPDF;
		this.arquivoDestino=arquivoDestino;
		 
		
	}

	 

	public void gerarRelatorio() throws JRException, IOException {
		 
		 JasperPrint fillReport = JasperFillManager.fillReport(this.relArquivo, this.parametros, this.cnx );
		
		 byte[] exportReportToPdf = JasperExportManager.exportReportToPdf(fillReport);
		 
		 if(exportReportToPdf!=null) {
			 OutputStream streamPDF=new  FileOutputStream( new File(this.arquivoDestino));
			 if(streamPDF!=null) {
				 streamPDF.write(exportReportToPdf, 0, exportReportToPdf.length);
				 streamPDF.close();
			 }
			 
		 }
		 
	}

}
