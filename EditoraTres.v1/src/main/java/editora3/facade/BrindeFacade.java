package editora3.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import editora3.entidades.Brinde;
import editora3.entidades.BrindeEntradaItens;
 
 

public class BrindeFacade extends AbstractFacade<Brinde> {

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
			 TypedQuery<Brinde> createQuery = getEntityManager().createQuery("select distinct c from Brinde C" +
			 (exibirSomenteItensComEstoque ? " inner join fetch c.brindeEstoqueEquipe bee  where c.quantidade>0 " : " where 1=1 ") +
			 (CodigoEquipe==null ? "" : " where bee.equipeBean.codigo=:codigoEquipe and bee.quantidade>0")
			 
			 ,Brinde.class);
			 if(CodigoEquipe!=null) {
			 createQuery.setParameter("codigoEquipe", CodigoEquipe);
			 }
			 ret = createQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
		
		
		return ret;
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
