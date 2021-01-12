package editora3.facade;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import editora3.entidades.Auditoria;
import editora3.entidades.Brinde;
import editora3.entidades.InfraModulo;
import editora3.entidades.InfraUsuario;
import editora3.seguranca.LoginInfo;

public class AuditoriaFacade extends AbstractFacade<Auditoria> implements Serializable {
	@Inject
    private LoginInfo lusuario;
	@Inject
    private InfraUsuarioFacade infraUsuarioFacade;
	
	public AuditoriaFacade() {
		super(Auditoria.class);
		// TODO Auto-generated constructor stub
	}
	@PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	@Transactional
	public void auditar(String NomeClasse , String Acao, String detalhe) {
		Integer idusuario = lusuario.getUsuario_logado().getIdusuario();
		auditar(NomeClasse,Acao, detalhe,idusuario,"Código : ");
	}
	@Transactional
	public void auditar(String NomeClasse , String Acao, String detalhe, String detalhePadrao) {
		Integer idusuario = lusuario.getUsuario_logado().getIdusuario();
		auditar(NomeClasse,Acao, detalhe,idusuario,detalhePadrao);
	}
	
	@Transactional
	public void auditar(String NomeClasse ,String Acao, String detalhe, Integer idusuario, String detalhePadrao) {
		Auditoria auditoria = new Auditoria();
		 if(Acao.equalsIgnoreCase("editar")){
             auditoria.setEditar(true);
        }else if(Acao.equalsIgnoreCase("criar")){
            auditoria.setCriar(true);
        }else if(Acao.equalsIgnoreCase("excluir")){
            auditoria.setExcluir(true);
        }else if(Acao.equalsIgnoreCase("acessar")){            
            auditoria.setAcessar(true);
        }
		 InfraUsuario localizarPeloIDUsuario = infraUsuarioFacade.LocalizarPeloIDUsuario(idusuario.toString());
		 
		 List<InfraModulo> modulo = infraUsuarioFacade.getModulo(NomeClasse);
         if(modulo!=null && !modulo.isEmpty()) {
      	   auditoria.setIdmodulo(modulo.get(0));
      	   auditoria.setIdusuario(localizarPeloIDUsuario);
      	   auditoria.setDatahora(new Timestamp(new Date().getTime()));
         } 
		 
		 auditoria.setDetalhe((detalhePadrao!=null ? detalhePadrao : "") + detalhe);
		 em.persist(auditoria);	
		 
		
	}
	public LazyObjetos<Auditoria> findAllLazy(Integer idusuario,FiltrosLazyDataModel dataModel , Timestamp inicio, Timestamp termino){
		LazyObjetos<Auditoria> retLazy = new LazyObjetos();  
		List<Auditoria> ret =null;
		try {
			StringBuffer sqlcomum = new StringBuffer();
			sqlcomum 
			.append(" from auditoria a, infra_usuario iu, infra_modulos im ")
			.append(" where iu.idusuario=a.idusuario and im.idmodulo=a.idmodulo and a.datahora>=:inicio and a.datahora<=:termino " )
			.append(idusuario!=null ? " and a.idusuario=:idusuario  " : "");

			StringBuilder sql = new StringBuilder();
			sql
			.append("select  a.* ")
			.append(sqlcomum.toString());
			 
			 
			StringBuilder sqlContagem = new StringBuilder();
			sqlContagem
			.append("select count(distinct a.*) ")
			.append(sqlcomum.toString());
			
			List<ParametrosNativosQuery> parametrosNativosQuery = new ArrayList<ParametrosNativosQuery>();
			if(idusuario!=null) {
				ParametrosNativosQuery p1=new ParametrosNativosQuery();
				p1.setNome("idusuario");
				p1.setValor(idusuario);
				parametrosNativosQuery.add(p1);
			}
			
			ParametrosNativosQuery p2=new ParametrosNativosQuery();
			p2.setNome("inicio");
			p2.setValor(inicio);
			parametrosNativosQuery.add(p2);
			
			ParametrosNativosQuery p3=new ParametrosNativosQuery();
			p3.setNome("termino");
			p3.setValor(termino);
			parametrosNativosQuery.add(p3);
			 
			QueryLazyGerador<Auditoria> queryLazyGerador= new QueryLazyGerador<>(getEntityManager(), sql.toString(), sqlContagem.toString(), dataModel,parametrosNativosQuery,Auditoria.class,"a");
			retLazy.setLista(queryLazyGerador.getTypedQueryResultado());
			retLazy.setTotalObjetos(queryLazyGerador.getTypedQueryTotalizador());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retLazy;
	}

}