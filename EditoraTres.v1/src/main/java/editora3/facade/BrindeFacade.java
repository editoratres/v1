package editora3.facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import editora3.entidades.Brinde;
import editora3.entidades.BrindeEntradaItens;
import editora3.entidades.Contrato;
 
 

public class BrindeFacade extends AbstractFacade<Brinde> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BrindeFacade() {
		super(Brinde.class);
		// TODO Auto-generated constructor stub
	}
	 
	@PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	
	public Brinde localizarPorNome(String nome) {
		Brinde c = null;
		
	 TypedQuery<Brinde> createQuery = getEntityManager().createQuery("from Brinde c where c.descricao=:nome",Brinde.class);
	 createQuery.setParameter("nome", nome);
	 List<Brinde> resultList = createQuery.getResultList();
	 if(resultList!=null && !resultList.isEmpty()) {
		 c = resultList.get(0);
	 }
		
		return c;
	}
	
	@Transactional
	public List<Brinde> findAll(String status){
		List<Brinde> ret =null;
		try {
			 TypedQuery<Brinde> createQuery = getEntityManager().createQuery("from Brinde c "+ (status.equalsIgnoreCase("todos") ? "": " where c.quantidade>0 and c.status=TRUE") + " order by c.descricao",Brinde.class);
			 
			 ret = createQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
		
		
		return ret;
	}
	
	@Transactional
	public List<Brinde> findAllDisponivel(){
		
		return findAllDisponivel(null,true);
	}
	
	
	public List<Brinde> findAllDisponivel(Integer CodigoEquipe, boolean exibirSomenteItensComEstoque){
		List<Brinde> ret =null;
		try {
			StringBuilder sql = new StringBuilder();
			sql
			.append("select distinct c from Brinde c ")
			.append(exibirSomenteItensComEstoque ? " inner join fetch c.brindeEstoqueEquipe bee " :"")
			.append("where 1=1 ")
			.append(CodigoEquipe==null ? "" : " and bee.equipeBean.codigo=:codigoEquipe and bee.quantidade>0")
			.append(exibirSomenteItensComEstoque ? " and c.quantidade>0  " :"");
			
			 TypedQuery<Brinde> createQuery = getEntityManager().createQuery(sql.toString() ,Brinde.class);
			
			 if(CodigoEquipe!=null) {
				 createQuery.setParameter("codigoEquipe", CodigoEquipe);
			 }
			 
			 
			 ret = createQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
		
		
		return ret;
	}
	public LazyObjetos<Brinde> findAllDisponivelLazy(Integer codigoEquipe, boolean exibirSomenteItensComEstoque,FiltrosLazyDataModel dataModel){
		LazyObjetos<Brinde> retLazy = new LazyObjetos();  
		List<Brinde> ret =null;
		try {
			StringBuffer sqlcomum = new StringBuffer();
			sqlcomum 
			.append(" from Brinde b , Equipe e ")
			.append(exibirSomenteItensComEstoque ? ", brinde_estoque_equipe bee " :"")
			.append("where bee.equipelbean=e.codigo")
			.append(codigoEquipe==null ? "" : " and bee.equipeBean=:codigoEquipe and bee.quantidade>0")
			.append(exibirSomenteItensComEstoque ? " and b.quantidade>0  " :"");

			StringBuilder sql = new StringBuilder();
			sql
			.append("select distinct b.* ")
			.append(sqlcomum.toString()) ;
			 
			StringBuilder sqlContagem = new StringBuilder();
			sqlContagem
			.append("select count(distinct b.*) ")
			.append(sqlcomum.toString());
			
			List<ParametrosNativosQuery> parametrosNativosQuery = new ArrayList<ParametrosNativosQuery>();
			if(codigoEquipe!=null) {
				ParametrosNativosQuery p1=new ParametrosNativosQuery();
				p1.setNome("codigoEquipe");
				p1.setValor(codigoEquipe);
				parametrosNativosQuery.add(p1);
			}
			
			QueryLazyGerador<Brinde> queryLazyGerador= new QueryLazyGerador<>(getEntityManager(), sql.toString(), sqlContagem.toString(), dataModel,parametrosNativosQuery,Brinde.class,"c");
			retLazy.setLista(queryLazyGerador.getTypedQueryResultado());
			retLazy.setTotalObjetos(queryLazyGerador.getTypedQueryTotalizador());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retLazy;
	}
	
	
	
	@Transactional
	public Integer movimentacoesBrinde(Integer id) {
		 int ret=0;
		 TypedQuery<BrindeEntradaItens> createQuery = getEntityManager().createQuery("from BrindeEntradaItens b where b.brindeBean.codigo =:codigo",BrindeEntradaItens.class);
		 createQuery.setParameter("codigo", id);
		 List<BrindeEntradaItens> resultList = createQuery.getResultList();
		 if(resultList!=null && !resultList.isEmpty()) {
			ret=resultList.size(); 
		 }
		 return ret;
	}
}
