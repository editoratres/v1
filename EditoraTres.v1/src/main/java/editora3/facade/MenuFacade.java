package editora3.facade;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import editora3.entidades.MenuApp;
import editora3.util.ManipularString;
 

/**
 *
 * @author Fernando
 */
public class MenuFacade extends AbstractFacade<MenuApp> implements Serializable {

    @PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuFacade() {
        super(MenuApp.class);
    }

    public List<MenuApp> findAll() {
        TypedQuery<MenuApp> createNamedQuery = getEntityManager().createNamedQuery("Menu.findAll", MenuApp.class);

        return createNamedQuery.getResultList();
    }

    public List<MenuApp> findAllMenu(String menuPai) {
        Query createNamedQuery = getEntityManager().createNativeQuery("select * from menu where "
                + (menuPai == null ? "nomePai is null" : "nomepai ='" + menuPai + "'") + " order by nome", MenuApp.class);

        return createNamedQuery.getResultList();
    }

     public Integer getProximoidMenu() {
        Integer ret = 0;
        Query createQuery = getEntityManager().createNativeQuery("select max(id) ultimomenu from menu");

        List resultList = createQuery.getResultList();
        if (!resultList.isEmpty()) {

            String id = resultList.get(0).toString();
            if(ret!=null){
                ret = new Integer(id)+1;
            }

           
        }

        return ret;
    }
    public String getProximoMenu() {
        String ret = "";
        Query createQuery = getEntityManager().createNativeQuery("select max(nome) ultimomenu from menu");

        List resultList = createQuery.getResultList();
        if (!resultList.isEmpty()) {

            ret = resultList.get(0).toString();

            String[] split = ret.split("\\.");
            if (split != null && split.length > 0) {
                String Ultimoindice = split[split.length - 1];

                String proximoIndice = String.valueOf(Integer.valueOf(Ultimoindice) + 1);

                ret = split[0] +"." + split[1]+ "." + ManipularString.Padl(3, proximoIndice, "0");
            }

        }

        return ret;
    }

    @Transactional
    public void  gravarMenu(MenuApp menuApp){
         
        if(menuApp.getId()==null){
           menuApp.setNome(getProximoMenu());
           menuApp.setId(getProximoidMenu());
           em.merge(menuApp);
        }else{
            em.merge(menuApp); 
        }
    } 
    @Transactional
    public void desvincularModulo( Integer idmenu_opcoes){
        
        Query createQuery = getEntityManager().createNativeQuery("delete from menu_opcoes where id=:idmenu_opcoes");
       
        createQuery.setParameter("idmenu_opcoes", idmenu_opcoes);
       
        createQuery.executeUpdate();

         
    
    }
     @Transactional
    public void  excluirMenu(Integer idmenuApp){
          Query createQuery = getEntityManager().createNativeQuery("delete from menu where id=" + idmenuApp);
          createQuery.executeUpdate();

    }
    public MenuApp findidMenu(Integer idmenu) {
        Query createNamedQuery = getEntityManager().createNativeQuery("select * from menu where id=" + idmenu, MenuApp.class);
        MenuApp menuApp = null;
        List<MenuApp> resultList = createNamedQuery.getResultList();
        if (resultList != null) {
            menuApp = resultList.get(0);
        }
        return menuApp; 
    }

}
