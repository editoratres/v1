package editora3.controller;
import java.io.ByteArrayInputStream;
 
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.primefaces.event.SelectEvent;
//import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import editora3.entidades.Auditoria;
import editora3.entidades.Brinde;
import editora3.entidades.InfraModulo;
import editora3.entidades.InfraTipoPerfilUsuario;
import editora3.entidades.InfraUsuario;
import editora3.entidades.InfraUsuarioPerfil;
import editora3.facade.AuditoriaFacade;
import editora3.facade.FiltrosLazyDataModel;
import editora3.facade.InfraModulosFacade;
import editora3.facade.InfraTipoPerfilUsuarioFacade;
import editora3.facade.InfraUsuarioFacade;
import editora3.facade.LazyObjetos;
import editora3.util.JsfUtil;
import org.primefaces.model.DefaultStreamedContent;

@Named("auditoriaController")
@RequestScoped
public class AuditoriaController implements Serializable {
	  
	/**
	 * 
	 */
 
	@Inject
	private FlashApp flash ;
	private static final long serialVersionUID = 1L;
	private Auditoria selected;
	private List<Auditoria> items = null;
	@Inject
	private InfraUsuarioFacade infraUsuariofacade;
	
	@Inject
	private InfraModulosFacade infraModulosFacade;
	
	@Inject
	private AuditoriaFacade facade;
	 
	public void atualizar() {
		setAuditoriaDisponiveis(null);
	}
	public List<InfraUsuario> getUsuarios(){
		
		List<InfraUsuario> findAll = infraUsuariofacade.findAll();
		
		findAll.sort(new Comparator<InfraUsuario>() {

			@Override
			public int compare(InfraUsuario o1, InfraUsuario o2) {
				// TODO Auto-generated method stub
				return o1.getNome().compareTo(o2.getNome());
			}
		});
		
		return findAll;
	}
	
	public List<InfraModulo> getModulos(){
		
		List<InfraModulo> findAll = infraModulosFacade.findAll();
		
		findAll.sort(new Comparator<InfraModulo>() {

			@Override
			public int compare(InfraModulo o1, InfraModulo o2) {
				// TODO Auto-generated method stub
				return o1.getDescricao().compareTo(o2.getDescricao());
			}
		});
		
		return findAll;
	}
	 
	public Auditoria getSelected() {
		 if( flash.getValoresPorID("infrausuariocontroller").get("selected")!=null) {
			 selected = (Auditoria) flash.getValoresPorID("infrausuariocontroller").get("selected");
		 }else {
			 selected =new Auditoria();
			 flash.getValoresPorID("infrausuariocontroller").put("selected", selected);
		 }
		return selected;
	}
	public void setSelected(Auditoria selected) {
		this.selected = selected;
	}
	 
	@PostConstruct
	public void iniciar() {
		
		//dtinicio=new Timestamp(new Date().getTime());
		//dttermino=new Timestamp(new Date().getTime());
	}
	private void IniciarLista(){
        try {
            
             
         

        } catch (Exception e) {
            JsfUtil.addErrorMessage("(IniciarLista) Falha na requisição\n\n" + e.getMessage());
        }
    }
	public void fecharDialogo() {
		flash.getValores().clear();
	}
	
	public void prepararCargaInicial() {
		flash.getValoresPorID("infrausuariocontroller").remove("auditoria");
		flash.getValoresPorID("infrausuariocontroller").remove("selected");
	}
	
	private InfraUsuario infraUsuarioSelected;
	private InfraModulo infraModulo;
	private static Date dtinicio=new Timestamp(new Date().getTime());
	public InfraModulo getInfraModulo() {
		return infraModulo;
	}

	public void setInfraModulo(InfraModulo infraModulo) {
		this.infraModulo = infraModulo;
	}

	public Date getInicio() {
		return dtinicio;
	}

	public void setInicio(Date inicio) {
		this.dtinicio = inicio;
	}

	public Date getTermino() {
		return dttermino;
	}
	public void onDateSelectInicio(SelectEvent event) {
       // FacesContext facesContext = FacesContext.getCurrentInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        if(event.getObject()!=null) {
        	dtinicio = (Date) event.getObject();
        	//auditoriaDisponiveis=null;
        }
        	 
    }

	public void setTermino(Date termino) {
		this.dttermino = termino;
	}

	private static Date  dttermino = new Timestamp(new Date().getTime());;
	
	private LazyDataModel<Auditoria> auditoriaDisponiveis=null;

	public Timestamp getDataInicialSelecionada() {

		GregorianCalendar g = new GregorianCalendar();
		g.setTime(dtinicio);
		g.set(GregorianCalendar.HOUR_OF_DAY, 0);
		g.set(GregorianCalendar.MINUTE, 0);
		g.set(GregorianCalendar.SECOND, 0);
		
		Timestamp tinicio = new Timestamp(g.getTime().getTime());
		
		return tinicio;
	}
	
	public Timestamp getDataTerminoSelecionada() {

		GregorianCalendar g = new GregorianCalendar();
		g.setTime(dttermino);
		g.set(GregorianCalendar.HOUR_OF_DAY, 23);
		g.set(GregorianCalendar.MINUTE, 59);
		g.set(GregorianCalendar.SECOND, 59);
		
		Timestamp ttermino = new Timestamp(g.getTime().getTime());
		
		return ttermino;
	}
	public LazyDataModel<Auditoria> getAuditoriaDisponiveis() {
		auditoriaDisponiveis = (LazyDataModel<Auditoria>) flash.getValoresPorID("infrausuariocontroller")
				.get("auditoria");
		if (auditoriaDisponiveis == null) {
			//Date inicioSelecionada = getInicio();
			//Date terminoSelecionada = getTermino();
			auditoriaDisponiveis = new LazyDataModel<Auditoria>() {

				private static final long serialVersionUID = 1L;

				@Override
				public List<Auditoria> load(int first, int pageSize, String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {
					 
					Timestamp dataInicialSelecionada = getDataInicialSelecionada();
					
					Timestamp dataTerminoSelecionada = getDataTerminoSelecionada();
					
					if(filters.containsKey("idusuario.nome")) {
						InfraUsuario infraUsuario =  (InfraUsuario) filters.get("idusuario.nome");
						if(infraUsuario!=null){
							filters.replace("idusuario.nome", infraUsuario.getNome());
						}
						
					}
					if(filters.containsKey("idmodulo.descricao")) {
						InfraModulo infraModulo =  (InfraModulo) filters.get("idmodulo.descricao");
						if(infraModulo!=null) {
							filters.replace("idmodulo.descricao", infraModulo.getDescricao());
						}
					}
					
					FiltrosLazyDataModel filtrosLazyDataModel = new FiltrosLazyDataModel(first, pageSize, sortField,
							sortOrder, filters);
					filtrosLazyDataModel.getMapeamentoCampoViewModel().put("datahora","a.datahora");
					filtrosLazyDataModel.getMapeamentoCampoViewModel().put("idusuario.nome","iu.nome");
					filtrosLazyDataModel.getMapeamentoCampoViewModel().put("idmodulo.descricao","im.descricao");
					filtrosLazyDataModel.getMapeamentoCampoViewModel().put("detalhe","a.detalhe");
					
					LazyObjetos<Auditoria> findAllLazy = facade.findAllLazy((infraUsuarioSelected!=null ? infraUsuarioSelected.getIdusuario() : null), filtrosLazyDataModel,dataInicialSelecionada,dataTerminoSelecionada);
					if(sortField==null) {
						findAllLazy.getLista().sort(new Comparator<Auditoria>() {
	
							@Override
							public int compare(Auditoria arg0, Auditoria arg1) {
								// TODO Auto-generated method stub
								return arg0.getDatahora().compareTo(arg1.getDatahora());
							}
						});
					}
				
					
					setRowCount(findAllLazy.getTotalObjetos());

					return findAllLazy.getLista();

				}
			};

		}

		setAuditoriaDisponiveis(auditoriaDisponiveis);
		
		return auditoriaDisponiveis;
	
		}
		public void setAuditoriaDisponiveis(LazyDataModel<Auditoria> auditoriaDisponiveis) {
			flash.getValoresPorID("infrausuariocontroller").put("auditoria",auditoriaDisponiveis);
		}

		public InfraUsuario getInfraUsuarioSelected() {
			return infraUsuarioSelected;
		}

		public void setInfraUsuarioSelected(InfraUsuario infraUsuarioSelected) {
			this.infraUsuarioSelected = infraUsuarioSelected;
		}
   
}
