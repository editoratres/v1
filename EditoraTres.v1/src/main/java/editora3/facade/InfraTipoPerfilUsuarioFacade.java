package editora3.facade;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import editora3.entidades.InfraTipoPerfilUsuario;


/**
 *
 * @author Fernando
 */

public class InfraTipoPerfilUsuarioFacade extends AbstractFacade<InfraTipoPerfilUsuario> implements Serializable{

    @PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InfraTipoPerfilUsuarioFacade() {
        super(InfraTipoPerfilUsuario.class);
    }
     public InfraTipoPerfilUsuario Localizar(String NomePerfil) {
        InfraTipoPerfilUsuario ret =null; 
         CriteriaBuilder cb = em.getCriteriaBuilder();
        // Metamodel m = em.getMetamodel();
         //EntityType<InfraUsuario> infra_ = m.entity(InfraUsuario.class);
         CriteriaQuery<InfraTipoPerfilUsuario> cq = cb.createQuery(InfraTipoPerfilUsuario.class);
         Root<InfraTipoPerfilUsuario> pet = cq.from(InfraTipoPerfilUsuario.class);
         cq.where(cb.equal(pet.get("tipoperfil"), NomePerfil));
         TypedQuery<InfraTipoPerfilUsuario> createQuery = em.createQuery(cq);
         try {
            ret = createQuery.getSingleResult();                
         } catch (Exception ex) {
            ret = null;
         }
        return ret;//getEntityManager().createQuery(cq).getResultList();
        
        
         
    }
     public Integer totalUsuariosComOPerfil(Integer idtipoperfil) {
 		Integer ret = 0;
 		try {
 			Query createQuery = getEntityManager()
 					.createNativeQuery("select count(*) from infra_usuario_perfil where idtipoperfil =:idtipoperfil"
 									);
 			 
 				createQuery.setParameter("idtipoperfil", idtipoperfil);
 			 

 			Object singleResult = createQuery.getSingleResult();
 			if(singleResult!=null) {
 				ret = Integer.valueOf(singleResult.toString());
 			}

 		} catch (Exception e) {
 			e.printStackTrace();
 			// TODO: handle exception
 		}

 		return ret;

 	}
}
