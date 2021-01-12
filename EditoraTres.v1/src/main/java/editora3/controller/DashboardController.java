package editora3.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import editora3.infra.InterfaceMenuHtml;
import editora3.seguranca.LoginInfo;
@Named("dashboardController")
@SessionScoped
public class DashboardController implements Serializable {
	/**
	 * 
	 */
	
	private boolean bloquearPaginaPrincipal=false;
	 @Inject
	 private LoginInfo loginInfo;
	 
	@Inject
	private FlashApp flashApp;
	private static final long serialVersionUID = 1L;
	private String paginaAtual="/ui/inicio.xhtml";
	private HttpSession sessaoAtual;
	public void bloquarPaginaPrincipal(Boolean status) {
		this.bloquearPaginaPrincipal=status;
	}
	public String getPaginaAtual() {
		return paginaAtual;
	}
	public void keepAlive() {
		System.out.println("teste");
	    //logger.info("User " + loggedInUser.getUserLogin() + " requested the Session " + getCurrentHttpSessionId() + "  to be kept alive at " + new Date().toString());

	    /**
	     * Do nothing
	     */
	}

	public void setPaginaAtual(String paginaAtual) {
		this.paginaAtual = paginaAtual;
	}
	public void navegar(String pagina) {
		if(!pagina.isEmpty()) {
			flashApp.limpar();
			this.bloquearPaginaPrincipal=false;
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
	
	@PostConstruct
	public void reload() throws IOException {
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	    if(sessaoAtual==null) {
	    	sessaoAtual=getHttpSession();
	    }
	}
	
	
	public void refreshPagina() {
		System.out.println("");
	}
	public  HttpSession getHttpSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		int maxInactiveInterval = session.getMaxInactiveInterval();
		return session;

	}
	public HttpSession getSessaoAtual() {
		return sessaoAtual;
	}
	public void setSessaoAtual(HttpSession sessaoAtual) {
		this.sessaoAtual = sessaoAtual;
	}
	public boolean isBloquearPaginaPrincipal() {
		return bloquearPaginaPrincipal;
	}
	public void setBloquearPaginaPrincipal(boolean bloquearPaginaPrincipal) {
		this.bloquearPaginaPrincipal = bloquearPaginaPrincipal;
	}
	
}
