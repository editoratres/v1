package editora3.util;

import java.io.BufferedReader;
import java.io.IOException;
 
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;

 
import org.codehaus.jettison.json.JSONException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
 

@SessionScoped
public class CepWebService implements Serializable {   
    /**
	 * 
	 */
	private static final long serialVersionUID = -4353863280376752555L;

	private  ArrayList<Estados> estados=null;
    
    private HashMap<String, ArrayList<Municipio> >municipiosUF=new HashMap<>();
    
    public cep Consultar(String cep) {
    	cep c = null;
        try {
        	
            c = new cep();
        	
            URL url = new URL(
                    "http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep
                            + "&formato=xml");
 
            Document document = getDocumento(url);
 
            Element root = document.getRootElement();
 
            for (Iterator i = root.elementIterator(); i.hasNext();) {
                Element element = (Element) i.next();
 
                if (element.getQualifiedName().equals("uf"))
                    c.setEstado(element.getText());
 
                if (element.getQualifiedName().equals("cidade"))
                   c.setCidade(element.getText());
 
                if (element.getQualifiedName().equals("bairro"))
                	c.setBairro(element.getText());
 
                if (element.getQualifiedName().equals("tipo_logradouro"))
                	c.setTipoLogradouro(element.getText());
 
                if (element.getQualifiedName().equals("logradouro"))
                	c.setLogradouro(element.getText());
 
                if (element.getQualifiedName().equals("resultado"))
                	c.setResultado(Integer.parseInt(element.getText()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return c;
    }
    public cep PesquisarCEP(String cep) {
    	cep c =null;
	    try {	
	    	JSONObject urlToJsonObjeto = urlToJsonObjeto("https://viacep.com.br/ws/"+ cep.replace(".", "").replace("-", "") +"/json/");
	    	if(urlToJsonObjeto!=null) {
	    		if(!urlToJsonObjeto.has("erro")) {
	    			c=new cep();
	    			c.setLogradouro(urlToJsonObjeto.isNull("logradouro") ? "" : urlToJsonObjeto.getString("logradouro"));
	    			c.setComplemento(urlToJsonObjeto.isNull("complemento") ? "" : urlToJsonObjeto.getString("complemento"));
	    			c.setBairro(urlToJsonObjeto.isNull("bairro") ? "" : urlToJsonObjeto.getString("bairro"));
	    			c.setCidade(urlToJsonObjeto.isNull("localidade") ? "" : urlToJsonObjeto.getString("localidade"));
	    			c.setEstado(urlToJsonObjeto.isNull("uf") ? "" : urlToJsonObjeto.getString("uf"));
	    			c.setIbge(urlToJsonObjeto.isNull("ibge") ? "" : urlToJsonObjeto.getString("ibge"));
	    		}
	    	}
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
    	
    	return c;
    }
    private   String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
      }
    
    private  JSONArray urlToJson(String path) throws MalformedURLException {
    	URL urlString = new URL(path);
        StringBuilder sb = null;
        URL url;
        URLConnection urlCon;
        try {
            url = urlString;
            urlCon = url.openConnection();
            BufferedReader in = null;
            if (urlCon.getHeaderField("Content-Encoding") != null
                    && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
               // LOGGER.info("reading data from URL as GZIP Stream");
                in = new BufferedReader(new InputStreamReader(new GZIPInputStream(urlCon.getInputStream() ),"utf-8" ));
            } else {
                //LOGGER.info("reading data from URL as InputStream");
                in = new BufferedReader(new InputStreamReader(urlCon.getInputStream(),"utf-8"));
            }
            String inputLine;
            sb = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            //LOGGER.info("Exception while reading JSON from URL - {}", e);
        }
        if (sb != null) {
            return new JSONArray(sb.toString());
        } else {
           // LOGGER.warn("No JSON Found in given URL");
            return null;
        }
    }

    private  JSONObject urlToJsonObjeto(String path) throws MalformedURLException {
    	URL urlString = new URL(path);
        StringBuilder sb = null;
        URL url;
        URLConnection urlCon;
        try {
            url = urlString;
            urlCon = url.openConnection();
            BufferedReader in = null;
            if (urlCon.getHeaderField("Content-Encoding") != null
                    && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
               // LOGGER.info("reading data from URL as GZIP Stream");
                in = new BufferedReader(new InputStreamReader(new GZIPInputStream(urlCon.getInputStream() ),"utf-8" ));
            } else {
                //LOGGER.info("reading data from URL as InputStream");
                in = new BufferedReader(new InputStreamReader(urlCon.getInputStream(),"utf-8"));
            }
            String inputLine;
            sb = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            //LOGGER.info("Exception while reading JSON from URL - {}", e);
        }
        if (sb != null) {
            return new JSONObject(sb.toString());
        } else {
           // LOGGER.warn("No JSON Found in given URL");
            return null;
        }
    }

    public String localizarCodigoUFPorNome(String nome) {
    	String ret=null;
    	
    	for (Iterator iterator = estados.iterator(); iterator.hasNext();) {
			Estados estados2 = (Estados) iterator.next();
			
			if(estados2.getSigla().equalsIgnoreCase(nome)) {
				ret = estados2.getCodigo().toString();
				break;
			}
			
		}
    	
    	
    	return ret;
    }
    private  ArrayList<Estados> carregarUF(){
    	ArrayList ret= new ArrayList<>();
    	try {
    	   
    		JSONArray urlToJson = urlToJson("https://servicodados.ibge.gov.br/api/v1/localidades/estados");
    		if(urlToJson!=null && urlToJson.length()>0) {
    			for (int i = 0; i < urlToJson.length(); i++) {
    				JSONObject item =(JSONObject) urlToJson.get(i);
    				if(item!=null && !item.isNull("id") && !item.isNull("nome")) {
    					Estados e = new Estados();
    					e.setCodigo(item.getInt("id"));
    					e.setSigla(item.getString("sigla"));
    					e.setNome(item.getString("nome")); 
    					ret.add(e);
    				}
				}
    		}
			
		} catch (Exception ex) {
			 ex.printStackTrace();
		}
    	return ret;
    }
    
    private  ArrayList<Municipio> CarregarMunicipios(String uf){
    	ArrayList<Municipio> ret= new ArrayList<CepWebService.Municipio>();
    	try {
    	   
    		JSONArray urlToJson = urlToJson("https://servicodados.ibge.gov.br/api/v1/localidades/estados/"+ uf +"/municipios");
    		if(urlToJson!=null && urlToJson.length()>0) {
    			for (int i = 0; i < urlToJson.length(); i++) {
    				JSONObject item =(JSONObject) urlToJson.get(i);
    				if(item!=null && !item.isNull("id") && !item.isNull("nome")) {
    					Municipio m  = new Municipio();
    					m.setCodigo(item.getInt("id"));
    					m.setNome( item.getString("nome"));
    					ret.add(m); 
    				}
				}
    		}
			
		} catch (Exception ex) {
			 ex.printStackTrace();
		}
    	return ret;
    }
    public static void main(String[] args) throws IOException, JSONException {
    	 
    	 
    	
     }
    private Document getDocumento(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
 
        return document;
    }
    
    public  ArrayList<Estados> getEstados() {
    	if(estados==null) {
    		estados=carregarUF();
    		estados.sort(new Comparator<Estados>() {

				@Override
				public int compare(Estados o1, Estados o2) {
					// TODO Auto-generated method stub
					return o1.getNome().compareTo(o2.getNome());
				}
			});
    	}
		return estados;
	}

	private void setEstados( ArrayList<Estados> estados) {
		this.estados = estados;
	}

	public ArrayList<Municipio> getMunicipios(String codigoUF) {
		// HashMap<Integer, String> municipios =null;
	 
		if(!getMunicipiosUF().containsKey(codigoUF)) {
			String codigoUFPorNome = localizarCodigoUFPorNome(codigoUF);
			ArrayList<Municipio> carregarMunicipios = CarregarMunicipios(codigoUFPorNome);
			carregarMunicipios.sort(new Comparator<Municipio>() {

				@Override
				public int compare(Municipio o1, Municipio o2) {
					// TODO Auto-generated method stub
					return o1.getNome().compareTo(o2.getNome());
				}
			});
			getMunicipiosUF().put(codigoUF,carregarMunicipios);
		 }
		return getMunicipiosUF().get(codigoUF);
		 
		 
	}

	 

	private  HashMap<String, ArrayList<Municipio> > getMunicipiosUF() {
		return municipiosUF;
	}

	private void setMunicipiosUF(HashMap<String,  ArrayList<Municipio> > municipiosUF) {
		this.municipiosUF = municipiosUF;
	}


	public class Municipio{
		Integer codigo;
		String nome;
		public Integer getCodigo() {
			return codigo;
		}
		public void setCodigo(Integer codigo) {
			this.codigo = codigo;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
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
			Municipio other = (Municipio) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (codigo == null) {
				if (other.codigo != null)
					return false;
			} else if (!codigo.equals(other.codigo))
				return false;
			return true;
		}
		private CepWebService getOuterType() {
			return CepWebService.this;
		}
		
	}
	public class Estados{
		
		Integer codigo;
		public Integer getCodigo() {
			return codigo;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
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
			Estados other = (Estados) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (codigo == null) {
				if (other.codigo != null)
					return false;
			} else if (!codigo.equals(other.codigo))
				return false;
			return true;
		}
		public void setCodigo(Integer codigo) {
			this.codigo = codigo;
		}
		public String getSigla() {
			return sigla;
		}
		public void setSigla(String sigla) {
			this.sigla = sigla;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		String sigla;
		String nome;
		private CepWebService getOuterType() {
			return CepWebService.this;
		}
	}

	public class cep{
    	private String estado = "";
        private String cidade = "";
        private String bairro = "";
        private String tipoLogradouro = "";
        private String logradouro = "";
        private String complemento = "";
        private String ibge;
        private int resultado = 0;
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
		public String getCidade() {
			return cidade;
		}
		public void setCidade(String cidade) {
			this.cidade = cidade;
		}
		public String getBairro() {
			return bairro;
		}
		public void setBairro(String bairro) {
			this.bairro = bairro;
		}
		public String getTipoLogradouro() {
			return tipoLogradouro;
		}
		public void setTipoLogradouro(String tipoLogradouro) {
			this.tipoLogradouro = tipoLogradouro;
		}
		public String getLogradouro() {
			return logradouro;
		}
		public void setLogradouro(String logradouro) {
			this.logradouro = logradouro;
		}
		public int getResultado() {
			return resultado;
		}
		public void setResultado(int resultado) {
			this.resultado = resultado;
		}
		public String getComplemento() {
			return complemento;
		}
		public void setComplemento(String complemento) {
			this.complemento = complemento;
		}
		public String getIbge() {
			return ibge;
		}
		public void setIbge(String ibge) {
			this.ibge = ibge;
		}
    }
}
