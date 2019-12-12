package editora3.controller;

import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
@Named("dashboardController")
@SessionScoped
public class DashboardController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String paginaAtual="/ui/inicio.xhtml";

	public String getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(String paginaAtual) {
		this.paginaAtual = paginaAtual;
	}
	public void navegar(String pagina) {
		this.paginaAtual= pagina;
	}
}
