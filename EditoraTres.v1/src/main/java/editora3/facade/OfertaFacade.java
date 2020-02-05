package editora3.facade;

import java.util.Iterator;
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

	public OfertaIten localizarOfertaItemPorEdicao(String edicao, Integer codigooferta) {
		OfertaIten c = null;

		TypedQuery<OfertaIten> createQuery = getEntityManager().createQuery("from OfertaIten oi where oi.edicao=:edicao and oi.oferta.codigo=:codigooferta",
				OfertaIten.class);
		createQuery.setParameter("edicao", edicao);
		createQuery.setParameter("codigooferta", codigooferta);
		List<OfertaIten> resultList = createQuery.getResultList();
		if (resultList != null && !resultList.isEmpty()) {
			c = resultList.get(0);
		}

		return c;
	}
 
	@Transactional
	public List<Oferta> findAllLazy(String status){
		List<Oferta> ret = null;
		try {
		
			TypedQuery<Oferta> createQuery = getEntityManager().createQuery("select o from Oferta o"+ 
			(!status.equalsIgnoreCase("todos") ? " inner join fetch o.ofertaItens io where o.ativa=TRUE and io.ativa=TRUE ":"") + " order by o.produtoBean.descricao"  ,
					Oferta.class);
			 
			ret = createQuery.getResultList();
			for (Iterator iterator = ret.iterator(); iterator.hasNext();) {
				Oferta oferta = (Oferta) iterator.next();
				oferta.getOfertaItens().size();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return ret;
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
