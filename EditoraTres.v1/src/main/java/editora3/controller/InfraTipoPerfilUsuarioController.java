package editora3.controller;

 



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.inject.Named;

//import org.primefaces.context.RequestContext;

import editora3.entidades.InfraModulo;
import editora3.entidades.InfraTipoPerfilDet;
import editora3.entidades.InfraTipoPerfilUsuario;
import editora3.entidades.InfraUsuario;
import editora3.facade.InfraTipoPerfilUsuarioFacade;
import editora3.util.JsfUtil;
import editora3.util.JsfUtil.PersistAction;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Inject;


@Named("infraTipoPerfilUsuarioController")
@RequestScoped
public class InfraTipoPerfilUsuarioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private FlashApp flash ;
    @Inject
    private InfraTipoPerfilUsuarioFacade ejbFacade;
    @Inject
    private editora3.facade.InfraModulosFacade InfraModulosFacade;
    private List<InfraTipoPerfilUsuario> items = null;
    private InfraTipoPerfilUsuario selected;

    public InfraTipoPerfilUsuarioController() {
    }

    public InfraTipoPerfilUsuario getSelected() {
    	 if( flash.getValores().get("selected")!=null) {
			 selected = (InfraTipoPerfilUsuario) flash.getValores().get("selected");
		 }else {
			 selected =new InfraTipoPerfilUsuario();
			 flash.getValores().put("selected", selected);
		 }
		 
        return selected;
    }
    public void prepararEditar(InfraTipoPerfilUsuario infraTipoPerfilUsuario) {
		try {
			flash.getValores().put("selected", infraTipoPerfilUsuario);
			selected=infraTipoPerfilUsuario;
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			   JsfUtil.addErrorMessage("(prepararEditar) Falha na requisição\n\n" + e.getMessage());
		}
	}
    public void setSelected(InfraTipoPerfilUsuario selected) {
        this.selected = selected;
        flash.getValores().put("selected", selected);
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InfraTipoPerfilUsuarioFacade getFacade() {
        return ejbFacade;
    }

    public InfraTipoPerfilUsuario prepareCreate() {
    	//
        selected = new InfraTipoPerfilUsuario();        
        selected.setInfraTipoPerfilDets(IniciarListaPerfilDetalhes());
        //setInfraTipoPerfilDetLista(IniciarListaPerfilDetalhes());
       
        initializeEmbeddableKey();
        
        flash.getValores().put("selected", selected);
        
        return getSelected();
    }
    public InfraTipoPerfilUsuario prepareEdit() {
        //selected = new InfraTipoPerfilUsuario();        
        
       // setInfraTipoPerfilDetLista( selected.getInfraTipoPerfilDetCollection());        
        initializeEmbeddableKey();
        return getSelected();
    }
   
    @PostConstruct
	public void iniciar() {
		 if(flash.getValores().get("items")!=null) {
			 items = (List<InfraTipoPerfilUsuario>) flash.getValores().get("items");
		 }else {
			 items = getItems();
			 flash.getValores().put("items", items);
		 }
		 
		
	}
    public void create() {
        try {
        	
            selected=getSelected();            
            
            /*if(selected.getIdtipoperfil()==null){
                for (Iterator<InfraTipoPerfilDet> iterator = selected.getInfraTipoPerfilDets().iterator(); iterator.hasNext();) {
                    InfraTipoPerfilDet next = iterator.next();
                    next.setInfraTipoPerfilUsuario(selected);
                    //getInfraTipoPerfilDetLista().get(i).setIdtipoperfil(selected);
                }
                //selected.setInfraTipoPerfilDetCollection(getInfraTipoPerfilDetLista());
            }*/
            persist(PersistAction.CREATE, "");
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }

        } catch (Exception e) {
             JsfUtil.addErrorMessage("(create) Falha na requisição\n\n" + e.getMessage());
              FacesContext.getCurrentInstance().validationFailed();
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Informação modificada com sucesso");
    }

    public void destroy(InfraTipoPerfilUsuario item) {
    	if(item.isUsuarioadm()) {
    		  JsfUtil.addErrorMessage("Não é possivel excluir o perfil administrador","Procedimento não permitido");
    	}else {
    		getFacade().remove(item);
     
	        if (!JsfUtil.isValidationFailed()) {
	            selected = null; // Remove selection
	            items = null;    // Invalidate list of items to trigger re-query.
	            flash.getValores().clear();
	        }
    	}
    }
    public void atualizar() {
    	items=null;
    	 flash.getValores().put("items", items);
    }
    public List<InfraTipoPerfilUsuario> getItems() {
       if (items == null) {
            items = getFacade().findAll();
            flash.getValores().put("items", items);
       }
        return items;
    }
    private InfraTipoPerfilDet InfraTipoPerfilDetSelecionado;
    private Collection<InfraTipoPerfilDet>  _InfraTipoPerfilDetLista ;
    public Collection<InfraTipoPerfilDet> IniciarListaPerfilDetalhes(){
        Collection<InfraTipoPerfilDet> _InfraTipoPerfilDetList=new ArrayList<>();        
        try {
            List<InfraModulo> modulos = getInfraModulosFacade().findAll();
            for (int i = 0; i < modulos.size(); i++) {
                InfraTipoPerfilDet _InfraTipoPerfilDet = new InfraTipoPerfilDet();
               // _InfraTipoPerfilDet.setIdtipoperfildet(i);                
                _InfraTipoPerfilDet.setInfraModulo(modulos.get(i));
                _InfraTipoPerfilDet.setAcessar(false);
                _InfraTipoPerfilDet.setCriar(false);
                _InfraTipoPerfilDet.setEditar(false);
                _InfraTipoPerfilDet.setExcluir(false);
                _InfraTipoPerfilDet.setInfraTipoPerfilUsuario(selected);
                _InfraTipoPerfilDetList.add(_InfraTipoPerfilDet);
            }
            
        } catch (Exception e) {
            JsfUtil.addErrorMessage("(getModulos) Falha na requisição\n\n" + e.getMessage());
        }
       
       return _InfraTipoPerfilDetList;
    } 
    private void persist(PersistAction persistAction, String successMessage) {
    	selected=getSelected();
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                	if(selected.getIdtipoperfil()!=null) {
                		getFacade().edit(selected);
                		 JsfUtil.addSuccessMessage("Perfil modificado com sucesso","Procedimento OK");
                	} 
                    else {
                    	getFacade().create(selected);
                    	 JsfUtil.addSuccessMessage("Perfil criado com sucesso","Procedimento OK");
                    }
                } else {
                    getFacade().remove(selected);
                    JsfUtil.addSuccessMessage("Perfil removido com sucesso","Procedimento OK");
                }
                items=null;
		        flash.limpar();
               
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex,"");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "");
            }
        }
    }

    public InfraTipoPerfilUsuario getInfraTipoPerfilUsuario(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<InfraTipoPerfilUsuario> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<InfraTipoPerfilUsuario> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    /**
     * @return the InfraModulosFacade
     */
    public editora3.facade.InfraModulosFacade getInfraModulosFacade() {
        return InfraModulosFacade;
    }

    /**
     * @param InfraModulosFacade the InfraModulosFacade to set
     */
    public void setInfraModulosFacade(editora3.facade.InfraModulosFacade InfraModulosFacade) {
        this.InfraModulosFacade = InfraModulosFacade;
    }

    /**
     * @return the InfraTipoPerfilDetSelecionado
     */
    public InfraTipoPerfilDet getInfraTipoPerfilDetSelecionado() {
        return InfraTipoPerfilDetSelecionado;
    }

    /**
     * @param InfraTipoPerfilDetSelecionado the InfraTipoPerfilDetSelecionado to set
     */
    public void setInfraTipoPerfilDetSelecionado(InfraTipoPerfilDet InfraTipoPerfilDetSelecionado) {
        this.InfraTipoPerfilDetSelecionado = InfraTipoPerfilDetSelecionado;
    }

    /**
     * @return the _InfraTipoPerfilDetLista
     */
    public Collection<InfraTipoPerfilDet> getInfraTipoPerfilDetLista() {
        return _InfraTipoPerfilDetLista;
    }

    /**
     * @param _InfraTipoPerfilDetLista the _InfraTipoPerfilDetLista to set
     */
    public void setInfraTipoPerfilDetLista(Collection<InfraTipoPerfilDet> _InfraTipoPerfilDetLista) {
        this._InfraTipoPerfilDetLista = _InfraTipoPerfilDetLista;
    }

    @FacesConverter(forClass = InfraTipoPerfilUsuario.class)
    public static class InfraTipoPerfilUsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InfraTipoPerfilUsuarioController controller = (InfraTipoPerfilUsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "infraTipoPerfilUsuarioController");
            return controller.getInfraTipoPerfilUsuario(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof InfraTipoPerfilUsuario) {
                InfraTipoPerfilUsuario o = (InfraTipoPerfilUsuario) object;
                return getStringKey(o.getIdtipoperfil());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InfraTipoPerfilUsuario.class.getName()});
                return null;
            }
        }

    }

}
