package editora3.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import editora3.entidades.Relatorio;

public class ContratoFacade extends AbstractFacade<Relatorio> {

	public ContratoFacade() {
		super(Relatorio.class);
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext(unitName = "EditoraTres.v1")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}

	public Relatorio localizarPorNome(String identificacao) {
		Relatorio c = null;

		TypedQuery<Relatorio> createQuery = getEntityManager().createQuery("from Relatorio c where c.identificacao=:identificacao",
				Relatorio.class);
		createQuery.setParameter("identificacao", identificacao);
		List<Relatorio> resultList = createQuery.getResultList();
		if (resultList != null && !resultList.isEmpty()) {
			c = resultList.get(0);
		}

		return c;
	}

}
