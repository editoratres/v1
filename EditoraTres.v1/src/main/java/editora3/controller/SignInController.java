package editora3.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import editora3.entidades.InfraUsuario;
import editora3.util.JsfUtil;
import editora3.facade.InfraUsuarioFacade;
import editora3.seguranca.LoginInfo;

@Named
@RequestScoped
public class SignInController {

	 
	@Inject
	private LoginInfo loginInfo;
	@Inject
	private FlashApp flashApp;
	public FlashApp getFlashApp() {
		return flashApp;
	}

	public void setFlashApp(FlashApp flashApp) {
		this.flashApp = flashApp;
	}

	@Inject
    private InfraUsuarioFacade InfraUsuarioFacade;
	
	
	public InfraUsuarioFacade getInfraUsuarioFacade() {
		return InfraUsuarioFacade;
	}

	public void setInfraUsuarioFacade(InfraUsuarioFacade infraUsuarioFacade) {
		InfraUsuarioFacade = infraUsuarioFacade;
	}

	public InfraUsuario getUsuario() {
		InfraUsuario usuario= (InfraUsuario)getFlashApp().getValores().get("usuario");
		if(usuario==null) {
			usuario=new InfraUsuario();
		}
		setUsuario(usuario);
		return usuario;
	}
	public void setUsuario(InfraUsuario usuario) {
		getFlashApp().getValores().put("usuario",usuario);
		
	}
	
	
	
	
	@PostConstruct
	public void iniciar() {
		
		getFlashApp().getValores().clear();
		
		if(getInfraUsuarioFacade().contarUsuarios().intValue()>0) {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			try {
				context.redirect(context.getRequestContextPath() + "/login.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void signin() {
		 
		try {
			 
			InfraUsuario usuario = getUsuario();
			usuario.setUsuario(usuario.getUsuario().toUpperCase());
			getInfraUsuarioFacade().signIN(usuario);
			//JsfUtil.addSuccessMessage("Usuário Administrador criado com sucesso","Administrador OK");
			
			//ret="/login.xhtml?faces-redirect=true";
			
		} catch (Exception e) {
			   JsfUtil.addErrorMessage("(signin) Falha na requisição\n\n" + e.getMessage());
			   FacesContext.getCurrentInstance().validationFailed();
		}
	 
		 
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}
 
	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	 
	
	
}
