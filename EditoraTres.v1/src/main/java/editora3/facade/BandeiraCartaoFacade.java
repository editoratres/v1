package editora3.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import editora3.entidades.BandeiraCartao;
import editora3.entidades.Canal;
 

public class BandeiraCartaoFacade extends AbstractFacade<BandeiraCartao> {

	public BandeiraCartaoFacade() {
		super(BandeiraCartao.class);
		// TODO Auto-generated constructor stub
	}
	 
	@PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	
	public BandeiraCartao localizarPorNome(String nome) {
		BandeiraCartao c = null;
		
	 TypedQuery<BandeiraCartao> createQuery = getEntityManager().createQuery("from BandeiraCartao c where c.descricao=:nome",BandeiraCartao.class);
	 createQuery.setParameter("nome", nome);
	 List<BandeiraCartao> resultList = createQuery.getResultList();
	 if(resultList!=null && !resultList.isEmpty()) {
		 c = resultList.get(0);
	 }
		
		return c;
	}
	
	@Transactional
	public List<BandeiraCartao> findAll(String  Status){
		
		 TypedQuery<BandeiraCartao> createQuery = getEntityManager().createQuery("from BandeiraCartao c" +(!Status.equalsIgnoreCase("todos")? " where c.status=TRUE":""),BandeiraCartao.class);
		  
		 List<BandeiraCartao> ret = createQuery.getResultList();
		
		 return ret;
	}

}
