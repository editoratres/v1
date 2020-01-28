package editora3.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

 
import editora3.entidades.Produto;

public class ProdutoFacade extends AbstractFacade<Produto> {

	public ProdutoFacade() {
		super(Produto.class);
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext(unitName = "EditoraTres.v1")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}

	public Produto localizarPorNome(String nome) {
		Produto c = null;

		TypedQuery<Produto> createQuery = getEntityManager().createQuery("from Produto c where c.descricao=:nome",
				Produto.class);
		createQuery.setParameter("nome", nome);
		List<Produto> resultList = createQuery.getResultList();
		if (resultList != null && !resultList.isEmpty()) {
			c = resultList.get(0);
		}

		return c;
	}

}
