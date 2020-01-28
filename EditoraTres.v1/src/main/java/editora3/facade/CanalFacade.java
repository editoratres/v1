package editora3.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import editora3.entidades.Canal;
 

public class CanalFacade extends AbstractFacade<Canal> {

	public CanalFacade() {
		super(Canal.class);
		// TODO Auto-generated constructor stub
	}
	 
	@PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	
	public Canal localizarPorNome(String nome) {
		Canal c = null;
		
	 TypedQuery<Canal> createQuery = getEntityManager().createQuery("from Canal c where c.descricao=:nome",Canal.class);
	 createQuery.setParameter("nome", nome);
	 List<Canal> resultList = createQuery.getResultList();
	 if(resultList!=null && !resultList.isEmpty()) {
		 c = resultList.get(0);
	 }
		
		return c;
	}

}
