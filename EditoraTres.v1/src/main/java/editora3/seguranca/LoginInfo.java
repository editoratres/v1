package editora3.seguranca;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import editora3.entidades.Equipe;
import editora3.entidades.InfraUsuario;
import editora3.facade.EquipeFacade;
import editora3.facade.InfraUsuarioFacade;
import editora3.util.JsfUtil;
 

/**
 *
 * @author Fernando
 */
@Named
@SessionScoped
public class LoginInfo implements Serializable {

	@Inject
	private EquipeFacade equipeFacade;
	@Inject
	private InfraUsuarioFacade infraUsuarioFacade;
    private static final long serialVersionUID = 4582254362630538481L;
    private String IdSessao;
    private String usuariologado;
    private Integer idusuariologado;
    private Integer idrepresentante;
    private InfraUsuario usuario_logado;
    private Timestamp logadodesde;
    private String estagioaplicacao="Development";

    public LoginInfo() {
        System.out.println("Criou objeto logininfo");
    }

    public String getUsuariologado() {
        return usuariologado;
    }

    public void setUsuariologado(String _usuariologado) {
        this.usuariologado = _usuariologado;
    }

    /**
     * @return the idusuariologado
     */
    public Integer getIdusuariologado() {
        return idusuariologado;
    }

    /**
     * @param idusuariologado the idusuariologado to set
     */
    public void prepararEditarUsaurio() {
    	senhaAtual="";
    	senhaNova="";
    }
    public void atualizarPerfil() {
    	try {
    		if(!senhaAtual.trim().isEmpty()) {
	    		if(!senhaNova.trim().isEmpty() && !usuario_logado.getSenha().equalsIgnoreCase(senhaAtual)) {
	    			JsfUtil.addErrorMessage("Senha atual não confere", "Procedimento não realizado");
	    			FacesContext.getCurrentInstance().validationFailed();
	    			return;
	    		}else {
	    			usuario_logado.setSenha(senhaNova);
	    		}
    			
    		}
    		usuario_logado.setUsuario(usuario_logado.getUsuario().toUpperCase());
    		getInfraUsuarioFacade().edit(usuario_logado);
    		
			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "atualizarPerfil");
		}
    	 
    }
    public void setIdusuariologado(Integer idusuariologado) {
        this.idusuariologado = idusuariologado;
    }

    /**
     * @return the idrepresentante
     */
    public Integer getIdrepresentante() {
        return idrepresentante;
    }

    /**
     * @param idrepresentante the idrepresentante to set
     */
    public void setIdrepresentante(Integer idrepresentante) {
        this.idrepresentante = idrepresentante;
    }

    /**
     * @return the usuario_logado
     */
    private String senhaAtual="";
    private String senhaNova="";
    public Integer getCodigoEquipeVinculada() {
    	Integer ret = null;
    	
    	equipeVinculada = getEquipeVinculada(); 
    	if(equipeVinculada!=null) {
    		ret=equipeVinculada.getCodigo();
    	}
    	
    	return ret;
    }
    private Equipe equipeVinculada ;
    public Equipe getEquipeVinculada() {
		if(equipeVinculada==null) {
		
			equipeVinculada = getEquipeFacade().localizarEquipePorUsuario(usuario_logado.getIdusuario());
		}
		return equipeVinculada;
	}
    public InfraUsuario getUsuario_logado() {
        return usuario_logado;
    }

    /**
     * @param usuario_logado the usuario_logado to set
     */
    public void setUsuario_logado(InfraUsuario usuario_logado) {
        this.usuario_logado = usuario_logado;
    }

    /**
     * @return the logadodesde
     */
    public Timestamp getLogadodesde() {
        return logadodesde;
    }

    /**
     * @param logadodesde the logadodesde to set
     */
    public void setLogadodesde(Timestamp logadodesde) {
        this.logadodesde = logadodesde;
    }

    /**
     * @return the IdSessao
     */
    public String getIdSessao() {
        return IdSessao;
    }

    /**
     * @param IdSessao the IdSessao to set
     */
    public void setIdSessao(String IdSessao) {
        this.IdSessao = IdSessao;
    }

    /**
     * @return the estagioaplicacao
     */
    public String getEstagioaplicacao() {
        return estagioaplicacao;
    }

    /**
     * @param estagioaplicacao the estagioaplicacao to set
     */
    public void setEstagioaplicacao(String estagioaplicacao) {
        this.estagioaplicacao = estagioaplicacao;
    }
    
    public String logout() {
    	this.idusuariologado=null;
    	this.usuario_logado=null;
    	this.logadodesde=null;
    	this.usuariologado=null;
    	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    	return "/login.xhtml?faces-redirect=true";
    }

	public EquipeFacade getEquipeFacade() {
		return equipeFacade;
	}

	public void setEquipeFacade(EquipeFacade equipeFacade) {
		this.equipeFacade = equipeFacade;
	}

	public InfraUsuarioFacade getInfraUsuarioFacade() {
		return infraUsuarioFacade;
	}

	public void setInfraUsuarioFacade(InfraUsuarioFacade infraUsuarioFacade) {
		this.infraUsuarioFacade = infraUsuarioFacade;
	}
	public StreamedContent getFoto() {
	       StreamedContent file=null;
	        try {
	              
	            if (usuario_logado != null && usuario_logado.getFoto() != null) {                   

	            	String arquivo = usuario_logado.getUsuario()+ String.valueOf( new Date().getTime()) +".png";
	                file = new DefaultStreamedContent(new ByteArrayInputStream(usuario_logado.getFoto()),"image/png",arquivo);
	                //FileOutputStream f 
	            }
	                       
	        } catch (Exception e) {
	             JsfUtil.addErrorMessage("(handleFileUpload) Falha na requisição\n\n" + e.getMessage());
	        }
	        return file;
	    }
	
	 public void handleFileUpload(FileUploadEvent event) {
	        try 
	        {
	            
	            
	            UploadedFile file = event.getFile();
	            int tamanho = new Long(file.getSize()).intValue();
	            byte arquivobytes[]  = new byte[tamanho];
	            file.getInputstream().read(arquivobytes,0,tamanho);            
	            usuario_logado.setFoto(arquivobytes);
	            file.getInputstream().close();
	                    
	            //FacesMessage message = new FacesMessage("Upload OK", event.getFile().getFileName() + " carregado com sucesso");
	            
	            //FacesContext.getCurrentInstance().addMessage(null, message);
	            
	            
	            
	        } catch (IOException e) {
	            JsfUtil.addErrorMessage("(handleFileUpload) Falha na requisição\n\n" + e.getMessage());
	        }
	     
	    }
		public UploadedFile getFotoupload() {
		return fotoupload;
	}

	public void setFotoupload(UploadedFile fotoupload) {
		this.fotoupload = fotoupload;
	}
	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

		public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

		private UploadedFile fotoupload;

}
  


