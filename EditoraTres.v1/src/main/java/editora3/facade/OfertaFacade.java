package editora3.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import editora3.entidades.Oferta;
import editora3.entidades.OfertaIten;
 

public class OfertaFacade extends AbstractFacade<Oferta> {

	public OfertaFacade() {
		super(Oferta.class);
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
	public void excluirOfertaItem(OfertaIten ofertaIten) {
		em.remove( em.merge(ofertaIten));
	}

	public Oferta localizarPorNome(String nome) {
		Oferta c = null;

		TypedQuery<Oferta> createQuery = getEntityManager().createQuery("from Oferta c where c.descricao=:nome",
				Oferta.class);
		createQuery.setParameter("nome", nome);
		List<Oferta> resultList = createQuery.getResultList();
		if (resultList != null && !resultList.isEmpty()) {
			c = resultList.get(0);
		}

		return c;
	}

}
