package editora3.controller;

import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import editora3.infra.InterfaceMenuHtml;
import editora3.seguranca.LoginInfo;
@Named("dashboardController")
@SessionScoped
public class DashboardController implements Serializable {
	/**
	 * 
	 */
	 @Inject
	 private LoginInfo loginInfo;
	 
	@Inject
	private FlashApp flashApp;
	private static final long serialVersionUID = 1L;
	private String paginaAtual="/ui/inicio.xhtml";

	public String getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(String paginaAtual) {
		this.paginaAtual = paginaAtual;
	}
	public void navegar(String pagina) {
		if(!pagina.isEmpty()) {
			flashApp.limpar();
			this.paginaAtual= pagina;
		}
	}
	
	 public void checkF5() {
		 paginaAtual="/ui/inicio.xhtml";
	 }

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}
	
	 
	
}
