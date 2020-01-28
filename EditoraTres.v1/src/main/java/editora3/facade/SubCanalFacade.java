package editora3.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

 
import editora3.entidades.Subcanal;
 

public class SubCanalFacade extends AbstractFacade<Subcanal> {

	public SubCanalFacade() {
		super(Subcanal.class);
		// TODO Auto-generated constructor stub
	}
	 
	@PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	
	public Subcanal localizarPorNome(String nome) {
		Subcanal c = null;
		
	 TypedQuery<Subcanal> createQuery = getEntityManager().createQuery("from Subcanal c where c.descricao=:nome",Subcanal.class);
	 createQuery.setParameter("nome", nome);
	 List<Subcanal> resultList = createQuery.getResultList();
	 if(resultList!=null && !resultList.isEmpty()) {
		 c = resultList.get(0);
	 }
		
		return c;
	}

}
