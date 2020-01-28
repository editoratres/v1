package editora3.controller;
import java.io.ByteArrayInputStream;
 
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.picklist.PickList;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import editora3.entidades.InfraTipoPerfilUsuario;
import editora3.entidades.InfraUsuario;
import editora3.entidades.InfraUsuarioPerfil;
import editora3.facade.InfraTipoPerfilUsuarioFacade;
import editora3.facade.InfraUsuarioFacade;
import editora3.util.JsfUtil;
import org.primefaces.model.DefaultStreamedContent;

@Named("infraUsuarioController")
@RequestScoped
public class InfraUsuarioController implements Serializable {
	  
	/**
	 * 
	 */
 
	@Inject
	private FlashApp flash ;
	private static final long serialVersionUID = 1L;
	private InfraUsuario selected;
	private List<InfraUsuario> items = null;
	@Inject
	private InfraUsuarioFacade facade;
	private UploadedFile fotoupload;
	 @Inject
	    private InfraTipoPerfilUsuarioFacade infraTipoPerfilUsuarioFacade;
	
	 private DualListModel<InfraTipoPerfilUsuario> dualListModelPerfilUsuario=new DualListModel<>();
	 public DualListModel<InfraTipoPerfilUsuario> getDualListModelPerfilUsuario() {
		 DualListModel<InfraTipoPerfilUsuario> ret = null ;
		 
		 if(flash.getValores().get("dualListModelPerfilUsuario")==null) {
			ret = dualListModelPerfilUsuario;
		 }else {
			 ret=  (DualListModel<InfraTipoPerfilUsuario>) flash.getValores().get("dualListModelPerfilUsuario");
		 }
	        return ret; //
	    }

	    /**
	     * @param dualListModelPerfilUsuario the dualListModelPerfilUsuario to set
	     */
	    public void setDualListModelPerfilUsuario(DualListModel<InfraTipoPerfilUsuario> dualListModelPerfilUsuario) {
	        this.dualListModelPerfilUsuario = dualListModelPerfilUsuario;
	        flash.getValores().put("dualListModelPerfilUsuario", dualListModelPerfilUsuario);
	    }

	   
	 public UploadedFile getFotoupload() {
	        return fotoupload;
	    }

	    /**
	     * @param fotupload the fotupload to set
	     */
    public void setFotoupload(UploadedFile fotoupload) {
        this.fotoupload = fotoupload;
    }
	public List<InfraUsuario> getItems() {
			
		  if(items==null) {
		   	items = getFacade().findAll();
		  }
			
		 return items;
	}
	public void atualizar() {
		items=null;
	}
	public void setItems(List<InfraUsuario> items) {
		this.items = items;
	}
	public InfraUsuarioFacade getFacade() {
		return facade;
	}
	public void setFacade(InfraUsuarioFacade facade) {
		this.facade = facade;
	}
	public InfraUsuario getSelected() {
		 if( flash.getValores().get("selected")!=null) {
			 selected = (InfraUsuario) flash.getValores().get("selected");
		 }else {
			 selected =new InfraUsuario();
			 flash.getValores().put("selected", selected);
		 }
		return selected;
	}
	public void setSelected(InfraUsuario selected) {
		this.selected = selected;
	}
	public void prepararNovo() {
		selected=new InfraUsuario();
		 IniciarLista(); 
		 flash.getValores().put("selected", selected);
	}
	@PostConstruct
	public void iniciar() {
		 if(flash.getValores().get("items")!=null) {
			 items = (List<InfraUsuario>) flash.getValores().get("items");
		 }else {
			 items = getItems();
			 flash.getValores().put("items", items);
		 }
		
	}
	private void IniciarLista(){
        try {
            
            List<InfraTipoPerfilUsuario> TodosOsPerfils = getInfraTipoPerfilUsuarioFacade().findAll();
            if (selected.getIdusuario() != null) {
                List<InfraTipoPerfilUsuario> PerfilsDoUsuario = new ArrayList<>();
                for (Iterator<InfraUsuarioPerfil> iterator = selected.getInfraUsuarioPerfils().iterator(); iterator.hasNext();) {
                    InfraUsuarioPerfil next = iterator.next();
                    PerfilsDoUsuario.add(next.getInfraTipoPerfilUsuario());
                     
                }
                TodosOsPerfils.removeAll(PerfilsDoUsuario);
                setDualListModelPerfilUsuario(new DualListModel<>(
                    TodosOsPerfils,
                    PerfilsDoUsuario));
            }else{             
            	setDualListModelPerfilUsuario( new DualListModel<>(
                    TodosOsPerfils,
                    new ArrayList<>() ));
            }
         

        } catch (Exception e) {
            JsfUtil.addErrorMessage("(IniciarLista) Falha na requisição\n\n" + e.getMessage());
        }
    }
	public void fecharDialogo() {
		flash.getValores().clear();
	}
	
	public void prepararCargaInicial() {
		flash.getValores().remove("items");
		flash.getValores().remove("selected");
	}
	public void prepararEditar(InfraUsuario infraUsuario) {
		try {
			selected=infraUsuario;
		    IniciarLista(); 
			flash.getValores().put("selected", selected);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			   JsfUtil.addErrorMessage("(handleFileUpload) Falha na requisição\n\n" + e.getMessage());
		}
	}
	public void excluirUsuario(InfraUsuario infraUsuario) {
		try {
			if(infraUsuario.isUsuarioadm()) {
				JsfUtil.addSuccessMessage("Não é possivel excluir o usúario administrador","Usuário não excluido");
			}else {
				facade.remove(infraUsuario);
				flash.getValores().clear();
				items=null;
				JsfUtil.addSuccessMessage("Procedimento realizado com sucesso","Usuário Excluido");
			}
		} catch (Exception e) {
			// TODO: handle exception
			   JsfUtil.addErrorMessage("(handleFileUpload) Falha na requisição\n\n" + e.getMessage());
		}
		
	}

	public void create() {
		try {
			selected = getSelected();
			selected.setUsuario(selected.getUsuario().toUpperCase().toString());
			InfraUsuario localizarPeloUsuario = facade.LocalizarPeloUsuario(selected.getUsuario());
			if (localizarPeloUsuario != null && localizarPeloUsuario
					.getIdusuario() != (selected.getIdusuario() == null ? 0 : selected.getIdusuario())) {

				JsfUtil.addErrorMessage("O Login informado já foi utilizado", "Procedimento não realizado");
				FacesContext.getCurrentInstance().validationFailed();
				return;
			}

			if (selected.getInfraUsuarioPerfils() != null) {
				for (InfraUsuarioPerfil next : selected.getInfraUsuarioPerfils()) {
					next.setInfraUsuario(null);
				}
			}

			List<InfraTipoPerfilUsuario> target = getDualListModelPerfilUsuario().getTarget();

			HashSet<InfraUsuarioPerfil> listperfilusuario = new HashSet<>();
			if (selected.getInfraUsuarioPerfils() == null) {
				selected.setInfraUsuarioPerfils(new HashSet<>());
			}
			int i = 0;
			for (InfraTipoPerfilUsuario next : target) {
				InfraUsuarioPerfil perfilusuario = new InfraUsuarioPerfil();
				//perfilusuario.setIdperfil(i);
				perfilusuario.setInfraUsuario(selected);
				perfilusuario.setInfraTipoPerfilUsuario(next);
				listperfilusuario.add(perfilusuario);
				i++;
			}
			selected.setInfraUsuarioPerfils(listperfilusuario);

			if (selected.getIdusuario() == null) {
				facade.create(selected);
				JsfUtil.addSuccessMessage("Usuário criado com sucesso", "Procedimento OK");
			} else {
				facade.edit(selected);
				JsfUtil.addSuccessMessage("Usuário alterado com sucesso", "Procedimento OK");
			}
			items = null;
			flash.limpar();

		} catch (Exception e) {
			JsfUtil.addErrorMessage("(handleFileUpload) Falha na requisição\n\n" + e.getMessage());
		}

	}
	  public void handleFileUpload(FileUploadEvent event) {
	        try 
	        {
	            
	            
	            UploadedFile file = event.getFile();
	            int tamanho = new Long(file.getSize()).intValue();
	            byte arquivobytes[]  = new byte[tamanho];
	            file.getInputstream().read(arquivobytes,0,tamanho);            
	            getSelected().setFoto(arquivobytes);
	            file.getInputstream().close();
	                    
	            //FacesMessage message = new FacesMessage("Upload OK", event.getFile().getFileName() + " carregado com sucesso");
	            
	            //FacesContext.getCurrentInstance().addMessage(null, message);
	            
	            
	            
	        } catch (IOException e) {
	            JsfUtil.addErrorMessage("(handleFileUpload) Falha na requisição\n\n" + e.getMessage());
	        }
	     
	    }
	  @FacesConverter("PerfilUsuarioConverter")
	    public static class PerfilUsuarioConverter implements Converter {
	        @Override
	        public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
	               Object ret = null;
	               if (arg1 instanceof PickList) {
	                   Object dualList = ((PickList) arg1).getValue();
	                   DualListModel dl = (DualListModel) dualList;
	                   for (Object o : dl.getSource()) {
	                       String id = "" + ((InfraTipoPerfilUsuario) o).getIdtipoperfil();
	                       if (arg2.equals(id)) {
	                           ret = o;
	                           break;
	                       }
	                   }
	                   if (ret == null) {
	                       for (Object o : dl.getTarget()) {
	                           String id = "" + ((InfraTipoPerfilUsuario) o).getIdtipoperfil();
	                           if (arg2.equals(id)) {
	                               ret = o;
	                               break;
	                           }
	                       }
	                   }
	               }
	               return ret;
	           }
	         @Override
	        public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
	             String str = "";
	             if (arg2 instanceof InfraTipoPerfilUsuario) {
	                 str = "" + ((InfraTipoPerfilUsuario) arg2).getIdtipoperfil();
	             }
	             return str;
	        }

	    }
	  public StreamedContent getFoto() {
	       StreamedContent file=null;
	        try {
	              
	            if (getSelected() != null && getSelected().getFoto() != null) {                   

	            	String arquivo = getSelected().getUsuario()+ String.valueOf( new Date().getTime()) +".png";
	                file = new DefaultStreamedContent(new ByteArrayInputStream(selected.getFoto()),"image/png",arquivo);
	                //FileOutputStream f 
	            }
	                       
	        } catch (Exception e) {
	             JsfUtil.addErrorMessage("(handleFileUpload) Falha na requisição\n\n" + e.getMessage());
	        }
	        return file;
	    }

	public InfraTipoPerfilUsuarioFacade getInfraTipoPerfilUsuarioFacade() {
		return infraTipoPerfilUsuarioFacade;
	}

	public void setInfraTipoPerfilUsuarioFacade(InfraTipoPerfilUsuarioFacade infraTipoPerfilUsuarioFacade) {
		this.infraTipoPerfilUsuarioFacade = infraTipoPerfilUsuarioFacade;
	}
	  

	   
}
