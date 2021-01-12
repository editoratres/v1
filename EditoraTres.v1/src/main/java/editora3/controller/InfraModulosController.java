package editora3.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


import editora3.facade.InfraModulosFacade;

@Named("infraModulosController")
@RequestScoped
public class InfraModulosController implements Serializable {
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
 
	 
	
	@Inject
	private InfraModulosFacade infraModulosFacade;

	public InfraModulosFacade getInfraModulosFacade() {
		return infraModulosFacade;
	}

	public void setInfraModulosFacade(InfraModulosFacade infraModulosFacade) {
		this.infraModulosFacade = infraModulosFacade;
	}  
	   
}
