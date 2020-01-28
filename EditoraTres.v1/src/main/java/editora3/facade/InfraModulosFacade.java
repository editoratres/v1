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

import editora3.entidades.InfraModulo;


/**
 *
 * @author Fernando
 */

public class InfraModulosFacade extends AbstractFacade<InfraModulo> implements Serializable{

    @PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InfraModulosFacade() {
        super(InfraModulo.class);
    }
   
    public List<InfraModulo> findAllNaoVinculados() {
         
        List<InfraModulo> ret = null;
        
        Query createNativeQuery = em.createNativeQuery("select * from infra_modulos where not idmodulo in " +
                "(select idmodulo from menu_opcoes)",InfraModulo.class);
        
        ret = createNativeQuery.getResultList();
        
        return ret;
       
    }
     public List<InfraModulo> findModulosVinculado(Integer idmodulo) {
         
        List<InfraModulo> ret = null;
        
        Query createNativeQuery = em.createNativeQuery("select im.* from infra_modulos im, menu_opcoes mo " +
" where   im.idmodulo = mo.idmodulo and im.idmodulo="+idmodulo ,InfraModulo.class);
        
        ret = createNativeQuery.getResultList();
        
        return ret;
       
    }

}
