package editora3.facade;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import editora3.entidades.InfraModulo;
import editora3.entidades.InfraTipoPerfilDet;
import editora3.entidades.InfraTipoPerfilUsuario;
import editora3.entidades.InfraUsuario;
import editora3.entidades.InfraUsuarioPerfil;
import editora3.entidades.Representante;
import editora3.util.JsfUtil;


/**
 *
 * @author Fernando
 */

public class InfraUsuarioFacade extends AbstractFacade<InfraUsuario> implements Serializable {

    @PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;
    
    @Inject
    private editora3.facade.InfraModulosFacade InfraModulosFacade;

	//private InfraTipoPerfilUsuario InfraTipoPerfilUsuario infraTipoPerfilUsuario;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InfraUsuarioFacade() {
        super(InfraUsuario.class);
    }
    
    @Transactional
    public boolean EditarPerfil(InfraUsuario infraUsuario ){
        boolean ret=false;
        try {
                  
            em.merge(infraUsuario);
            
            ret=true;
        } catch (Exception e) {
            JsfUtil.addErrorMessage("(EditarPerfil) Falha na requisição" + e.getMessage());
        }
        return ret;
    }
    
    @Transactional
    public boolean AlterarSenha(Integer usuario ,String senha ){
        boolean ret=false;
        try {
                  String SQL = "update " +
"  INFRA_USUARIO set senha=:senha " +
"WHERE " +
" idusuario =:idusuario";
            Query q =  em.createNativeQuery(SQL);
            q.setParameter("idusuario", usuario);
            q.setParameter("senha", senha);
          
            q.executeUpdate();
            ret=true;
        } catch (Exception e) {
            JsfUtil.addErrorMessage("(AlterarSenha) Falha na requisição" + e.getMessage());
        }
        return ret;
    }
    
     public InfraUsuario LocalizarPeloIDUsuario( String idusuario  ){
        InfraUsuario ret=null;
        try {
                  String SQL = "SELECT " +
"  INFRA_USUARIO.* " +
"FROM " +
"  INFRA_USUARIO " +
"WHERE " +
" INFRA_USUARIO.idusuario =:idusuario ";
            Query q =  em.createNativeQuery(SQL,InfraUsuario.class);
            q.setParameter("idusuario", Integer.valueOf(idusuario));
            
            //q.setParameter("IDCONTRATADO", IdContratado);
            ret =  (InfraUsuario) q.getSingleResult();
            
        } catch (NoResultException e) {    
            ret=null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage("(LocalizarPeloIDUsuario) Falha na requisição" + e.getMessage());
        }
        return ret;
    }
    /* public Representante LocalizarRepresentantePeloUsuario(String idusuario){
         Representante ret=null;
         try {
             
             String SQL = "SELECT " +
"  INFRA_USUARIO.* " +
"FROM " +
"  INFRA_USUARIO " +
"WHERE " +
" INFRA_USUARIO.idusuario =:idusuario ";
            Query q =  em.createNativeQuery(SQL,InfraUsuario.class);
            q.setParameter("idusuario", Integer.valueOf(idusuario));
            
            //q.setParameter("IDCONTRATADO", IdContratado);
            InfraUsuario retusuario =  (InfraUsuario) q.getSingleResult();
             
            if(retusuario!=null && !retusuario.getRepresentanteCollection().isEmpty()){
                ret = retusuario.getRepresentanteCollection().iterator().next();
            }
         } catch (Exception e) {
              JsfUtil.addErrorMessage("(LocalizarRepresentantePeloUsuario) Falha na requisição" + e.getMessage());
         }
         return ret;
     }
    */
     public InfraUsuario LocalizarPeloUsuario( String usuario  ){
        InfraUsuario ret=null;
        try {
                  String SQL = "SELECT " +
"  INFRA_USUARIO.* " +
"FROM " +
"  INFRA_USUARIO " +
"WHERE " +
" INFRA_USUARIO.usuario =:usuario ";
            Query q =  em.createNativeQuery(SQL,InfraUsuario.class);
            q.setParameter("usuario", usuario);
            
            //q.setParameter("IDCONTRATADO", IdContratado);
            ret =  (InfraUsuario) q.getSingleResult();
            
        } catch (NoResultException e) {    
            ret=null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage("(LocalizarPeloUsuario) Falha na requisição" + e.getMessage());
        }
        return ret;
    }
    @Transactional
    public void signIN(InfraUsuario administrador) {
    	administrador.setAtivo(true);
    	administrador.setUsuarioadm(true);
    	em.persist(administrador);
    	
    
     
    	editora3.entidades.InfraTipoPerfilUsuario perfilAdministrador = gerarPerfilAdministrador();
    	em.persist(perfilAdministrador);
    	 
    	InfraUsuarioPerfil infraUsuarioPerfil = new InfraUsuarioPerfil();
    	infraUsuarioPerfil.setInfraTipoPerfilUsuario(perfilAdministrador);
    	infraUsuarioPerfil.setInfraUsuario(administrador);
    	
    	em.persist(infraUsuarioPerfil);
    	
    	
    }
    public InfraTipoPerfilUsuario gerarPerfilAdministrador(){
    	
    	InfraTipoPerfilUsuario infraTipoPerfilUsuario = new InfraTipoPerfilUsuario();
    	infraTipoPerfilUsuario.setAtivo(true);
    	infraTipoPerfilUsuario.setUsuarioadm(true);
    	infraTipoPerfilUsuario.setTipoperfil("Administrador");
        try {
        	Collection<InfraTipoPerfilDet> _InfraTipoPerfilDetList=new ArrayList<>();       
        	
            List<InfraModulo> modulos = InfraModulosFacade.findAll();
            for (int i = 0; i < modulos.size(); i++) {
                InfraTipoPerfilDet _InfraTipoPerfilDet = new InfraTipoPerfilDet();
               // _InfraTipoPerfilDet.setIdtipoperfildet(i);                
                _InfraTipoPerfilDet.setInfraModulo(modulos.get(i));
                _InfraTipoPerfilDet.setAcessar(true);
                _InfraTipoPerfilDet.setCriar(true);
                _InfraTipoPerfilDet.setEditar(true);
                _InfraTipoPerfilDet.setExcluir(true);
                _InfraTipoPerfilDet.setInfraTipoPerfilUsuario(infraTipoPerfilUsuario);
                _InfraTipoPerfilDetList.add(_InfraTipoPerfilDet);
            }
            infraTipoPerfilUsuario.setInfraTipoPerfilDets(_InfraTipoPerfilDetList);
            
        } catch (Exception e) {
            JsfUtil.addErrorMessage("(getModulos) Falha na requisição\n\n" + e.getMessage());
        }
       
       return infraTipoPerfilUsuario;
    }
    public InfraUsuario Localizar( String usuario ,String senha ){
        InfraUsuario ret=null;
        try {
                  String SQL = "SELECT " +
"  INFRA_USUARIO.* " +
"FROM " +
"  INFRA_USUARIO " +
"WHERE " +
" INFRA_USUARIO.usuario =:usuario and INFRA_USUARIO.senha=:senha";
            Query q =  em.createNativeQuery(SQL,InfraUsuario.class);
            q.setParameter("usuario", usuario);
            q.setParameter("senha", senha);
            //q.setParameter("IDCONTRATADO", IdContratado);
            ret =  (InfraUsuario) q.getSingleResult();
            
        } catch (NoResultException e) {    
            ret=null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage("(Localizar) Falha na requisição" + e.getMessage());
        }
        return ret;
    }
    public List<InfraModulo> getModulo(String NomeClasse) {
    	List<InfraModulo>  ret= null;
    	Query q =  em.createNativeQuery("select * from infra_modulos where nomeclasse=:nomeclasse",InfraModulo.class);
    	q.setParameter("nomeclasse", NomeClasse);
        //q.setParameter("IDCONTRATADO", IdContratado);
        ret =  q.getResultList();
    	
    	
    	return ret;
    }
    public List<InfraTipoPerfilDet> LocalizarRecursoClasse(Integer Usuario,String NomeClasse){
         List<InfraTipoPerfilDet> ret=null;
         try {
             String SQL = "SELECT " +
"  infra_tipo_perfil_det.* " +
" FROM " +
"  infra_tipo_perfil_det, " +
"  infra_tipo_perfil_usuario, " +
"  infra_usuario, " +
"  infra_usuario_perfil, " +
"  infra_modulos " +
" WHERE " +
"  infra_tipo_perfil_det.idtipoperfil = infra_tipo_perfil_usuario.idtipoperfil AND" +
"  infra_tipo_perfil_det.idmodulo = infra_modulos.idmodulo AND" +
"  infra_tipo_perfil_usuario.idtipoperfil = infra_usuario_perfil.idtipoperfil AND" +
"  infra_usuario_perfil.idusuario = infra_usuario.idusuario and " +
" infra_usuario.idusuario =:idusuario and infra_modulos.nomeclasse=:nomeclasse ";
            Query q =  em.createNativeQuery(SQL,InfraTipoPerfilDet.class);
            q.setParameter("idusuario", Usuario);
            q.setParameter("nomeclasse", NomeClasse);
            //q.setParameter("IDCONTRATADO", IdContratado);
            ret =  q.getResultList();
             
         } catch (Exception e) {
              JsfUtil.addErrorMessage("(LocalizarRecursoClasse) Falha na requisição" + e.getMessage());
         }
         return ret;
    }
    
    public  List<InfraTipoPerfilDet> LocalizarRecurso(Integer Usuario,String Recurso){
         List<InfraTipoPerfilDet> ret=null;
         try {
             String SQL = "SELECT " +
"  infra_tipo_perfil_det.* " +
"FROM " +
"  infra_tipo_perfil_det, " +
"  infra_tipo_perfil_usuario, " +
"  infra_usuario, " +
"  infra_usuario_perfil, " +
"  infra_modulos " +
" WHERE " +
"  infra_tipo_perfil_det.idtipoperfil = infra_tipo_perfil_usuario.idtipoperfil AND" +
"  infra_tipo_perfil_det.idmodulo = infra_modulos.idmodulo AND" +
"  infra_tipo_perfil_usuario.idtipoperfil = infra_usuario_perfil.idtipoperfil AND" +
"  infra_usuario_perfil.idusuario = infra_usuario.idusuario and " +
" infra_usuario.idusuario =:idusuario and infra_modulos.modulo=:modulo ";
            Query q =  em.createNativeQuery(SQL,InfraTipoPerfilDet.class);
            q.setParameter("idusuario", Usuario);
            q.setParameter("modulo", Recurso);
            //q.setParameter("IDCONTRATADO", IdContratado);
            ret =  q.getResultList();
             
         } catch (Exception e) {
              JsfUtil.addErrorMessage("(LocalizarRecurso) Falha na requisição" + e.getMessage());
         }
         return ret;
    }
    
    public List<InfraUsuario> listarUsuario() {
        List<InfraUsuario> ret = null;

        try {
            String SQL = "SELECT "
                    + "  INFRA_USUARIO.* "
                    + "FROM "
                    + "  INFRA_USUARIO "
                    + "WHERE "
                    + " INFRA_USUARIO.ativo=true";
            Query q = em.createNativeQuery(SQL, InfraUsuario.class);

            ret = q.getResultList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage("(listarUsuario) Falha na requisição" + e.getMessage());
        }

        return ret;
    }


	public BigInteger contarUsuarios() {
		BigInteger ret = BigInteger.ZERO;
	
	    try {
	        String SQL = "SELECT count(*) total "
	                + "FROM "
	                + "  INFRA_USUARIO "
	                ;
	        Query q = em.createNativeQuery(SQL);
	
	        ret = (BigInteger) q.getSingleResult();
	    } catch (Exception e) {
	        JsfUtil.addErrorMessage("(contarUsuarios) Falha na requisição" + e.getMessage());
	    }
	
	    return ret;
	}
	public Integer totalEquipeComUsuario(Integer infrausuariobean) {
		Integer ret = 0;
		try {
			Query createQuery = getEntityManager()
					.createNativeQuery("select count(*) from equipe where infrausuariobean =:infrausuariobean"
									);
			 
				createQuery.setParameter("infrausuariobean", infrausuariobean);
			 

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
