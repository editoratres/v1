package editora3.infra;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
 

import editora3.entidades.InfraTipoPerfilDet;
import editora3.entidades.MenuApp;
import editora3.entidades.MenuOpcoe;
import editora3.facade.InfraUsuarioFacade;
import editora3.facade.MenuFacade;
import editora3.seguranca.LoginInfo;
import editora3.util.JsfUtil;


/**
 *
 * @author Fernando
 */
@Named("interfaceMenuHtml")
@SessionScoped
public class InterfaceMenuHtml implements Serializable{
   
    @Inject
    private LoginInfo loginInfo;
    @Inject
    private InfraUsuarioFacade infraUsuarioFacade;
    @Inject
    private MenuFacade menuFacade;
    private ArrayList<MenuItemApp> itens;
    private MenuItemApp _menuapp;
    private String idsessao=null;
     public ArrayList<MenuItemApp> getMenuModel(){
        
      if(getItens()==null){  
            setItens(new ArrayList<>());
         
         List<MenuApp> findAll = getMenuFacade().findAll();
         ArrayList<MenuItemApp> menu = GerarModeloMenuHtml(findAll,null);  
         setItens(menu);
       
      }
      return getItens();
    
    }
    public void ExcluirNo(MenuItemApp no,ArrayList<MenuItemApp> itensmenu){
        try {
            
            for (int i = 0; i < itensmenu.size(); i++) {               
            
                MenuItemApp next = itensmenu.get(i);
                if(next.getID().equalsIgnoreCase(no.getID())){
                    itensmenu.remove(no);
                }else{
                    if(!next.getItens().isEmpty()){
                      ExcluirNo(no,next.getItens());
                    }
                }                
            }
            
        } catch (Exception e) {
             JsfUtil.addErrorMessage(e, "(LimparNosVazio)");
        }
    
    }    
    
    public boolean NosVazio(ArrayList<MenuItemApp> menuspesquisa){
        try { 
            int i=0;
            while (i < menuspesquisa.size()) {
            
                MenuItemApp next = menuspesquisa.get(i);
                if(!next.getItens().isEmpty()){
                   
                   NosVazio(next.getItens());                  
                  
                   if(next.getItens().isEmpty()){
                      menuspesquisa.remove(next);
                   }else{
                       i++;
                   }
                }else{
                   if(next.getURL()==null ){ 
                    //ExcluirNo(next,itens);
                     menuspesquisa.remove(next);                     
                   }else{
                       i++;
                   }
                }
                 
            }
        } catch (Exception e) {
               JsfUtil.addErrorMessage(e, "(LimparNosVazio)");
        }
        return true;
        
    } 
   
    
    
    public ArrayList<MenuItemApp> GerarModeloMenuHtml(Collection<MenuApp> menus, MenuItemApp pai){
        ArrayList<MenuItemApp> _menuAppItens = new ArrayList<MenuItemApp>();  
        try {
        	 boolean Permitido=true;
        	 MenuApp menupai = menus.iterator().next();
        	 if( menupai.getNomepai()==null){
        		 menus =menupai.getMenuCollection();
        	 }
        			 
              for (Iterator<MenuApp> iterator = menus.iterator(); iterator.hasNext();) {
                MenuApp menuapp = iterator.next();
                //if(menuapp.getNomepai()!=null) {
                //MenuItemApp item = new MenuItemApp();
                MenuItemApp item = new MenuItemApp();
                ArrayList<MenuItemApp> filhos =null;
                Permitido=true;
                if(menuapp.getMenuOpcoesCollection()!=null && !menuapp.getMenuOpcoesCollection().isEmpty()){
                    //SubMenus
                
                	// item.setID(menuapp.getId().toString());
                     //item.setLabel(menuapp.getDescricao());
                     
                     //item.setValue(menuapp.getDescricao());                
                     //item.getChildren()
                     //if(menuapp.getIcone()!=null){
                     //    item.setIcone(menuapp.getIcone());
                     //}
                     String Contexto = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                     MenuOpcoe next = menuapp.getMenuOpcoesCollection().iterator().next();
                    
                     if(next!=null){
                         String Modulo  = next.getInfraModulo().getModulo();
                         String estagioApp =  (getLoginInfo().getEstagioaplicacao()==null ? "Producao" : getLoginInfo().getEstagioaplicacao());
                         //if(!estagioApp.equalsIgnoreCase("Development")){
                            List<InfraTipoPerfilDet> LocalizarRecurso = getInfraUsuarioFacade().LocalizarRecurso(getLoginInfo().getUsuario_logado().getIdusuario(), Modulo);                         
                            if(LocalizarRecurso!=null && LocalizarRecurso.size()>0){
                                InfraTipoPerfilDet recurso = LocalizarRecurso.get(0);
                                Permitido = recurso.getAcessar();
                            }
                        /* }else{
                             Permitido=true;
                         }*/
                         //String URL = Modulo.replace(".xhtml", "");
                         //item.setURL(Contexto +"/"+ URL.substring(1)+".jsf");
                         item.setURL(Modulo);
                    }
                    /*if(Permitido){  
                      pai.getItens().add(item);
                      _menuAppItens.add(pai);
                    }*/
                }
                if(Permitido){
					item.setID(menuapp.getId().toString());
					item.setLabel(menuapp.getDescricao());
					//item.setURL(" ");
	
					// menu.setExpanded(true);
					if (menuapp.getIcone() != null) {
						item.setIcone(menuapp.getIcone());
					}
	
					if ( !menuapp.getMenuCollection().isEmpty()) {
						filhos = GerarModeloMenuHtml(menuapp.getMenuCollection(), item);
					}
					if (filhos != null) {
						// pai.getItens().add(item);
						item.getItens().addAll(filhos);
						_menuAppItens.add(item);
					} else {
						_menuAppItens.add(item);
						// break;
					}
	                if(menuapp.getNomepai()==null){
	             
	                    break;
	                }
                }
                }
             // }
              
        } catch (Exception e) {
             JsfUtil.addErrorMessage(e, "(MontarMenu)");
        }
        return _menuAppItens;
    } 

    /**
     * @return the menuFacade
     */
    public MenuFacade getMenuFacade() {
        return menuFacade;
    }

    /**
     * @param menuFacade the menuFacade to set
     */
    public void setMenuFacade(MenuFacade menuFacade) {
        this.menuFacade = menuFacade;
    }

    /**
     * @return the itens
     */
    private void iniciarMenus() {
    	 List<MenuApp> findAll = getMenuFacade().findAll();
         ArrayList<MenuItemApp> menu = GerarModeloMenuHtml(findAll,null);  
         
         setItens(menu);
         
         NosVazio((ArrayList<MenuItemApp>) itens);
         
        // NosVazio((ArrayList<MenuItemApp>) itens.clone());
    }
    public ArrayList<MenuItemApp> getItens() {
      
        return itens;
    }

    /**
     * @param itens the itens to set
     */
    public void setItens(ArrayList<MenuItemApp> itens) {
        this.itens = itens;
    }

    /**
     * @return the infraUsuarioFacade
     */
    public InfraUsuarioFacade getInfraUsuarioFacade() {
        return infraUsuarioFacade;
    }

    /**
     * @param infraUsuarioFacade the infraUsuarioFacade to set
     */
    public void setInfraUsuarioFacade(InfraUsuarioFacade infraUsuarioFacade) {
        this.infraUsuarioFacade = infraUsuarioFacade;
    }

    /**
     * @return the loginInfo
     */
    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    /**
     * @param loginInfo the loginInfo to set
     */
    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
    
    @PostConstruct
    public void iniciar() {
    	try {
    		if(itens==null) {
        		iniciarMenus();
        		idsessao= JsfUtil.idSessao();
        	}else {
        	    String sessaoAtual =  JsfUtil.idSessao();
        	    if(!sessaoAtual.equalsIgnoreCase(idsessao)) {
        	    	idsessao=sessaoAtual;
        	    	iniciarMenus();
        	    }
        	}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
    	
    }
    private String previousPage = null;

    public void checkF5() {
		if (itens != null) {
			String msg = "";
			UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
			String id = viewRoot.getViewId();
			if (previousPage != null && (previousPage.equals(id))) {
				iniciarMenus();
			}
			previousPage = id;
		}
    }

}
